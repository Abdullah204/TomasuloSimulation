package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.event.ActionEvent;


public class MainSceneController {
    @FXML
    private TextArea instructionsFile;
	
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
    void OnLoad(ActionEvent event) {
    	FileChooser fc = new FileChooser();
    	fc.getExtensionFilters().add(new ExtensionFilter("TXT File", "*.txt"));
    	File f = fc.showOpenDialog(null);
    	String fileText = "";
    	if(f != null) {
    		fileText = readFile(f.toString());
    	}
    	instructionsFile.setText(fileText);	
    }

    @FXML
    void nextCycleClick(MouseEvent event) {

    }

}

