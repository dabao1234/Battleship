import java.util.ArrayList;
import java.util.Scanner;


public class Driver {
	
	public static void main(String[] args) {
		
		final int NUM_SHIPS = 5; 
		ArrayList<Ship> p1Ship = new ArrayList<>();
		ArrayList<Ship> p2Ship = new ArrayList<>();
		
		Player p = Player.P1;

		Scanner in = new Scanner(System.in);
		
		boolean gameOver = false;
		
		Board b1 = new Board(Player.P1);
		Board b2 = new Board(Player.P2);
		
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
		
		//Reset values for player 2
		System.out.println("Player one's ships placed! Player two, place now:");
		p = Player.P2;
		x = 0;
		
		//Loop for player 2 to place ships
		while(x < NUM_SHIPS) {
			if(place(in, b2, p, x, p2Ship)) {
				x++;
			}
		}
		
		System.out.println("Player two's ships placed! Player one, your turn!");
		p = Player.P1;
		
		while(!gameOver) {
			turn(in, p, b1, b2);
		}
		
	}
	/**
	 * 
	 * @param in - the scanner object
	 * @return the row the user has inputed
	 */
	public static int getRow(Scanner in) {
		return in.nextInt();
	}
	/**
	 * Asks for either one or two, and depending on the answer, the user will be 
	 * placing a ship vertically
	 * or horizontally
	 * @param in
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
	 * 
	 * @param in
	 * @return  the col the user has inputed
	 */
	static int robustInt(Scanner in) {
		boolean valid = false;
		int value = 0;
		
		while (valid != true) {
			if (in.hasNextInt()) {
				value = in.nextInt();
				if (value < 1 && value > 9 ) {
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
	 * 
	 * @param r - the row
	 * @param c - the column
	 * @param b - the board
	 * @return true if the missile has been successfully shot or false if it failed
	 */
	
	static boolean shoot(int r, int c, Board b) {
		if(!b.shoot(r, c)) {
			b.display();
			return true;
		}
		b.display();
		return false;
	}
	
	/**
	 * 
	 * @param in - the scanner object
	 * @param b - the board
	 * @param p - the player
	 * @param count - how many times the player has placed a ship
	 * @param ships - the players ships
	 * @return true if the ship is successfully placed or false if the ship is not placed
	 */
	static boolean place(Scanner in, Board b, Player p, int count, ArrayList<Ship> ships) {

		int shipAlign = 0;
		int startCol = 0;
		int row = 0;
		int[] lengthShips = {2,3,3,4,5};
		int lengthShip = 0;
		
		System.out.println("Ship #" + (count + 1));
		System.out.println("Enter 1 if you would like to place a ship horizontally or 2 if you would like to place a ship vertically."
				+ "" + " Length: " + lengthShips[count]);
		
		shipAlign = getAlign(in);
		
		//Get the values for the ship placement
		System.out.println("Please enter the starting column: ");
		startCol = robustInt(in) - 1;
		System.out.println("Please enter the starting row: ");
		row = robustInt(in) - 1;
		
		
		//Placing the Ship, if it is 1 place it horizontally, if it is 2 place it vertically
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
		else if(shipAlign == 2) {
			
			Ship s = b.placeShipVertical(row, startCol, lengthShip);
			lengthShip = lengthShips[count];
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
	 * 
	 * @param in - the users input
	 * @param p - the current player
	 * @param b1 - player 1's board
	 * @param b2 - player 2's board
	 * @return true when the turn is complete
	 */
	static boolean turn(Scanner in, Player p, Board b1, Board b2) {
		int row;
		int col;
		//Getting 
		System.out.println("Please enter the column: ");
		col = robustInt(in) - 1;
		System.out.println("Please enter the row: ");
		row = robustInt(in) - 1;
		
		if(p.equals(Player.P1)) {
			if(shoot(row, col, b2)) {
				b1.hideShips();
				b2.showShips();
				p = Player.P2;
				return true;
			}
			else {
				return turn(in, p, b1, b2);
			}
		}
		else {
			if(shoot(row, col, b1)) {
				b1.showShips();
				b2.hideShips();
				p = Player.P1;
				return true;
			}
			else {
				return turn(in, p, b1, b2);
			}
		}
	}
	
	static boolean checkSunk(Ship ship, Board b) {
		return ship.getSunk(b);
	}
	
	/**
	 * Goes through all the ships and check if they are sunk
	 * @param ships - all the ships
	 * @param b - the board
	 * @return returns true if the player has won
	 */
	static boolean checkWin(ArrayList<Ship> ships, Board b) {
		boolean won = true;
		
		for(Ship s : ships) {
			if(!s.getSunk(b)) {
				won = false;
			}
		}
		return won;
	}
	

}



