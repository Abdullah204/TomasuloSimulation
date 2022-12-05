
public class StoreBuffers {
	StoreBuffer buffer[];
	public StoreBuffers(int size) {
		buffer = new StoreBuffer[size];
		for(int i = 0 ; i  < size ; i++) {
			buffer[i] = new StoreBuffer();
		}
}

}
