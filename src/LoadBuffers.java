
public class LoadBuffers {

	LoadBuffer station[];
	StationType type;

	public LoadBuffers(int size) {
		station = new LoadBuffer[size];
		type = StationType.LOAD;
		for (int i = 0; i < size; i++) {
			ReservationID reservationID = new ReservationID(type, i);
			station[i] = new LoadBuffer(reservationID);
		}
	}
}
