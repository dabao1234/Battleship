import java.util.Scanner;
public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		boolean programFinished = false;
		Board b=new Board();
		
		
		
		b.display();
		while(!programFinished) {
			//Section to place the ship 
			b.display();
			break;
		}
		
	}
	/**
	 * 
	 * @param in
	 * @return
	 */
	public static int getRow(Scanner in) {
		return in.nextInt();
	}
	
	/**
	 * 
	 * @param in
	 * @return
	 */
	public static int getCol(Scanner in) {
		return in.nextInt();
	}

}
