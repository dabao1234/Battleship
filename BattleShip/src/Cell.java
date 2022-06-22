/**
 * One piece of the board that the players can shoot at and place ships on
 */
public class Cell {
	// Each cell will have a state
	private Cellstate state;
	
	// Each cell will have a location on the board
	private int row;
	private int col;
	
	// Each cell will have a current visibility
	private boolean isVisible = true;
	
	/**
	 * Creates the cell object. By default it will be empty
	 * @param row - the row the cell is in
	 * @param column - the column the cell is in
	 */
	public Cell(int row, int column) {
		state = Cellstate.empty;
		col = column;
		this.row = row;
	}
	
	/**
	 * Sets the state of the cell
	 * @param state - the desired state
	 */
	public void setState(Cellstate state) {
		this.state = state;
	}

	/**
	 * The current state of the cell
	 * @return the cellstate
	 */
	public Cellstate getState() {
		return state;
	}
	
	/**
	 * Gets the cell's row
	 * @return the row the cell is in
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Gets the cell's column
	 * @return the column the cell is in
	 */
	public int getCol() {
		return col;
	}
	
	/**
	 * Checks to see if the cell has a hit ship
	 * @return whether or not it has a hit ship
	 */
	public boolean isHit() {
		if(state.equals(Cellstate.hit)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks to see if the cell has a missed shot
	 * @return whether or not the cell is a miss
	 */
	public boolean isMiss() {
		if(state.equals(Cellstate.miss)) {
			return true;
		}
		return false;
	}
	
	/**
	 * sets the visibility of the cell which will determine whether any ship on it will be displayed or not
	 * @param t - whether or not it is visible
	 */
	// 
	public void setVisible(boolean t) {
		isVisible = t;
	}

	/**
	 * Displays the cell
	 */
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
		// if not, doesn't display any ships
		else {
			switch (state) {
			case miss:
				return "M";
			case hit:
				return "X";
			case empty:
				return "-";
			// Unhit ships appear as empty spaces
			case ship:
				return "-";
			default:
				return "-";
			}
		}

	}
}
