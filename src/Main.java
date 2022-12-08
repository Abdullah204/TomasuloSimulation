import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		Parser p = new Parser();
		ArrayList<String> arr = p.readProgram(new File("sampleProgram.txt"));
		Program program = p.parse(arr);
		Processor processor = new Processor(program);
		
		while(true) {
			boolean still = processor.next();
			if(!still)
				break;
		}
		
		
		
		
		
	}

}
