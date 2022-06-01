
public class Board {
	private Cell[][] board;
	private int row;
	private int col;

	// constutor change
	// awesome work
	public Board(int aRows, int aCols) {
		row = aRows;
		col = aCols;
		board = new Cell[aRows][aCols];
		for (int i = 0; i < aRows; i++) {
			for (int j = 0; j < aCols; j++) {
				board[i][j] = new Cell(); // no color
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
				board[i][j] = new Cell(); // no color
			}
		}
		// fills board with empty cells
	}

	public void placeShipVertical(int startrow, int startcol, int shipLength) {
		// place the start of the ship and then set the state of evercell upwards to
		// ship
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

	}

	public void placeShipHorizontall(int startrow, int startcol, int shipLength) {
		// place the start of the ship and then set the state of evercell upwards to
		// ship
		// to avoid overlap check if all cells are empty
		boolean isEmpty = false;
		for (int i = 0; i < shipLength; i++) {
			isEmpty = true;
			if (board[startrow][startcol + i].getState().equals(Cellstate.ship)) {
				isEmpty = false;
				break;
			}
		}
		System.out.println(isEmpty);
		if(isEmpty==true) {
		for (int i = 0; i < shipLength; i++) {
			board[startrow][startcol + i].setState(Cellstate.ship);
		}
		}
	}
	
	public boolean isHit(int row, int col) {
		if(board[row][col].getState().equals(Cellstate.ship)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//sets the enum of all ships to the state  
	public void hideShips() {
		
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
			System.out.println();// evertime row increase go down
		}
	}
}
