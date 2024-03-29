package Tomasulo;

public class StoreBuffer {
	boolean busy;
	int A;
	double v;
	ReservationID Q;
	int index;
	
	public StoreBuffer() {
	}
	
	public StoreBuffer(int A) {
		this.A = A;
		index = -1;
	}

	public boolean isBusy() {
		return busy;
	}

	public void setBusy(boolean busy) {
		this.busy = busy;
	}

	public double getV() {
		return v;
	}

	public void setV(double v) {
		this.v = v;
	}
	

	public ReservationID getQ() {
		return Q;
	}

	public void setQ(ReservationID Q) {
		this.Q = Q;
	}

	public int getA() {
		return A;
	}

	public void setA(int A) {
		this.A = A;
	}
	
	public String toString() {
		return " busy: " + busy +
				" A: " +A+ 
				" v: " + v+
				" Q: " + (Q==null? "null":Q.toString()) +
				" index: "+ index;
	}

}
