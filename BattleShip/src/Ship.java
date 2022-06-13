
public class Ship {
	
	private Cell[] parts;//the cells in which a ship is made 
	private int length;
	private boolean isSunk = false;
	private Board playerBoard;
	private String orientation;
	
	//The Constructor
	Ship(int length, Cell[] cells, String orientation) {
		this.orientation = orientation;
		this.parts = cells;
		this.length=length;
	}
	Ship(int length) {
		this.length = length;
	}
	
	public String getOrientation() {
		return orientation;
	}
	
	public int getStartCol() {
		return parts[0].getCol();
	}
	
	public int getStartRow() {
		return parts[0].getRow();
	}
	
	public boolean getSunk(Board b) {
		this.isSunk = checkSunk(b);
		return this.isSunk;
	}
	
	public void setSunk(boolean sunk) {
		isSunk = sunk;
	}
	
	public Cell[] getParts() {
		return parts;
	}
	
	public int getLength() {
		return length;
	}
	//Check if the ship has been fully hit/if it has been sunken
	private boolean checkSunk(Board b) {
		int r;
		int c;
		
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
	
	public void addShip(Cell[] cells) {
		parts=cells;
	}
}