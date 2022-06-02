import java.util.Scanner;
public class Driver {

	public static void main(String[] args) {
		final int NUM_SHIPS = 5; 
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		boolean programFinished = false;
		Board b=new Board();
		int shipAlign = 0;
		int startCol = 0;
		int endCol = 0;
		int lengthShip = 0;
		
		while(!programFinished) {
			//Section to place the ship 
			b.display();
			
			//First Player 5 Ships
			//The user will input 1 for vertical and 2 for horizontal 
			System.out.println("P1, Please Place Your Ships: Enter 1 if you would like to place"
					+ " a ship vertically or 2 if you would like to place a ship horizontally. ");
			for(int x = NUM_SHIPS; x > 0; x++ ) {
				
				shipAlign = getAlign(in);
				System.out.println("Please enter the starting column: ");
				startCol = in.nextInt();
				System.out.println("Please enter the end column: ");
				endCol = in.nextInt();
				System.out.println("Please enter the ship length: ");
				lengthShip = in.nextInt();
				
				if(shipAlign == 1) {
					b.placeShipHorizontall(startCol, endCol, lengthShip);
					b.display();
				}
				else if(shipAlign == 2) {
					b.placeShipVertical(startCol, endCol, lengthShip);
					b.display();
				}
				else {
					//They have not inputed a proper value 
				}
				System.out.println("P1, Please Place Your Ships: Enter 1 if you would like to place"
						+ " a ship vertically or 2 if you would like to place a ship horizontally. ");
			}
			
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
	public static int getCol(Scanner in) {
		return in.nextInt();
	}
	/**
	 * 
	 */
	
}
