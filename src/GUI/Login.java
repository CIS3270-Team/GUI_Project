package GUI;

import java.sql.*;
import javafx.application.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;

public class Login extends Application implements EventHandler<ActionEvent> {
	static Customer customer;

	public static void setCustomer(Customer customer) {
		Login.customer = customer;
	}
	public static Customer getCustomer() {
		return customer;
	}

	public static Scene scene;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Application.launch(args);

	}

	public void start(Stage primaryStage) throws Exception {
		
		Customer customer = new Customer();
		setCustomer(customer);
		
		primaryStage.setTitle("Log In");
		primaryStage.setResizable(false);
		AnchorPane anchor = new AnchorPane();
		anchor.setPadding(new Insets(10, 10, 10, 10));

		Label loginLabel = new Label("Enter your username and password");
		loginLabel.setAlignment(javafx.geometry.Pos.CENTER);
		loginLabel.setLayoutX(144.0);
		loginLabel.setLayoutY(51.0);
		loginLabel.setPrefHeight(32.0);
		loginLabel.setPrefWidth(351.0);
		loginLabel.setText("Enter Your Username And Password");
		loginLabel.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
		loginLabel.setFont(new Font(22.0));

		Label usernameLabel = new Label("Username:");
		usernameLabel.setLayoutX(189.0);
		usernameLabel.setLayoutY(131.0);
		usernameLabel.setText("Username:");
		usernameLabel.setFont(new Font(20.0));

		TextField userTxt = new TextField();
		userTxt.setLayoutX(311.0);
		userTxt.setLayoutY(133.0);
		userTxt.setPromptText("Username");

		Label passwordLabel = new Label("Password:");
		passwordLabel.setLayoutX(193.0);
		passwordLabel.setLayoutY(174.0);
		passwordLabel.setFont(new Font(20.0));

		PasswordField passwordTxt = new PasswordField();
		passwordTxt.setLayoutX(311.0);
		passwordTxt.setLayoutY(177.0);
		passwordTxt.setPromptText("Password");

		// login button and event handler
		Button login = new Button("Log In");
		login.setLayoutX(237.0);
		login.setLayoutY(222.0);
		login.setMnemonicParsing(false);
		login.setPrefHeight(25.0);
		login.setPrefWidth(149.0);
		login.setText("Log In");

		login.setOnAction(e -> {

			// Exception handling for connecting to the database
			try {
				// get a connection to the database
				Connection myConn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/sys", "root", "password");
				// create a statement
				PreparedStatement myStat = myConn.prepareStatement("SELECT username, password FROM customer WHERE username = '" + customer.getUser() 
					+ "' and password = '" + customer.getPassword() + "'");
				// execute a query
				ResultSet myRs;

				// collects user name from the user name text field and assigns to a string
				// called user
				customer.setUser(userTxt.getText().trim());

				// Collects password from the password text field and assigns to a string called
				// password
				customer.setPassword(passwordTxt.getText().trim());
				
				// SQL query to check if user name and password is in database
				myRs = myStat.executeQuery();
				
				// Creates a variable for future checking
				int count = 0;

				// While loop that will determine if user is in the database
				while (myRs.next()) {

					count = count + 1;

				}
				
				PreparedStatement getID = myConn.prepareStatement("SELECT Customer_ID FROM customer WHERE username = '" + customer.getUser() + "'");
				myRs = getID.executeQuery();
				
				while (myRs.next()) {
					customer.setCustomer_ID("Customer_ID");
				}
				
				myRs.close();
				myStat.close();
				myConn.close();

				// If user is in the database and the password is correct it it will take user
				// to main page
				if (count == 1) {
					MainPage MainPage = new MainPage();
					MainPage.start(primaryStage);
					send(customer);

				}

				/**
				 * If user is not in database or password is incorrect, nothing happens
				 **/
				else if (count < 1) {
					
				}

			}

			catch (Exception ex) {

			}
			;
		});

		Button register = new Button("Register");
		register.setLayoutX(237.0);
		register.setLayoutY(255.0);
		register.setMnemonicParsing(false);
		register.setPrefHeight(25.0);
		register.setPrefWidth(149.0);
		register.setOnAction(e -> {
			Register registerPage = new Register();
			try {
				registerPage.start(primaryStage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		Button passwordRecover = new Button("Forgot Password");
		passwordRecover.setLayoutX(236.0);
		passwordRecover.setLayoutY(290.0);
		passwordRecover.setMnemonicParsing(false);
		passwordRecover.setPrefHeight(26.0);
		passwordRecover.setPrefWidth(150.0);
		passwordRecover.setOnAction(e -> {
			PasswordRecovery recoverPage = new PasswordRecovery();
			try {

				recoverPage.start(primaryStage);
				
			}

			catch (Exception e1) {
			}
		});

		Button exit = new Button("Exit");
		exit.setLayoutX(236.0);
		exit.setLayoutY(328.0);
		exit.setMnemonicParsing(false);
		exit.setPrefHeight(25.0);
		exit.setPrefWidth(150.0);
		exit.setOnAction(e -> {
			//AlertBox.display("Exit", "System Will Now exit.");
			System.exit(0);
		});

		exit.setMinWidth(150);
		register.setMinWidth(150);
		passwordRecover.setMinWidth(150);
		login.setMinWidth(150);

		anchor.getChildren().addAll(userTxt, passwordTxt, login, register, passwordRecover, usernameLabel, exit,
				loginLabel, passwordLabel);
		BackgroundImage myBI = new BackgroundImage(new Image(
				
				
				"file:///C:/Thushar/College/Fall%202021/Application%20Development/photo-1529074963764-98f45c47344b.jpg"),
				BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);
		anchor.setBackground(new Background(myBI));
		scene = new Scene(anchor, 550, 370);
		;
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.centerOnScreen();
	}
	
	public Customer send(Customer customer) {
		return customer;
	}
	
	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub

	}

}
