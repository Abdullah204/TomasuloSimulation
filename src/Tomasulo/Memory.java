package Tomasulo;

public class Memory {
	double arr[];

	public Memory(int size) {
		arr = new double[size];
	}

	public double load(int index) {
		return arr[index];
	}

	public void store(int index, double value) {
		arr[index] = value;
	}

	public double[] getArr() {
		return arr;
	}

	public void setArr(double[] arr) {
		this.arr = arr;
	}

}
