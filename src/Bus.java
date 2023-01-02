
public class Bus {

	double value;
	ReservationID sourceID;

	public Bus() {

	}

	public Bus(double value, ReservationID sourceID) {
		this.value = value;
		this.sourceID = sourceID;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	public String toString() {
		return sourceID.toString() + " : "+ value + "\n"; 
	}
	

}
