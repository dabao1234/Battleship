import java.util.ArrayList;
import java.util.Random;

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
import javafx.scene.text.Font;

/**
 * Adds visuals to the game
 */

public class GUIDriver extends Application {
	// Constants for the board
	final int num_Rows = 10;
	final int num_Cols = 10;
	final int WIDTH = 700;
	final int HEIGHT = 600;
	
	// Indicates if it's either P1 or P2's turn to place their ships
	// If both are false, it's the shooting phase of the game
	boolean placeTurnP1 = true;
	boolean placeTurnP2 = false;
	
	// if true it is vertical, if false it is horizontal
	boolean orientation = false; 
	
	// The current player whose turn it is
	Player p;
	// The opponent. Either a human or AI.
	Player opp;
	
	// Each player's ships
	ArrayList<Ship> p1Ships = new ArrayList<>();
	ArrayList<Ship> p2Ships = new ArrayList<>();
	
	// How many ships each player has placed so far (will go up to 5)
	int numP1Placed = 0;
	int numP2Placed = 0;
	
	// The lengths of the ships the players will be allowed to place
	// Going by classic rules here
	int[] lengthShips = { 2, 3, 3, 4, 5 };

	// The label to indicate whose turn it is and if your ship placement is invalid
	Label lblTurn = new Label();

	@Override
	public void start(Stage stage1) throws Exception {
		
		// board objects
		lblTurn.setAlignment(Pos.CENTER);
		lblTurn.setStyle("-fx-border-color: black; -fx-font-size: 30; -fx-background-color: linear-gradient(to bottom, #f2f2f2, #d4d4d4);");
		Board p1Board = new Board(10, 10, Player.P1);
		Board p2Board = new Board(10, 10, Player.P2);
		
		// Creates the button arrays to make up the player grids
		Button[][] tilesP1 = new Button[num_Rows][num_Cols];
		Button[][] tilesP2 = new Button[num_Rows][num_Cols];

		// Creates the grid panes to house the buttons
		GridPane boardP1 = new GridPane();
		GridPane boardP2 = new GridPane();
		
		// Creates the buttons themselves
		for (int i = 0; i < num_Rows; i++) {
			for (int z = 0; z < num_Cols; z++) {
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
				
				// adds them to the grid pane
				boardP1.add(tilesP1[i][z], z, i);
				boardP2.add(tilesP2[i][z], z, i);
			}
		}

		// HBox to add a label to both boards
		HBox top = new HBox();
		HBox mid = new HBox();

		// labels for the HBoxes
		Label lblTop = new Label("");
		Label lblMid = new Label("");

		top.getChildren().add(lblTop);
		mid.getChildren().add(lblMid);

		top.setAlignment(Pos.CENTER);
		mid.setAlignment(Pos.CENTER);
		
		// Creates P2 play scene
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

		//End Screen
		VBox end = new VBox();
		end.setStyle("-fx-background-image: url('https://pixeljoint.com/files/icons/full/win__r1340336691.gif')");
		Label lblEnd = new Label();
		lblEnd.setStyle("-fx-font-size: 30; -fx-text-fill: white; -fx-background-color: black");
		Button bttnReset = new Button("Play Again");
		bttnReset.setStyle("-fx-font-size:30");
		end.getChildren().addAll(lblEnd, bttnReset);
		end.setAlignment(Pos.CENTER);
		Scene endScreen = new Scene(end, 750, 700);

		//Screen In-between
		// This makes it so that you can do 2-player mode without the players automatically seeing eachothers
		// ship placements when the turn changes
		VBox a = new VBox();
		Label lblmid = new Label();
		
		Button bttnBetween = new Button("Next Player's Turn");
		bttnBetween.setStyle("-fx-font-size: 30;");
		

		a.setStyle(
				"-fx-background-image: url('https://c.tenor.com/dIYElE0kJHQAAAAC/wee-ship.gif')");
		a.getChildren().addAll(lblmid, bttnBetween);

		a.setAlignment(Pos.CENTER);
		Scene betweens = new Scene(a, 450, 370);

		// Setting the stage
		stage1.setScene(selection);
		stage1.show();

		// Setting the player to P1
		p = p.P1;

		//Button to select human mode
		bttnHuman.setOnAction(e -> {
			lblTurn.setText(p + "'s turn. " + " Next ship length: " + lengthShips[numP1Placed]);
			stage1.setScene(game);
			stage1.show();
			
			// Making the opposition a human
			opp = p.P2;

			if (p.equals(Player.P1)) {
				lblTop.setText("Player 2 Board");
				lblMid.setText("Player 1 Board");
			}
		});
		//Button to set the game mode to AI
		bttnAI.setOnAction(e -> {

			stage1.setScene(game);
			stage1.show();
			
			// Making the opposition the computer
			opp = p.AI;

			if (p.equals(Player.P1)) {
				lblTop.setText("Player 2 Board");
				lblMid.setText("Player 1 Board");
			}
		});
		//Reset for the next playthrough
		bttnReset.setOnAction(E -> {
			clear(tilesP1, tilesP2);
			p1Board.clear();
			p2Board.clear();

			placeTurnP1 = true;
			orientation = false;
			numP1Placed = 0;
			numP2Placed = 0;

			stage1.setScene(selection);
			stage1.show();
		});

		//Button for changing orientation
		bttnVert.setOnAction(e -> {
			orientation = true;
		});
		//Button for changing orientation
		bttnHor.setOnAction(e -> {
			orientation = false;

		});
		//Button in between to hide boards and show boards
		bttnBetween.setOnAction(e -> {
			stage1.setScene(game);
			stage1.show();
		});

		// sets the turn label at the top
		lblTurn.setAlignment(Pos.CENTER);
		lblTurn.setText(p + " turn");

		// Action event for all of the buttons in the grid (checking each button for an event every turn)
		for (int i = 0; i < num_Rows; i++) {
			for (int z = 0; z < num_Cols; z++) {
				// P2's board is clickable if it is player 1's turn
				tilesP1[i][z].setOnAction(e -> {
					
					// Checking to see if it is P1's placement turn
					if (placeTurnP1 == true) {
						
						if(opp.equals(Player.AI)) {
							// If your opposition is AI, the only time the between screen will appear is
							// when moving from placement to shoot mode, so the button should have a different
							// message as there's only one real player taking their turn.
							bttnBetween.setText("Shooting Time!");
						} else {
							bttnBetween.setText("Next player");
						}
						// Taking the row and column locations from the user
						int column = ((FancyButton) e.getSource()).getCol();
						int row = ((FancyButton) e.getSource()).getRow();

						// if the orientation is true, place the ship vertically
						// Also check that the player has not already placed 5 ships
						if (orientation == true && numP1Placed < 5) {
							// Creating a ship object in the location
							Ship maybeShip;
							maybeShip = p1Board.placeShipVertical(row, column, lengthShips[numP1Placed]);
							
							// If it exists, adding the ship to the board and going to the next turn
							if (maybeShip != null) {
								p1Ships.add(maybeShip);
								colorTiles(p1Board, tilesP1, lengthShips[numP1Placed]);
								numP1Placed++;
								try {
									lblTurn.setText(p + "'s turn. " + " Next ship length: " + lengthShips[numP1Placed]);
								}catch(Exception l) {
									
								}
								p1Board.display();
							// If the ship can't exist, getting another location from the user
							}else {
								lblTurn.setText("Invalid Placement ");
							}
						// Place it horizontally is orientation is false
						} else if (orientation == false && numP1Placed < 5) {
							
							// Creating a ship object in the location
							Ship maybeShip;
							maybeShip = p1Board.placeShipHorizontal(row, column, lengthShips[numP1Placed]);
							
							// If it exists, adding the ship to the board and going to the next turn
							if (maybeShip != null) {
								p1Ships.add(maybeShip);
								colorTiles(p1Board, tilesP1, lengthShips[numP1Placed]);
								numP1Placed++;
								try {
									lblTurn.setText(p + "'s turn. " + " Next ship length: " + lengthShips[numP1Placed]);
								}catch(Exception f) {
									
								}
								p1Board.display();
								
							// If the ship can't exist, getting another location from the user
							}else {
								lblTurn.setText("Invalid Placement ");
							}

						}
						// Checks if all the ships are placed. If so, go to the next player's turn
						if (numP1Placed == 5) {
							// Moving to P2
							placeTurnP1 = false;
							placeTurnP2 = true;
							lblTurn.setText("P2's Turn");
							
							// If opposition is a human, switching the scenes and preparing for their ship placement
							if(opp.equals(Player.P2)) {
								p = Player.P2;
								stage1.setScene(betweens);
								stage1.show();

								//hide P1 Ships 
								hideShips(p1Board,tilesP1);
							}
							
							// If opposition is AI, doing their placement automatically
							if(opp.equals(Player.AI)) {
								
								Random r = new Random();
								numP2Placed = 0;
								
								while(numP2Placed < 5) {
									
									// Taking a random orientation for the ship
									orientation = r.nextBoolean();
									
									// Placing vertically
									if(orientation == true) {
										column = r.nextInt(10);
										// Making sure row will not go out of bounds
										row = r.nextInt(10 - lengthShips[numP2Placed]);
									}
									// Placing horizontally
									else {
										// Making sure column will not go out of bounds
										column = r.nextInt(10 - lengthShips[numP2Placed]);
										row = r.nextInt(10);
									}
									// Vertical ship placement for AI
									if (orientation == true && numP2Placed < 5) {
										// Creating ship in the spot chosen
										Ship maybeShip;
										maybeShip = p2Board.placeShipVertical(row, column, lengthShips[numP2Placed]);
										
										// If it exists, adding it and moving on
										if (maybeShip != null) {
											p2Ships.add(maybeShip);
											colorTiles(p2Board, tilesP2, lengthShips[numP2Placed]);
											numP2Placed++;
											
											p2Board.display();
										// Otherwise, do not advance! The AI will try again
										}else {
										}
										
									// Horizontal ship placement for AI
									} else if (orientation == false && numP2Placed < 5) {
										// Creating ship in the spot chosen
										Ship maybeShip;
										maybeShip = p2Board.placeShipHorizontal(row, column, lengthShips[numP2Placed]);
										
										// If it exists, adding it and moving on
										if (maybeShip != null) {
											p2Ships.add(maybeShip);
											colorTiles(p2Board, tilesP2, lengthShips[numP2Placed]);
											numP2Placed++;
											
											p2Board.display();
										// Otherwise, do not advance! The AI will try again
										}else {
										}
			
									}
									//If the ships are all placed, going to shooting time
									if (numP2Placed == 5) {
										// Going to Player 1's turn
										placeTurnP2 = false;
										p=Player.P1;
										lblTurn.setText(p+"'s turn");
										
										// Going to intermission
										stage1.setScene(betweens);
										stage1.show();
										
										//hide p2 Ships and Show p1 ships
										hideShips(p2Board,tilesP2);
										showShips(p1Board,tilesP1);
									}
								}
							}
							
						}
					// if all placement is over, shoot time
					} else if (placeTurnP1 == false && placeTurnP2 == false) {
						if (p.equals(Player.P2)) {
							
							colorTilesShoot(p1Board, tilesP1, 1);
							// Getting the shot location
							int column = ((FancyButton) e.getSource()).getCol();
							int row = ((FancyButton) e.getSource()).getRow();

							hideShips(p1Board, tilesP1);// hides on GUI
							showShips(p2Board, tilesP2); // shows on GUI
						
							// If the shot succeeds and the turn is over, going to the next player's turn
							if (p1Board.shoot(row, column)) {
								p = Player.P1;
								stage1.setScene(betweens);
								stage1.show();
								
								lblTurn.setText(p+"'s turn");

								hideShips(p2Board, tilesP2);// hides on GUI
								showShips(p1Board, tilesP1); // shows on GUI
								
								stage1.setScene(betweens);
								colorTilesShoot(p1Board, tilesP1, 1);
							};
							
							// Checks to see if any ships have been sunk
							// If so, clearing the tiles around them
							checkSunk(p1Ships, p1Board);
							
							// Recolouring all the tiles accordingly
							colorTilesShoot(p1Board, tilesP1, 1);

							// checks if there is a winner
							if (checkWin(p1Ships, p1Board) == true) {
								// player 2 wins
								lblEnd.setText("Player 2 wins!!!!!");
								stage1.setScene(endScreen);
							}
						}
					}
				});
				// Ship placement for player 2
				tilesP2[i][z].setOnAction(e -> {
					int column;
					int row;
					
					//If it's player 2's turn, place ships
					// This works the same way as the P1 ship placement
					if (placeTurnP2 == true) {
												
						if (opp.equals(Player.P2)) {
							// Getting ship placement
							column = ((FancyButton) e.getSource()).getCol();
							row = ((FancyButton) e.getSource()).getRow();
							//Vertical Placement
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
								}else {
									lblTurn.setText("Invalid Placement ");
								}
								//Horizontal Placement
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
								}else {
									lblTurn.setText("Invalid Placement ");
								}
	
							}
							//If player 2 has finished placing 5 ships, move on
							if (numP2Placed == 5) {
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
					//Section for Player One's shooting turn
					} else if (placeTurnP1 == false && placeTurnP2 == false) {

						if (p.equals(Player.P1)) {
							// This works the same way as the P2 shooting turn
							colorTilesShoot(p2Board, tilesP2, 1);
							boolean done;

							hideShips(p2Board, tilesP2);
							showShips(p1Board, tilesP1);

							column = ((FancyButton) e.getSource()).getCol();
							row = ((FancyButton) e.getSource()).getRow();
							
							// Checks to see if the player's turn is over and if they have sunk anything yet
							done = p2Board.shoot(row, column);
							
							//If the player is done shooting, moving to the next turn
							if (done) {
								p = opp;
								
								// Going to the next player's turn if there is another
								if(p.equals(Player.P2)) {
									
									stage1.setScene(betweens);
									stage1.show();

									hideShips(p1Board, tilesP1);// hides on GUI
									showShips(p2Board, tilesP2); // shows on GUI
									
									// Recolouring the tiles
									colorTilesShoot(p2Board, tilesP2, 1);
								}
							}
							// Checking again for any sunk ships and recolouring
							checkSunk(p2Ships, p2Board);
							colorTilesShoot(p2Board, tilesP2, 1);
							
							//Checking win condition
							if (checkWin(p2Ships, p2Board) == true) {
								// player 1 wins
								lblEnd.setText("Player 1 wins!!!!!");
								stage1.setScene(endScreen);
							}
							// AI Mode
							if(p.equals(Player.AI) && done) {
								int shots = 0;
								// If it's the AI's turn and P1 is done shooting, doing the AI shoot turn
								
								// Initializing the cell to be shot at
								Cell k;
								Random r = new Random();
								
								// Whether or not the AI's turn has ended
								boolean done1 = false;
							
								while(!done1) {
									shots++;
									
									Ship m = null;
									
									// Whether or not the AI will aim correctly given the chance
									// about a 1/3 chance
									// If chance = 0, they will guess the next tile placement correctly
									// If it's 1 or 2, they will hit somewhere random
									int chance = r.nextInt(3);
									
									// Looping through P1's ships to see if the AI has hit, but not fully sunk,
									// any of their ships
									for (Ship s : p1Ships) {
										if(s.checkHit(p1Board)) {
											// If so, storing the ship
											m = s;
										}
									}
									
									// If there is no such ship or they guess wrong, randomly selecting a place
									if(m == null || chance != 0) {
										// Taking a random unhit cell from the board
										k = p1Board.getRandom();
										
										// If for some reason there is no cell, checking for a win and
										// ending the turn
										// This should not execute really
										if(k == null) {
											done1 = true;
											if (checkWin(p1Ships, p1Board) == true) {
												// player 2 wins
												lblEnd.setText("You lose!");
												stage1.setScene(endScreen);
											}
											
										}
										// If there is a cell, shooting at it
										else {
											row = k.getRow();
											column = k.getCol();
											
											done1 = p1Board.shoot(row, column);
											checkSunk(p1Ships, p1Board);
											colorTilesShoot(p1Board, tilesP1, 1);

											}
									}
									// If there is a ship to be shot at and the AI guesses its placement
									// correctly, shooting at it
									else {
										// Taking an unhit cell from the ship
										k = m.notHit();
										
										// Shooting at that cell
										row = k.getRow();
										column = k.getCol();
										
										done1 = p1Board.shoot(row, column);
										checkSunk(p1Ships, p1Board);
										colorTilesShoot(p1Board, tilesP1, 1);
									}
																		
								}
								// Making sure the board is up to date
								colorTilesShoot(p1Board, tilesP1, 1);
								
								// Moving back to player 1's turn
								p = Player.P1;

								// checks if there is a winner
								if (checkWin(p1Ships, p1Board) == true) {
									// player 2 wins
									lblEnd.setText("You lose!");
									stage1.setScene(endScreen);

								}
								lblTurn.setText("AI shot " + shots + " time(s)");
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
	 * @param buts     - the player's buttons
	 * @param numTiles - number of tiles 
	 * Method updates the board based on the status of each tile, grey for the ship, red if the ship is hit, 
	 * and white if the tile was a miss
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
	/**
	 * 
	 * @param board - the board being shot at
	 * @param buts - the buttons being shot at
	 * @param numTiles - number of tiles
	 * Change the color of the tiles, if it is hit, make it red, if it is a miss, make the tile white
	 */
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
	/**
	 * Hides the ships on the board so that the other player can't see them
	 * @param board - board
	 * @param buts - buttons
	 */
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
	/**
	 * Shows the ships on the board when it's your turn again
	 * @param board - the board
	 * @param buts - buttons
	 */
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
	
	/**
	 * Checks to see if any ships have been sunk
	 * @param ships - the correct player's list of ships
	 * @param b - the board the ships belong to
	 * @return - whether or not they have been sunk
	 */
	
	static boolean checkSunk(ArrayList<Ship> ships, Board b) {
		// By default, nothing has been sunk
		boolean sunk = false;
		
		// Looping through the ships
		for (Ship s : ships) {
			// Checking to see if any have been sunk
			boolean a = s.getSunk(b);
			
			// If so, clearing the spaces around them and setting sunk to true
			if (a) {
				b.clearSunk(s);
				sunk = true;
			}
		}
		return sunk;
	}
	
	/**
	 * Checks to see if the current player has won
	 * @param ships - the ships of the current player's opponent
	 * @param b - the board of the current player's opponent
	 * @return true if all the ships are sunk
	 */
	static boolean checkWin(ArrayList<Ship> ships, Board b) {
		// By default,t hey have won
		boolean won = true;
		
		for(int i = 0; i<10; i++) {
			for(int j = 0; j<10; j++) {
				// If there are any ship tiles still remaining, they have not won yet
				if(b.getCells()[i][j].getState().equals(Cellstate.ship)) {
					won = false;
				}
			}
		}
		return won;
	}
	/**
	 ** Clears the board visually. All tiles will be blue again
	 * @param b1 - board 1
	 * @param b2 - board 2
	 */
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
