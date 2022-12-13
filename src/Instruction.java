
public class Instruction {
	InstructionType instructionType;
	String rs;
	String rt;
	String rd;
	int offset;
	int startExec;
	int endExec;
	

	public Instruction() {

	}

	public Instruction(InstructionType instructionType, String rs, String rt, String rd, int offset) {
		this.instructionType = instructionType;
		this.rs = rs;
		this.rt = rt;
		this.rd = rd;
		this.offset = offset;
		endExec = Integer.MAX_VALUE;
	}

	public InstructionType getInstructionType() {
		return instructionType;
	}

	public void setInstructionType(InstructionType instructionType) {
		this.instructionType = instructionType;
	}

	public String getRs() {
		return rs;
	}

	public void setRs(String rs) {
		this.rs = rs;
	}

	public String getRt() {
		return rt;
	}

	public void setRt(String rt) {
		this.rt = rt;
	}

	public String getRd() {
		return rd;
	}

	public void setRd(String rd) {
		this.rd = rd;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public String toString() {
		return "type: " + instructionType + " rs: " + rs + " rt: " + rt + " rd: " + rd + " offset: " + offset;
	}
	
	public int getStartExec() {
		return startExec;
	}

	public void setStartExec(int startExec) {
		this.startExec = startExec;
	}

	public int getEndExec() {
		return endExec;
	}

	public void setEndExec(int endExec) {
		this.endExec = endExec;
	}

}
