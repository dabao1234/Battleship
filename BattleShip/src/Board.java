import java.util.ArrayList;
import java.util.Random;

/**
 * The player's Battleship board with all their cells
 */

public class Board {
	// The array of cells the board is made up of
	private Cell[][] board;
	
	// The number of rows and columns in the board
	private int row;
	private int col;
	
	// The owner of the board
	private Player player;
	
	// The ArrayList of cells that have not been shot at yet by the AI
	private ArrayList<Cell> possible = new ArrayList<>();

	/**
	 * Constructor to create a board
	 * @param aRows - the number of rows in the board
	 * @param aCols - the number of columns in the board
	 * @param p - the player who owns the board
	 */
	public Board(int aRows, int aCols, Player p) {
		row = aRows;
		col = aCols;
		player = p;
		board = new Cell[aRows][aCols];
		for (int i = 0; i < aRows; i++) {
			for (int j = 0; j < aCols; j++) {
				// Creates all the cells and adds them to the possible list
				board[i][j] = new Cell(i, j); // no color
				possible.add(board[i][j]);
			}
		}
	}
	
	/**
	 * Updates the list of possible cells to shoot at
	 */
	private void updatePossible() {
		possible.clear();
		
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if(board[i][j].getState().equals(Cellstate.empty) || board[i][j].getState().equals(Cellstate.ship))
					possible.add(board[i][j]);
			}
		}
	}
	
	/**
	 * Gets the owner of the board
	 * @return the player who owns the board
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Constructor for the board, creates a 10 by 10
	 * @param p - the player who owns the board
	 */
	public Board(Player p) {
		row = 10;
		col = 10;
		player = p;
		board = new Cell[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				board[i][j] = new Cell(i, j); // no color
				possible.add(board[i][j]);
			}
		}
		// fills board with empty cells
	}

	/**
	 * Returns the actual cells that make up the board
	 * @return the array of cells the board holds
	 */
	public Cell[][] getCells() {
		return board;
	}

	/**
	 * Checks to make sure there is room to place a ship vertically
	 * @param startrow where the ship starts in the row
	 * @param startcol where the ship starts column
	 * @return whether the ship is good to place or not
	 */
	public boolean checkAroundVertical(int startRow, int startCol, int shipLength) {

		int col = startCol;
		int row = startRow - 1;
		
		int counter = 0;

		// checks vertical overlap
		for (int i = 0; i < shipLength + 2; i++) {// plus two as it needs to check above and below ship length
			try {
				if ((board[row + i][col].getState()).equals(Cellstate.ship)) { 
					return false;
				}
			} catch (Exception e) {
				counter++;
			}
		}
		// If the counter is over 2, we know the shipLength has gone out of bounds not just the
		// shipLength + 2 (which can be out of bounds), so the ship can't be placed there and we return
		// false!
		if(counter >= 2) {
			return false;
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
	 * Checks to make sure there is ample room to place a ship horizontally
	 * @param startRow - the starting row
	 * @param startCol - the starting column
	 * @param shipLength - the ship length
	 * @return false if any ships overlap each other or if there is an invalid placement
	 */
	public boolean checkOverlapHorizontal(int startRow, int startCol, int shipLength) {
		try {
			
			int row = startRow;
			int col = startCol;
			row = startRow - 1;
			col = startCol - 1;// always starts one column behind

			// checks above
			for (int i = 0; i < shipLength + 2; i++) {

				try {
					if ((board[row][col + i].getState()).equals(Cellstate.ship)) {
						return false;
					}
				} catch (Exception e) {
				}
			}

			// checks mid
			int counter = 0;
			col = startCol - 1;
			row = startRow; // resets start row
			for (int i = 0; i < shipLength + 2; i++) {
				try {
					if ((board[row][col + i].getState()).equals(Cellstate.ship)) {
						return false;
					}
				}catch(Exception e) {
					// Every time shipLength + 2 is out of bounds of the board, it'll add one to the counter
					counter++;
				}
				
			}
			// If the counter is over 2, we know the shipLength has gone out of bounds not just the
			// shipLength + 2 (which can be out of bounds), so the ship can't be placed there and we return
			// false!
			if(counter >= 2) {
				return false;
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
			return false;
		}

	}
	/**
	 * Places a ship vertically on the board
	 * @param startRow - the starting row
	 * @param startCol - the starting column
	 * @param shipLength - the length of the ship
	 * @return the new ship that is placed, will return null if the ship cannot be placed
	 */
	public Ship placeShipVertical(int startRow, int startCol, int shipLength) {
		// place the start of the ship and then set the state of every cell upwards to
		// ship
		
		// Initializing the ship cells
		Cell[] cell = new Cell[shipLength];
		boolean isEmpty = false;
		// Checks to make sure it's possible to place here
		isEmpty = checkAroundVertical(startRow, startCol, shipLength);// checks around the ship
		
		// if it is empty place ship
		if (isEmpty == true) {
			for (int i = 0; i < shipLength; i++) {
				// Sets the cells to be ships
				
				try {
					// Modifies the ship cell list to include each cell
					board[startRow + i][startCol].setState(Cellstate.ship);
					cell[i] = board[startRow + i][startCol];
				} catch (Exception k) {
				}
			}
		// Returns a null ship object if the placement doesn't go through
		} else {
			return null;
		}
		// Creates the ship object if possible and returns it
		Ship newShip = new Ship(shipLength, cell, "V");
		return newShip;

	}
	/**
	 * Places a ship horizontally on the board
	 * @param startRow - the starting row
	 * @param startCol - the starting column
	 * @param shipLength - the ship length
	 * @return the new ship that is placed horizontally, returns null if it cannot be placed
	 */
	public Ship placeShipHorizontal(int startRow, int startCol, int shipLength) {
		// place the start of the ship and then set the state of every cell to the right as ship
		// to avoid overlap, check if all cells are empty to begin with
		
		// Initializing the ship cells
		Cell[] cell = new Cell[shipLength];
		boolean isEmpty = false;
		// Checks to make sure it's possible to place here
		isEmpty = checkOverlapHorizontal(startRow, startCol, shipLength);

		if (isEmpty == true) {
			for (int i = 0; i < shipLength; i++) {
				// Sets the cells to be ships
				
				try {
					// Modifies the ship cell list to include each cell
					board[startRow][startCol + i].setState(Cellstate.ship);
					cell[i] = board[startRow][startCol + i];
				} catch (Exception k) {
				}
			}
		// Returns a null ship object if the placement doesn't go through
		} else {
			return null;
		}
		// Creates the ship object if possible and returns it
		Ship newShip = new Ship(shipLength, cell, "H");
		return newShip;
	}
	/**
	 * Clears the area around the ship setting them as misses so the user
	 * can't shoot there, since they will be empty
	 * @param ship - the ship that has been sunk
	 */
	public void clearSunk(Ship ship) {
		//The starting values of the ship
		int shipLength = ship.getLength();
		int startRow = ship.getStartRow();
		int startCol = ship.getStartCol();
		
		//Checks whether the ship is placed vertically or horizontally
		if (ship.getOrientation().equals("V")) {
			// checks to the left
			int row = startRow - 1;
			int col = startCol - 1; //this happens so it will start the column above start
						
			// Setting the cells to the left of the ship and the left diagonals to be misses
			for(int x = 0; x < shipLength+2; x++) {
				try {
					// Makes sure it doesn't accidentally override a ship tile!
					// (that shouldn't happen, but it did one single time, so just to be safe)
					if(!board[row + x][col].getState().equals(Cellstate.ship)) {
						board[row + x][col].setState(Cellstate.miss);
					}
				}catch(Exception e) {
					
				}
			}
			
			// Sets the cells to the right of the ship and the right diagonals to be misses
			col = startCol + 1;
			for(int x = 0; x < shipLength+2; x++) {
				// Catches out-of-bounds errors
				try {
					if(!board[row + x][col].getState().equals(Cellstate.ship)) {
						board[row + x][col].setState(Cellstate.miss);
					}
				}catch(Exception e) {
					
				}
			}
			
			// Catches out-of-bounds errors
			try {
				// Sets directly above the ship to be a miss
				if(!board[row][startCol].getState().equals(Cellstate.ship)) {
					board[row][startCol].setState(Cellstate.miss);
				}
			}catch(Exception e) {
				
			}
			try {
				// Sets directly below the ship to be a miss
				if(!board[startRow + shipLength][startCol].getState().equals(Cellstate.ship)) {
					board[startRow + shipLength][startCol].setState(Cellstate.miss);
				}
			}catch(Exception e) {
				
			}
			
		} else {
			//Surrounds the ship if it is placed horizontally 
			int row = startRow;
			int col = startCol;
			
			row = startRow - 1;
			col = startCol - 1;
			
			// Setting above the ship and the above diagonals to be misses
			for(int x = 0; x < shipLength+2; x++) {
				try {
					if(!board[row][col + x].getState().equals(Cellstate.ship)) {
						board[row][col + x].setState(Cellstate.miss);
					}
				}catch(Exception e) {
					
				}
			}
				
			row = startRow + 1;
			// Setting below the ship and the below diagonals to be misses
			for(int x = 0; x < shipLength+2; x++) {
				try {
					if(!board[row][col + x].getState().equals(Cellstate.ship)) {
						board[row][col + x].setState(Cellstate.miss);
					}
				}catch(Exception e) {
					
				}
			}
			//Catches any errors in the case it goes out of bounds
			try {
				// Sets the cell to the left of the ship to be a miss
				if(!board[startRow][col].getState().equals(Cellstate.ship)) {
					board[startRow][col].setState(Cellstate.miss);
				}
			}catch(Exception e) {
				
			}
			try {
				// Sets the cell to the right of the ship to be a miss
				if(!board[startRow][startCol + shipLength].getState().equals(Cellstate.ship)) {
					board[startRow][startCol + shipLength].setState(Cellstate.miss);
				}
			}catch(Exception e) {
				
			}

		}

	}
	/**
	 * Checks to see if there is a ship in the given coordinates
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
	 * Checks to see if the coordinates have been hit
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

	/**
	 * Hides all the ships on a board by making the cells invisible
	 */
	public void hideShips() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				board[i][j].setVisible(false);
			}
		}
	}

	/**
	 * Displays all the ships on the board by making the cells visible
	 */
	public void showShips() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				board[i][j].setVisible(true);
			}
		}
	}
	/**
	 * Checks to see whether or not the spot has already been shot at
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
	 * Returns a random cell in the board that has not been shot at yet
	 * @return a random cell that can be shot at
	 */
	public Cell getRandom() {
		// Updating the list of possible cells to pick from
		updatePossible();
		
		// If there are none to choose from, returning null
		if(possible.size() <= 0) {
			// This should not happen, because a win condition would have executed by this point
			return null;
		}
		
		Random r = new Random();
		
		// Picking a random cell from the amount of cells still left to choose from
		int c = r.nextInt(possible.size());
		Cell d = possible.get(c);
		
		return d;
	}


	/**
	 * Shoots at the given coordinates
	 * @param row - the row we want to shoot at
	 * @param col - the column we want to shoot at
	 * @return whether or not the turn is finished (false will allow another shot to be chosen (if you hit
	 * or you aim at an already-hit cell) and true will end the turn (if you miss))
	 */

	public boolean shoot(int row, int col) {
		// checks to see if there's a ship there
		if (hasShip(row, col)) {
			// checks to see if the ship has been hit already
			if (!hasAttempt(row, col)) {
				// if not, make it a hit
				//this.possible.remove(board[row][col]);
				board[row][col].setState(Cellstate.hit);
				return false;
			} else {
				// if so, we want them to enter another thing
				return false;
			}
		}
		// if no ship there, make it a miss
		else if(!hasAttempt(row, col)){
			board[row][col].setState(Cellstate.miss);
			//this.possible.remove(board[row][col]);
		}
		// if no ship but you've already shot there, try again
		else {
			return false;
		}
		return true;
	}
	//Clear the entire board, change all the states to empty
	public void clear() {
		for(int i=0;i<10;i++) {
			for(int j=0;j<10;j++) {
				board[i][j].setState(Cellstate.empty);
				possible.add(board[i][j]);
			}
		}
	}
	
	/**
	 * Displays the board
	 * Made this do nothing because the driver game is not meant to be played, our Battleship is the GUI
	 */
	public void display() {
		//System.out.println(player + "'s board:");
		//System.out.println("  1 2 3 4 5 6 7 8 9 10");
		for (int i = 0; i < row; i++) {
			//System.out.print(i + 1 + " ");
			for (int j = 0; j < col; j++) {
				//System.out.print(board[i][j] + " ");

			}
			// go to the next line for the next row of ships
			//System.out.println();
		}
	}
}
