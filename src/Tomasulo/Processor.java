package Tomasulo;

import java.util.ArrayList;
import java.util.Collections;

public class Processor {
	public Program program;
	int cycle;
	public int pc;
	ReservationStation addStation;
	ReservationStation mulStation;
	LoadBuffers lb;
	StoreBuffers sb;
	RegisterFile rf;
	Bus bus;
	Memory memory;
	String issueSummary = "issue: \n";
	String executeSummary = "execute: \n";
	String publishSummary = "publish: \n";
	String busSummary = "bus: \n";

	int addlat = 2;
	int mullat = 2;
	int sublat = 2;
	int divlat = 2;
	int ldlat = 2;
	int stlat = 2;

	public Processor(Program program) {
		this.program = program;
		bus = new Bus();
		cycle = 1;
		pc = 0;
		addStation = new ReservationStation(3, StationType.ADD, 3);
		mulStation = new ReservationStation(2, StationType.MUL, 3);
		sb = new StoreBuffers(3, 2); // We are assuming CACHE takes 2 cycles
		lb = new LoadBuffers(3, 2);
		rf = new RegisterFile();
		memory = new Memory(2048);
	}

	public Processor(Program program, int addsub, int muldiv, int ld, int str) {
		this.program = program;
		bus = new Bus();
		cycle = 1;
		pc = 0;
		addStation = new ReservationStation(3, StationType.ADD, addsub);
		mulStation = new ReservationStation(2, StationType.MUL, muldiv);
		sb = new StoreBuffers(3, str); // We are assuming CACHE takes 2 cycles
		lb = new LoadBuffers(3, ld);
		rf = new RegisterFile();
		memory = new Memory(2048);
	}

	public Processor(Program program, int addlat, int mullat, int sublat, int divlat, int ldlat, int stlat) {
		this.program = program;
		bus = new Bus();
		cycle = 1;
		pc = 0;
		addStation = new ReservationStation(3, StationType.ADD, addlat);
		mulStation = new ReservationStation(2, StationType.MUL, mullat);
		sb = new StoreBuffers(3, stlat); // We are assuming CACHE takes 2 cycles
		lb = new LoadBuffers(3, ldlat);
		rf = new RegisterFile();
		memory = new Memory(2048);

		this.addlat = addlat;
		this.mullat = mullat;
		this.sublat = sublat;
		this.divlat = divlat;
		this.ldlat = ldlat;
		this.stlat = stlat;
	}

	public void resetBus() {
		bus.sourceID = null;
	}

	public boolean next1() {

		issueSummary = "issue: \n";
		executeSummary = "execute: \n";
		publishSummary = "publish: \n";
		busSummary = "bus: \n";

		resetBus();

		if (pc >= program.getInstructionQueue().length && allStationsEmpty())
			return false;
		checkExecution();
		boolean issueSuccessful = tryIssue();
		checkPublish();
		checkBus();
		System.out.println(printCycle());
		return issueSuccessful;	
	}
	
	public boolean next2(boolean issueSuccessful) {


		if (issueSuccessful)
			pc++;
		cycle++;
		return true;
	}

	public boolean allStationsEmpty() {
		boolean ret = true;
		ret &= reservationEmpty(addStation);
		ret &= reservationEmpty(mulStation);
		ret &= loadBuffersEmpty();
		ret &= storeBuffersEmpty();
		return ret;
	}

	public boolean storeBuffersEmpty() {
		int c = 0;
		for (StoreBuffer res : sb.getStation())
			if (res.isBusy())
				c++;
		return c == 0;
	}

	public boolean loadBuffersEmpty() {
		int c = 0;
		for (LoadBuffer res : lb.getStation())
			if (res.isBusy())
				c++;
		return c == 0;
	}

	public boolean reservationEmpty(ReservationStation station) {
		int c = 0;
		for (Reservation res : station.getStation())
			if (res.isBusy())
				c++;
		return c == 0;
	}

	// checks if any reservation needs data on bus and assign it
	public void checkBus() {
		// Check if any slot need any thing from the BUS
		if (bus.sourceID == null) {
			return;
		}

		ALUStationTakeBus(0);
		ALUStationTakeBus(1);
		StoreBufferTakeBus();
	}

	public void ALUStationTakeBus(int x) {
		ReservationStation curr = (x == 0) ? addStation : mulStation;
		for (Reservation res : curr.getStation()) {
			if (res.getQj() != null && res.getQj().equals(bus.sourceID)) {
				// Now i had something i need from the bus at Qj for this reservation
				res.setQj(null);
				res.setVj(bus.value);
			}
			if (res.getQk() != null && res.getQk().equals(bus.sourceID)) {
				// Now i had something i need from the bus at Qk for this reservation
				res.setQk(null);
				res.setVk(bus.value);
			}
		}
	}

	public void StoreBufferTakeBus() {
		for (StoreBuffer res : sb.getStation()) {
			if (res.getQ() != null && res.getQ().equals(bus.sourceID)) {
				// Now i had something i need from the bus at Q for this storeBuffer
				res.setQ(null);
				res.setV(bus.value);
			}
		}
	}

	public void getFinished(ArrayList<Object> finishedSlots, int x) // Load Add Mul
	{
		ReservationStation curr = (x == 0) ? addStation : ((x == 1) ? mulStation : null);
		if (curr != null) {
			for (Reservation res : curr.getStation()) {
				int positionInProgram = res.index;
				if (positionInProgram == -1)
					continue;
				if (program.getInstructionQueue()[res.index].getEndExec() <= cycle && res.busy == true) {
					// This one can be considered as a finsihed instruction
					finishedSlots.add(res);
				}
			}

		} else {
			for (LoadBuffer lodB : lb.getStation()) {
				int positionInProgram = lodB.index;

				if (positionInProgram == -1)
					continue;

				if (program.getInstructionQueue()[lodB.index].getEndExec() <= cycle && lodB.busy == true) {
					finishedSlots.add(lodB);
				}
			}
		}

	}

	// checks if any instruction finished execution and publishes result on bus
	public void checkPublish() {
		// Loop over all the Stations
		// If any instruction in any station has endExec == currCycle:
		// for each instruction loop over all other instructions(stations count
		// dependencies (store + add + mul) then get Max)
		checkFinishedStores();
		ArrayList<Object> finishedSlots = new ArrayList<Object>();
		for (int i = 0; i < 3; ++i) {
			getFinished(finishedSlots, i);// return arrayList of reservations
		}

		int max = Integer.MIN_VALUE; // mx
		ArrayList<Integer> dependencies = new ArrayList<Integer>();
		for (Object x : finishedSlots) // Count in the loop
		{
			if (x.getClass().getSimpleName().equals("Reservation")) // pc instruction has station of the this finiished
																	// ++
			{
				int cnt = 0;
				cnt = (countDependencies(((Reservation) x).getID()));
				// my type is Reservation
				Op operation = ((Reservation) x).getOp();
				if (pc < program.getInstructionQueue().length) {
					InstructionType needToBeAdded = program.getInstructionQueue()[pc].getInstructionType();
					if (needToBeAdded == InstructionType.ADD || needToBeAdded == InstructionType.SUB) {
						if (operation == Op.ADD || operation == Op.SUB) {
							// We are on the same type
							cnt += (addStation.size >= addStation.getStation().length) ? 1 : 0;
						}
					}
					if (needToBeAdded == InstructionType.MUL || needToBeAdded == InstructionType.DIV) {
						if (operation == Op.MUL || operation == Op.DIV) {
							// We are on the same type
							cnt += (mulStation.size >= mulStation.getStation().length) ? 1 : 0;
						}
					}
				}

				dependencies.add(cnt);
			} else {
				int cnt = (countDependencies(((LoadBuffer) x).getQ()));
				if (pc < program.getInstructionQueue().length) {
					InstructionType needToBeAdded = program.getInstructionQueue()[pc].getInstructionType();
					if (needToBeAdded == InstructionType.LOAD) {
						cnt += (lb.size >= lb.getStation().length) ? 1 : 0;
					}

				}
				dependencies.add(cnt);

			}

		}

		if (dependencies.isEmpty()) {

//			System.out.println("no finished slots");
			publishSummary += " No Instructions are publishing on the bus ";

			return;
		}

		// Get Max from the count
		Integer maxVal = Collections.max(dependencies);
		Integer maxIdx = dependencies.indexOf(maxVal);

		// Now iam Published

		publish(finishedSlots.get(maxIdx));

		publishSummary += "Dependencies " + maxVal + "\n";

	}

	public void checkFinishedStores() {
		for (StoreBuffer b : sb.getStation())
			if (program.getInstructionQueue()[b.index].endExec == cycle)
				b.setBusy(false);
	}

	public void publish(Object x) {

		String operation = (x.getClass().getSimpleName().equals("Reservation")) ? ((Reservation) x).getOp() + "" : "LD";
		/*
		 * This One Made the 4 targets 1- Write on BUS (Value and ID) 2- Nullify the
		 * Register File if possible and write data there 3- Busy False 4- size--
		 * 
		 */
		getResult(operation, x);

	}

	public void getResult(String op, Object slot) {

		String rd = "";
		ReservationID id = null;
		double result = 0;
		switch (op) {
		case "LD":
			result = memory.getArr()[((LoadBuffer) slot).getA()];
			// check on Register File write it there and nullify the tag if no one has
			// overriden it
			// ABDOU ASK HIM ABOUT THIS FIRST
			// ASSUMPTION THE LOAD INSTRUCTIONS ALWAYS LOAD IN FLOATINGPOINT REGISTER

			// now set the result in the register file and check if this register is now
			// done

			lb.size--; // Decrease Size of the LOAD BUFFERS as this one is saaying BYE BYE
			rd = program.getInstructionQueue()[((LoadBuffer) slot).index].getRd();
			break;

		case "ADD":
			addStation.size--;
			// Set

			result = ((Reservation) slot).getVj() + ((Reservation) slot).getVk();
			rd = program.getInstructionQueue()[((Reservation) slot).index].getRd();
			break;
		case "SUB":
			addStation.size--;
			result = ((Reservation) slot).getVj() - ((Reservation) slot).getVk();
			rd = program.getInstructionQueue()[((Reservation) slot).index].getRd();
			break;
		case "MUL":
			mulStation.size--;
			result = ((Reservation) slot).getVj() * ((Reservation) slot).getVk();
			rd = program.getInstructionQueue()[((Reservation) slot).index].getRd();
			break;
		case "DIV":
			mulStation.size--;
			result = ((Reservation) slot).getVj() / ((Reservation) slot).getVk();
			rd = program.getInstructionQueue()[((Reservation) slot).index].getRd();
			break;
		}

		// Make the slot busy back to false
		if (slot.getClass().getSimpleName().equals("Reservation") == true) {
			((Reservation) slot).setBusy(false);
			id = ((Reservation) slot).ID;
		} else {
			id = ((LoadBuffer) slot).Q;
			((LoadBuffer) slot).setBusy(false);
		}

		// Now i have the operation Result and the RD of the slot that will be removed

		// GET the registerIndex
		int targetIdx = getRegisterIndex(rd);

		// set the result value in the register file now (Trash because if the register
		// need something else in Q overriden it will never be used but i will put it
		// anyways)
		rf.setValueFloating(targetIdx, result);

		// check over the Q of the targetRegister in RF if the Q is same as my slot then
		// it will be nullified otherwise DONT TOUCH ART
		if (rf.getQiFloating(targetIdx).equals(id))
			rf.setQiFloating(targetIdx, null);

		// FINALLYY PUT ON THE BUS THE VALUE OF RESULT + ID OF SLOT
		bus.setValue(result);
		bus.sourceID = id;

		publishSummary += " instruction " + ((slot.getClass().getSimpleName().equals("Reservation") == true)
				? program.getInstructionQueue()[((Reservation) slot).index]
				: program.getInstructionQueue()[((LoadBuffer) slot).index]) + " started publishing on the bus ";

		return;
	}

	public Integer countDependencies(ReservationID id) {
		int ans = 0;
		for (Reservation res : addStation.getStation()) {
			if ((res.getQj() != null && res.getQj().equals(id)) || (res.getQk() != null && res.getQk().equals(id))) {
				ans++; // RAW Dependency
			}
		}
		for (Reservation res : mulStation.getStation()) {
			if ((res.getQj() != null && res.getQj().equals(id)) || (res.getQk() != null && res.getQk().equals(id))) {
				ans++; // RAW Dependency
			}
		}
		for (StoreBuffer res : sb.getStation()) {
			if (res.getQ() != null && res.getQ().equals(id)) {
				ans++; // RAW Dependency
			}
		}

		return ans;
	}

	public void checkExecution() {
		// LOGIC loop over all stations if Vj and Vk available set sstartCycle with
		// current Cycle
		ALUCheckExecution(0);
		ALUCheckExecution(1);
		storeBufferCheckExecution();
		// LoadBufferCheckExecution(); //Redundant i think
	}

	public void storeBufferCheckExecution() {

		for (StoreBuffer buff : sb.getStation()) {
			if (buff.getQ() == null && buff.busy == true) {
				int instructionLocation = buff.index;
				Instruction instruction = program.getInstructionQueue()[instructionLocation];
				if (instruction.startExec == -1) {
					instruction.setStartExec(cycle);
					instruction.setEndExec(cycle + getLatency(instruction.instructionType));
				}

			}
		}

	}

	public void ALUCheckExecution(int i) {
		ReservationStation curr = (i == 0) ? addStation : mulStation;
		for (Reservation res : curr.getStation()) {
			if (res.getQj() == null && res.getQk() == null && res.busy == true) {
				// I don;t have neither both so all is OK with my inputs
				// start EXEC
				Instruction instruction = program.getInstructionQueue()[res.index];
				if (instruction.startExec == -1) {
					instruction.setStartExec(cycle);
					instruction.setEndExec(cycle + getLatency(instruction.instructionType));

				}

			}
		}
	}

	public String executeSummary() {
		String res = "execute: \n";
		for (int i = 0; i < program.getInstructionQueue().length; i++) {
			Instruction inst = program.getInstructionQueue()[i];
			if (inst.getStartExec() == cycle) {
				res += "\nthe following instruction started execution: \n" + inst.toString() + "\n";
			}
			if (inst.getEndExec() == cycle) {
				res += "\nthe following instruction finished executing\n" + inst.toString() + "\n";
			}
		}
		return res + "\n";

	}

	public String busSummary() {
		String ret = "bus: ";
		if (bus.sourceID == null)
			ret += "empty \n";
		else
			ret += bus.toString() + "\n";
		return ret;

	}

	public String getCycleSummary() {
		String res = "";
		res += "cycle number " + (cycle) + ": \n"; //To serve GUI shifting bug, was cycle only
		res += issueSummary;
		res += executeSummary();
		res += publishSummary;
		res += busSummary();
		return res;
	}

	public String printCycle() {
		String res = "";
		res += getCycleSummary();
		res += printStation(addStation);
		res += printStation(mulStation);
		res += printLoadBuffers();
		res += printStoreBuffers();
		res += printProgram();
		res += printRegisterFile();
		return res;

	}

	public String printProgram() {
		return "program: \n" + program.toString();
	}

	public String printRegisterFile() {
		String ret = "Floating Register File: \n";

		for (int i = 0; i < 32; i++)
			ret += "F" + i + " " + rf.getFloating()[i] + "\n";
//		for (int i = 0; i < 32; i++)
//			ret += "R" + i + " " + rf.getInteger()[i];
		return ret;
	}

	public String printStoreBuffers() {
		String ret = "";
		ret += "Store Buffers: \n";
		for (int i = 0; i < sb.getStation().length; i++)
			ret += sb.getStation()[i] + "\n";
		return ret;
	}

	public String printLoadBuffers() {
		String ret = "";
		ret += "Load Buffers: \n";

		for (int i = 0; i < lb.getStation().length; i++)
			ret += lb.getStation()[i] + "\n";
		return ret;
	}

	public String printStation(ReservationStation station) {
		String ret = "";
		ret += station.type.toString() + " station: \n";
		for (Reservation res : station.getStation()) {
			ret += res.toString() + "\n";
		}
		return ret + "\n";

	}

	public boolean tryIssue() {
		if (pc >= program.getInstructionQueue().length) // No More to issue in the stations
		{
			issueSummary += "no more issues remaining\n";
			return false;
		}
		Instruction current = program.getInstructionQueue()[pc];
		boolean yes;
		if (current.instructionType == InstructionType.ADD || current.instructionType == InstructionType.SUB) {
			Op op = current.instructionType == InstructionType.ADD ? Op.ADD : Op.SUB;
			yes = issueStation(addStation, op);

		} else if (current.instructionType == InstructionType.DIV || current.instructionType == InstructionType.MUL) {
			Op op = current.instructionType == InstructionType.DIV ? Op.DIV : Op.MUL;
			yes = issueStation(mulStation, op);

		} else if (current.instructionType == InstructionType.LOAD) {
			yes = issueLoadBuffer();

		} else if (current.instructionType == InstructionType.STORE) {
			yes = issueStoreBuffer();
		} else {
			yes = false;
		}

		if (yes)
			issueSummary += " instruction " + current + " is issued\n";

		else
			issueSummary += " instruction " + current + " can not be issued\n";

		return yes;
	}

	public boolean issueStation(ReservationStation station, Op op) {
		for (int i = 0; i < station.getStation().length; i++) {
			Reservation res = station.getStation()[i];
			if (!res.busy) {
				res.setBusy(true);
				station.size++;
				res.op = op;
				prepareInstructionArithmetic(program.instructionQueue[pc], res);
				return true;

			}
		}
		return false;
	}

	public void prepareInstructionArithmetic(Instruction instruction, Reservation res) {
		res.index = pc;
		String rs = instruction.getRs();
		if (rs.charAt(0) == 'F') {
			int idx = getRegisterIndex(rs);
			if (rf.getFloating()[idx].Qi == null) {
				res.Vj = rf.getFloating()[idx].value;
			} else {
				res.Qj = rf.getFloating()[idx].Qi;
			}
		} else {
			int idx = getRegisterIndex(rs);
			if (rf.getInteger()[idx].Qi == null) {
				res.Vj = rf.getInteger()[idx].value;
			} else {
				res.Qj = rf.getInteger()[idx].Qi;
			}

		}
		String rt = instruction.getRt();
		if (rt.charAt(0) == 'F') {
			int idx = getRegisterIndex(rt);
			if (rf.getFloating()[idx].Qi == null) {
				res.Vk = rf.getFloating()[idx].value;
			} else {
				res.Qk = rf.getFloating()[idx].Qi;
			}
		} else {
			int idx = getRegisterIndex(rt);
			if (rf.getInteger()[idx].Qi == null) {
				res.Vk = rf.getInteger()[idx].value;
			} else {
				res.Qk = rf.getInteger()[idx].Qi;
			}

		}
		String rd = instruction.getRd();
		int idx = getRegisterIndex(rd);
		Register reg;
		if (rd.charAt(0) == 'F')
			reg = rf.getFloating()[idx];

		else
			reg = rf.getInteger()[idx];

		reg.Qi = res.ID;
	}

	public int getRegisterIndex(String r) {
		return Integer.parseInt(r.substring(1));
	}

	public boolean issueStoreBuffer() {
		for (int i = 0; i < lb.getStation().length; i++) {
			StoreBuffer buffer = sb.getStation()[i];
			if (!buffer.busy) {
				buffer.setBusy(true);
				prepareInstructionStore(program.instructionQueue[pc], buffer);
				return true;

			}
		}
		return false;

	}

	public void prepareInstructionStore(Instruction instruction, StoreBuffer buffer) {
		int rs = getRegisterIndex(instruction.rs);
		int A = rf.getValueInteger(rs) + instruction.offset;
		buffer.setA(A);
		String rd = instruction.getRd();
		if (rd.charAt(0) == 'F') {
			int idx = getRegisterIndex(rd);
			if (rf.getFloating()[idx].Qi == null) {
				buffer.v = rf.getFloating()[idx].value;
			} else {
				buffer.Q = rf.getFloating()[idx].Qi;
			}
		} else {
			int idx = getRegisterIndex(rd);
			if (rf.getInteger()[idx].Qi == null) {
				buffer.v = rf.getInteger()[idx].value;
			} else {
				buffer.Q = rf.getInteger()[idx].Qi;
			}

		}
		buffer.index = pc;

	}

	public boolean issueLoadBuffer() {
		for (int i = 0; i < lb.getStation().length; i++) {
			LoadBuffer buffer = lb.getStation()[i];
			if (!buffer.busy) {
				buffer.setBusy(true);
				lb.size++;
				prepareInstructionLoad(program.instructionQueue[pc], buffer);
				return true;

			}
		}
		return false;

	}

	public void prepareInstructionLoad(Instruction instruction, LoadBuffer buffer) {
		int rs = getRegisterIndex(instruction.rs);
		int A = rf.getValueInteger(rs) + instruction.offset;
		buffer.setA(A);
		rf.setQiFloating(getRegisterIndex(instruction.rd), buffer.Q);
		instruction.setStartExec(cycle + 1);
		instruction.setEndExec(cycle + 1 + getLatency(instruction.instructionType));
		buffer.index = pc;
		// Started Executing from the next Cycle iam in directly
	}

	public ReservationStation getAddStation() {
		return addStation;
	}

	public ReservationStation getMulStation() {
		return mulStation;
	}

	public LoadBuffers getLoadStation() {
		return lb;
	}

	public StoreBuffers getStoreStation() {
		return sb;
	}

	public RegisterFile getRegisterFile() {
		return rf;
	}

	public int getLatency(InstructionType instructionType) {
		// TODO Auto-generated method stub
		if (instructionType == InstructionType.ADD)
			return addlat;
		if (instructionType == InstructionType.SUB)
			return sublat;
		if (instructionType == InstructionType.MUL)
			return mullat;
		if (instructionType == InstructionType.DIV)
			return divlat;
		if (instructionType == InstructionType.LOAD)
			return ldlat;
		if (instructionType == InstructionType.STORE)
			return stlat;

		return 0;
	}

}
