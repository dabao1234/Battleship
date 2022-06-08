public class Board {
	private Cell[][] board;
	private int row;
	private int col;
	private Player player;

	// constructor change
	public Board(int aRows, int aCols, Player p) {
		row = aRows;
		col = aCols;
		player = p;
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
	public Board(Player p) {
		row = 10;
		col = 10;
		player = p;
		board = new Cell[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				board[i][j] = new Cell(i,j); // no color
			}
		}
		// fills board with empty cells
	}
	
	public Cell[][] getCells(){
		return board;
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
			col = startcol + 1;// checks to the right of the place
			for (int i = 0; i < shiplength + 2; i++) {// plus two as it needs to check above and below ship length
				try {
				if ((board[startrow + i][col].getState()).equals(Cellstate.ship)) {
					return false;
				}
				}catch(Exception e) {
					
				}
			}

		return true;
	}
	
	public boolean checkOverlapHorizontal(int startrow, int startcol, int shiplength) {
		try {
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
		}catch(Exception f) {
			// idk about this
			return false;
		}
		
	}


	public Ship placeShipVertical(int startrow, int startcol, int shipLength) {
		// place the start of the ship and then set the state of every cell upwards to
		// ship

		Cell[] cell = new Cell[shipLength];
		boolean isEmpty = false;
		isEmpty = checkAroundVertical(startrow, startcol, shipLength);// checks around the ship

		if (isEmpty == true) {// if it is empty place ship
			for (int i = 0; i < shipLength; i++) {
				board[startrow+i][startcol].setState(Cellstate.ship);
				try {
					cell[i]=board[startrow+i][startcol];
				}catch(Exception k) {
				}
			}
		}
		else {
			//System.out.println("Invalid placement, try again.");
			return null;
		}

		Ship newShip = new Ship(shipLength, cell, "V");
		return newShip;

	}


	public Ship placeShipHorizontal(int startrow, int startcol, int shipLength) {
		// place the start of the ship and then set the state of evercell upwards to
		// ship
		// to avoid overlap check if all cells are empty
		Cell[] cell = new Cell[shipLength];
		boolean isEmpty = false;
		isEmpty=checkOverlapHorizontal(startrow, startcol, shipLength);

		if (isEmpty == true) {
			System.out.println("arrayboy");
			for (int i = 0; i < shipLength; i++) {
				board[startrow][startcol + i].setState(Cellstate.ship);
				// ADD THESE CELLS TO THE SHIP OBJECT
				try {
					cell[i]=board[startrow+i][startcol];
				}catch(Exception k) {
				}
			}
		}
		else {
			System.out.println("Invalid placement, try again.");
			return null;
		}

		Ship newShip = new Ship(shipLength, cell, "H");
		return newShip;
	}
	
	public void clearSunk(Ship ship) {
		if(ship.getOrientation().equals("V")) {
			int shipLength = ship.getLength();
			int startRow = ship.getStartRow();
			int startCol = ship.getStartCol();
					
			// checks to the left
			int row = startRow - 1;
			int col = startCol - 1;// this happens so it will start the column below start
			
			for (int i = 0; i < shipLength + 2; i++) {// plus two as it needs to check above and below ship length
				board[row + i][col].setState(Cellstate.miss);
			}
			col = startCol + 1;// checks to the right of the place
			
			for (int i = 0; i < shipLength + 2; i++) {// plus two as it needs to check above and below ship length
				board[startRow + i][col].setState(Cellstate.miss);
			}
		}
		else {
			
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
	
	// makes every cell invisible, so ships will display as blank 
	public void hideShips() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				board[i][j].setVisible(false);
			}
		}
	}
	
	public void showShips() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				board[i][j].setVisible(true);
			}
		}
	}
	
	public boolean hasAttempt(int row, int col) {
		if(board[row][col].getState().equals(Cellstate.hit) || board[row][col].getState().equals(Cellstate.miss)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param row
	 * @param col
	 * @return whether or not the shot has succeeded
	 */
	
	public boolean shoot(int row, int col) {
		// checks to see if there's a ship there
		if(hasShip(row, col) == true) {
			// checks to see if the ship has been hit already
			if(!isHit(row, col) == true) {
				// if not, make it a hit
				board[row][col].setState(Cellstate.hit);
				return false;
			}
			else {
				// if so, we want them to enter another thing
				System.out.println("Bad");
				return false;
			}
		}
		// if no ship there, make it a miss
		else {
			board[row][col].setState(Cellstate.miss);
		}
		return true;
	}
	
	
	//public boolean isSunk() {
		
	//}
	
	// displays the board
	public void display() {
		System.out.println("  1 2 3 4 5 6 7 8 9 10");
		for (int i = 0; i < row; i++) {
			System.out.print(i+1 + " ");
			for (int j = 0; j < col; j++) {
				System.out.print(board[i][j] + " ");

			}
			// go to the next line for the next row of ships
			System.out.println();
		}
	}
}
