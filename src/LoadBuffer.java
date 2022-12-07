
public class LoadBuffer {
	ReservationID ID;
	int A;
	boolean busy;
	int index;
	public boolean isBusy() {
		return busy;
	}


	public void setBusy(boolean busy) {
		this.busy = busy;
	}


	public LoadBuffer(ReservationID ID, int A) {
		this.ID = ID;
		this.A = A;
	}
	
	
	public LoadBuffer(ReservationID ID) {
		this.ID = ID;
	}


	public ReservationID getID() {
		return ID;
	}


	public void setID(ReservationID ID) {
		this.ID = ID;
	}


	public int getA() {
		return A;
	}


	public void setA(int A) {
		this.A = A;
	}

}
