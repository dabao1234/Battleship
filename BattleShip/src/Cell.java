
public class Cell {
	private Cellstate state;
	private int row;
	private int col;
	private boolean isVisible = true;
	
	//always empty unless told otherwise 
	public Cell(int row, int collumn) {
		state = Cellstate.empty;
		col=collumn;
		this.row=row;
	}
	// sets the state
	public void setState(Cellstate state) {
		this.state = state;
	}

	// gets state
	public Cellstate getState() {
		return state;
	}
	
	public boolean isHit() {
		if(state.equals("X")) {
			return true;
		}
		return false;
	}
	
	public void setVisible(boolean t) {
		isVisible = t;
	}
	
	

	// To string
	@Override
	public String toString() {
		if(isVisible == true) {
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
		else {
			switch (state) {
			case miss:
				return "M";
			case hit:
				return "X";
			case empty:
				return "-";
			case ship:
				return "-";
			default:
				return "-";
			}
		}

	}
}
