
public class LoadBuffer {
	ReservationID Q;
	int A;
	boolean busy;
	int index;
	public boolean isBusy() {
		return busy;
	}


	public void setBusy(boolean busy) {
		this.busy = busy;
	}


	public LoadBuffer(ReservationID Q, int A) {
		this.Q = Q;
		this.A = A;
		index = -1;
	}
	
	
	public LoadBuffer(ReservationID Q) {
		this.Q = Q;
		index = -1;
	}


	public ReservationID getQ() {
		return Q;
	}


	public void setID(ReservationID Q) {
		this.Q = Q;
	}


	public int getA() {
		return A;
	}


	public void setA(int A) {
		this.A = A;
	}
	
	public String toString() {
		return "Q: "+ Q.toString() + " busy: " + busy + " A: " +A;
	}

}
