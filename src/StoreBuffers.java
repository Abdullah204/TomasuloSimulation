
public class StoreBuffers {
	StoreBuffer station[];
	public StoreBuffers(int size) {
		station = new StoreBuffer[size];
		for(int i = 0 ; i  < size ; i++) {
			station[i] = new StoreBuffer();
		}
}
	public StoreBuffer[] getStation() {
		return station;
	}
	public void setStation(StoreBuffer[] station) {
		this.station = station;
	}

}
