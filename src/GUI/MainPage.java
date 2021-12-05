package GUI;

import java.sql.*;

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

	private String usernameId = "";

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
		Customer customer = Login.getCustomer();
		primaryStage.setTitle("Home");
		primaryStage.setResizable(false);
		AnchorPane anchor = new AnchorPane();
		anchor.setPadding(new Insets(10, 10, 10, 10));
		
		//Tracks changes in flights
		TableView<Flight> table = new TableView<>();
		final ObservableList<Flight> data = FXCollections.observableArrayList();

		Label userId = new Label();
		Button searchFlights = new Button();
		Label myFlights = new Label();
		Button deleteFlights = new Button();
		Button logOut = new Button();
		TextField deleteFlightTxt = new TextField();
		Button refresh = new Button("Refresh");


		userId.setAlignment(javafx.geometry.Pos.CENTER);
		userId.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
		userId.setLayoutX(970.0);
		userId.setLayoutY(20.0);
		userId.setText("Logged in as: " + customer.getUser());
		userId.setTextAlignment(javafx.scene.text.TextAlignment.RIGHT);
		userId.setFont(new Font(18.0));

		searchFlights.setLayoutX(1100.0);
		searchFlights.setLayoutY(214.0);
		searchFlights.setMnemonicParsing(false);
		searchFlights.setText("Search Flights");
		searchFlights.setOnAction(e -> {
			SearchQuestion search = new SearchQuestion();
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
				String sqlUserCheck = "SELECT flights.Flight_ID, Airline, Arrival_City_ID, Departure_City_ID, Departure_Date, Departure_Time, Arrival_Date, Arrival_Time FROM flights, customer, reservation WHERE flights.Reservation_ID = reservation.Reservation_ID AND reservation.Customer_ID = customer.Customer_ID and Username = '" + customer.getUser() + "'";
				// create a statement
				PreparedStatement myStat = myConn.prepareStatement(sqlUserCheck);
				// execute a query
				ResultSet myRs;
				myRs = myStat.executeQuery();
				table.getItems().clear();

				// Creates a variable for future checking

				while (myRs.next()) {

					table.getItems().add(new Flight(myRs.getInt("Flight_ID"), myRs.getString("Airline"), myRs.getString("Arrival_City_ID"),
							myRs.getString("Departure_City_ID"), myRs.getString("Departure_Date"),
							myRs.getString("Departure_Time"), myRs.getString("Arrival_Date"),
							myRs.getString("Arrival_Time")));
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

			String sqlUserCheck = "SELECT flights.Flight_ID, Airline, Arrival_City_ID, Departure_City_ID, Departure_Date, Departure_Time, Arrival_Date, Arrival_Time FROM flights, reservation, customer WHERE flights.Reservation_ID = reservation.Reservation_ID AND reservation.Customer_ID = customer.Customer_ID AND Username = '" + customer.getUser() + "'";
			// create a statement
			PreparedStatement myStat = myConn.prepareStatement(sqlUserCheck);
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
				String sqlFlightUpdate = "UPDATE flights SET Reservation_ID = NULL WHERE flights.Flight_ID = '" + deleteFlightTxt.getText().trim() + "'";
				String sqlSeatsUpdate = "UPDATE flights SET Seats_Available = Seats_Available + 1 WHERE flights.Flight_ID = '" + deleteFlightTxt.getText().trim() + "'";
				String sqlFlightDelete = "DELETE FROM reservation where reservation.Flight_id = '"
						+ deleteFlights.getText().trim() + "' and reservation.Customer_ID= '" + customer.getCustomer_ID() + "'";
				String sqlFlightCheck = "SELECT Flight_ID, Customer_ID FROM reservation where Customer_ID = '"
						+ customer.getCustomer_ID() + "' and Flight_ID= '" + deleteFlightTxt.getText().trim() + "'";
				// create a statement
				PreparedStatement myStats = myConn.prepareStatement(sqlFlightUpdate);
				myStats.executeUpdate(sqlFlightUpdate);
				myStats = myConn.prepareStatement(sqlSeatsUpdate);
				myStats.executeUpdate(sqlSeatsUpdate);
				PreparedStatement myStat = myConn.prepareStatement(sqlFlightDelete);
				// execute a query
				ResultSet myRs;
				myStat.executeUpdate(sqlFlightDelete);
				myRs = myStat.executeQuery(sqlFlightCheck);
				myStats.close();
				myStat.close();
				myRs.close();
				myConn.close();
			}

			catch (SQLException e1) {
				System.out.println(e1.getMessage());

			}

		});

		TableColumn<Flight, Integer> column1 = new TableColumn<>("Flight Number");
		column1.setCellValueFactory(new PropertyValueFactory<>("Flight_ID"));
		column1.setMinWidth(128.88);

		TableColumn<Flight, String> column2 = new TableColumn<>("Airline");
		column2.setCellValueFactory(new PropertyValueFactory<>("Airline"));
		column2.setMinWidth(128.88);

		TableColumn<Flight, String> column3 = new TableColumn<>("Origin City");
		column3.setCellValueFactory(new PropertyValueFactory<>("Arrival_City_ID"));
		column3.setMinWidth(128.88);

		TableColumn<Flight, String> column4 = new TableColumn<>("Destination City");
		column4.setCellValueFactory(new PropertyValueFactory<>("Departure_City_ID"));
		column4.setMinWidth(128.88);

		TableColumn<Flight, String> column5 = new TableColumn<>("Departure Date");
		column5.setCellValueFactory(new PropertyValueFactory<>("Departure_Date"));
		column5.setMinWidth(128.88);

		TableColumn<Flight, String> column6 = new TableColumn<>("Departure Time");
		column6.setCellValueFactory(new PropertyValueFactory<>("Departure_Time"));
		column6.setMinWidth(128.88);

		TableColumn<Flight, String> column7 = new TableColumn<>("Arrival Date");
		column7.setCellValueFactory(new PropertyValueFactory<>("Arrival_Date"));
		column7.setMinWidth(128.88);

		TableColumn<Flight, String> column8 = new TableColumn<>("Arrival Time");
		column8.setCellValueFactory(new PropertyValueFactory<>("Arrival_Time"));
		column8.setMinWidth(128.88);

		table.getColumns().addAll(column1, column2, column3, column4, column5, column6, column7, column8);


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

	public Customer receive(Customer customer) {
		return customer;
	}
	
	public void start1(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
