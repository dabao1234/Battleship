import java.util.Scanner;


public class Driver {
	

	public static void main(String[] args) {
		final int NUM_SHIPS = 5; 
		
		Player p = Player.P1;

		Scanner in = new Scanner(System.in);
		
		boolean gameOver = false;
		boolean placed = false;
		
		Board b = new Board(p);
		
		int shipAlign = 0;
		int startCol = 0;
		int row = 0;
		int lengthShip = 0;
		
		for(int a = 0; a <= 2; a++) {
				//Section to place the ship 
				b.display();
				
				//First Player 5 Ships
				//The user will input 1 for horizontal and 2 for vertical 
				System.out.println(p + " please Place Your Ships: Enter 1 if you would like to place"
						+ " a ship horizontally or 2 if you would like to place a ship vertically.");
				for(int x = 0; x < NUM_SHIPS; x++ ) {
					
					shipAlign = getAlign(in);
					
					//Get the values for the ship placement
					System.out.println("Please enter the starting column: ");
					startCol = robustInt(in) - 1;
					System.out.println("Please enter the starting row: ");
					row = robustInt(in) - 1;
					System.out.println("Please enter the ship length: ");
					lengthShip = robustInt(in);
					
					//Placing the Ship
					if(shipAlign == 1) {
						b.placeShipHorizontall(row, startCol, lengthShip);
						b.display();
					}
					else if(shipAlign == 2) {
						b.placeShipVertical(row, startCol, lengthShip);
						b.display();
					}
					else {
						System.out.println("Invalid value.");
					}
					//Re Say the Prompt
					System.out.println("Enter 1 if you would like to place a ship horizontally or 2 if you would like to place a ship vertically.");
				}
				
			p = Player.P2;
		}
		
		int col;
		
		while(!gameOver) {
			System.out.println("Please enter the column: ");
			col = robustInt(in) - 1;
			System.out.println("Please enter the row: ");
			row = robustInt(in) - 1;
			
			shoot(row, col, b);
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
		if(in.nextInt() == 1) {
			return 1;
		}
		else if(in.nextInt() == 2) {
			return 2; 
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
				if (String.valueOf(value).length() < 0 && String.valueOf(value).length() > 10 ) {
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
	
	static void shoot(int r, int c, Board b) {
		b.shoot(r, c);
		b.display();
	}
	
}
