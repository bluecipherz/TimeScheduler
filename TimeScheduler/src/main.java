
import com.sun.javafx.geom.*;


import javafx.application.*;
import javafx.collections.*;
import javafx.embed.swing.*;
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

public class main extends Application {
	public static void main(String args[]) {
		launch(args);
	}
	public void start(Stage primaryStage) {	
		primaryStage.setTitle("JavaFX Welcome");
		

		Text scenetitle = new Text("Welcome");
		scenetitle.setFont(Font.font("Tahoma",FontWeight.NORMAL,20));
		
		ObservableList<String> options = 
			    FXCollections.observableArrayList(
			        "BCA",
			        "BCOM",
			        "BBA",
			        "BSc"
			    );
		
		ComboBox deptbox = new ComboBox(options);
		deptbox.setPrefWidth(200);
		deptbox.setValue("select the departments");
		
		ComboBox divbox = new ComboBox();
		divbox.setPrefWidth(200);
		divbox.setValue("select the divisions");
		
        
		BorderPane borderpane = new BorderPane();
		BorderPane bottompane = new BorderPane();
		GridPane combogrid = new GridPane();
		
		bottompane.setPadding(new Insets(20));
		borderpane.setBottom(bottompane);
		
		HBox nexthbox = new HBox();
		//HBox centerhbox = new HBox();
		Button nextb = new Button("Next");
		//centerhbox.setMaxSize(200, 200);
		

		
		Button addb = new Button("+");
		Button delb = new Button("x");
		nexthbox.getChildren().add(nextb);
		
		combogrid.add(divbox, 0, 4);
		combogrid.add(deptbox, 0, 0);
		combogrid.setAlignment(Pos.CENTER);
		
		
		//centerhbox.getChildren().add(deptbox);
		//centerhbox.getChildren().add(divbox);
		bottompane.setRight(nexthbox);
		borderpane.setCenter(combogrid);
		
		
        
		Scene scene = new Scene(borderpane,800,600);
		 
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	
}