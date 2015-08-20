
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class main extends Application{
	
	
	public Scene scene;

	public BorderPane borderpane,bottompane,toppane,startuppane,settingspane;

	public GridPane combogrid,startupgrid,centergrid;

	public HBox nexthbox,gaphbox,sethbox,startgaphbox,settinggaphbox;
	public Button nextb,addb,delb,setb,detailb,entryb,backb,addDivb1,rmdivb1,addDivb2,rmdivb2;
	public Stage primaryStage;
	public ComboBox deptbox,divbox;
	public TextField setdepttextf,setdivtextf;
	
	public String sqlCreate = "CREATE TABLE IF NOT EXISTS TimeScheduler"  
    + "  (id           	  INTEGER primary key,"
    + "   dept            VARCHAR(50),"
    + "   div             VARCHAR(50),"
    + "   hout         INTEGER )";

	public Connection connection = null;
	public Statement statement;
	public ResultSet rs,r1;
	
	public static void main(String args[]) {
		launch(args);
	}
	public void start(Stage primaryStage) {	
		primaryStage.setTitle("JavaFX Welcome");
		
	// DATABASE CONNECTION
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			statement = connection.createStatement();
			statement.setQueryTimeout(30);
			statement.executeUpdate("drop table if exists timeScheduler");
			  statement.executeUpdate("create table if not exists timeScheduler (id integer, dept string, div string)");
			  statement.executeUpdate("insert into timeScheduler values(1, 'BCA','a')");
			  statement.executeUpdate("insert into timeScheduler values(2, 'BCA','b')");
			  statement.executeUpdate("insert into timeScheduler values(3, 'BCom','as')");
			  ResultSet rs = statement.executeQuery("select * from timeScheduler");
			System.out.println("worked");
			while (rs.next()) {
				System.out.print("id = "+rs.getString("id"));
				System.out.print(" dept = "+rs.getString("dept"));
				System.out.print(" div = "+rs.getString("div")+"\n");
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		finally{
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
				System.out.println(e2);
			}
		}
		
	// CALLING FRAME FUNCTIONS
		startupWindow(primaryStage);
		
	}
	
	public Scene startupWindow(final Stage primaryStage){
		
		startuppane = new BorderPane();
		startupgrid = new GridPane();
		startgaphbox = new HBox();
		detailb = new Button("Detail view");
		entryb = new Button("entry view");

	//	CSS OF STARUPWINDOW	
		
		startuppane.setStyle("-fx-base: #217d63;");
		entryb.setStyle("-fx-font: 12 verdana; -fx-base: #5a716b;");
		detailb.setStyle("-fx-font: 12 verdana; -fx-base: #5a716b;");
		

	// ACTIONS OF STARTUPWINDOW	
		
		entryb.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent ae) {
				
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
			
			deptbox = new ComboBox(options);		
			deptbox.setPrefWidth(200);
			deptbox.setValue("select the departments");
			
			divbox = new ComboBox();
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
		
		// BUTTON EVENT HANDLERS	
			
			setb.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent arg0){
					
					entryset(e);
				}
			});
			
			backb.setOnAction(new EventHandler<ActionEvent>() {
				
				public void handle(ActionEvent arg0) {
					
					startupWindow(e);
				}
			});

//			nextb.setOnAction(new EventHandler<ActionEvent>() {
//				
//				public void handle(ActionEvent b) {
//					
//				}
//			});
			
			
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
			borderpane.setStyle("-fx-base: #217d63;");
			
			toppane.setPadding(new Insets(10,10,0,10));		//PADDINGS OF BACKBTN AND SETTINGSBTN
	        
			scene = new Scene(borderpane,800,600);
			e.setScene(scene);
			e.show();
			return scene;
			
		}
	


	public void entryset(final Stage e){
		
		borderpane = new BorderPane();
		centergrid = new GridPane();
		toppane = new BorderPane();
		
		settinggaphbox = new HBox();
		settinggaphbox .setMinSize(10, 10);
		
		addDivb1 = new Button("+");
		rmdivb1 = new Button("x");
		addDivb2 = new Button("+");
		rmdivb2 = new Button("x");
		backb = new Button("Back");
		
		
		borderpane.setStyle("-fx-base: #217d63;");
		addDivb1.setStyle("-fx-font: 12 verdana; -fx-base: #5a716b;");
		rmdivb1.setStyle("-fx-font: 12 verdana; -fx-base: #5a716b;");
		
		backb.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent arg0) {
				
				entryView(e);
			}
		});
		
		    setdepttextf = new TextField ();
		    setdepttextf.setText("dept");
		    setdivtextf = new TextField();
		    setdivtextf .setText("division");
		
		toppane.setPadding(new Insets(10,10,0,10));

		
		centergrid.add(setdepttextf, 0, 0);
		centergrid.add(addDivb1, 1, 0);
		centergrid.add(rmdivb1, 2, 0);
		centergrid.add(settinggaphbox, 0, 1);
		centergrid.add(setdivtextf, 0, 2);
		centergrid.add(addDivb2, 1, 2);
		centergrid.add(rmdivb2, 2, 2);
		
		centergrid.setAlignment(Pos.CENTER);
		
		toppane.setLeft(backb);
		//borderpane.setStyle("-fx-base: #217d63;");
		borderpane.setCenter(centergrid);
		borderpane.setTop(toppane);
		scene = new Scene(borderpane,800,600);
		e.setScene(scene);
		e.show();
	}

	
}