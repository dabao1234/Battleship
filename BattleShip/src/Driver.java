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
			
		while(x < NUM_SHIPS) {
			if(place(in, b1, p, x, p1Ship)) {
				x++;
			}
		}
		
		System.out.println("Player one's ships placed! Player two, place now:");
		
		p = Player.P2;
		x = 0;
		
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
	 * @return
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
	 */
	
	static boolean shoot(int r, int c, Board b) {
		if(!b.shoot(r, c)) {
			b.display();
			return true;
		}
		b.display();
		return false;
	}
	

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
		
		
		//Placing the Ship
		if(shipAlign == 1) {
			lengthShip = lengthShips[count];
			Ship s;
			
			s = b.placeShipHorizontal(row, startCol, lengthShip);
		
			if(s == null) {
				return false;
			}
			else {
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
			
			ships.add(b.placeShipVertical(row, startCol, lengthShip));
			b.display();
			return true;
		}
		return false;	
	}
	
	static boolean turn(Scanner in, Player p, Board b1, Board b2) {
		int row;
		int col;
		
		System.out.println("Please enter the column: ");
		col = robustInt(in) - 1;
		System.out.println("Please enter the row: ");
		row = robustInt(in) - 1;
		
		if(p.equals(Player.P1)) {
			if(shoot(row, col, b2)) {
				b2.showShips();
				b1.hideShips();
				p = Player.P2;
				System.out.println("arrayboy");
				return true;
			}
			else {
				return turn(in, p, b1, b2);
			}
		}
		else {
			if(shoot(row, col, b1)) {
				b2.hideShips();
				b1.showShips();
				p = Player.P1;
				System.out.println("sdkfahsklfjashflkajsf");
				return true;
			}
			else {
				return turn(in, p, b1, b2);
			}
		}
	}
	
	static boolean checkWin() {
		return true;
	}
	

}



