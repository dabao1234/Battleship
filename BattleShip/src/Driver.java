
public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Board b=new Board();

		b.placeShipHorizontall(0, 2, 5);
		b.display();
		b.placeShipVertical(0, 2, 5);
		b.display();

		
	}

}
