
public class Processor {
	Program program;
	int cycle;
	int pc;
	ReservationStation addStation;
	ReservationStation mulStation;
	LoadBuffers lb;
	StoreBuffers sb;
	RegisterFile rf;

	public Processor(Program program) {
		this.program = program;
		cycle = 0;
		pc = 0;
		addStation = new ReservationStation(3, StationType.ADD);
		mulStation = new ReservationStation(2, StationType.MUL);
		// TODO Auto-generated constructor stub
		sb = new StoreBuffers(3);
		lb = new LoadBuffers(3);
		rf = new RegisterFile();
	}

	public void next() {
		if (tryIssue())
			pc++;

		cycle++;
	}

	public boolean tryIssue() {
		Instruction current = program.getInstructionQueue()[pc];
		if (current.instructionType == InstructionType.ADD || current.instructionType == InstructionType.SUB) {
			for(int i = 0 ; i < addStation.getStation().length ; i++) {
				Reservation res = addStation.getStation()[i];
				if(!res.busy) {
					res.setBusy(true);
					
					
					return true;
				}
			}
			return false;

		} else if (current.instructionType == InstructionType.DIV || current.instructionType == InstructionType.MUL) {

		}  else if (current.instructionType == InstructionType.LOAD) {

		} else if (current.instructionType == InstructionType.STORE ) {

		}
		else {
			System.out.println("bug");
		}

		return false;
	}

}
