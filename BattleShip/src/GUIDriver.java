import java.util.ArrayList;


import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class GUIDriver extends Application {
	final int num_Rows = 10;
	final int num_Cols = 10;
	final int WIDTH = 700;
	final int HEIGHT = 600;
	boolean isGame = false;
	boolean placeTurn = true;
	boolean orentation = false; // if false it is vertical, if true it is horizontal
	Player p;
	Label lblTurn = new Label();
	ArrayList<Ship> p1Ships = new ArrayList<>();
	ArrayList<Ship> p2Ships = new ArrayList<>();
	int numP1Placed = 0;
	int numP2Placed = 0;
	int[] lengthShips = { 2, 3, 3, 4, 5 };

	// make list of ships
	@Override
	public void start(Stage stage1) throws Exception {
		// board objects

		Board p1Board = new Board(10, 10, Player.P1);
		Board p2Board = new Board(10, 10, Player.P2);
		// Creates the buttons for P1
		Button[][] tilesP1 = new Button[num_Rows][num_Cols];
		Button[][] tilesP2 = new Button[num_Rows][num_Cols];

		// creates the grid panes to house the buttons
		GridPane boardP1 = new GridPane();
		GridPane boardP2 = new GridPane();

		for (int i = 0; i < num_Rows; i++) {
			for (int z = 0; z < num_Cols; z++) {
				// creates the buttons
				Button bttnP1 = new FancyButton("", i, z);
				Button bttnP2 = new FancyButton("", i, z);
				bttnP1.setPrefSize(100, 50);
				bttnP1.setStyle("-fx-border-color: black;");
				bttnP2.setPrefSize(100, 50);
				bttnP2.setStyle("-fx-border-color: black;");
				tilesP1[i][z] = bttnP1;
				tilesP2[i][z] = bttnP2;
				// sets the color
				tilesP1[i][z].setBackground(Background.fill(Color.BLUE));
				tilesP2[i][z].setBackground(Background.fill(Color.BLUE));
				// adds to the grid pane

				boardP1.add(tilesP1[i][z], i, z);
				boardP2.add(tilesP2[i][z], i, z);
			}
		}

		// HBox to add a label to both boards
		HBox top = new HBox();
		HBox mid = new HBox();

		// labels for the HBox's
		Label lblTop = new Label("");
		Label lblMid = new Label("");

		top.getChildren().add(lblTop);
		mid.getChildren().add(lblMid);

		top.setAlignment(Pos.CENTER);
		mid.setAlignment(Pos.CENTER);

		// Creates P2 play scene

		// Creates play scene

		VBox P2 = new VBox();
		HBox orentations = new HBox();
		Button bttnVert = new Button("Vertical");
		Button bttnHor = new Button("Horizontal");
		orentations.getChildren().addAll(bttnVert, bttnHor);
		P2.getChildren().addAll(lblTurn, top, boardP2, mid, boardP1, orentations);
		Scene game = new Scene(P2, 500, 700);

		// Selection Screen

		VBox select = new VBox();
		Label lblStart = new Label("Welcom to BATTLESHIP!");
		HBox srtButtons = new HBox();
		Button bttnHuman = new Button("Vs Human");
		Button bttnAI = new Button("Vs AI");
		srtButtons.getChildren().addAll(bttnHuman, bttnAI);

		srtButtons.setAlignment(Pos.CENTER);
		select.getChildren().addAll(lblStart, srtButtons);
		select.setAlignment(Pos.CENTER);
		Scene selection = new Scene(select, 200, 200);

		// end Screen
		VBox end = new VBox();
		Label lblEnd = new Label();
		Button bttnReset = new Button("Play Again");
		end.getChildren().addAll(lblEnd, bttnReset);
		end.setAlignment(Pos.CENTER);
		Scene endScreen = new Scene(end, 700, 700);

		stage1.setScene(selection);
		stage1.show();

		p = p.P1;

		// button events
		bttnHuman.setOnAction(e -> {

			stage1.setScene(game);
			stage1.show();
			if (p.equals(Player.P1)) {
				lblTop.setText("Player 2 Board");
				lblMid.setText("Player 1 Board");
			}
		});

		// true means vertical
		bttnVert.setOnAction(e -> {
			orentation = true;
		});

		// false means horizontal
		bttnHor.setOnAction(e -> {
			orentation = false;
		});

		// sets the turn label at the top
		lblTurn.setAlignment(Pos.CENTER);
		lblTurn.setText(p + " turn");
		
		
		for (int i = 0; i < num_Rows; i++) {
		for (int z = 0; z < num_Cols; z++) {				
				// if it is the fist players turn, P2 board is clickable

				// if it is the first players turn, P2 board is clickable

				tilesP2[i][z].setOnAction(e -> {
					int column = ((FancyButton) e.getSource()).getCol();
					int row = ((FancyButton) e.getSource()).getRow();
					if (p.equals(Player.P1)) {
						// if it is in placement mode it enter's this loop
						if (placeTurn == true) {
							// allows player to place the ships till it all placed
							// p1Board.p
							if (orentation == true && p1Ships.size() < 6) {
								p1Ships.add(p1Board.placeShipVertical(column, row, lengthShips[numP1Placed]));
								numP1Placed++;

							}
							if (orentation == false && p1Ships.size() < 6) {
								p1Ships.add(p1Board.placeShipVertical(column, row, lengthShips[numP1Placed]));
								numP1Placed++;
							}
							// does not continue switch players till all ships are placed
						}
						// if it is the game mode enter this logic
						if (isGame == true) {
							p1Board.showShips();
							p2Board.hideShips();
							this.p = Player.P2;
							lblTurn.setText(p + " turn");
						}
					}
				});

				//Players 2 turns P1's board  is clickable
				tilesP1[i][z].setOnAction(e -> {
					if (p.equals(Player.P2)) {
						if (placeTurn == true) {

							// does not continue switch players till all ships are placed
						}
						// game code
						else if (isGame == true) {
							p2Board.showShips();
							p1Board.hideShips();
							this.p = Player.P1;
							lblTurn.setText(p + " turn");
						}
					}
				});
			}
			}
		

	}

	public static void main(String[] args) {
		launch(args);

	}

}
