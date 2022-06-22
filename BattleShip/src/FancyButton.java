import javafx.scene.control.Button;

/**
 * This class makes it so that we can give buttons locations
 */
public class FancyButton extends Button {
	// Initializing the row and column of the button
	private int col;
	private int row;
	
	/**
	 * Constructor to create a button with 
	 * @param s - what should be displayed
	 * @param row - the row of the button
	 * @param coll - the column of the button
	 */
	public FancyButton(String s, int row, int coll) {
		super(s);
		col = coll;
		this.row=row;
	}

	/**
	 * Gets the column of the button
	 * @return the column
	 */
	public int getCol() {
		return col;
	}
	
	/**
	 * Gets the row of the button
	 * @return the row
	 */
	public int getRow() {
		return row;
	}
}
