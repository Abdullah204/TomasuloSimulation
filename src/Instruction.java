
public class Instruction {
	InstructionType instructionType;
	BranchType branchType;
	int rs;
	int rt;
	int rd;
	int offset;

	public Instruction(InstructionType instructionType, BranchType branchType, int rs, int rt, int rd, int offset) {
		this.instructionType = instructionType;
		this.branchType = branchType;
		this.rs = rs;
		this.rt = rt;
		this.rd = rd;
		this.offset = offset;
	}
}
