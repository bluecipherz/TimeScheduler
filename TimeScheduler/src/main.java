
import javafx.application.*;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
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
	public ComboBox<String> deptbox,divbox;
	public TextField setdepttextf,setdivtextf;
	
	public ObservableList<String> options;

	public Connection connection = null;
	public Statement statement;
	public ResultSet rs;
	
	
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
			  statement.executeUpdate("insert into timeScheduler values(1, 'BCA','A')");
			  statement.executeUpdate("insert into timeScheduler values(2, 'BCA','B')");
			  statement.executeUpdate("insert into timeScheduler values(3, 'BCom','A')");
			  statement.executeUpdate("insert into timeScheduler values(4, 'BCom','B')");
			  statement.executeUpdate("insert into timeScheduler values(5, 'BCom','C')");
			  statement.executeUpdate("insert into timeScheduler values(6, 'BCom','D')");
			  statement.executeUpdate("insert into timeScheduler values(7, 'BBA','A')");
			  statement.executeUpdate("insert into timeScheduler values(8, 'Bsc_English','A')");
			  
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		startupWindow(primaryStage,statement);
		
	}
	
	public void startupWindow(final Stage primaryStage,final Statement statement){
		
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
				
				try {
					entryView(primaryStage,statement);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
	}
	
	public void entryView(final Stage primaryStage,final Statement statement) throws SQLException {
		
			
			
		//COMBOBOX DECLARATION
			
			deptbox = new ComboBox<String>();
			
			loadDept(statement);
			deptbox.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event){try {
					
						loadDiv(statement,event);
						
						} catch (SQLException e) {e.printStackTrace();}
				}});
			
			deptbox.setPrefWidth(200);
			deptbox.setValue("select the departments");
			
			divbox = new ComboBox<String>();
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
					
					entryset(primaryStage,statement);
				}
			});
			
			backb.setOnAction(new EventHandler<ActionEvent>() {
				
				public void handle(ActionEvent arg0) {
					
					startupWindow(primaryStage,statement);
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
			primaryStage.setScene(scene);
			primaryStage.show();
			
		}
	
	public void entryset(final Stage primaryStage,final Statement statement){
		
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
				
				try {
					entryView(primaryStage,statement);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public ComboBox<String> loadDept(final Statement statement) throws SQLException{
		
		rs = statement.executeQuery("select * from TimeScheduler");
		try{
		while (rs.next()) {
			if(deptbox.getItems().contains((rs.getString("dept")))){
				continue;
			}
			else
				deptbox.getItems().add(rs.getString("dept"));
		}
		}catch(Exception e){System.out.println(e);}
		return deptbox;
	}
	
	public ComboBox<String> loadDiv(final Statement statement,ActionEvent event) throws SQLException{
		try{
			divbox.getItems().clear();
			divbox.setValue("select the division");
		String name = "'"+deptbox.getValue()+"'";
		rs = statement.executeQuery("select * from TimeScheduler where dept = " + name);
		}catch(Exception e){System.out.println(e);}
		try{
		while (rs.next()) {
			if(divbox.getItems().contains((rs.getString("div")))){
				continue;
			}
			else
				divbox.getItems().add(rs.getString("div"));
		}
		}catch(Exception e){System.out.println(e);}
		rs.close();
		return divbox;
		
	}
}