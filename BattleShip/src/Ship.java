
public class Ship {
	
	private Cell[] parts;//the cells in which a ship is made 
	private int length;
	private boolean isSunk = false;
	private Board playerBoard;
	
	//constructor 
	Ship(int length, Cell[] cells) {
		cells = parts;
		this.length=length;
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