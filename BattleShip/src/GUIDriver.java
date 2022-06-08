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
import javafx.stage.Stage;

public class GUIDriver extends Application {
	final int num_Rows=10;
	final int num_Cols=10;
	final int WIDTH = 700;
	final int HEIGHT = 600;
<<<<<<< HEAD
	
=======
	Player p;
>>>>>>> refs/remotes/origin/main
	@Override
	public void start(Stage stage1) throws Exception {
		
		//Creates the buttons for P1 
		Button[][] tilesP1=new Button[num_Rows][num_Cols];
		Button[][] tilesP2=new Button[num_Rows][num_Cols];;//p2 screen 
		
		//creates the grid panes to house the buttons 
		GridPane boardP1=new GridPane();
		GridPane boardP2=new GridPane();
		
		for(int i=0;i<num_Rows;i++) {
			for(int z=0;z<num_Cols;z++) {
				//creates the buttons
				Button bttnp1= new Button();
				Button bttnP2=new Button();
				bttnp1.setPrefSize(100, 100);
				bttnP2.setPrefSize(100, 100);
				tilesP1[i][z]= bttnp1;
				tilesP2[i][z]= bttnP2;
				//sets the color
				tilesP1[i][z].setBackground(Background.fill(Color.BLUE));
				tilesP2[i][z].setBackground(Background.fill(Color.BLUE));
				//adds to the grid pane 
			
				boardP1.add(tilesP1[i][z], i, z);
				boardP2.add(tilesP2[i][z], i, z);
			}
		}
		
		//HBox to add a label to both boards
		HBox top=new HBox();
		HBox mid=new HBox();
	
		//labels for the HBox's 
		Label lblTop=new Label("");
		Label lblMid=new Label("");
		
		
		top.getChildren().add(lblTop);
		mid.getChildren().add(lblMid);
		
		//Creates the scene for P1 play 
		VBox P1=new VBox();
		P1.getChildren().addAll(top,boardP1,mid,boardP2);
		Scene P1game=new Scene(P1,WIDTH,HEIGHT);
		
		//Creates P2 play scene
		
		VBox P2=new VBox();
		P2.getChildren().addAll(top,boardP1,mid,boardP2);
		Scene P2game = new Scene(P2,WIDTH,HEIGHT);
	
		//Selection Screen 
		
		VBox select=new VBox();
		Label lblStart=new Label("Welcom to BATTLESHIP!");
		HBox srtButtons=new HBox();
		Button bttnHuman=new Button("Vs Human");
		Button bttnAI=new Button("Vs AI");
		srtButtons.getChildren().addAll(bttnHuman,bttnAI);
		
		srtButtons.setAlignment(Pos.CENTER);
		select.getChildren().addAll(lblStart,srtButtons);
		select.setAlignment(Pos.CENTER);
		Scene selection=new Scene(select,200,200);
		
		
		//end Screen 
		VBox end=new VBox();
		Label lblEnd=new Label();
		Button bttnReset= new Button("Play Again");
		end.getChildren().addAll(lblEnd,bttnReset);
		end.setAlignment(Pos.CENTER);
		Scene endScreen =new Scene(end,700,700);
		
		stage1.setScene(selection);
		stage1.show();
		
		p=p.P1;
		
		//button events
		bttnHuman.setOnAction(e->{
			stage1.setScene(P2game);
			stage1.show();
			if(p.equals(Player.P1)) {
				lblTop.setText("Player 2 Board");
			}
		});
		
		

	}

	public static void main(String[] args) {
		launch(args);
		

	}

}
