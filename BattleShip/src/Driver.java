import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Battleship - A game where the goal is to sink all of your opponent's ships before they sink yours!
 * @author Caitlin Holloway, Christopher Holland, David Bao
 */

public class Driver {
	// Note that the driver class has been lobotomized by yours truly, so the text-based game should no longer
	// function. Please redirect to the GUIDriver to actually play and look at the game code! The driver
	// code is not relevant to the graphical game, its methods were only used as inspiration.
	
	static boolean gameOver = false;
	// Setting the opponent - this is AI for testing purposes
	static Player opponent = Player.AI;
	
	/**
	 * This is useless test code, as our game is graphics-based.
	 * @param args
	 */
	public static void main(String[] args) {
		
		final int NUM_SHIPS = 5; 
		ArrayList<Ship> p1Ship = new ArrayList<>();
		ArrayList<Ship> p2Ship = new ArrayList<>();
		
		Player p = Player.P1;

		Scanner in = new Scanner(System.in);
		
		Board b1 = new Board(Player.P1);
		Board b2;
		if(opponent.equals(Player.P2)) {
			b2 = new Board(Player.P2);
		}
		else {
			b2 = new Board(Player.AI);
		}
		
		//Section to place the ship 
		//First Player 5 Ships
		//The user will input 1 for horizontal and 2 for vertical 
		
		int x = 0;
		//Loop for player 1 to place ships
		while(x < NUM_SHIPS) {
			if(place(in, b1, p, x, p1Ship)) {
				x++;
			}
		}
		
		System.out.println("Player one's ships placed! Player two, place now:");
		p = opponent;
		x = 0;
		
		//Loop for player 2 to place ships
		while(x < NUM_SHIPS) {
			if(place(in, b2, p, x, p2Ship)) {
				x++;
			}
		}
		
		System.out.println("Player two's ships placed!");
		p = Player.P1;
		
		while(!gameOver) {
			turn(in, p, b1, b2, p1Ship, p2Ship);
		}
		
	}
	
	
	/**
	 * Asks for either one or two, and depending on the answer, the user will be 
	 * placing a ship vertically or horizontally
	 * This is NOT used in the graphical game
	 * @param in - the scanner to be used
	 * @return the alignment value, 1 or 2
	 */
	public static int getAlign(Scanner in) {
		boolean valid = false;
		int value = 0;
		
		while (valid != true) {
			if (in.hasNextInt()) {
				value = in.nextInt();
				if (value < 1 || value > 2) {
					System.out.println("Please Enter A Valid Number: ");
				} else if(value == 1){
					valid = true;
					return 1; 
				}
				else if(value == 2) {
					valid = true;
					return 2; 
				}
			} else {
				System.out.println("Please Enter A Valid Number: ");
			}
			in.nextLine();
		}
		return 0;
	}

	/**
	 * Takes user input for an integer between 1 and 9
	 * This is NOT used in the graphical game
	 * @param in - the scanner to take user input
	 * @return  the number the user has input
	 */
	static int robustInt(Scanner in) {
		boolean valid = false;
		int value = 0;
		
		while (valid != true) {
			if (in.hasNextInt()) {
				value = in.nextInt();
				if (value < 1 || value > 10 ) {
					System.out.println("Please Enter A Valid Number: ");
				} else {
					valid = true;
				}
			} else {
				System.out.println("Please Enter A Valid Number: ");
			}
			in.nextLine();
		}
		return value;
	}

	/**
	 * Shoots at the cell the user selects
	 * This is NOT used in the graphical game
	 * @param r - the row
	 * @param c - the column
	 * @param b - the board
	 * @return whether or not to go to the next turn
	 */
	static boolean shoot(int r, int c, Board b, ArrayList<Ship> ships) {
		// Calling the board shoot method to see if the player's turn should end yet
		boolean done = b.shoot(r, c);
		// Checking to see if anyone has won
		if(checkWin(ships, b)) {
			gameOver = true;
			System.out.println("Congratulations " + b.getPlayer() + "!");
			return true;
		}
		
		return done;
	}
	
	/**
	 * Places all of the user's ships for them
	 * This is NOT used in the GUI driver
	 * @param in - the scanner object
	 * @param b - the board
	 * @param p - the player
	 * @param count - how many times the player has placed a ship
	 * @param ships - the players ships
	 * @return true if the ship is successfully placed or false if the ship is not placed
	 */
	static boolean place(Scanner in, Board b, Player p, int count, ArrayList<Ship> ships) {
		
		// Initializing our variables
		int shipAlign = 0;
		int startCol = 0;
		int row = 0;
		int[] lengthShips = {2,3,3,4,5};
		int lengthShip = 0;
		
		Random r = new Random();
		
		System.out.println("Ship #" + (count + 1));
		
		if(p.equals(Player.P1) || p.equals(Player.P2)) {
			System.out.println("Enter 1 if you would like to place a ship horizontally or 2 if you would like to place a ship vertically."
					+ "" + " Length: " + lengthShips[count]);
			
			shipAlign = getAlign(in);
			
			//Get the values for the ship placement
			System.out.println("Please enter the starting column: ");
			startCol = robustInt(in) - 1;
			System.out.println("Please enter the starting row: ");
			row = robustInt(in) - 1;
		}
		else {
			//Ship placement for the AI
			shipAlign = 1 + r.nextInt(2);
			if(shipAlign == 1) {
				startCol = r.nextInt(10);
				row = r.nextInt(10 - lengthShips[count]);
			}
			else {
				startCol = r.nextInt(10 - lengthShips[count]);
				row = r.nextInt(10);
			}
		}
		
		
		//Placing the Ship, if it is 1 place it horizontally
		if(shipAlign == 1) {
			lengthShip = lengthShips[count];
			Ship s;
			
			s = b.placeShipHorizontal(row, startCol, lengthShip);
			//Return false if it cannot be placed
			if(s == null) {
				return false;
			}
			else {
				//Add the ship if it is placed successfully
				ships.add(s);
			}
			b.display();
			return true;
		}
		//Vertical ship placement
		else if(shipAlign == 2) {
			lengthShip = lengthShips[count];
			
			Ship s = b.placeShipVertical(row, startCol, lengthShip);
			if(s == null) {
				return false;
			}
			else {
				ships.add(s);
			}
			
			ships.add(s);
			b.display();
			return true;
		}
		return false;	
	}
	/**
	 * Runs a user's entire turn, including shooting and checking for wins
	 * This is NOT used in the graphical game
	 * @param in - the users input
	 * @param p - the current player
	 * @param b1 - player 1's board
	 * @param b2 - player 2's board
	 * @return true when the turn is complete
	 */
	
	static int lastRow = -1;
	static int lastCol = -1;
	
	static boolean turn(Scanner in, Player p, Board b1, Board b2, ArrayList<Ship> p1Ships, ArrayList<Ship> p2Ships) {
		if(gameOver) {
			return false;
		}
		
		Random r = new Random();
		
		
		System.out.println(p + "'s turn!");
		int row;
		int col;
		//Display player 1's ships and hide player 2's ships
		if(p.equals(Player.P1)) {
			b2.hideShips();
			b1.showShips();
		}
		else {
			//Do the same for player 2
			b2.showShips();
			b1.hideShips();
		}
		
		b1.display();
		b2.display();
		
		//The row and column values for shooting
		if(p.equals(Player.P1) || p.equals(Player.P2)) {
			System.out.println("Please enter the column: ");
			col = robustInt(in) - 1;
			System.out.println("Please enter the row: ");
			row = robustInt(in) - 1;
		}
		else {
			
			Cell z = b1.getRandom();
			
			if(z == null) {
				return false;
			}

			col = z.getCol();
			row = z.getRow();
		
		}
		
		
		//Player 1 or Player 2's turn to shoot the ships
		if(p.equals(Player.P1)) {
			b1.display();
			b2.display();
			if(shoot(row, col, b2, p2Ships)) {
				b2.display();
				p = opponent;
				//Check win condition
				if(checkWin(p2Ships, b2)) {
					gameOver = true;
					System.out.println("Congratulations P1!");
				}
				return turn(in, p, b1, b2, p1Ships, p2Ships);
			}
			else {
				return turn(in, p, b1, b2, p1Ships, p2Ships);
			}
		}
		else {
			b1.display();
			b2.display();
			if(shoot(row, col, b1, p1Ships)) {
				// Goes to the next player's turn
				b1.display();
				p = Player.P1;
				lastCol = -1;
				lastRow = -1;
				
				return turn(in, p, b1, b2, p1Ships, p2Ships);
			}
			else {
				lastCol = col;
				lastRow = row;
				return turn(in, p, b1, b2, p1Ships, p2Ships);
			}
		}
	}
	
	/**
	 * Loops through all of a user's ships and check if they are sunk to
	 * determine whether or not the game has been won
	 * This is NOT called in the graphical game, but the code is reused
	 * @param ships - all the ships
	 * @param b - the board
	 * @return returns true if the player has won
	 */
	static boolean checkWin(ArrayList<Ship> ships, Board b) {
		boolean won = true;
		
		for(Ship s : ships) {
			
			boolean a = s.getSunk(b);
			
			if(a) {
				b.clearSunk(s);
			}
			
			if(!a) {
				won = false;
			}
		}
		return won;
	}
}
