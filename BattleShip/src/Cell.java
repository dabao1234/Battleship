public class Cell {
	private Cellstate state;
	private int row;
	private int col;
	private boolean isVisible = true;
	
	//always empty unless told otherwise 
	public Cell(int row, int column) {
		state = Cellstate.empty;
		col = column;
		this.row = row;
	}
	// sets the state
	public void setState(Cellstate state) {
		this.state = state;
	}

	// gets state
	public Cellstate getState() {
		return state;
	}
	//gets row
	public int getRow() {
		return row;
	}
	//gets column
	public int getCol() {
		return col;
	}
	
	// checks to see if the cell has been hit
	public boolean isHit() {
		if(state.equals(Cellstate.hit)) {
			return true;
		}
		return false;
	}
	//checks to see if the cell is a miss
	public boolean isMiss() {
		if(state.equals(Cellstate.miss)) {
			return true;
		}
		return false;
	}
	
	// sets the visibility of the cell which will determine whether the ships will be
	// displayed or not
	public void setVisible(boolean t) {
		isVisible = t;
	}
	
	

	// To string
	@Override
	public String toString() {
		// checks to see if the ships should be visible
		if(isVisible == true) {
			switch (state) {
				case miss:
					return "M";
				case hit:
					return "X";
				case empty:
					return "-";
				case ship:
					// displays the ships
					return "S";
				default:
					return "-";
			}
		}
		// otherwise, doesn't display the ships
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
