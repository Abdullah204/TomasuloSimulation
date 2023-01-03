package Tomasulo;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		Parser p = new Parser();
		ArrayList<String> arr = p.readProgram(new File("HusseinTest1.txt"));
		Program program = p.parse(arr);
		Processor processor = new Processor(program,2,2,2,2);
		
		Scanner sc = new Scanner(System.in);
		//processor.checkPublish();
		//processor.printCycle();
		while(true) {
//			sc.next();
			boolean still1 = processor.next1();
			boolean still2 = processor.next2(still1);
			if(!still1)
				break;
		}		
		
		
	}

}
