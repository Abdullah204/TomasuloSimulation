
public class ReservationStation {
	Reservation station[];
	StationType type;

	public ReservationStation(int size, StationType type) {
		station = new Reservation[size];
		this.type = type;
		for (int i = 0; i < size; i++) {
			ReservationID reservationID = new ReservationID(type, i);
			station[i] = new Reservation(reservationID);
		}
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
}
