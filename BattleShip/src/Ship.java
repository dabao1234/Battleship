
public class Ship {
	
	private Cell[] parts;//the cells in which a ship is made 
	private int length;
	private boolean isSunk = false;
	private Board playerBoard;
	private String orientation;
	
	/**
	 * 
	 * @param length - the length of the ship
	 * @param cells - an array of the cells on the board the ship is comprised of
	 * @param orientation - vertical or horizontal orientation
	 */
	Ship(int length, Cell[] cells, String orientation) {
		this.orientation = orientation;
		this.parts = cells;
		this.length = length;
	}
	
	/**
	 * Gets the orientation of the ship 
	 * @return the orientation
	 */
	public String getOrientation() {
		return orientation;
	}
	
	/**
	 * Gets the start column of the ship 
	 * @return the column of the first cell in the ship
	 */
	public int getStartCol() {
		return parts[0].getCol();
	}
	/**
	 * Gets the starting row of the ship 
	 * @return the row of the first cell in the ship
	 */
	public int getStartRow() {
		return parts[0].getRow();
	}
	/**
	 * Updates the ship's sunk status and returns it 
	 * @return whether or not the ship has sunk
	 */
	public boolean getSunk(Board b) {
		this.isSunk = checkSunk(b);
		return this.isSunk;
	}
	
	/**
	 * Gets the cells in the ship
	 * @return the array of cells the ship is comprised of
	 */
	public Cell[] getParts() {
		return parts;
	}
	
	/**
	 * Gets the length of the ship 
	 * @return the ship's length
	 */
	public int getLength() {
		return length;
	}
	
	/**
	 * Checks if each cell in the ship has been hit to determine if it is sunk
	 * @param b - the board the ship belongs to
	 * @return whether or not it's been sunk
	 */
	private boolean checkSunk(Board b) {
		isSunk = true;
		//Loops through each part of the ship and checks if it is hit
		for(Cell part : parts) {

			String state = part.toString();

			if(!state.equals("X")) {
				isSunk = false;
			}
		}
		return isSunk;
	}
}