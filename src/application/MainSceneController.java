package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import Tomasulo.Parser;
import Tomasulo.Processor;
import Tomasulo.Program;
import Tomasulo.RegisterFile;
import Tomasulo.Reservation;
import Tomasulo.ReservationStation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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

    @FXML
    private Label resAddID1;

    @FXML
    private Label resAddID2;

    @FXML
    private Label resAddID3;

    @FXML
    private Label resMulID1;

    @FXML
    private Label resMulID2;

    @FXML
    private Label resMulBusy1;

    @FXML
    private Label resMulBusy2;

    @FXML
    private Label resMulOp1;

    @FXML
    private Label resMulOp2;

    @FXML
    private Label resMulVj1;

    @FXML
    private Label resMulVj2;

    @FXML
    private Label resMulVk1;

    @FXML
    private Label resMulVk2;

    @FXML
    private Label resMulQj1;

    @FXML
    private Label resMulQk1;

    @FXML
    private Label resMulQk2;

    @FXML
    private Label resMulQj2;

    @FXML
    private Label resAddBusy1;

    @FXML
    private Label resAddBusy2;

    @FXML
    private Label resAddBusy3;

    @FXML
    private Label resAddOp1;

    @FXML
    private Label resAddOp2;

    @FXML
    private Label resAddOp3;

    @FXML
    private Label resAddVj1;

    @FXML
    private Label resAddVj2;

    @FXML
    private Label resAddVj3;

    @FXML
    private Label resAddVk1;

    @FXML
    private Label resAddVk2;

    @FXML
    private Label resAddVk3;

    @FXML
    private Label resAddQj1;

    @FXML
    private Label resAddQj2;

    @FXML
    private Label resAddQj3;

    @FXML
    private Label resAddQk1;

    @FXML
    private Label resAddQk2;

    @FXML
    private Label resAddQk3;

    @FXML
    private Label F0;

    @FXML
    private Label F1;

    @FXML
    private Label F2;

    @FXML
    private Label F3;

    @FXML
    private Label F4;

    @FXML
    private Label F5;

    @FXML
    private Label F6;

    @FXML
    private Label F7;

    @FXML
    private Label F8;

    @FXML
    private Label F9;

    @FXML
    private Label F10;

    @FXML
    private Label F11;

    @FXML
    private Label F12;

    @FXML
    private Label F13;

    @FXML
    private Label F14;

    @FXML
    private Label F15;
	
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
    
    void updateInfo() {
    	summary.setText(summary.getText() + processor.printCycle() + "\n" + "\n");
    	
    	RegisterFile rf = processor.getRegisterFile();
    	
    	for (int i = 0; i < rf.getFloating().length; i++) {
			
		}
    	
    	
    	Reservation[] addReservationStation = processor.getAddStation().getStation();
    	Reservation[] mulReservationStation = processor.getMulStation().getStation();
    	
    	for (int i = 0; i < addReservationStation.length; i++) {
    		if(i == 0) {
	    		if(addReservationStation[i].getID() != null)
	    		resAddID1.setText(addReservationStation[i].getID().toString());
	    		resAddBusy1.setText(addReservationStation[i].isBusy() + "");
	    		if(addReservationStation[i].getOp() != null)
	    		resAddOp1.setText(addReservationStation[i].getOp().toString());
	    		resAddVj1.setText(addReservationStation[i].getVj() + "");
	    		resAddVk1.setText(addReservationStation[i].getVk() + "");
	    		if(addReservationStation[i].getQj() != null)
	    		resAddQk1.setText(addReservationStation[i].getQj().toString());
	    		if(addReservationStation[i].getQk() != null)
	    		resAddQk1.setText(addReservationStation[i].getQk().toString());
    		}
    		
    		if(i == 1) {
	        	if(addReservationStation[i].getID() != null)
	    		resAddID2.setText(addReservationStation[i].getID().toString());
	    		resAddBusy2.setText(addReservationStation[i].isBusy() + "");
	    		if(addReservationStation[i].getOp() != null)
	    		resAddOp2.setText(addReservationStation[i].getOp().toString());
	    		resAddVj2.setText(addReservationStation[i].getVj() + "");
	    		resAddVk2.setText(addReservationStation[i].getVk() + "");
	    		if(addReservationStation[i].getQj() != null)
	    		resAddQk2.setText(addReservationStation[i].getQj().toString());
	    		if(addReservationStation[i].getQk() != null)
	    		resAddQk2.setText(addReservationStation[i].getQk().toString());
    		}
    		
    		if(i == 2) {
	            if(addReservationStation[i].getID() != null)
	    		resAddID3.setText(addReservationStation[i].getID().toString());
	    		resAddBusy3.setText(addReservationStation[i].isBusy() + "");
	    		if(addReservationStation[i].getOp() != null)
	    		resAddOp3.setText(addReservationStation[i].getOp().toString());
	    		resAddVj3.setText(addReservationStation[i].getVj() + "");
	    		resAddVk3.setText(addReservationStation[i].getVk() + "");
	    		if(addReservationStation[i].getQj() != null)
	    		resAddQk3.setText(addReservationStation[i].getQj().toString());
	    		if(addReservationStation[i].getQk() != null)
	    		resAddQk3.setText(addReservationStation[i].getQk().toString());
    		}
    		
		}
    	
    	for (int i = 0; i < mulReservationStation.length; i++) {
    		if(i == 0) {
		        if(mulReservationStation[i].getID() != null)
		        resMulID1.setText(mulReservationStation[i].getID().toString());
	    		resMulBusy1.setText(mulReservationStation[i].isBusy() + "");
	            if(mulReservationStation[i].getOp() != null)
	    		resMulOp1.setText(mulReservationStation[i].getOp().toString());
	    		resMulVj1.setText(mulReservationStation[i].getVj() + "");
	    		resMulVk1.setText(mulReservationStation[i].getVk() + "");
	            if(mulReservationStation[i].getQj() != null)
	    		resMulQk1.setText(mulReservationStation[i].getQj().toString());
	            if(mulReservationStation[i].getQk() != null)
	    		resMulQk1.setText(mulReservationStation[i].getQk().toString());
    		}
    		
	    		if(i == 1) {
		            if(mulReservationStation[i].getID() != null)
		    		resMulID2.setText(mulReservationStation[i].getID().toString());
		    		resMulBusy2.setText(mulReservationStation[i].isBusy() + "");
		            if(mulReservationStation[i].getOp() != null)
		    		resMulOp2.setText(mulReservationStation[i].getOp().toString());
		    		resMulVj2.setText(mulReservationStation[i].getVj() + "");
		    		resMulVk2.setText(mulReservationStation[i].getVk() + "");
		            if(mulReservationStation[i].getQj() != null)
		    		resMulQk2.setText(mulReservationStation[i].getQj().toString());
		            if(mulReservationStation[i].getQk() != null)
		    		resMulQk2.setText(mulReservationStation[i].getQk().toString());
    		}
    		
		}
    }

    @FXML
    void nextCycleClick(MouseEvent event) {
    	processor.next();
    	updateInfo();
    }
    

    @FXML
    void onStart(ActionEvent event) throws FileNotFoundException {
    	Parser p = new Parser();
		ArrayList<String> arr = p.readProgram(new File(filePath));
		System.out.println(filePath);
		Program program = p.parse(arr);
		processor = new Processor(program);
    	//updateInfo();
    }

}

