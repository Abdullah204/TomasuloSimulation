import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Parser {

	public Parser() {
		// TODO Auto-generated constructor stub
	}

	public Program parse(ArrayList<String> s) {
		Instruction[] res = new Instruction[s.size()];

		for (int i = 0; i < res.length; i++) {
			res[i] = this.parseLine(s.get(i));
		}

		return new Program(res);
	}

	public Instruction parseLine(String s) {

		String[] parameters = s.split(", ");
		System.out.println(parameters[1]);
		String op = parameters[0].split(" ")[0];
		InstructionType instructionType = null;
		String rd = parameters[0].split(" ")[1];
		String rs = null;
		String rt = null;
		int offset = -1;
		if (op.equals("DIV.D")) {
			instructionType = InstructionType.DIV;
			rs = parameters[1];
			rt = parameters[2];
		}

		else if (op.equals("ADD.D")) {
			instructionType = InstructionType.ADD;
			rs = parameters[1];
			rt = parameters[2];

		} else if (op.equals("SUB.D")) {
			instructionType = InstructionType.SUB;
			rs = parameters[1];
			rt = parameters[2];

		} else if (op.equals("MUL.D")) {
			instructionType = InstructionType.MUL;
			rs = parameters[1];
			rt = parameters[2];

		} else if (op.equals("L.D")) {
			instructionType = InstructionType.LOAD;
			offset = Integer.parseInt(parameters[1].split("\\(")[0]);
			rs = parameters[1].split("\\(")[1].split("\\)")[0];

		} else if (op.equals("S.D")) {
			instructionType = InstructionType.STORE;

			offset = Integer.parseInt(parameters[1].split("\\(")[0]);
			rs = parameters[1].split("\\(")[1].split("\\)")[0];

		} else {
			System.out.println("wad gaban!");
		}
		return new Instruction(instructionType, rs, rt, rd, offset);

	}

	public ArrayList<String> readProgram(File program) throws FileNotFoundException {

		Scanner myReader = new Scanner(program);
		ArrayList<String> prog = new ArrayList<String>();
		while (myReader.hasNextLine()) {

			String data = myReader.nextLine();
			prog.add(data);

		}

		return prog;
	}

}
