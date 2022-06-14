import javafx.scene.control.Button;

public class FancyButton extends Button {
	private int col;
	private int row;
	public FancyButton(String s, int row,int coll) {
		super(s);
		col = coll;
		this.row=row;
	}

	/**
	 * 
	 * @return the coll
	 */
	public int getCol() {
		return col;
	}
	
	public int getRow() {
		return row;
	}
}
