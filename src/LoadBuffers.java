
public class LoadBuffers {

	LoadBuffer station[];
	StationType type;

	public LoadBuffers(int size) {
		station = new LoadBuffer[size];
		type = StationType.LOAD;
		for (int i = 0; i < size; i++) {
			ReservationID reservationID = new ReservationID(type, i+1);
			station[i] = new LoadBuffer(reservationID);
		}
	}

	public LoadBuffer[] getStation() {
		return station;
	}

	public void setStation(LoadBuffer[] station) {
		this.station = station;
	}

	public StationType getType() {
		return type;
	}

	public void setType(StationType type) {
		this.type = type;
	}
}
