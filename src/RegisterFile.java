
public class RegisterFile {
	Register[] floating;
	Register[] integer;

	public RegisterFile() {
		floating = new Register[32];
		integer = new Register[32];
	}

	public Register[] getFloating() {
		return floating;
	}

	public void setFloating(Register[] f) {
		this.floating = f;
	}

	public double getValueFloating(int index) {
		return floating[index].value;
	}

	public void setValueFloating(int index, double value) {
		floating[index].value = value;
	}

	public ReservationID getQiFloating(int index) {
		return floating[index].Qi;
	}

	public void setQiFloating(int index, ReservationID Qi) {
		floating[index].Qi = Qi;
	}

	public Register getRegisterFloating(int index) {
		return floating[index];
	}

	public void setRegisterFloating(int index, Register register) {
		floating[index] = register;
	}

	public Register[] getInteger() {
		return integer;
	}

	public void setInteger(Register[] integer) {
		this.integer = integer;
	}
	
	
	public double getValueInteger(int index) {
		return integer[index].value;
	}

	public void setValueInteger(int index, double value) {
		integer[index].value = value;
	}

	public ReservationID getQiInteger(int index) {
		return integer[index].Qi;
	}

	public void setQiInteger(int index, ReservationID Qi) {
		integer[index].Qi = Qi;
	}

	public Register getRegisterInteger(int index) {
		return integer[index];
	}

	public void setRegisterInteger(int index, Register register) {
		integer[index] = register;
	}

}
