package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import Tomasulo.Parser;
import Tomasulo.Processor;
import Tomasulo.Program;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.event.ActionEvent;


public class MainSceneController {
	
	static Processor processor;
	static String filePath;
    
    @FXML
    private TextArea summary;

    @FXML
    private TextArea addReservationStation;

    @FXML
    private TextArea mulReservationStation;

    @FXML
    private TextArea storeBuffer;

    @FXML
    private TextArea loadBuffer;

    @FXML
    private TextArea registerFile;
	
	String readFile(String filePath) {
		String fileText = "";
		try {
		      File myObj = new File(filePath);
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {
		        fileText += myReader.nextLine() + "\n";
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		return fileText;
	}
	


    @FXML
    void onLoad(ActionEvent event) {
    	FileChooser fc = new FileChooser();
    	fc.getExtensionFilters().add(new ExtensionFilter("TXT File", "*.txt"));
    	File f = fc.showOpenDialog(null);
    	if(f != null) {
    		filePath = f.toString();
    	}
    	
    }

    @FXML
    void nextCycleClick(MouseEvent event) {
    	processor.next();
    	summary.setText(summary.getText() + processor.printCycle() + "\n" + "\n");
    }
    

    @FXML
    void onStart(ActionEvent event) throws FileNotFoundException {

    	Parser p = new Parser();
		ArrayList<String> arr = p.readProgram(new File(filePath));
		Program program = p.parse(arr);
		processor = new Processor(program);
    	summary.setText(summary.getText() + processor.printCycle() + "\n" + "\n");

    }

}

