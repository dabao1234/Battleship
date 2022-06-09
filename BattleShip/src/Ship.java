
public class Ship {
	
	private Cell[] parts;//the cells in which a ship is made 
	private int length;
	private boolean isSunk = false;
	private Board playerBoard;
	private String orientation;
	
	//constructor 
	Ship(int length, Cell[] cells, String orientation) {
		this.orientation = orientation;
		cells = parts;
		this.length=length;
	}
	Ship(int lenght) {
		this.length=length;
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
		checkSunk(b);
		return isSunk;
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
	
	public boolean checkSunk(Board b) {
		int r;
		int c;
		
		isSunk = true;
		for(Cell part : parts) {
			r = part.getRow();
			c = part.getCol();
			
			Cell z = b.getCells()[r][c];
			
			if(z.isHit() == false) {
				isSunk = false;
			}
		}
		return isSunk;
	}
	
	public void addShip(Cell[] cells) {
		parts=cells;
	}
}