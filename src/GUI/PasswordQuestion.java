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

	String user = PasswordRecovery.user;

	String secQuest = "";
	// string to hold security question answer once received from database
	String secAnswer = "";

	// string for user answer to security question to be compared to database entry
	String userAnswer = "";

	// string for password to be held once received from the database
	String password = "";

	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("Security Question");
		primaryStage.setResizable(false);
		GridPane grid = new GridPane();
		// grid.setGridLinesVisible(true);
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(10);
		grid.setHgap(10);

		try {
			// get a connection to the database
			Connection myConn = DriverManager.getConnection(
					"jdbc:mysql://35.193.248.221:3306/?verifyServerCertificate=false&useSSL=true", "root",
					"Tdgiheay12");
			// create a statement
			Statement myStat = myConn.createStatement();
			// execute a query
			ResultSet myRs;
			String sqlUserCheck = "SELECT `security_question` FROM `flights`.`users` where username = '" + user + "'";
			myRs = myStat.executeQuery(sqlUserCheck);

			// Creates a variable for future checking
			int count = 0;

			while (myRs.next()) {

				count += 1;

				secQuest = myRs.getString("security_question");

				// sets security answer from database to be compared to user answer
				secAnswer = myRs.getString("security_answer");

				// stores password from database
				password = myRs.getString("password");
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
						"jdbc:mysql://35.193.248.221:3306/?verifyServerCertificate=false&useSSL=true", "root",
						"Tdgiheay12");
				// create a statement
				Statement myStat = myConn.createStatement();
				// execute a query
				ResultSet myRs;
				String sqlUserCheck = "SELECT * FROM `flights`.`users` where username = '" + user + "'";
				myRs = myStat.executeQuery(sqlUserCheck);

				// Creates a variable for future checking
				int count = 0;

				while (myRs.next()) {

					count += 1;

					// sets security answer from database to be compared to user answer
					secAnswer = myRs.getString("security_answer");

					// stores password from database
					password = myRs.getString("password");
				}

				myRs.close();
				myStat.close();
				myConn.close();
				// If user is in the database and the password is correct it it will take user
				// to main page
				if (count == 1 && userAnswer.equals(secAnswer)) {
					AlertBox.display("Password", "The password for your account is: " + password);

				} else if (count == 1 && secAnswer != userAnswer) {
					AlertBox.display("Incorrect Answer", "That answer is incotrrect. Please try again.");
				}

			}

			catch (Exception e1) {

			}

		});

		Button cancel = new Button("cancel");
		GridPane.setConstraints(cancel, 1, 3);
		GridPane.setHalignment(cancel, HPos.RIGHT);
		cancel.setOnAction(e -> {
			{
				passwordRecovery recoverPage = new passwordRecovery();
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
