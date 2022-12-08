
public class Register {
	double value;
	ReservationID Qi;
	
	public Register(double value , ReservationID Qi) {
		this.value = value;
		this.Qi = Qi;
	}
	
	public String toString() {
		return "Qi: " + Qi + " value: " + value;
	}
}
