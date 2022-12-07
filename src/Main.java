import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Parser p = new Parser();
		
		ArrayList<String> arr = p.readProgram(new File("sampleProgram.txt"));
		Program program = p.parse(arr);
		System.out.println(program);

	}

}
