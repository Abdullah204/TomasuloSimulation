
public class ReservationStation {
	Reservation station[];
	StationType type;
	int latency;
	int size;
	public ReservationStation(int size, StationType type,int latency) {
		this.latency = latency;
		station = new Reservation[size];
		this.type = type;
		for (int i = 0; i < size; i++) {
			ReservationID reservationID = new ReservationID(type, i+1);
			station[i] = new Reservation(reservationID);
		}
		this.size = 0;
	}

	public Reservation[] getStation() {
		return station;
	}

	public void setStation(Reservation[] station) {
		this.station = station;
	}

	public StationType getType() {
		return type;
	}

	public void setType(StationType type) {
		this.type = type;
	}
	
	public String toString() {
		String s = "";
		for (int i = 0; i < station.length; i++) {
			s+= station[i].toString() + "\n";
		}
		return s+"\n";
	}
}
