import javafx.scene.control.Button;

public class FancyButtom extends Button {
	private int col;
	private int row;
	public FancyButtom(String s, int coll,int row) {
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
