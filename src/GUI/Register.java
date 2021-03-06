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


public class Register extends Application implements EventHandler<ActionEvent> {

	TextField tfName = new TextField();
	TextField tlName = new TextField();
	TextField tAddress = new TextField();
	TextField tZip = new TextField();
	TextField tState = new TextField();
	TextField tUserName = new TextField();
	TextField tEmail = new TextField();
	//TextField tSSN = new TextField();
	TextField tSecutiryQ = new TextField();
	TextField tSecutiryA = new TextField();
	PasswordField tPassword = new PasswordField();
	PasswordField tConfirm = new PasswordField();
	Button button = new Button();
	Button button0 = new Button();
	Label Title = new Label();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}

	/**
	 * Initialize the contents of the frame.
	 */

	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Register");
		primaryStage.setResizable(false);
		AnchorPane anchor = new AnchorPane();
		anchor.setPadding(new Insets(10, 10, 10, 10));

		Label firstNameLbl = new Label();
		firstNameLbl.setLayoutX(170.0);
		firstNameLbl.setLayoutY(81.0);
		firstNameLbl.setText("First Name");
		firstNameLbl.setFont(new Font(20.0));

		Label lastNameLbl = new Label();
		lastNameLbl.setLayoutX(171.0);
		lastNameLbl.setLayoutY(114.0);
		lastNameLbl.setText("Last Name");
		lastNameLbl.setFont(new Font(20.0));

		Label addressLbl = new Label();
		addressLbl.setLayoutX(194.0);
		addressLbl.setLayoutY(149.0);
		addressLbl.setText("Address");
		addressLbl.setFont(new Font(20.0));

		Label zipLbl = new Label();
		zipLbl.setLayoutX(184.0);
		zipLbl.setLayoutY(184.0);
		zipLbl.setText("Zip Code");
		zipLbl.setFont(new Font(20.0));

		Label stateLbl = new Label();
		stateLbl.setLayoutX(218.0);
		stateLbl.setLayoutY(221.0);
		stateLbl.setText("State");
		stateLbl.setFont(new Font(20.0));

		Label usernameLbl = new Label();
		usernameLbl.setLayoutX(174.0);
		usernameLbl.setLayoutY(256.0);
		usernameLbl.setText("Username");
		usernameLbl.setFont(new Font(20.0));

		Label passLbl = new Label();
		passLbl.setLayoutX(177.0);
		passLbl.setLayoutY(287.0);
		passLbl.setText("Password");
		passLbl.setFont(new Font(20.0));

		Label pconfirmPassLbl = new Label();
		pconfirmPassLbl.setLayoutX(103.0);
		pconfirmPassLbl.setLayoutY(329.0);
		pconfirmPassLbl.setText("Confirm Password");
		pconfirmPassLbl.setFont(new Font(20.0));

		Label emailLbl = new Label();
		emailLbl.setLayoutX(217.0);
		emailLbl.setLayoutY(362.0);
		emailLbl.setText("Email");
		emailLbl.setFont(new Font(20.0));


		Label secQLbl = new Label();
		secQLbl.setLayoutX(105.0);
		secQLbl.setLayoutY(434.0);
		secQLbl.setText("Security Question");
		secQLbl.setFont(new Font(20.0));

		Label secALbl = new Label();
		secALbl.setLayoutX(124.0);
		secALbl.setLayoutY(472.0);
		secALbl.setText("Security Answer");
		secALbl.setFont(new Font(20.0));

		tfName.setLayoutX(274.0);
		tfName.setLayoutY(83.0);
		tfName.setPromptText("First Name");

		tlName.setLayoutX(274.0);
		tlName.setLayoutY(116.0);
		tlName.setPromptText("Last Name");

		tAddress.setLayoutX(274.0);
		tAddress.setLayoutY(151.0);
		tAddress.setPromptText("Address");

		tZip.setLayoutX(274.0);
		tZip.setLayoutY(186.0);
		tZip.setPromptText("#####");

		tState.setLayoutX(274.0);
		tState.setLayoutY(223.0);
		tState.setPromptText("State");

		tUserName.setLayoutX(274.0);
		tUserName.setLayoutY(258.0);
		tUserName.setPromptText("Username");

		tEmail.setLayoutX(274.0);
		tEmail.setLayoutY(364.0);
		tEmail.setPromptText("Example@example.com");

		tSecutiryQ.setLayoutX(274.0);
		tSecutiryQ.setLayoutY(436.0);
		tSecutiryQ.setPromptText("Security Question");

		tSecutiryA.setLayoutX(274.0);
		tSecutiryA.setLayoutY(474.0);
		tSecutiryA.setPromptText("Security Answer");

		tPassword.setLayoutX(274.0);
		tPassword.setLayoutY(290.0);
		tPassword.setPromptText("Password");

		tConfirm.setLayoutX(274.0);
		tConfirm.setLayoutY(329.0);
		tConfirm.setPromptText("Confirm PAssword");

		button.setLayoutX(284.0);
		button.setLayoutY(534.0);
		button.setMnemonicParsing(false);
		button.setPrefHeight(25.0);
		button.setPrefWidth(105.0);
		button.setText("Register");
		button.setOnAction(e -> {
			if (tfName.getText().isEmpty() || tlName.getText().isEmpty() || tUserName.getText().isEmpty()
					|| tPassword.getText().isEmpty() || tEmail.getText().isEmpty() || tSecutiryQ.getText().isEmpty() || tSecutiryA.getText().isEmpty()) {
				
			} else {
				if (tPassword.getText().equals((tConfirm.getText())) == false) {
					
				} else {
					
					

					try {
						Connection myConn = DriverManager.getConnection(
								"jdbc:mysql://localhost:3306/sys", "root", "password");
						// create a statement
						int count = 0;
						PreparedStatement checkQuery = myConn.prepareStatement("SELECT * FROM customer");
						System.out.println("Checking query");
						ResultSet check = checkQuery.executeQuery();
						System.out.println("Queried");
						while (check.next()) {
							count++;
							System.out.println("Counting");
						}
						String countNew = String.valueOf(count);
						
						PreparedStatement myStat = myConn.prepareStatement("INSERT INTO customer VALUES ('" + countNew + "', '" + tfName.getText() + "', '" + tlName.getText() + "', '" + tAddress.getText() + "', '" + tZip.getText() + "', '" + tState.getText() + "', '" + tUserName.getText() + "', '" + tPassword.getText() + "', '" + tEmail.getText() + "', '" + tSecutiryQ.getText() + "', '" + tSecutiryA.getText() + "')");
						System.out.println("Prepared");
						myStat.executeUpdate();
						System.out.println("Jobs done");

						

						Thread.sleep(3000);

						Login loginPage = new Login();
						try {
							loginPage.start(primaryStage);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} catch (SQLException | InterruptedException e1) {
						
						System.out.println(e1.getMessage());
					}

				}
			}
		});

		button0.setLayoutX(154.0);
		button0.setLayoutY(534.0);
		button0.setMnemonicParsing(false);
		button0.setText("Return To Log In");
		button0.setOnAction(e -> {
			Login loginPage = new Login();
			try {
				loginPage.start(primaryStage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});

		Title.setLayoutX(204.0);
		Title.setLayoutY(14.0);
		Title.setText("Register User");
		Title.setFont(new Font(24.0));

		anchor.getChildren().addAll(firstNameLbl,lastNameLbl,addressLbl,zipLbl, stateLbl,usernameLbl,passLbl,
				pconfirmPassLbl, emailLbl,secQLbl,secALbl,tfName, tlName, tAddress, tZip, tState,tUserName,
				tEmail, tSecutiryQ, tSecutiryA, tPassword, tConfirm, button, button0, Title);
		
		
		Scene scene = new Scene(anchor, 613, 612);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.centerOnScreen();
	}

}
