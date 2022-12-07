
public class StoreBuffer {
	boolean busy;
	int A;
	double v;
	ReservationID ID;

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

	public ReservationID getID() {
		return ID;
	}

	public void setQ(ReservationID ID) {
		this.ID = ID;
	}

	public StoreBuffer(int A) {
		this.A = A;
	}

	public StoreBuffer() {
	}

	public int getA() {
		return A;
	}

	public void setA(int A) {
		this.A = A;
	}

}
