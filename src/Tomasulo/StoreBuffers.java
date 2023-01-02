package Tomasulo;

public class StoreBuffers {
	StoreBuffer station[];
	int latency;
	public StoreBuffers(int size, int latency) {
		station = new StoreBuffer[size];
		for(int i = 0 ; i  < size ; i++) {
			station[i] = new StoreBuffer();
		}
		this.latency = latency;
}
	public StoreBuffer[] getStation() {
		return station;
	}
	public void setStation(StoreBuffer[] station) {
		this.station = station;
	}

}
