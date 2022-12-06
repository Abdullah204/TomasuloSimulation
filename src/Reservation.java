
public class Reservation {
	ReservationID ID;
	boolean busy;
	Op op;
	double Vj;
	double Vk;
	ReservationID Qj;
	ReservationID Qk;
	int A;

	public Reservation(ReservationID ID, boolean busy, Op op, double vj, double vk, ReservationID qj, ReservationID qk, int a) {
		this.ID = ID;
		this.busy = busy;
		this.op = op;
		this.Vj = vj;
		this.Vk = vk;
		this.Qj = qj;
		this.Qk = qk;
		this.A = a;
	}
	
	public Reservation(ReservationID ID) {
		this.busy = false;
		this.ID = ID;
	}

	public ReservationID getID() {
		return ID;
	}

	public void setID(ReservationID iD) {
		ID = iD;
	}

	public boolean isBusy() {
		return busy;
	}

	public void setBusy(boolean busy) {
		this.busy = busy;
	}

	public Op getOp() {
		return op;
	}

	public void setOp(Op op) {
		this.op = op;
	}

	public double getVj() {
		return Vj;
	}

	public void setVj(double vj) {
		Vj = vj;
	}

	public double getVk() {
		return Vk;
	}

	public void setVk(double vk) {
		Vk = vk;
	}

	public ReservationID getQj() {
		return Qj;
	}

	public void setQj(ReservationID qj) {
		Qj = qj;
	}

	public ReservationID getQk() {
		return Qk;
	}

	public void setQk(ReservationID qk) {
		Qk = qk;
	}

	public int getA() {
		return A;
	}

	public void setA(int a) {
		A = a;
	}

}
