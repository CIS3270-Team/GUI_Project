package GUI;

import javafx.application.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;

public class Alerts {

	public static void main(String[] args) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		
		
		Text label = new Text(message);
		label.setTextAlignment(TextAlignment.CENTER);
		
		Button okay = new Button("OK");
		okay.setOnAction(e -> {window.close();});
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, okay);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene =new Scene(layout,500,100);
		window.setScene(scene);
		window.showAndWait();

	}

}
