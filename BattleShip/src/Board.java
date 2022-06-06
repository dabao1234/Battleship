
public class Board {
	private Cell[][] board;
	private int row;
	private int col;
	private Player p;

	// constructor change
	public Board(int aRows, int aCols) {
		row = aRows;
		col = aCols;
		board = new Cell[aRows][aCols];
		for (int i = 0; i < aRows; i++) {
			for (int j = 0; j < aCols; j++) {
				board[i][j] = new Cell(i, j); // no color
			}
		}
	}

	/**
	 * default board is a array of 10 by 10 cells
	 */
	public Board(Player p1) {
		p=p1;
		row = 10;
		col = 10;
		board = new Cell[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				board[i][j] = new Cell(i, j); // no color
			}
		}
		// fills board with empty cells
	}

	public Ship placeShipVertical(int startrow, int startcol, int shipLength) {
		// place the start of the ship and then set the state of every cell upwards to
		// ship

		Cell[] cell = new Cell[shipLength];
		boolean isEmpty = false;
		isEmpty = checkAroundVertical(startrow, startcol, shipLength);// checks around the ship
		System.out.println(isEmpty);
		if (isEmpty == true) {// if it is empty place ship
			for (int i = 0; i < shipLength; i++) {
				board[startrow+i][startcol].setState(Cellstate.ship);
			}
		}
		else {
			System.out.println("error");
		}

		Ship newShip = new Ship(shipLength, cell);
		return newShip;

	}

	public void placeShipHorizontall(int startrow, int startcol, int shipLength) {
		// place the start of the ship and then set the state of evercell upwards to
		// ship
		// to avoid overlap check if all cells are empty
		boolean isEmpty = false;
		isEmpty=checkOverlapHorizontal( startrow, startcol,  shipLength);
		System.out.println(isEmpty);
		if (isEmpty == true) {
			for (int i = 0; i < shipLength; i++) {
				board[startrow][startcol + i].setState(Cellstate.ship);
				// ADD THESE CELLS TO THE SHIP OBJECT
			}
		}
		else {
			System.out.println("error");
		}
	}

	public boolean hasShip(int row, int col) {
		if (board[row][col].getState().equals(Cellstate.ship)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isHit(int row, int col) {
		if (board[row][col].getState().equals(Cellstate.hit)) {
			return true;
		} else {
			return false;
		}
	}

	// sets the enum of all ships to the state
	public void hideShips() {

	}

	/**
	 * 
	 * @param startrow where the ship starts in the row
	 * @param startcol where the ship starts column
	 * @return
	 */
	public boolean checkAroundVertical(int startrow, int startcol, int shiplength) {
		
			int col=startcol;
			int row=startrow;
			
			
			// checks vertical overlap
			for (int i = 0; i < shiplength; i++) {// plus two as it needs to check above and below ship length
				try {
				if ((board[row + i][col].getState()).equals(Cellstate.ship)) {
					return false;
				}
				}catch(Exception e) {
					
				}
			}

			// checks to the left
			row = startrow - 1;
			col = startcol - 1;// this happens os it will start the colloum below start
			for (int i = 0; i < shiplength + 2; i++) {// plus two as it needs to check above and below ship length
				try {
				if ((board[row + i][col].getState()).equals(Cellstate.ship)) {
					return false;
				}
				}catch(Exception e) {
					
				}
			}
			row=startrow-1;
			col = startcol + 1;// checks to the right of the place
			for (int i = 0; i < shiplength + 2; i++) {// plus two as it needs to check above and below ship length
				try {
				if ((board[row + i][col].getState()).equals(Cellstate.ship)) {
					return false;
				}
				}catch(Exception e) {
					
				}
			}

			
	
	

		return true;
	}
	
	public boolean checkOverlapHorizontal(int startrow, int startcol, int shiplength) {
		//checks above 
		int row=startrow;
		int col=startcol;
		row=startrow-1;
		col=startcol-1;//always starts one col behid 
		
		for(int i=0;i<shiplength+2;i++) {
		
			try {
			
			if((board[row][col+i].getState()).equals(Cellstate.ship)) {
				return false;
			}
			}catch(Exception e) {
				System.out.println("error");
			}
		}
		
		//checks mid
		row=row+1;//resets start row 
		for(int i=0;i<shiplength+2;i++) {
			if((board[row][startcol+i].getState()).equals(Cellstate.ship)) {
				return false;
			}
		}
		
		//checks below 
		
		row=startrow+1;//goes below
		for(int i=0;i<shiplength+2;i++) {
			try {
			if(board[row][col+i].getState().equals(Cellstate.ship)) {
				return false;
			}
			}catch(Exception e) {
				
			}
		}
		
		return true;
	}
	
	public void nuke() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				board[i][j].setState(Cellstate.hit); // no color
			}
		}
	}
	public boolean hasAttempt(int row, int col) {
		if (board[row][col].getState().equals(Cellstate.hit) || board[row][col].getState().equals(Cellstate.miss)) {
			return true;
		} else {
			return false;
		}
	}

	public void shoot(int row, int col) {
		// checks to see if there's a ship there
		if (hasShip(row, col) == true) {
			// checks to see if the ship has been hit already
			if (!isHit(row, col) == true) {
				board[row][col].setState(Cellstate.hit);
			} else {
				System.out.println("Bad");
			}
		} else {
			board[row][col].setState(Cellstate.miss);
		}
	}

	// public boolean isSunk() {

	// }
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
