
public class Ship {
	
	private Cell[] parts;//the cells in which a ship is made 
	private int length;
	private boolean isSunk = false;
	
	//constructor 
	public Ship(int length,Cell[] cells) {
		cells = parts;
		this.length=length;
	}
	
	public boolean getSunk() {
		return isSunk;
	}
	
	public void setSunk(boolean sunk) {
		isSunk = sunk;
	}
	
	private boolean checkSunk() {
		isSunk = true;
		for(Cell part : parts) {
			if(part.isHit() == false) {
				isSunk = false;
			}
		}
	}
	
}
