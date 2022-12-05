
public class ReservationID {
	StationType stationType;
	int index;

	public ReservationID(StationType stationType, int index) {
		this.stationType = stationType;
		this.index = index;
	}

	public StationType getStationType() {
		return stationType;
	}

	public void setStationType(StationType stationType) {
		this.stationType = stationType;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
