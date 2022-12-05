
public class Reservation {
	ReservationID ID;
	boolean busy;
	Op op;
	double Vj;
	double Vk;
	double Qj;
	double Qk;
	double A;

	public Reservation(ReservationID ID, boolean busy, Op op, double vj, double vk, double qj, double qk, double a) {
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

	public double getQj() {
		return Qj;
	}

	public void setQj(double qj) {
		Qj = qj;
	}

	public double getQk() {
		return Qk;
	}

	public void setQk(double qk) {
		Qk = qk;
	}

	public double getA() {
		return A;
	}

	public void setA(double a) {
		A = a;
	}

}
