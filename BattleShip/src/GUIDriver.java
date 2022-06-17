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
	// Constants for the board
	final int num_Rows = 10;
	final int num_Cols = 10;
	final int WIDTH = 700;
	final int HEIGHT = 600;
	boolean isGame = false;
	boolean placeTurnP1 = true;
	boolean placeTurnP2 = false;
	boolean orientation = false; // if false it is vertical, if true it is horizontal
	Player p;
	Label lblTurn = new Label();
	ArrayList<Ship> p1Ships = new ArrayList<>();
	ArrayList<Ship> p2Ships = new ArrayList<>();
	int numP1Placed = 0;
	int numP2Placed = 0;
	int[] lengthShips = { 2, 3, 3, 4, 5 };

	Player opp;

	// make list of ships
	@Override
	public void start(Stage stage1) throws Exception {
		// board objects
		lblTurn.setAlignment(Pos.CENTER);
		lblTurn.setStyle("-fx-border-color: black; -fx-font-size: 30; -fx-background-color: linear-gradient(to bottom, #f2f2f2, #d4d4d4);");
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

				boardP1.add(tilesP1[i][z], z, i);
				boardP2.add(tilesP2[i][z], z, i);
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
		P2.setStyle(
				"-fx-background-image: url('https://i.pinimg.com/originals/07/8e/53/078e53a1f98f3cf6c77d996e7a98c4b4.jpg')");
		HBox orientations = new HBox();
		Button bttnVert = new Button("Vertical");
		bttnVert.setStyle("-fx-font-size: 20");
		Button bttnHor = new Button("Horizontal");
		bttnHor.setStyle("-fx-font-size: 20");
		orientations.getChildren().addAll(bttnVert, bttnHor);
		P2.setAlignment(Pos.CENTER);
		P2.getChildren().addAll(lblTurn, top, boardP2, mid, boardP1, orientations);
		Scene game = new Scene(P2, 500, 700);

		// Selection Screen

		VBox select = new VBox();
		Label lblStart = new Label("Welcome to Battleship");
		lblStart.setStyle("-fx-font-size: 30; -fx-text-fill: white;");
		HBox srtButtons = new HBox();
		Button bttnHuman = new Button("Vs Human");
		Button bttnAI = new Button("Vs AI");
		srtButtons.getChildren().addAll(bttnHuman, bttnAI);
		select.setStyle("-fx-background-image: url('https://www.cbc.ca/kids/images/battleship_play.jpg')");
		srtButtons.setAlignment(Pos.CENTER);
		select.getChildren().addAll(lblStart, srtButtons);
		select.setAlignment(Pos.CENTER);
		Scene selection = new Scene(select, 1000, 450);

		// end Screen
		VBox end = new VBox();
		end.setStyle("-fx-background-image: url('https://pixeljoint.com/files/icons/full/win__r1340336691.gif')");
		Label lblEnd = new Label();
		lblEnd.setStyle("-fx-font-size: 30; -fx-text-fill: white; -fx-background-color: black");
		Button bttnReset = new Button("Play Again");
		bttnReset.setStyle("-fx-font-size:30");
		end.getChildren().addAll(lblEnd, bttnReset);
		end.setAlignment(Pos.CENTER);
		Scene endScreen = new Scene(end, 750, 700);

		// mid Screen
		VBox a = new VBox();
		Label lblmid = new Label();

		Button bttnbetwen = new Button("Next Player's Turn");

		a.setStyle(
				"-fx-background-image: url('https://c.tenor.com/dIYElE0kJHQAAAAC/wee-ship.gif')");
		a.getChildren().addAll(lblmid, bttnbetwen);

		a.setAlignment(Pos.CENTER);
		Scene betweens = new Scene(a, 450, 370);

		stage1.setScene(selection);
		stage1.show();

		p = p.P1;

		// button events
		bttnHuman.setOnAction(e -> {
			lblTurn.setText(p + "'s turn. " + " Next ship length: " + lengthShips[numP1Placed]);
			stage1.setScene(game);
			stage1.show();
			opp = p.P2;

			if (p.equals(Player.P1)) {
				lblTop.setText("Player 2 Board");
				lblMid.setText("Player 1 Board");
			}
		});

		bttnAI.setOnAction(e -> {

			stage1.setScene(game);
			stage1.show();
			opp = p.AI;

			if (p.equals(Player.P1)) {
				lblTop.setText("Player 2 Board");
				lblMid.setText("Player 1 Board");
			}
		});

		bttnReset.setOnAction(E -> {
			clear(tilesP1, tilesP2);
			p1Board.clear();
			p2Board.clear();

			placeTurnP1 = true;

			stage1.setScene(game);
			stage1.show();
		});

		// true means vertical
		bttnVert.setOnAction(e -> {
			orientation = true;
			System.out.print(orientation);
		});

		bttnHor.setOnAction(e -> {
			orientation = false;

		});

		bttnbetwen.setOnAction(e -> {
			stage1.setScene(game);
			stage1.show();
		});

		// sets the turn label at the top
		lblTurn.setAlignment(Pos.CENTER);
		lblTurn.setText(p + " turn");

		// Action event if the button is placed
		for (int i = 0; i < num_Rows; i++) {
			for (int z = 0; z < num_Cols; z++) {
				// P2's board is clickable if it is player 1's turn
				tilesP1[i][z].setOnAction(e -> {
					lblTurn.setText(p + "'s turn");

					if (placeTurnP1 == true) {

						int column = ((FancyButton) e.getSource()).getCol();
						int row = ((FancyButton) e.getSource()).getRow();

						// if the orientation- vertical or horizontal is true, place the ship
						// horizontally
						if (orientation == true && numP1Placed < 5) {
							Ship maybeShip;
							maybeShip = p1Board.placeShipVertical(row, column, lengthShips[numP1Placed]);
							if (maybeShip != null) {
								p1Ships.add(maybeShip);
								colorTiles(p1Board, tilesP1, lengthShips[numP1Placed]);
								numP1Placed++;
								try {
									lblTurn.setText(p + "'s turn. " + " Next ship length: " + lengthShips[numP1Placed]);
								} catch (Exception l) {

								}
								p1Board.display();
							}
							// If the player chooses to place a ship vertically and the number of ships that
							// are
							// placed is less than 5
						} else if (orientation == false && numP1Placed < 5) {
							Ship maybeShip;
							maybeShip = p1Board.placeShipHorizontal(row, column, lengthShips[numP1Placed]);
							if (maybeShip != null) {
								p1Ships.add(maybeShip);
								colorTiles(p1Board, tilesP1, lengthShips[numP1Placed]);
								numP1Placed++;
								try {
									lblTurn.setText(p + "'s turn. " + " Next ship length: " + lengthShips[numP1Placed]);
								} catch (Exception h) {

								}
								p1Board.display();
							}

						}
						// Checks if all the ships are placed, if it is, swap players turn
						if (numP1Placed == 5) {
							System.out.println("all placed");
							placeTurnP1 = false;
							placeTurnP2 = true;
							lblTurn.setText("P2's Turn");
							p = Player.P2;
							stage1.setScene(betweens);
							stage1.show();
							// hide P1 Ships
							hideShips(p1Board, tilesP1);
						}
					} else if (placeTurnP1 == false && placeTurnP2 == false) {
						if (p.equals(Player.P2)) {

							int column = ((FancyButton) e.getSource()).getCol();
							int row = ((FancyButton) e.getSource()).getRow();

							p1Board.hideShips();// hides on Board
							hideShips(p1Board, tilesP1);// hides on GUI
							p2Board.showShips();// shows on Board
							showShips(p2Board, tilesP2); // shows on GUI

							if (p1Board.shoot(row, column) == true) {
								p = Player.P1;
								stage1.setScene(betweens);
								stage1.show();

								p2Board.hideShips();// hides on Board
								hideShips(p2Board, tilesP2);// hides on GUI
								p1Board.showShips();// shows on Board
								showShips(p1Board, tilesP1); // shows on GUI

								stage1.setScene(betweens);
							}
							;
							p1Board.display();
							colorTilesShoot(p1Board, tilesP1, 1);

							// checks if there is a winner
							if (checkWin(p1Ships, p1Board) == true) {
								// player 2 wins
								lblEnd.setText("Player 2 wins!!!!!");
								stage1.setScene(endScreen);

							}

							// check for a win

						}

					}
				});
				// Ship placement for player 2
				tilesP2[i][z].setOnAction(e -> {
					lblTurn.setText(p + "'s turn");

					if (placeTurnP2 == true) {
						if (opp.equals(Player.P2)) {
							int column = ((FancyButton) e.getSource()).getCol();
							int row = ((FancyButton) e.getSource()).getRow();
							if (orientation == true && numP2Placed < 5) {
								Ship maybeShip;
								maybeShip = p2Board.placeShipVertical(row, column, lengthShips[numP2Placed]);
								if (maybeShip != null) {
									p2Ships.add(maybeShip);
									colorTiles(p2Board, tilesP2, lengthShips[numP2Placed]);
									numP2Placed++;
									try {
										lblTurn.setText(p + "'s turn. " + " Next ship length: " + lengthShips[numP2Placed]);
									} catch (Exception f) {

									}
									p2Board.display();
								}
							} else if (orientation == false && numP2Placed < 5) {
								Ship maybeShip;
								maybeShip = p2Board.placeShipHorizontal(row, column, lengthShips[numP2Placed]);
								if (maybeShip != null) {
									p2Ships.add(maybeShip);
									colorTiles(p2Board, tilesP2, lengthShips[numP2Placed]);
									numP2Placed++;
									try {
										lblTurn.setText(p + "'s turn. " + " Next ship length: " + lengthShips[numP2Placed]);
									} catch (Exception f) {

									}
									p2Board.display();
								}

							}
							if (numP2Placed == 5) {
								System.out.println("all placed");
								placeTurnP2 = false;
								p = Player.P1;
								lblTurn.setText(p + "'s turn");
								stage1.setScene(betweens);
								stage1.show();
								// hide p2 Ships and Show p1 ships
								hideShips(p2Board, tilesP2);
								showShips(p1Board, tilesP1);
							}
						}

					} else if (placeTurnP1 == false && placeTurnP2 == false) {

						if (p.equals(Player.P1)) {

							p2Board.hideShips();
							hideShips(p2Board, tilesP2);
							p1Board.showShips();
							showShips(p1Board, tilesP1);

							int column = ((FancyButton) e.getSource()).getCol();
							int row = ((FancyButton) e.getSource()).getRow();

							if (p2Board.shoot(row, column) == true) {
								p = Player.P2;
								stage1.setScene(betweens);
								stage1.show();

								p1Board.hideShips();// hides on Board
								hideShips(p1Board, tilesP1);// hides on GUI
								p2Board.showShips();// shows on Board
								showShips(p2Board, tilesP2); // shows on GUI
							}
							;
							p2Board.display();
							colorTilesShoot(p2Board, tilesP2, 1);

							if (checkWin(p2Ships, p2Board) == true) {
								// player 1 wins
								lblEnd.setText("Player 1 wins!!!!!");
								stage1.setScene(endScreen);

							}

						}
					}
				});

			}
		}

	}

	/**
	 * 
	 * @param board    - the current board
	 * @param buts     - the buttons
	 * @param numTiles - number of tiles Method updates the board based on the
	 *                 status of each tile, grey for the ship, red if the ship is
	 *                 hit, and white if the tile was a miss
	 */
	public void colorTiles(Board board, Button[][] buts, int numTiles) {
		Cell[][] bCells = board.getCells();
		board.display();

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (bCells[i][j].getState().equals(Cellstate.ship)) {
					buts[i][j].setBackground((Background.fill(Color.GREY)));
				} else if (bCells[i][j].getState().equals(Cellstate.hit)) {
					buts[i][j].setBackground((Background.fill(Color.RED)));
				} else if (bCells[i][j].getState().equals(Cellstate.miss)) {
					buts[i][j].setBackground((Background.fill(Color.WHITE)));
				}
			}

		}

	}

	public void colorTilesShoot(Board board, Button[][] buts, int numTiles) {
		Cell[][] bCells = board.getCells();
		board.display();

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {

				if (bCells[i][j].getState().equals(Cellstate.hit)) {
					buts[i][j].setBackground((Background.fill(Color.RED)));
				} else if (bCells[i][j].getState().equals(Cellstate.miss)) {
					buts[i][j].setBackground((Background.fill(Color.WHITE)));
				}
			}

		}

	}

	public void hideShips(Board board, Button[][] buts) {
		Cell[][] bCells = board.getCells();
		board.display();

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (bCells[i][j].getState().equals(Cellstate.ship)) {
					buts[i][j].setBackground((Background.fill(Color.BLUE)));

				}
			}
		}
	}

	public void showShips(Board board, Button[][] buts) {
		Cell[][] bCells = board.getCells();
		board.display();

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (bCells[i][j].getState().equals(Cellstate.ship)) {
					buts[i][j].setBackground((Background.fill(Color.GREY)));

				}
			}
		}
	}

	static boolean checkWin(ArrayList<Ship> ships, Board b) {
		boolean won = true;

		for (Ship s : ships) {

			boolean a = s.getSunk(b);

			if (a) {
				b.clearSunk(s);
			}

			if (!a) {
				won = false;
			}
		}
		return won;
	}

	public void clear(Button[][] b1, Button[][] b2) {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				b1[i][j].setBackground((Background.fill(Color.BLUE)));
				b2[i][j].setBackground((Background.fill(Color.BLUE)));
			}
		}
	}

	public static void main(String[] args) {
		launch(args);

	}

}
