import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		Parser p = new Parser();
		ArrayList<String> arr = p.readProgram(new File("sampleProgram.txt"));
		Program program = p.parse(arr);
		//System.out.println(program);
		ReservationID r1 = new ReservationID(StationType.ADD, 1);
		ReservationID r2 = new ReservationID(StationType.ADD, 1);
		System.out.println(r1.equals(r2));
		
		
		
		
	}

}
