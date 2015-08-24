
import javafx.application.*;
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
	
	
	private Scene 				scene;
	private BorderPane 			borderpane,bottompane,toppane,startuppane;
	private GridPane 			startupgrid,centergrid;
	private HBox 				nexthbox,gaphbox,sethbox,startgaphbox,settinggaphbox;
	private Button 				nextb,setb,detailb,entryb,backb,addDivb1,rmdivb1,addDivb2,rmdivb2;
	private ComboBox<String> 	deptbox,divbox,prd1,prd2,prd3,prd4,prd5,prd6;
	private TextField 			setdepttextf,setdivtextf;
	private Connection 			connection = null;
	private Statement 			statement;
	private ResultSet 			rs;
	private String				name;
	
	
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
						
							name = returnName(event);
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
			new Button("+");
			new Button("x");
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

			nextb.setOnAction(new EventHandler<ActionEvent>() {
				
				public void handle(ActionEvent b) {
					try {
						createTable(statement,name);
						insertMode(primaryStage,statement,name);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						System.out.println(e);
					}
				}
			});
			
			
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
		
	// Adding Actions
		
		
		
		
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

	public void insertMode(final Stage primaryStage,final Statement statement,String name){
		
		//COMBOBOX DECLARATION
			
			prd1 = new ComboBox();
			prd2 = new ComboBox();
			prd3 = new ComboBox();
			prd4 = new ComboBox();
			prd5 = new ComboBox();
			prd6 = new ComboBox();
			
			prd1.setValue("period 1");
			prd2.setValue("period 2");
			prd3.setValue("period 3");
			prd4.setValue("period 4");
			prd5.setValue("period 5");
			prd6.setValue("period 6");
			
//			prd1.setMouseTransparent(true);
			
			
		//LAYOUT DECLARATION
			borderpane = new BorderPane();
			borderpane.setStyle("-fx-base: #217d63;");
			
			centergrid = new GridPane();
			
		//HBOX DECLARATION
			
			
			
		// ADDING SECTION
			centergrid.add(prd1, 0, 1);
//			centergrid.add(, 0, 2);
			centergrid.add(prd2, 0, 3);
			centergrid.add(prd3, 0, 5);
			centergrid.add(prd4, 0, 7);
			centergrid.add(prd5, 0, 9);
			centergrid.add(prd6, 0, 11);
			
			centergrid.setPadding(new Insets(10));
			centergrid.setAlignment(Pos.CENTER);
			
			borderpane.setCenter(centergrid);
			
			scene = new Scene(borderpane,800,500);
			primaryStage.setTitle(name);
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
		name = "'"+deptbox.getValue()+"'";
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

	public String returnName(ActionEvent event){
		String name = "'"+deptbox.getValue()+"'";
		return name;
		
	}
	
	public void createTable(final Statement statement,String name) throws SQLException{	
		statement.executeUpdate("drop table if exists timeScheduler");
		statement.executeUpdate("create table if not exists "+ name + "(id integer,name string,hour int)");
//		statement.executeUpdate("insert into timeScheduler values(1, 'BCA','A')");
	}
	
}