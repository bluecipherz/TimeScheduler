
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class main extends Application{
	
	
	private Scene 				scene;
	private BorderPane 			borderpane,bottompane,toppane,startuppane;
	private GridPane 			startupgrid,centergrid,bottomgrid;
	private HBox 				nexthbox,gaphbox,sethbox,startgaphbox,settinggaphbox;
	private Button 				nextb,setb,detailb,entryb,backb,addDept1,rmdept1,addDivb2,rmdivb2,finishb;
	private ComboBox<String> 	deptbox,divbox,prd1,prd2,prd3,prd4,prd5,prd6;
	private TextField 			setdepttextf,setdivtextf;
	private Connection 			connection = null;
	private Statement 			statement;
	private ResultSet 			rs;
	private String				name,deptfield,divfield,deptname,divname;
	private int					flag;
	private DatePicker			datePicker;
	
	
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
		detailb = new Button("Detail Mode");
		entryb = new Button("Entry Mode");

	//	CSS OF STARUPWINDOW	
		
		

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
		scene.getStylesheets().add("css/stylesheet.css");
		primaryStage.setScene(scene);
		primaryStage.setTitle("Welcome");
		primaryStage.show();
	}
	
	public void entryView(final Stage primaryStage,final Statement statement) throws SQLException {
		flag=0;
		//COMBOBOX DECLARATION
			
			deptbox = new ComboBox<String>();
			
			loadDept(statement);
			
			deptbox.setPrefWidth(200);
			deptbox.setValue("select the departments");
			
			divbox = new ComboBox<String>();
			divbox.setPrefWidth(200);
			divbox.setValue("select the divisions");
			
		//COMBOBOX ACTIONS
			
			deptbox.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event){try {
						
							deptname =deptbox.getValue();
							loadDiv(statement,event,deptname);
							flag=1;
						} catch (SQLException e) {e.printStackTrace();}
				}});
			
			divbox.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					divname =divbox.getValue();
					if(flag==1){
						flag=2;
					}
				}
			});
			
			
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

					System.out.println(flag);
					if(flag==2){
					try {
						createTable(statement,deptname);
						insertMode(primaryStage,statement,deptname,divname);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						System.out.println(e);
					}
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
			
			toppane.setPadding(new Insets(10));		//PADDINGS OF BACKBTN AND SETTINGSBTN
	        
			scene = new Scene(borderpane,800,600);
			scene.getStylesheets().add("css/stylesheet.css");
			primaryStage.setScene(scene);
			primaryStage.setTitle("Entry Mode");
			primaryStage.show();
			
		}
	
	public void entryset(final Stage primaryStage,final Statement statement){
		
		borderpane = new BorderPane();
		centergrid = new GridPane();
		toppane = new BorderPane();
		
		settinggaphbox = new HBox();
		settinggaphbox .setMinSize(10, 10);
		
		addDept1 = new Button("+");
		rmdept1 = new Button("x");
		addDivb2 = new Button("+");
		rmdivb2 = new Button("x");
		backb = new Button("Back");
		
	// Adding Actions
		
		
		backb.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent arg0) {
				
				try {
					entryView(primaryStage,statement);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println(e);
				}
			}
		});
		
		    setdepttextf = new TextField ();
		    setdepttextf.setText("dept");
		    setdivtextf = new TextField();
		    setdivtextf .setText("division");
		
		addDept1.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				deptfield = setdepttextf.getText();
				try {
					updateData(statement,deptfield);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println(e);
				}
			}
		});
		    
		toppane.setPadding(new Insets(10,10,0,10));

		
		centergrid.add(setdepttextf, 0, 0);
		centergrid.add(addDept1, 1, 0);
		centergrid.add(rmdept1, 2, 0);
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
		scene.getStylesheets().add("css/stylesheet.css");
		primaryStage.setScene(scene);
		primaryStage.setTitle("Settings");
		primaryStage.show();
	}

	public void insertMode(final Stage primaryStage,final Statement statement,String name1,String name2){
		
			borderpane = new BorderPane();
			toppane = new BorderPane();
			bottompane = new BorderPane();
			bottomgrid = new GridPane();
			
			nexthbox = new HBox();
			
			prd1= new ComboBox<String>();
			prd2 = new ComboBox<String>();
			prd3 = new ComboBox<String>();
			prd4 = new ComboBox<String>();
			prd5 = new ComboBox<String>();
			prd6 = new ComboBox<String>();		
			
			datePicker = new DatePicker();
			datePicker.setValue(LocalDate.now());
			datePicker.setId("datePicker");
			
			prd1.setValue("period 1");
			prd2.setValue("period 2");
			prd3.setValue("period 3");
			prd4.setValue("period 4");
			prd5.setValue("period 5");
			prd6.setValue("period 6");
			
			centergrid = new GridPane();
			
			centergrid.add(prd1, 0, 0);
			centergrid.add(prd2, 0, 1);
			centergrid.add(prd3, 0, 2);
			centergrid.add(prd4, 0, 3);
			centergrid.add(prd5, 0, 4);
			centergrid.add(prd6, 0, 5);
			
			backb = new Button("back");
			nextb = new Button("next");
			finishb =  new Button("finish");
			
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
			
			bottomgrid.add(nextb, 0, 0);
			bottomgrid.add(finishb, 1, 0);
			
			nexthbox.getChildren().add(bottomgrid);
			nexthbox.setPadding(new Insets(20));
			
			bottomgrid.setAlignment(Pos.CENTER);
			centergrid.setAlignment(Pos.CENTER);
			borderpane.setCenter(centergrid);
			borderpane.setTop(toppane);
			borderpane.setBottom(bottompane);
			toppane.setLeft(backb);
			toppane.setRight(datePicker);
			bottompane.setRight(nexthbox);
			
			toppane.setPadding(new Insets(10));
			
			scene = new Scene(borderpane,800,600);
			scene.getStylesheets().add("css/stylesheet.css");
			primaryStage.setTitle(name1+"-"+name2);
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
	
	public ComboBox<String> loadDiv(final Statement statement,ActionEvent event,String deptname) throws SQLException{
		try{
			divbox.getItems().clear();
			divbox.setValue("select the division");
		rs = statement.executeQuery("select * from TimeScheduler where dept = '" + deptname+"'");
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

	public void createTable(final Statement statement,String name) throws SQLException{
		statement.executeUpdate("drop table if exists "+name);
		statement.executeUpdate("create table if not exists "+ name + "(id integer,name string,hour int)");
		System.out.println(name+" table updated");
//		statement.executeUpdate("insert into timeScheduler values(1, 'BCA','A')");
	}
	
	public void updateData(final Statement statement,String deptfield) throws SQLException{
		rs = statement.executeQuery("select * from TimeScheduler");
		int i=1;
		while(rs.next()){
			i=i+1;
		}
		rs.close();
		System.out.println("i = "+i);
//		System.out.println("updateData : "+deptfield);
		statement.executeUpdate("insert into timeScheduler values("+i+",'"+deptfield+"','A')");
	}
	
}