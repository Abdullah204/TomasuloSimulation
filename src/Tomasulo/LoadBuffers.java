package Tomasulo;

public class LoadBuffers {

	LoadBuffer station[];
	StationType type;
	int latency;
	int size;

	public LoadBuffers(int size, int  latency) {
		station = new LoadBuffer[size];
		type = StationType.LOAD;
		for (int i = 0; i < size; i++) {
			ReservationID reservationID = new ReservationID(type, i+1);
			station[i] = new LoadBuffer(reservationID);
		}
		this.latency = latency;
		size = 0;
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
