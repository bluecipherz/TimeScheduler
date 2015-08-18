
import com.sun.javafx.geom.*;

import javafx.application.*;
import javafx.collections.*;
import javafx.embed.swing.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.*;

public class main extends Application{
	
	
	public Scene scene;
	public BorderPane borderpane,bottompane,toppane,startuppane;
	public GridPane combogrid,startupgrid;
	public HBox nexthbox,gaphbox,sethbox,startgaphbox;
	public Button nextb,addb,delb,setb,detailb,entryb,backb;
	public Stage primaryStage;
	
	
	public static void main(String args[]) {
		launch(args);
	}
	public void start(Stage primaryStage) {	
		primaryStage.setTitle("JavaFX Welcome");
		
	// CALLING FRAME FUNCTIONS
		startupWindow(primaryStage);
		
		
		
	}
	
	public Scene startupWindow(final Stage primaryStage){
		
		startuppane = new BorderPane();
		startupgrid = new GridPane();
		startgaphbox = new HBox();
		detailb = new Button("Detail view");
		entryb = new Button("entry view");
		
		entryb.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent ae) {
				// TODO Auto-generated method stub
				entryView(primaryStage);
			}
		});
		
		startgaphbox .setMinSize(10, 10);
		detailb.setPrefWidth(150);
		entryb.setPrefWidth(150);
		
		
		startuppane.setCenter(startupgrid);
		startupgrid.add(detailb, 0, 0);
		startupgrid.add(startgaphbox, 0, 1);
		startupgrid.add(entryb, 0, 2);
		
		
		
		startupgrid .setAlignment(Pos.CENTER);
		startupgrid .setMinSize(10, 10);
		
		scene = new Scene(startuppane,800,600);
		primaryStage.setScene(scene);
		primaryStage.show();
		return scene;
	}
	
	public Scene entryView(final Stage e) {
			
			
			ObservableList<String> options = 
				    FXCollections.observableArrayList(
				        "BCA",
				        "BCOM",
				        "BBA",
				        "BSc"
				    );
		//COMBOBOX DECLARATION
			
			ComboBox deptbox = new ComboBox(options);		
			deptbox.setPrefWidth(200);
			deptbox.setValue("select the departments");
			
			ComboBox divbox = new ComboBox();
			divbox.setPrefWidth(200);
			divbox.setValue("select the divisions");
			
		//BORDER PANE DECLARATION
			
			
			borderpane = new BorderPane();		
			bottompane = new BorderPane();
			toppane = new BorderPane();
			
			GridPane combogrid = new GridPane();
			
			bottompane.setPadding(new Insets(20));
			borderpane.setBottom(bottompane);
			
		//HBOX DECLRATION
			
			nexthbox = new HBox();
			gaphbox = new HBox();
			sethbox = new HBox();
			
			
			gaphbox.setMinSize(10, 10);
			
		// BUTTONS DECLARATION
			
			nextb = new Button("Next");
			addb = new Button("+");
			delb = new Button("x");
			setb = new Button("Settings");
			backb = new Button("Back");
			
			backb.setOnAction(new EventHandler<ActionEvent>() {
				
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					startupWindow(e);
				}
			});
			
			toppane.setPadding(new Insets(10,10,0,10));
			
		//ADDING SECTION

			nexthbox.getChildren().add(nextb);
			sethbox.getChildren().add(setb);
			combogrid.add(deptbox, 0, 0);
			combogrid.add(gaphbox, 0, 2);
			combogrid.add(divbox, 0, 4);
			
		// ALLINGING STATEMENTS
			
			combogrid.setAlignment(Pos.CENTER);
			toppane.setRight(sethbox);
			toppane.setLeft(backb);
			bottompane.setRight(nexthbox);
			borderpane.setCenter(combogrid);
			borderpane.setTop(toppane);
			
			
	        
			scene = new Scene(borderpane,800,600);
			e.setScene(scene);
			e.show();
			return scene;
			
		}
	
	
	
}