public class Board {
	private Cell[][] board;
	private int row;
	private int col;
	private Player player;

	// Constructor Change
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
	
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param p - the player Constructor for the board, creates a 10 by 10
	 */
	public Board(Player p) {
		row = 10;
		col = 10;
		player = p;
		board = new Cell[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				board[i][j] = new Cell(i, j); // no color
			}
		}
		// fills board with empty cells
	}

	/**
	 * 
	 * @return the board
	 */
	public Cell[][] getCells() {
		return board;
	}

	/**
	 * 
	 * @param startrow where the ship starts in the row
	 * @param startcol where the ship starts column
	 * @return 
	 */
	public boolean checkAroundVertical(int startRow, int startCol, int shipLength) {

		int col = startCol;
		int row = startRow;

		// checks vertical overlap
		for (int i = 0; i < shipLength; i++) {// plus two as it needs to check above and below ship length
			try {
				if ((board[row + i][col].getState()).equals(Cellstate.ship)) {
					return false;
				}
			} catch (Exception e) {

			}
		}

		// checks to the left
		row = startRow - 1;
		col = startCol - 1;// this happens so it will start the column below start
		for (int i = 0; i < shipLength + 2; i++) {// plus two as it needs to check above and below ship length
			try {
				if ((board[row + i][col].getState()).equals(Cellstate.ship)) {
					return false;
				}
			} catch (Exception e) {

			}
		}
		col = startCol + 1;// checks to the right of the place
		for (int i = 0; i < shipLength + 2; i++) {// plus two as it needs to check above and below ship length
			try {
				if ((board[startRow + i][col].getState()).equals(Cellstate.ship)) {
					return false;
				}
			} catch (Exception e) {

			}
		}

		return true;
	}
	/**
	 * 
	 * @param startRow - the starting row
	 * @param startCol - the starting column
	 * @param shipLength - the ship length
	 * @return false if any ships overlap each other or if there is an invalid placement
	 */
	public boolean checkOverlapHorizontal(int startRow, int startCol, int shipLength) {
		try {
			// checks above
			int row = startRow;
			int col = startCol;
			row = startRow - 1;
			col = startCol - 1;// always starts one col behid

			for (int i = 0; i < shipLength + 2; i++) {

				try {
					if ((board[row][col + i].getState()).equals(Cellstate.ship)) {
						return false;
					}
				} catch (Exception e) {
				}
			}

			// checks mid
			row = row + 1;// resets start row
			for (int i = 0; i < shipLength + 2; i++) {
				if ((board[row][startCol + i].getState()).equals(Cellstate.ship)) {
					return false;
				}
			}

			// checks below
			row = startRow + 1;// goes below
			for (int i = 0; i < shipLength + 2; i++) {
				try {
					if (board[row][col + i].getState().equals(Cellstate.ship)) {
						return false;
					}
				} catch (Exception e) {

				}
			}
			return true;
		} catch (Exception f) {
			// idk about this
			return false;
		}

	}
	/**
	 * 
	 * @param startRow - the starting row
	 * @param startCol - the starting column
	 * @param shipLength - the length of the ship
	 * @return the new ship that is placed, will return null if the ship cannot be placed
	 */
	public Ship placeShipVertical(int startRow, int startCol, int shipLength) {
		// place the start of the ship and then set the state of every cell upwards to
		// ship
		
		Cell[] cell = new Cell[shipLength];
		boolean isEmpty = false;
		isEmpty = checkAroundVertical(startRow, startCol, shipLength);// checks around the ship
		// if it is empty place ship
		if (isEmpty == true) {
			for (int i = 0; i < shipLength; i++) {
				board[startRow + i][startCol].setState(Cellstate.ship);
				try {
					cell[i] = board[startRow + i][startCol];
				} catch (Exception k) {
				}
			}
		} else {
			// System.out.println("Invalid placement, try again.");
			return null;
		}

		Ship newShip = new Ship(shipLength, cell, "V");
		return newShip;

	}
	/**
	 * 
	 * @param startRow - the starting row
	 * @param startCol - the starting column
	 * @param shipLength - the ship length
	 * @return the new ship that is placed horizontally, returns null if it cannot be placed
	 */
	public Ship placeShipHorizontal(int startRow, int startCol, int shipLength) {
		// place the start of the ship and then set the state of evercell upwards to
		// ship
		// to avoid overlap check if all cells are empty
		
		Cell[] cell = new Cell[shipLength];
		boolean isEmpty = false;
		isEmpty = checkOverlapHorizontal(startRow, startCol, shipLength);

		if (isEmpty == true) {
			for (int i = 0; i < shipLength; i++) {
				board[startRow][startCol + i].setState(Cellstate.ship);
				// ADD THESE CELLS TO THE SHIP OBJECT
				try {
					cell[i] = board[startRow][startCol + i];
				} catch (Exception k) {
				}
			}
		} else {
			System.out.println("Invalid placement, try again.");
			return null;
		}

		Ship newShip = new Ship(shipLength, cell, "H");
		return newShip;
	}
	/**
	 * 
	 * @param ship
	 */
	public void clearSunk(Ship ship) {
		int shipLength = ship.getLength();
		int startRow = ship.getStartRow();
		int startCol = ship.getStartCol();

		if (ship.getOrientation().equals("V")) {
			// checks to the left
			int row = startRow - 1;
			int col = startCol - 1; //this happens so it will start the column above start
			
			for(int x = 0; x < shipLength+2; x++) {
				try {
					board[row + x][col].setState(Cellstate.miss);
				}catch(Exception e) {
					
				}
			}
			
			col = startCol + 1;
			
			for(int x = 0; x < shipLength+2; x++) {
				try {
					board[row + x][col].setState(Cellstate.miss);
				}catch(Exception e) {
					
				}
			}
			
			try {
				board[row][startCol].setState(Cellstate.miss);
			}catch(Exception e) {
				
			}
			try {
				board[startRow + shipLength][startCol].setState(Cellstate.miss);
			}catch(Exception e) {
				
			}
			
		} else {
			int row = startRow;
			int col = startCol;
			
			row = startRow - 1;
			col = startCol - 1;
			
			for(int x = 0; x < shipLength+2; x++) {
				try {
					board[row][col + x].setState(Cellstate.miss);
				}catch(Exception e) {
					
				}
			}
				
			row = startRow + 1;
			
			for(int x = 0; x < shipLength+2; x++) {
				try {
					board[row][col + x].setState(Cellstate.miss);
				}catch(Exception e) {
					
				}
			}
					
			try {
				board[startRow][col].setState(Cellstate.miss);
			}catch(Exception e) {
				
			}
			try {
				board[startRow][startCol + shipLength].setState(Cellstate.miss);
			}catch(Exception e) {
				
			}

		}

	}
	/**
	 * 
	 * @param row - the row
	 * @param col - the column
	 * @return true if this section has a ship, return false if it doesn't not contain a ship
	 */
	public boolean hasShip(int row, int col) {
		if (board[row][col].getState().equals(Cellstate.ship) || board[row][col].getState().equals(Cellstate.hit)) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 
	 * @param row - the row
	 * @param col - the column
	 * @return true if the ship has been hit, false if it has not
	 */
	public boolean isHit(int row, int col) {
		if (board[row][col].getState().equals(Cellstate.hit)) {
			return true;
		} else {
			return false;
		}
	}

	// Makes the ships invisible/hides the ship
	public void hideShips() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				board[i][j].setVisible(false);
			}
		}
	}

	//Make the ships visible
	public void showShips() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				board[i][j].setVisible(true);
			}
		}
	}
	/**
	 * 
	 * @param row - the row
	 * @param col - the column
	 * @return true if the user has hit or missed 
	 */
	public boolean hasAttempt(int row, int col) {
		if (board[row][col].getState().equals(Cellstate.hit) || board[row][col].getState().equals(Cellstate.miss)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param row
	 * @param col
	 * @return whether or not the turn is finished (false will allow another shot to be chosen (if you hit
	 * or you aim at an already-hit cell) and true will end the turn (if you miss))
	 */

	public boolean shoot(int row, int col) {
		// checks to see if there's a ship there
		if (hasShip(row, col)) {
			// checks to see if the ship has been hit already
			if (!hasAttempt(row, col)) {
				// if not, make it a hit
				board[row][col].setState(Cellstate.hit);
				return false;
			} else {
				// if so, we want them to enter another thing
				System.out.println("Try again!");
				return false;
			}
		}
		// if no ship there, make it a miss
		else if(!hasAttempt(row, col)){
			board[row][col].setState(Cellstate.miss);
		}
		// if no ship but you've already shot there, try again
		else {
			System.out.println("Try again!");
			return false;
		}
		return true;
	}

	// public boolean isSunk() {

	// }

	//Displays the board
	public void display() {
		System.out.println(player + "'s board:");
		System.out.println("  1 2 3 4 5 6 7 8 9 10");
		for (int i = 0; i < row; i++) {
			System.out.print(i + 1 + " ");
			for (int j = 0; j < col; j++) {
				System.out.print(board[i][j] + " ");

			}
			// go to the next line for the next row of ships
			System.out.println();
		}
	}
}