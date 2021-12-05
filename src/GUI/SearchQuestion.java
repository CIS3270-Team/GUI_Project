package GUI;

import java.sql.*;
import java.util.ArrayList;

import javafx.application.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;

public class SearchQuestion extends Application implements EventHandler<ActionEvent> {
	private ObservableList<ObservableList> data;
	private String usernameId = "";
	private Date depDate;
	private Date arrDate;
	private Time depTime;
	private Time arrTime;
	private Date newDepDate;
	private Date newArrDate;
	private Time newDepTime;
	private Time newArrTime;
	private Timestamp depTimestamp;
	private Timestamp arrTimestamp;
	private Timestamp newFlightDep;
	private int conflictCount = 0;
	private int countHolder;
	private int numberOfReservations;

	public int getNumberOfReservations() {
		Connection myConn;
		int count = 1;

		try {
			myConn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/sys", "root", "password");

			String sqlUserCheck = "SELECT distinct * FROM reservation";
			// create a statement
			Statement myStat = myConn.createStatement();
			// execute a query
			ResultSet myRs;
			myRs = myStat.executeQuery(sqlUserCheck);
			while (myRs.next()) {
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	public Date getNewDepDate() {
		return newDepDate;
	}

	public void setNewDepDate(Date newDepDate) {
		this.newDepDate = newDepDate;
	}

	public Date getNewArrDate() {
		return newArrDate;
	}

	public void setNewArrDate(Date newArrDate) {
		this.newArrDate = newArrDate;
	}

	public Time getNewDepTime() {
		return newDepTime;
	}

	public void setNewDepTime(Time newDepTime) {
		this.newDepTime = newDepTime;
	}

	public Time getNewArrTime() {
		return newArrTime;
	}

	public void setNewArrTime(Time newArrTime) {
		this.newArrTime = newArrTime;
	}

	public Date getDepDate() {
		return depDate;
	}

	public void setDepDate(Date depDate) {
		this.depDate = depDate;
	}

	public Date getArrDate() {
		return arrDate;
	}

	public void setArrDate(Date arrDate) {
		this.arrDate = arrDate;
	}

	public Time getDepTime() {
		return depTime;
	}

	public void setDepTime(Time depTime) {
		this.depTime = depTime;
	}

	public Time getArrTime() {
		return arrTime;
	}

	public void setArrTime(Time arrTime) {
		this.arrTime = arrTime;
	}

	public Timestamp getDepTimestamp() {
		return depTimestamp;
	}

	public void setDepTimestamp(Timestamp depTimestamp) {
		this.depTimestamp = depTimestamp;
	}

	public Timestamp getArrTimestamp() {
		return arrTimestamp;
	}

	public void setArrTimestamp(Timestamp arrTimestamp) {
		this.arrTimestamp = arrTimestamp;
	}

	public String getUsernameId() {
		return usernameId;
	}

	public void setUsernameId(String usernameId) {
		this.usernameId = usernameId;
	}

	public int getConflictCount() {
		return conflictCount;
	}

	public void setConflictCount(int conflictCount) {
		this.conflictCount = conflictCount;
	}

	public int getCountHolder() {
		return countHolder;
	}

	public void setCountHolder(int countHolder) {
		this.countHolder = countHolder;
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Customer customer = Login.getCustomer();
		primaryStage.setTitle("Search");
		AnchorPane anchor = new AnchorPane();
		anchor.setPadding(new Insets(20, 20, 20, 20));
		
		//Create the table to see flights list
		TableView<Flight> table = new TableView<>();
		ArrayList<Integer> array = new ArrayList<>(); 

		Button returnHome = new Button("Return Home");
		returnHome.setOnAction(e -> {
			MainPage home = new MainPage();
			try {
				home.start(primaryStage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		returnHome.setLayoutX(970);
		returnHome.setLayoutY(60);
		returnHome.setMinWidth(100);

		Button logOut = new Button("Log Out");
		logOut.setOnAction(e -> {
			Login loginPage = new Login();
			try {
				loginPage.start(primaryStage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		logOut.setLayoutX(1080);
		logOut.setLayoutY(60);
		logOut.setMinWidth(100);

		Label userId = new Label();
		userId.setAlignment(javafx.geometry.Pos.CENTER);
		userId.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
		userId.setLayoutX(1000.0);
		userId.setLayoutY(10.0);
		userId.setText("Logged in as: " + customer.getUser());
		userId.setTextAlignment(javafx.scene.text.TextAlignment.RIGHT);
		userId.setFont(new Font(18.0));

		TextField addFlight = new TextField();
		addFlight.setLayoutX(1200);
		addFlight.setLayoutY(300);

		try {
			Connection myConn;
			myConn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/sys", "root", "password");

			String sqlUserCheck = "SELECT * FROM customer WHERE username = '" + customer.getUser() + "'";
			// create a statement
			Statement myStat = myConn.createStatement();
			// execute a query
			ResultSet myRs;
			myRs = myStat.executeQuery(sqlUserCheck);

			// Creates a variable for future checking
			int count = 0;
			while (myRs.next()) {
				count = count + 1;
				setUsernameId(myRs.getString("Customer_ID"));
			}

		} catch (Exception exc) {

		}

		Label addFlightLbl = new Label("Select Flight Number to Add");
		try {
			Connection myConn;
			myConn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/sys", "root", "password");

			String sqlUserCheck = "SELECT * FROM customer WHERE username = '" + customer.getUser() + "'";
			// create a statement
			Statement myStat = myConn.createStatement();
			// execute a query
			ResultSet myRs;
			myRs = myStat.executeQuery(sqlUserCheck);

			// Creates a variable for future checking
			int count = 0;
			while (myRs.next()) {
				count = count + 1;
				setUsernameId(myRs.getString("Customer_ID"));
			}

		} catch (Exception exc) {

		}

		addFlightLbl.setLayoutX(1200);
		addFlightLbl.setLayoutY(275);

		Button addFlightBtn = new Button("Add Flight");
		addFlightBtn.setLayoutX(1240);
		addFlightBtn.setLayoutY(335);
		addFlightBtn.setOnAction(e -> {
			try {

				Connection myConn;
				myConn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/sys", "root", "password");
				
				String sqlFlightBook = "INSERT INTO reservation VALUES ('" +getNumberOfReservations()+ "', '" +addFlight.getText().trim()+ "', '"+java.time.LocalDate.now()+"', '"+getUsernameId()+"', NULL)";
				String sqlFlightUpdate = "UPDATE flights SET Reservation_ID = '" +getNumberOfReservations()+ "' WHERE flights.Flight_ID = '" +addFlight.getText().trim()+ "'";
				String sqlSeatsUpdate = "UPDATE flights SET Seats_Available = Seats_Available - 1 WHERE flights.Flight_ID = '" +addFlight.getText().trim()+ "'";
				String sqlSeatsCheck = "SELECT Seats_Available FROM flights WHERE flights.Flight_ID = '" +addFlight.getText().trim()+ "'";
				
				String sqlBookingCheck = "SELECT flights.Flight_ID, Airline, Arrival_City_ID, Departure_City_ID, Departure_Date, Departure_Time, Arrival_Date, Arrival_Time FROM flights, reservation WHERE flights.Reservation_ID = reservation.Reservation_ID AND reservation.Customer_ID = '" + customer.getCustomer_ID() + "'";

				String bookingCheckValue = "SELECT Departure_Date, Departure_Time FROM flights WHERE flights.Flight_ID ='"
						+ addFlight.getText().trim() + "'";
				
				// create a statement
				Statement myStat = myConn.createStatement();
				Statement mySeats = myConn.createStatement();
				// execute a query
				ResultSet myRs;
				ResultSet myR = mySeats.executeQuery(sqlSeatsCheck);
				myStat.executeUpdate(sqlFlightBook);
				myStat.executeUpdate(sqlFlightUpdate);

				while (myR.next()) {
					array.add(myR.getInt("Seats_Available"));
				}
				if (array.get(0) == 0) {
					setCountHolder(1);
				}
				myR.close();
				mySeats.close();
				myStat.executeUpdate(sqlSeatsUpdate);

				// Creates a variable for future checking
				int count = 0;


				myRs = myStat.executeQuery(bookingCheckValue);
				while (myRs.next()) {
					setNewDepDate(myRs.getDate("departure_date"));
					setNewDepTime(myRs.getTime("departure_time"));
					setNewFlightDep(java.sql.Timestamp
							.valueOf(getNewDepDate().toString().concat(" " + getNewDepTime().toString())));
					
				}

				if (count == 0) {

					myRs = myStat.executeQuery(sqlBookingCheck);
					while (myRs.next()) {

						setDepDate(myRs.getDate("departure_date"));
						setArrDate(myRs.getDate("arrival_date"));
						setDepTime(myRs.getTime("departure_time"));
						setArrTime(myRs.getTime("arrival_time"));
						java.sql.Timestamp departure = java.sql.Timestamp
								.valueOf(getDepDate().toString().concat(" " + getDepTime().toString()));
						java.sql.Timestamp arrival = java.sql.Timestamp
								.valueOf(getArrDate().toString().concat(" " + getArrTime().toString()));

						setConflictCount(conflictCheck(departure, arrival, getNewFlightDep()));
						if (getConflictCount() == 1) {
							setCountHolder(1);
						}

						System.out.println(getConflictCount());

					}
					if (getCountHolder() == 0) {

						myStat.executeUpdate(sqlFlightBook);
					}

					else {
						
					}

				}

				else {
					
				}
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
		
		TableColumn<Flight, Integer> column9 = new TableColumn<>("Seats Available");
		column9.setCellValueFactory(new PropertyValueFactory<>("Seats_Available"));
		column9.setMinWidth(128.88);

		table.setTableMenuButtonVisible(false);

		Button searchButton = new Button("Search");
		searchButton.setLayoutX(500);
		searchButton.setLayoutY(60.0);
		searchButton.setMinWidth(60);
		searchButton.setOnAction(e -> {
			try {

				Connection myConn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/sys", "root", "password");
				String sqlUserCheck = "SELECT * FROM flights ";
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
							myRs.getString("Arrival_Time"),myRs.getInt("Seats_Available")));
				}
				myStat.close();
				myRs.close();
				myConn.close();

			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		});

		table.setLayoutY(100);
		table.setLayoutX(20);
		table.setMinWidth(1160);
		table.setMinHeight(580);
		table.getColumns().addAll(column1, column2, column3, column4, column5, column6, column7, column8, column9);
		anchor.getChildren().addAll(userId, searchButton, table, returnHome, logOut, addFlight,
				addFlightLbl, addFlightBtn);
		Scene scene = new Scene(anchor, 1300, 700);

		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setResizable(false);
		primaryStage.setMaximized(true);
	}

	public Timestamp getNewFlightDep() {
		return newFlightDep;
	}

	public void setNewFlightDep(Timestamp newFlightDep) {
		this.newFlightDep = newFlightDep;
	}

	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	public int conflictCheck(Timestamp d, Timestamp a, Timestamp d1) {

		if (d1.after(d) && d1.before(a)) {
			return 1;
		} else {
			return 0;
		}

	}

}
