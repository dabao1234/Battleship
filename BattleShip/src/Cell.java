
public class Cell {
	private Cellstate state;
	
	//always empty unless told otherwise 
	public Cell() {
		state = Cellstate.empty;
	}
	// sets the state
	public void setState(Cellstate state) {
		this.state = state;
	}

	// gets state
	public Cellstate getState() {
		return state;
	}
	
	

	// To string
	@Override
	public String toString() {
		switch (state) {
		case miss:
			return "M";
		case hit:
			return "X";
		case empty:
			return "-";
		case ship:
			return "S";
		default:
			return "-";
		}

	}
}
