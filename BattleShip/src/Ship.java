/**
 * Object made up of a number of cells that will appear on the board for the players to attempt to sink.
 */

public class Ship {
	
	private Cell[] parts; // Array of the cells the ship is made of
	private int length;
	// Whether or not the ship has sunk
	private boolean isSunk = false;
	
	// Vertical or horizontal
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
	 * Checks to see if a ship has been hit, but not sunk
	 * @param b - the board the ship is located on
	 * @return whether or not the ship has been hit but not sunk
	 */
	public boolean checkHit(Board b) {
		
		// Returning false if it has sunk already
		if(getSunk(b)) {
			return false;
		}
		
		// Setting to false as default
		boolean hit = false;
		
		// Looping through its parts
		for(Cell part : parts) {
			// If any part has been hit, hit is true
			if(part.getState().equals(Cellstate.hit)) {
				hit = true;
			}
		}
		return hit;
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
	 * Finds a cell in the ship that has not been hit yet
	 * @return an unhit cell
	 */
	public Cell notHit() {
		// Loops through all the ship parts
		for(Cell part : parts) {
			// When it finds an unhit part, returning it
			if(!part.getState().equals(Cellstate.hit)) {
				return part;
			}
		}
		// This will not execute, as the method is only called when the ship has not sunk yet
		return null;
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
			// Finding the state of the cells
			String state = part.toString();
			
			// If it's not hit, the ship has not sunk yet
			if(!state.equals("X")) {
				isSunk = false;
			}
		}
		return isSunk;
	}
}
