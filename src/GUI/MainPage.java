package GUI;

import java.sql.*;

import Objects.Flights.Flight;
import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;

public class MainPage extends Application implements EventHandler<ActionEvent> {

	private ObservableList<ObservableList> data;
	private String usernameId = "";

	private boolean isAdmin = false;

	public String getUsernameId() {
		return usernameId;
	}

	public void setUsernameId(String usernameId) {
		this.usernameId = usernameId;
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Home");
		primaryStage.setResizable(false);
		AnchorPane anchor = new AnchorPane();
		anchor.setPadding(new Insets(10, 10, 10, 10));

		TableView<Flight> table = new TableView<>();
		final ObservableList<Flight> data = FXCollections.observableArrayList();

		Label userId = new Label();
		Button searchFlights = new Button();
		Label myFlights = new Label();
		Button deleteFlights = new Button();
		Button logOut = new Button();
		TextField deleteFlightTxt = new TextField();
		Button refresh = new Button("Refresh");

		try {
			Connection myConn;
			myConn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/sys", "root", "password");

			String sqlUserCheck = "SELECT * FROM `flights`.`users` where username = '" + Login.getUser()
					+ "' and isAdmin = '1'";
			// create a statement
			Statement myStat = myConn.createStatement();
			// execute a query
			ResultSet myRs;
			myRs = myStat.executeQuery(sqlUserCheck);

			// Creates a variable for future checking
			int count = 0;
			while (myRs.next()) {
				count = count + 1;

			}

			if (count > 0) {
				setAdmin(true);
			}

		} catch (Exception exc) {

		}

		userId.setAlignment(javafx.geometry.Pos.CENTER);
		userId.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
		userId.setLayoutX(970.0);
		userId.setLayoutY(20.0);
		userId.setText("Logged in as: " + Login.getUser());
		userId.setTextAlignment(javafx.scene.text.TextAlignment.RIGHT);
		userId.setFont(new Font(18.0));

		searchFlights.setLayoutX(1100.0);
		searchFlights.setLayoutY(214.0);
		searchFlights.setMnemonicParsing(false);
		searchFlights.setText("Search Flights");
		searchFlights.setOnAction(e -> {
			searchPage search = new searchPage();
			try {
				search.start(primaryStage);
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});

		table.setLayoutX(10.0);
		table.setLayoutY(57.0);
		table.setPrefHeight(329.0);
		table.setPrefWidth(1031.11);

		myFlights.setLayoutX(420.0);
		myFlights.setLayoutY(10.0);
		myFlights.setText("My Flights");
		myFlights.setFont(new Font(25.0));

		refresh.setLayoutX(1100.0);
		refresh.setLayoutY(179.0);
		refresh.setMnemonicParsing(false);
		refresh.setPrefHeight(25);
		refresh.setPrefWidth(90);
		refresh.setOnAction(e -> {
			try {

				Connection myConn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/sys", "root", "password");
				String sqlUserCheck = "select  `number`, `airline`, `origin_city`, `destination_city`, `departure_time`, `arrival_time`, `departure_date`, `arrival_date` from\r\n"
						+ "flights.flight inner Join flights.Flight_User\r\n" + "on Flight_id = flight.id\r\n"
						+ "inner join flights.users on Flight_User.User_id = users.id where username = '"
						+ Login.getUser() + "'";
				// create a statement
				PreparedStatement myStat = myConn.prepareStatement(sqlUserCheck);
				// execute a query
				ResultSet myRs;
				myRs = myStat.executeQuery();
				table.getItems().clear();

				// Creates a variable for future checking

				while (myRs.next()) {

					data.add(new Flight(myRs.getInt("number"), myRs.getString("airline"), myRs.getString("origin_city"),
							myRs.getString("destination_city"), myRs.getDate("departure_date"),
							myRs.getTime("departure_time"), myRs.getDate("arrival_date"),
							myRs.getTime("arrival_time")));
					table.setItems(data);
				}
				myStat.close();
				myRs.close();
			} catch (Exception ex) {

			}

		});

		logOut.setLayoutX(1100.0);
		logOut.setLayoutY(249.0);
		logOut.setMnemonicParsing(false);
		logOut.setPrefHeight(25);
		logOut.setPrefWidth(90);
		logOut.setText("Log Out");
		logOut.setOnAction(e -> {
			Login loginPage = new Login();
			try {
				loginPage.start(primaryStage);
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		try {
			Connection myConn;
			myConn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/sys", "root", "password");

			String sqlUserCheck = "SELECT * FROM `flights`.`users` where username = '" + Login.getUser() + "'";
			// create a statement
			Statement myStat = myConn.createStatement();
			// execute a query
			ResultSet myRs;
			myRs = myStat.executeQuery(sqlUserCheck);

			// Creates a variable for future checking
			int count = 0;
			while (myRs.next()) {
				count = count + 1;
				setUsernameId(myRs.getString("id"));

			}

		} catch (Exception exc) {

		}

		Label deleteFlightLbl = new Label("Enter Flight Number to Delete:");
		deleteFlightLbl.setLayoutX(1065);
		deleteFlightLbl.setLayoutY(89);

		deleteFlightTxt.setLayoutX(1100);
		deleteFlightTxt.setLayoutY(109);
		deleteFlightTxt.setPrefHeight(25);
		deleteFlightTxt.setPrefWidth(90);

		deleteFlights.setLayoutX(1100.0);
		deleteFlights.setLayoutY(139.0);
		deleteFlights.setMnemonicParsing(false);
		deleteFlights.setPrefHeight(25.0);
		deleteFlights.setPrefWidth(90.0);
		deleteFlights.setText("Delete Flight");
		deleteFlights.setOnAction(e -> {
			try {

				Connection myConn;
				myConn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/sys", "root", "password");

				String sqlFlightDelete = "Delete FROM flights.Flight_User where Flight_User.Flight_id = '"
						+ deleteFlightTxt.getText().trim() + "' and Flight_User.User_id= '" + getUsernameId() + "'";
				String sqlFlightCheck = "SELECT `Flight_id`, `User_id` FROM `flights`.`Flight_User` where User_id = '"
						+ getUsernameId() + "' and Flight_id= '" + deleteFlightTxt.getText().trim() + "'";
				// create a statement
				Statement myStat = myConn.createStatement();
				// execute a query
				ResultSet myRs;
				myRs = myStat.executeQuery(sqlFlightCheck);

				// Creates a variable for future checking
				int count = 0;
				while (myRs.next()) {
					count = count + 1;
					setUsernameId(myRs.getString("User_id"));
				}

				if (count > 0) {
					myStat.executeUpdate(sqlFlightDelete);

				}

				else {
					AlertBox.display("Error!",
							"Error! you have not  booked flight number: " + deleteFlightTxt.getText().trim()
									+ " yet. \n You cannot delete a flight you havent booked!");
				}
				myStat.close();
				myRs.close();
				myConn.close();
			}

			catch (SQLException e1) {
				System.out.println(e1.getMessage());

			}

		});

		TableColumn<Flight, Integer> column1 = new TableColumn<Flight, Integer>("Flight Number");
		column1.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));
		column1.setMinWidth(128.88);

		TableColumn<Flight, String> column2 = new TableColumn<Flight, String>("Airline");
		column2.setCellValueFactory(new PropertyValueFactory<>("Airline"));
		column2.setMinWidth(128.88);

		TableColumn<Flight, String> column3 = new TableColumn<Flight, String>("Origin City");
		column3.setCellValueFactory(new PropertyValueFactory<>("originCity"));
		column3.setMinWidth(128.88);

		TableColumn<Flight, String> column4 = new TableColumn<Flight, String>("Destination City");
		column4.setCellValueFactory(new PropertyValueFactory<>("destinationCity"));
		column4.setMinWidth(128.88);

		TableColumn<Flight, Date> column5 = new TableColumn<Flight, Date>("Departure Date");
		column5.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
		column5.setMinWidth(128.88);

		TableColumn<Flight, Time> column6 = new TableColumn<Flight, Time>("Departure Time");
		column6.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
		column6.setMinWidth(128.88);

		TableColumn<Flight, Date> column7 = new TableColumn<Flight, Date>("Arrival Date");
		column7.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
		column7.setMinWidth(128.88);

		TableColumn<Flight, Time> column8 = new TableColumn<Flight, Time>("Arrival Time");
		column8.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
		column8.setMinWidth(128.88);

		table.getColumns().addAll(column1, column2, column3, column4, column5, column6, column7, column8);

		try {
			Connection myConn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/sys", "root", "password");
			String sqlUserCheck = "select  `number`, `airline`, `origin_city`, `destination_city`, `departure_time`, `arrival_time`, `departure_date`, `arrival_date` from\r\n"
					+ "flights.flight inner Join flights.Flight_User\r\n" + "on Flight_id = flight.id\r\n"
					+ "inner join flights.users on Flight_User.User_id = users.id where username = '" + Login.getUser()
					+ "'";
			// create a statement
			PreparedStatement myStat = myConn.prepareStatement(sqlUserCheck);
			// execute a query
			ResultSet myRs;
			myRs = myStat.executeQuery();
			table.getItems().clear();

			// Creates a variable for future checking

			while (myRs.next()) {

				data.add(new Flight(myRs.getInt("number"), myRs.getString("airline"), myRs.getString("origin_city"),
						myRs.getString("destination_city"), myRs.getDate("departure_date"),
						myRs.getTime("departure_time"), myRs.getDate("arrival_date"), myRs.getTime("arrival_time")));
				table.setItems(data);
			}
			myStat.close();
			myRs.close();
		} catch (Exception ex) {

		}
		Button adminTool = new Button("Admin Add flight");
		adminTool.setLayoutX(1100);
		adminTool.setLayoutY(290);
		adminTool.setOnAction(e ->{
			FlightRegistration flight =new FlightRegistration();
			try {
				flight.start(primaryStage);
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		Button adminTool1 = new Button("Admin Flight Edit/Delete");
		adminTool1.setLayoutX(1100);
		adminTool1.setLayoutY(330);
		adminTool1.setOnAction(e -> {
			flightUpdate update = new flightUpdate();
			try {
				update.start(primaryStage);
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		if (isAdmin() == true) {
			anchor.getChildren().add(adminTool);
			anchor.getChildren().add(adminTool1);
		}

		anchor.getChildren().addAll(deleteFlightLbl, userId, searchFlights, table, myFlights, deleteFlights,
				deleteFlightTxt, logOut, refresh);

		Scene scene = new Scene(anchor, 1250, 500);

		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.centerOnScreen();
	}

	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

}
