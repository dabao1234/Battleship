
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
				board[i][j] = new Cell(i, j); // no color
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
		isEmpty = checkOverlapVertical(startrow, startcol, shipLength);// checks around the ship
		System.out.println(isEmpty);
		if (isEmpty == true) {// if it is empty place ship
			for (int i = 0; i < shipLength; i++) {
				board[startrow+i][startcol].setState(Cellstate.ship);
			}
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
<<<<<<< HEAD

	// sets the enum of all ships to the state
=======
	
	// makes every cell invisible, so ships will display as blank 
>>>>>>> branch 'main' of https://github.com/dabao1234/Battleship
	public void hideShips() {
<<<<<<< HEAD

	}

	/**
	 * 
	 * @param startrow where the ship starts in the row
	 * @param startcol where the ship starts column
	 * @return
	 */
	public boolean checkOverlapVertical(int startrow, int startcol, int shiplength) {

		try {
			// checks vertical overlap
			for (int i = 0; i < shiplength + 2; i++) {// plus two as it needs to check above and below ship length
				if (board[startrow + 1][startcol].equals(Cellstate.ship)) {
					return false;
				}
			}

			// checks to the left
			startrow = startrow - 1;
			startcol = startcol - 1;// this happens os it will start the colloum below start
			// checks the row to the left
			for (int i = 0; i < shiplength + 2; i++) {// plus two as it needs to check above and below ship length
				if (board[startrow + i][startcol].equals(Cellstate.ship)) {
					return false;
				}
			}

			for (int i = 0; i < shiplength + 2; i++) {// plus two as it needs to check above and below ship length
				if (board[startrow + 1][startcol].equals(Cellstate.ship)) {
					return false;
				}
			}

			startcol = startcol + 1;// checks to the right of the place
		} catch (Exception e) {
			System.out.println("out of bounds");
			return false;
		}

		return true;
=======
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				board[i][j].setVisible(false);
			}
		}
>>>>>>> branch 'main' of https://github.com/dabao1234/Battleship
	}
	
	public boolean checkOverlapHorizontal(int startrow, int startcol, int shiplength) {
		//checks above 
		startrow=startrow-1;
		startcol=startcol-1;//always starts one col behid 
		for(int i=0;i<shiplength+2;i++) {
			if(board[startrow][startcol+i].equals(Cellstate.ship)) {
				return false;
			}
		}
		
		//checks mid]
		startrow=startrow+1;//resets start row 
		for(int i=0;i<shiplength+2;i++) {
			if(board[startrow][startcol+i].equals(Cellstate.ship)) {
				return false;
			}
		}
		
		//checks below 
		
		startrow=startrow+1;//goes below
		for(int i=0;i<shiplength+2;i++) {
			if(board[startrow][startcol+i].equals(Cellstate.ship)) {
				return false;
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
<<<<<<< HEAD

	public void shoot(int row, int col) {
=======
	
	/**
	 * 
	 * @param row
	 * @param col
	 * @return whether or not the shot has succeeded
	 */
	
	public boolean shoot(int row, int col) {
>>>>>>> branch 'main' of https://github.com/dabao1234/Battleship
		// checks to see if there's a ship there
		if (hasShip(row, col) == true) {
			// checks to see if the ship has been hit already
<<<<<<< HEAD
			if (!isHit(row, col) == true) {
=======
			if(!isHit(row, col) == true) {
				// if not, make it a hit
>>>>>>> branch 'main' of https://github.com/dabao1234/Battleship
				board[row][col].setState(Cellstate.hit);
<<<<<<< HEAD
			} else {
=======
			}
			else {
				// if so, we want them to enter another thing
>>>>>>> branch 'main' of https://github.com/dabao1234/Battleship
				System.out.println("Bad");
				return false;
			}
<<<<<<< HEAD
		} else {
=======
		}
		// if no ship there, make it a miss
		else {
>>>>>>> branch 'main' of https://github.com/dabao1234/Battleship
			board[row][col].setState(Cellstate.miss);
		}
		return true;
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
			// go to the next line for the next row of ships
			System.out.println();
		}
	}
}
