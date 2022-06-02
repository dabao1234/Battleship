
public class Board {
	private Cell[][] board;
	private int row;
	private int col;

	// constructor change
	public Board(int aRows, int aCols) {
		row = aRows;
		col = aCols;
		board = new Cell[aRows][aCols];
		for (int i = 0; i < aRows; i++) {
			for (int j = 0; j < aCols; j++) {
				board[i][j] = new Cell(i,j); // no color
			}
		}
	}

	/**
	 * default board is a array of 10 by 10 cells
	 */
	public Board() {
		row = 10;
		col = 10;
		board = new Cell[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				board[i][j] = new Cell(i,j); // no color
			}
		}
		// fills board with empty cells
	}

	public Ship placeShipVertical(int startrow, int startcol, int shipLength) {
		// place the start of the ship and then set the state of every cell upwards to
		// ship
		
		Cell[] cell=new Cell[shipLength];
		boolean isEmpty = false;
		for (int i = 0; i < shipLength; i++) {
			isEmpty = true;
			if (board[startrow + i][startcol].getState().equals(Cellstate.ship)) {
				isEmpty = false;
				break;
			}
		}
		System.out.println(isEmpty);
		if (isEmpty ==true) {// if it is empty place ship
			for (int i = startrow; i < shipLength; i++) {
				board[i][startcol].setState(Cellstate.ship);
			}
		}
		
		Ship newShip=new Ship(shipLength, cell);
		return newShip;

	}

	public void placeShipHorizontall(int startrow, int startcol, int shipLength) {
		// place the start of the ship and then set the state of evercell upwards to
		// ship
		// to avoid overlap check if all cells are empty
		boolean isEmpty = false;
		for (int i = startcol; i < shipLength; i++) {
			isEmpty = true;
			if (board[startrow][i].getState().equals(Cellstate.ship)) {
				isEmpty = false;
				break;
			}
		}
		System.out.println(isEmpty);
		if(isEmpty==true) {
		for (int i = startcol; i < shipLength; i++) {
			board[startrow][i].setState(Cellstate.ship);
			//ADD THESE CELLS TO THE SHIP OBJECT
		}
		}
	}
	
	public boolean hasShip(int row, int col) {
		if(board[row][col].getState().equals(Cellstate.ship)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isHit(int row, int col) {
		if(board[row][col].getState().equals(Cellstate.hit)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//sets the enum of all ships to the state  
	public void hideShips() {
		
	}
	
	public boolean hasAttempt(int row, int col) {
		if(board[row][col].getState().equals(Cellstate.hit) || board[row][col].getState().equals(Cellstate.miss)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void shoot(int row, int col) {
		// checks to see if there's a ship there
		if(hasShip(row, col) == true) {
			// checks to see if the ship has been hit already
			if(!isHit(row, col) == true) {
				board[row][col].setState(Cellstate.hit);
			}
			else {
				System.out.println("Bad");
			}
		}
		else {
			board[row][col].setState(Cellstate.miss);
		}
	}
	
	
	//public boolean isSunk() {
		
	//}
	// displays the board
	public void display() {
		System.out.println("  0 1 2 3 4 5 6 7 8 9");
		for (int i = 0; i < row; i++) {
			System.out.print(i + " ");
			for (int j = 0; j < col; j++) {
				System.out.print(board[i][j] + " ");

			}
			System.out.println();// everytime row increase go down
		}
	}
}
