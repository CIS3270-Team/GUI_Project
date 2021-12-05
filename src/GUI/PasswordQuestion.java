package GUI;

import java.sql.*;
import javafx.application.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;

public class PasswordQuestion extends Application implements EventHandler<ActionEvent> {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		launch(args);

	}

	String secQuest = "";
	// string to hold security question answer once received from database
	String secAnswer = "";

	// string for user answer to security question to be compared to database entry
	String userAnswer = "";

	// string for password to be held once received from the database
	String password = "";

	@Override
	public void start(Stage primaryStage) throws Exception {

		Customer customer = Login.getCustomer();
		
		primaryStage.setTitle("Security Question");
		primaryStage.setResizable(false);
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(10);
		grid.setHgap(10);

		try {
			// get a connection to the database
			Connection myConn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/sys", "root", "password");
			// create a statement
			Statement myStat = myConn.createStatement();
			// execute a query
			ResultSet myRs;
			String sqlUserCheck = "SELECT Security_Question FROM customer where username = '" + customer.getUser() + "'";
			myRs = myStat.executeQuery(sqlUserCheck);

			// Creates a variable for future checking
			int count = 0;

			while (myRs.next()) {

				count += 1;

				secQuest = myRs.getString("Security_Question");

				// sets security answer from database to be compared to user answer
				secAnswer = myRs.getString("Security_Answer");

				// stores password from database
				password = myRs.getString("Password");
			}
			myStat.close();
			myRs.close();
			myConn.close();
			// If user is in the database and the password is correct it it will take user
			// to main page
			if (count == 1) {

			}

		} catch (Exception e1) {

		}

		Label securityQuestionLabel = new Label("Enter The Answer To Your Security Question Below.");
		GridPane.setConstraints(securityQuestionLabel, 1, 0);
		grid.setAlignment(Pos.TOP_CENTER);
		securityQuestionLabel.setTextAlignment(TextAlignment.CENTER);

		Label questionLabel = new Label(secQuest);
		GridPane.setConstraints(questionLabel, 1, 1);

		TextField answerField = new TextField();
		GridPane.setConstraints(answerField, 1, 2);

		Button okay = new Button("Okay");
		GridPane.setConstraints(okay, 1, 3);
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
				ResultSet myRs;
				String sqlUserCheck = "SELECT * FROM customer WHERE Username = '" + customer.getUser() + "'";
				myRs = myStat.executeQuery(sqlUserCheck);

				// Creates a variable for future checking
				int count = 0;

				while (myRs.next()) {

					count += 1;

					// sets security answer from database to be compared to user answer
					secAnswer = myRs.getString("Security Answer");
					// stores password from database
					password = myRs.getString("Password");
				}

				myRs.close();
				myStat.close();
				myConn.close();
				// If user is in the database and the password is correct it it will take user
				// to main page
				if (userAnswer.equals(secAnswer)) {
					
					PasswordReset resetPage = new PasswordReset();
					resetPage.start(primaryStage);

				} else if (count == 1 && secAnswer != userAnswer) {
					
				}

			}

			catch (Exception e1) {

			}

		});

		Button cancel = new Button("Cancel");
		GridPane.setConstraints(cancel, 1, 3);
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
		grid.getChildren().addAll(securityQuestionLabel, questionLabel, answerField, okay, cancel);
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
