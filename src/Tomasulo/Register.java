package Tomasulo;

public class Register {
	double value;
	ReservationID Qi;
	
	public Register(double value , ReservationID Qi) {
		this.value = value;
		this.Qi = Qi;
	}
	
	public ReservationID getQ() {
		return Qi;
	}
	
	public double getValue() {
		return value;
	}
	
	public String toString() {
		return "Qi: " + Qi + " value: " + value;
	}
}
