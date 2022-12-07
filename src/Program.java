
public class Program {
	Instruction[] instructionQueue;
	
	public Program (Instruction[] instructionQueue) {
		this.instructionQueue = instructionQueue;
	}

	public Instruction[] getInstructionQueue() {
		return instructionQueue;
	}

	public void setInstructionQueue(Instruction[] instructionQueue) {
		this.instructionQueue = instructionQueue;
	}
	public String toString() {
		String s = "";
		for(Instruction i : instructionQueue) {
			s+=i.toString()+"\n";
		}
		return s;
	}
}
