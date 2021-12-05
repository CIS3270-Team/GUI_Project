package GUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class PasswordReset extends Application implements EventHandler<ActionEvent>{

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		launch(args);

	}
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Reset Password");
		primaryStage.setResizable(false);
		GridPane grid = new GridPane();
		// grid.setGridLinesVisible(true);
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(10);
		grid.setHgap(10);
		
		Label securityQuestionLabel = new Label("Enter Your New Password!");
		GridPane.setConstraints(securityQuestionLabel, 1, 0);
		grid.setAlignment(Pos.TOP_CENTER);
		securityQuestionLabel.setTextAlignment(TextAlignment.CENTER);

		TextField answerField = new TextField();
		GridPane.setConstraints(answerField, 1, 2);

		Button okay = new Button("Okay");
		GridPane.setConstraints(okay, 0, 3);
		GridPane.setHalignment(okay, HPos.LEFT);
		okay.setOnAction(e -> {

			try {
				// get a connection to the database
				String userAnswer = answerField.getText().trim();
				Connection myConn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/sys", "root", "password");
				// create a statement
				Statement myStat = myConn.createStatement();
				// execute a query
				String sqlUserCheck = "UPDATE customer SET password = '" +userAnswer+ "'";
				myStat.executeUpdate(sqlUserCheck);
				
				myStat.close();
				myConn.close();
				Login loginPage = new Login();
				try {
					loginPage.start(primaryStage);
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			} catch (Exception e1) {

			}
			
			

			
		});
		
		
		Button cancel = new Button("Cancel");
		GridPane.setConstraints(cancel, 3, 3);
		GridPane.setHalignment(cancel, HPos.RIGHT);
		cancel.setOnAction(e -> {
			{
				PasswordRecovery recoverPage = new PasswordRecovery();
				try {

					recoverPage.start(primaryStage);
				}

				catch (Exception e1) {
				}
			}
		});
		
		okay.setMinWidth(120);
		cancel.setMinWidth(120);
		grid.getChildren().addAll(securityQuestionLabel,  answerField, okay, cancel);
		Scene scene = new Scene(grid, 550, 150);

		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.centerOnScreen();
		
		
	}
	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
