package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import Tomasulo.LoadBuffer;
import Tomasulo.Parser;
import Tomasulo.Processor;
import Tomasulo.Program;
import Tomasulo.Register;
import Tomasulo.Reservation;
import Tomasulo.StoreBuffer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.event.ActionEvent;


public class MainSceneController {
	
	static Processor processor;
	static String filePath;
	
	 	@FXML
	    private TextField addLatency;
	 	
	 	@FXML
	    private Button nextButton;
	 	
	 	@FXML
	    private TextField subLatency;
	 	
	 	@FXML
	    private TextField mulLatency;
	 	
	 	@FXML
	    private TextField divLatency;
	 	
	 	@FXML
	    private TextField storeLatency;
	 	
	 	@FXML
	    private TextField loadLatency;
    
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

	    @FXML
	    private Label F16;

	    @FXML
	    private Label F17;

	    @FXML
	    private Label F18;

	    @FXML
	    private Label F19;

	    @FXML
	    private Label F20;

	    @FXML
	    private Label F21;

	    @FXML
	    private Label F22;

	    @FXML
	    private Label F23;

	    @FXML
	    private Label F24;

	    @FXML
	    private Label F25;

	    @FXML
	    private Label F26;

	    @FXML
	    private Label F27;

	    @FXML
	    private Label F28;

	    @FXML
	    private Label F29;

	    @FXML
	    private Label F30;

	    @FXML
	    private Label F31;

	    @FXML
	    private Label F32;

	    @FXML
	    private Label loadID1;

	    @FXML
	    private Label loadID2;

	    @FXML
	    private Label loadID3;

	    @FXML
	    private Label loadBusy1;

	    @FXML
	    private Label loadBusy2;

	    @FXML
	    private Label loadBusy3;

	    @FXML
	    private Label loadQj1;

	    @FXML
	    private Label loadQj2;

	    @FXML
	    private Label loadQj3;

	    @FXML
	    private Label storeID1;

	    @FXML
	    private Label storeID2;

	    @FXML
	    private Label storeID3;

	    @FXML
	    private Label storeBusy1;

	    @FXML
	    private Label storeBusy2;

	    @FXML
	    private Label storeBusy3;

	    @FXML
	    private Label storeQj1;

	    @FXML
	    private Label storeQj2;

	    @FXML
	    private Label storeQj3;
	
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
    
    void updateRegisterInfo() {
    	Register[] rf = processor.getRegisterFile().getFloating();
    	
    	if(rf[0].getQ() != null) {
			F0.setText(rf[0].getQ().toString());
		}
    	else {
    		F0.setText(rf[0].getValue() + "");
    	}
    	
    	if(rf[1].getQ() != null) {
			F1.setText(rf[1].getQ().toString());
		}
    	else {
    		F1.setText(rf[1].getValue() + "");
    	}
    	
    	if(rf[2].getQ() != null) {
			F2.setText(rf[2].getQ().toString());
		}
    	else {
    		F2.setText(rf[2].getValue() + "");
    	}
    	
    	if(rf[3].getQ() != null) {
			F3.setText(rf[3].getQ().toString());
		}
    	else {
    		F3.setText(rf[3].getValue() + "");
    	}
    	
    	if(rf[4].getQ() != null) {
			F4.setText(rf[4].getQ().toString());
		}
    	else {
    		F4.setText(rf[4].getValue() + "");
    	}
    	
    	if(rf[5].getQ() != null) {
			F5.setText(rf[5].getQ().toString());
		}
    	else {
    		F5.setText(rf[5].getValue() + "");
    	}
    	
    	if(rf[6].getQ() != null) {
			F6.setText(rf[6].getQ().toString());
		}
    	else {
    		F6.setText(rf[6].getValue() + "");
    	}
    	
    	if(rf[7].getQ() != null) {
			F7.setText(rf[7].getQ().toString());
		}
    	else {
    		F7.setText(rf[7].getValue() + "");
    	}
    	
    	if(rf[8].getQ() != null) {
			F8.setText(rf[8].getQ().toString());
		}
    	else {
    		F8.setText(rf[8].getValue() + "");
    	}
    	
    	if(rf[9].getQ() != null) {
			F9.setText(rf[9].getQ().toString());
		}
    	else {
    		F9.setText(rf[9].getValue() + "");
    	}
    	
    	if(rf[10].getQ() != null) {
			F10.setText(rf[10].getQ().toString());
		}
    	else {
    		F10.setText(rf[10].getValue() + "");
    	}
    	
    	if(rf[11].getQ() != null) {
			F11.setText(rf[11].getQ().toString());
		}
    	else {
    		F11.setText(rf[11].getValue() + "");
    	}
    	
    	if(rf[12].getQ() != null) {
			F12.setText(rf[12].getQ().toString());
		}
    	else {
    		F12.setText(rf[12].getValue() + "");
    	}
    	
    	if(rf[13].getQ() != null) {
			F13.setText(rf[13].getQ().toString());
		}
    	else {
    		F13.setText(rf[13].getValue() + "");
    	}
    	
    	if(rf[14].getQ() != null) {
			F14.setText(rf[14].getQ().toString());
		}
    	else {
    		F14.setText(rf[14].getValue() + "");
    	}
    	
    	if(rf[15].getQ() != null) {
			F15.setText(rf[15].getQ().toString());
		}
    	else {
    		F15.setText(rf[15].getValue() + "");
    	}
    	
    	if(rf[16].getQ() != null) {
			F16.setText(rf[16].getQ().toString());
		}
    	else {
    		F16.setText(rf[16].getValue() + "");
    	}
    	
    	if(rf[17].getQ() != null) {
			F17.setText(rf[17].getQ().toString());
		}
    	else {
    		F17.setText(rf[17].getValue() + "");
    	}
    	
    	if(rf[18].getQ() != null) {
			F18.setText(rf[18].getQ().toString());
		}
    	else {
    		F18.setText(rf[18].getValue() + "");
    	}
    	
    	if(rf[19].getQ() != null) {
			F19.setText(rf[19].getQ().toString());
		}
    	else {
    		F19.setText(rf[19].getValue() + "");
    	}
    	
    	if(rf[20].getQ() != null) {
			F20.setText(rf[20].getQ().toString());
		}
    	else {
    		F20.setText(rf[20].getValue() + "");
    	}
    	
    	if(rf[21].getQ() != null) {
			F21.setText(rf[21].getQ().toString());
		}
    	else {
    		F21.setText(rf[21].getValue() + "");
    	}
    	
    	if(rf[22].getQ() != null) {
			F22.setText(rf[22].getQ().toString());
		}
    	else {
    		F22.setText(rf[22].getValue() + "");
    	}
    	
    	if(rf[23].getQ() != null) {
			F23.setText(rf[23].getQ().toString());
		}
    	else {
    		F23.setText(rf[23].getValue() + "");
    	}
    	
    	if(rf[24].getQ() != null) {
			F24.setText(rf[24].getQ().toString());
		}
    	else {
    		F24.setText(rf[24].getValue() + "");
    	}
    	
    	if(rf[25].getQ() != null) {
			F25.setText(rf[25].getQ().toString());
		}
    	else {
    		F25.setText(rf[25].getValue() + "");
    	}
    	
    	if(rf[26].getQ() != null) {
			F26.setText(rf[26].getQ().toString());
		}
    	else {
    		F26.setText(rf[26].getValue() + "");
    	}
    	
    	if(rf[27].getQ() != null) {
			F27.setText(rf[27].getQ().toString());
		}
    	else {
    		F27.setText(rf[27].getValue() + "");
    	}
    	
    	if(rf[28].getQ() != null) {
			F28.setText(rf[28].getQ().toString());
		}
    	else {
    		F28.setText(rf[28].getValue() + "");
    	}
    	
    	if(rf[29].getQ() != null) {
			F29.setText(rf[29].getQ().toString());
		}
    	else {
    		F29.setText(rf[29].getValue() + "");
    	}
    	
    	if(rf[30].getQ() != null) {
			F30.setText(rf[30].getQ().toString());
		}
    	else {
    		F30.setText(rf[30].getValue() + "");
    	}
    	
    	if(rf[31].getQ() != null) {
			F31.setText(rf[31].getQ().toString());
		}
    	else {
    		F31.setText(rf[31].getValue() + "");
    	}
    }
    
    void updateInfo() {
    	
    	updateRegisterInfo();
    	
    	System.out.println(processor.printCycle());;
    	summary.setText(summary.getText() + processor.printCycle() + "\n" + "\n");
    	
    	Reservation[] addReservationStation = processor.getAddStation().getStation();
    	Reservation[] mulReservationStation = processor.getMulStation().getStation();
    	
    	LoadBuffer[] loadBuffer = processor.getLoadStation().getStation();
    	StoreBuffer[] storeBuffer = processor.getStoreStation().getStation();
    	
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
	    		resAddQj1.setText(addReservationStation[i].getQj().toString());
	    		else {
	    			resAddQj1.setText("");
	    		}
	    		if(addReservationStation[i].getQk() != null)
	    		resAddQk1.setText(addReservationStation[i].getQk().toString());
	    		else {
	    			resAddQj1.setText("");
	    		}
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
	    		resAddQj2.setText(addReservationStation[i].getQj().toString());
	    		else {
	    			resAddQj2.setText("");
	    		}
	    		if(addReservationStation[i].getQk() != null)
	    		resAddQk2.setText(addReservationStation[i].getQk().toString());
	    		else {
	    			resAddQk2.setText("");
	    		}
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
	    		resAddQj3.setText(addReservationStation[i].getQj().toString());
	    		else {
	    			resAddQj3.setText("");
	    		}
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
	    		resMulQj1.setText(mulReservationStation[i].getQj().toString());
	            else {
	            	resMulQj1.setText("");
	            }
	            if(mulReservationStation[i].getQk() != null)
	    		resMulQk1.setText(mulReservationStation[i].getQk().toString());
	            else {
	            	resMulQk1.setText("");
	            }
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
		    		resMulQj2.setText(mulReservationStation[i].getQj().toString());
		            else {
		            	resMulQj2.setText("");
		            }
		            if(mulReservationStation[i].getQk() != null)
		    		resMulQk2.setText(mulReservationStation[i].getQk().toString());
		            else {
		            	resMulQk2.setText("");
		            }
    		}
    		
		}
    	
    	
    	for (int i = 0; i < loadBuffer.length; i++) {
    		if(i == 0) {
	    		if(loadBuffer[i].getQ() != null)
	    		loadID1.setText(loadBuffer[i].getQ().toString());
	    		else {
	    			loadID1.setText(loadBuffer[i].getQ().toString());
	    		}
	    		loadBusy1.setText(loadBuffer[i].isBusy() + "");
    		}
    		
    		if(i == 1) {
	    		if(loadBuffer[i].getQ() != null)
	    		loadID2.setText(loadBuffer[i].getQ().toString());
	    		loadBusy2.setText(loadBuffer[i].isBusy() + "");
    		}
    		
    		if(i == 2) {
	    		if(loadBuffer[i].getQ() != null)
	    		loadID3.setText(loadBuffer[i].getQ().toString());
	    		loadBusy3.setText(loadBuffer[i].isBusy() + "");
    		}
    		
		}
    	
    	for (int i = 0; i < storeBuffer.length; i++) {
    		if(i == 0) {
	    		if(storeBuffer[i].getQ() != null)
	    		storeQj1.setText(storeBuffer[i].getQ().toString());
	    		else {
	    			storeQj1.setText(storeBuffer[i].getV() + "");
	    		}
	    		storeBusy1.setText(storeBuffer[i].isBusy() + "");
    		}
    		
    		if(i == 1) {
	    		if(storeBuffer[i].getQ() != null)
		    		storeQj2.setText(storeBuffer[i].getQ().toString());
	    		else {
	    			storeQj2.setText(storeBuffer[i].getV() + "");
	    		}
	    		storeBusy2.setText(storeBuffer[i].isBusy() + "");
    		}
    		
    		if(i == 2) {
	    		if(storeBuffer[i].getQ() != null)
		    		storeQj3.setText(storeBuffer[i].getQ().toString());
	    		else {
	    			storeQj3.setText(storeBuffer[i].getV() + "");
	    		}
	    		storeBusy3.setText(storeBuffer[i].isBusy() + "");
    		}
    		
		}
    	
    }

    @FXML
    void nextCycleClick(MouseEvent event) {
    	boolean issueSuccessful = processor.next1();
    	updateInfo();
    	processor.next2(issueSuccessful);
    	if (processor.pc >= processor.program.getInstructionQueue().length && processor.allStationsEmpty()) {
    		nextButton.setDisable(true);
    	}
    		
    }
    

    @FXML
    void onStart(ActionEvent event) throws FileNotFoundException {
    	Parser p = new Parser();
		ArrayList<String> arr = p.readProgram(new File(filePath));
		Program program = p.parse(arr);
		
		int addLat = Integer.parseInt(addLatency.getText());
		int subLat = Integer.parseInt(subLatency.getText());
		int mulLat = Integer.parseInt(mulLatency.getText());
		int divLat = Integer.parseInt(mulLatency.getText());
		int ldLat = Integer.parseInt(loadLatency.getText());
		int stLat = Integer.parseInt(storeLatency.getText());
		processor = new Processor(program, addLat, mulLat, subLat, divLat, ldLat, stLat);
		updateInfo();
    }

}

