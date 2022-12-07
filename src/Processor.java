
public class Processor {
	Program program;
	int cycle;
	int pc;
	ReservationStation addStation;
	ReservationStation mulStation;
	LoadBuffers lb;
	StoreBuffers sb;
	RegisterFile rf;
	Bus bus;
	Memory memory;

	public Processor(Program program) {
		this.program = program;
		bus = new Bus();
		cycle = 0;
		pc = 0;
		addStation = new ReservationStation(3, StationType.ADD, 3);
		mulStation = new ReservationStation(2, StationType.MUL, 3);
		// TODO Auto-generated constructor stub
		sb = new StoreBuffers(3, 2); //We are assuming CACHE takes 2 cycles
		lb = new LoadBuffers(3, 2);
		rf = new RegisterFile();
		memory = new Memory(2048);
	}

	public void next() {
		checkExecution(); //end cycle --> start + latency 
		if (tryIssue())
			pc++;
		checkPublish();
		checkBus();
		printCycle();
		cycle++;
		return;
	}

	// checks if any reservation needs data on bus and assign it
	public void checkBus() {
		//Check if any slot need any thing from the BUS
		if(bus.sourceID == null)
		{
			return;
		}
		
		ALUStationTakeBus(0);
		ALUStationTakeBus(1);
		StoreBufferTakeBus();	
	}
	
	public void ALUStationTakeBus(int x)
	{
		ReservationStation curr = (x == 0)? addStation : mulStation;
		for(Reservation res : curr.getStation())
		{
			if(res.getQj().equals(bus.sourceID))
			{
				//Now i had something i need from the bus at Qj for this reservation
				res.setQj(null);
				res.setVj(bus.value);
			}
			if(res.getQk().equals(bus.sourceID))
			{
				//Now i had something i need from the bus at Qk for this reservation
				res.setQk(null);
				res.setVk(bus.value);
			}
		}
	}
	
	public void StoreBufferTakeBus()
	{
		for(StoreBuffer res : sb.getStation())
		{
			if(res.getQ().equals(bus.sourceID))
			{
				//Now i had something i need from the bus at Q for this storeBuffer
				res.setQ(null);
				res.setV(bus.value);
			}
		}
	}

	// checks if any instruction finished execution and publishes result on bus
	public void checkPublish() {
		

	}

	public void checkExecution() {
		//LOGIC loop over all stations if Vj and Vk available set sstartCycle with current Cycle
		ALUCheckExecution(0);
		ALUCheckExecution(1);
		StoreBufferCheckExecution();
		//LoadBufferCheckExecution(); //Redundant i think
	}

	public void StoreBufferCheckExecution() {
		// TODO Auto-generated method stub
		//LATENCY
		for(StoreBuffer buff : sb.getStation())
		{
			if(buff.getQ() == null)
			{
				int instructionLocation = buff.index;
				Instruction instruction = program.getInstructionQueue()[instructionLocation];
				instruction.setStartExec(cycle + 1);
				instruction.setEndExec(cycle + 1 + sb.latency);
				//Start EXECUTING
			}
		}
		
	}

	private void ALUCheckExecution(int i) {
		// TODO Auto-generated method stub
		ReservationStation curr = (i == 0)? addStation : mulStation;
		for(Reservation res : curr.getStation())
		{
			if(res.getQj() == null && res.getQk() == null)
			{
				//I don;t have neither both so all is OK with my inputs
				//start EXEC
				Instruction instruction = program.getInstructionQueue()[res.index];
				instruction.setStartExec(cycle + 1);
				instruction.setEndExec(cycle + 1 + curr.latency);
			}
		}
	}

	public void printCycle() {

	}

	public boolean tryIssue() {
		if(pc >= program.getInstructionQueue().length) //No More to issue in the stations 
		{
			return false;
		}
		Instruction current = program.getInstructionQueue()[pc];
		if (current.instructionType == InstructionType.ADD || current.instructionType == InstructionType.SUB) {
			Op op = current.instructionType == InstructionType.ADD ? Op.ADD : Op.SUB;
			return issueStation(addStation, op);

		} else if (current.instructionType == InstructionType.DIV || current.instructionType == InstructionType.MUL) {
			Op op = current.instructionType == InstructionType.DIV ? Op.DIV : Op.MUL;
			return issueStation(mulStation, op);

		} else if (current.instructionType == InstructionType.LOAD) {
			return issueLoadBuffer();

		} else if (current.instructionType == InstructionType.STORE) {
			return issueStoreBuffer();
		} else {
			System.out.println("bug");
		}

		return false;
	}

	

	public boolean issueStation(ReservationStation station, Op op) {
		for (int i = 0; i < station.getStation().length; i++) {
			Reservation res = station.getStation()[i];
			if (!res.busy) {
				res.setBusy(true);
				res.op = op;
				prepareInstructionArithmetic(program.instructionQueue[pc], res);
				return true;

			}
		}
		return false;
	}

	private void prepareInstructionArithmetic(Instruction instruction, Reservation res) {
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
		// TODO Auto-generated method stub
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
	}

	public boolean issueLoadBuffer() {
		for (int i = 0; i < lb.getStation().length; i++) {
			LoadBuffer buffer = lb.getStation()[i];
			if (!buffer.busy) {
				buffer.setBusy(true);
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
		rf.setQiInteger(getRegisterIndex(instruction.rd), buffer.ID);
		instruction.setStartExec(cycle + 1);
		instruction.setEndExec(cycle + 1 + lb.latency);
	}

}
