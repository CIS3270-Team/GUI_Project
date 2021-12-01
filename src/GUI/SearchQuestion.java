package GUI;

import java.sql.*;

import Objects.Flights.Flight;
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
	private int emptySeats;
	private int takenSeats;
	private int full;

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

	public int getEmptySeats() {
		return emptySeats;
	}

	public void setEmptySeats(int emptySeats) {
		this.emptySeats = emptySeats;
	}

	public int getTakenSeats() {
		return takenSeats;
	}

	public void setTakenSeats(int takenSeats) {
		this.takenSeats = takenSeats;
	}

	public int getFull() {
		return full;
	}

	public void setFull(int full) {
		this.full = full;
	}

	public void setCountHolder(int countHolder) {
		this.countHolder = countHolder;
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("Search");
		AnchorPane anchor = new AnchorPane();
		anchor.setPadding(new Insets(20, 20, 20, 20));
		TableView<Flight> table = new TableView<>();
		final ObservableList<Flight> data = FXCollections.observableArrayList();

		ChoiceBox<String> dropdown = new ChoiceBox<>();
		dropdown.getItems().addAll("Destnation", "Origin", "Departure", "Airline");
		dropdown.setValue("Destnation");
		dropdown.setLayoutY(60);
		dropdown.setLayoutX(340);

		Button returnHome = new Button("Return Home");
		returnHome.setOnAction(e -> {
			mainPage home = new mainPage();
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
		userId.setText("Logged in as: " + Login.getUser());
		userId.setTextAlignment(javafx.scene.text.TextAlignment.RIGHT);
		userId.setFont(new Font(18.0));

		TextField searchTxt = new TextField();
		searchTxt.setLayoutX(460.0);
		searchTxt.setLayoutY(60.0);
		searchTxt.setMinWidth(250);

		TextField addFlight = new TextField();
		addFlight.setLayoutX(1200);
		addFlight.setLayoutY(300);

		try {
			Connection myConn;
			myConn = DriverManager.getConnection(
					"jdbc:mysql://35.193.248.221:3306/?verifyServerCertificate=false&useSSL=true", "root",
					"Tdgiheay12");

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
				System.out.println(getUsernameId());

			}

		} catch (Exception exc) {

		}

		Label addFlightLbl = new Label("Select Flight Number to Add");
		try {
			Connection myConn;
			myConn = DriverManager.getConnection(
					"jdbc:mysql://35.193.248.221:3306/?verifyServerCertificate=false&useSSL=true", "root",
					"Tdgiheay12");

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
				System.out.println(getUsernameId());

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
						"jdbc:mysql://35.193.248.221:3306/?verifyServerCertificate=false&useSSL=true", "root",
						"Tdgiheay12");

				String sqlFlightBook = "INSERT INTO `flights`.`Flight_User`(`Flight_id`,`User_id`)VALUES("
						+ addFlight.getText().trim() + ", " + getUsernameId() + ")";

				String sqlFlightCheck = "SELECT `Flight_id`, `User_id` FROM `flights`.`Flight_User` where User_id = '"
						+ getUsernameId() + "' and Flight_id= '" + addFlight.getText().trim() + "'";

				String sqlBookingCheck = "select  `number`,`departure_time`, `arrival_time`, `departure_date`, `arrival_date` from\r\n"
						+ "flights.flight inner Join flights.Flight_User on Flight_id = flight.id \r\n"
						+ "inner join flights.users on Flight_User.User_id = users.id where username = '"
						+ Login.getUser() + "'";

				String bookingCheckValue = "SELECT `departure_date`, `departure_time` FROM `flights`.`flight` where number ='"
						+ addFlight.getText().trim() + "'";

				// create a statement
				Statement myStat = myConn.createStatement();
				// execute a query
				ResultSet myRs;
				myRs = myStat.executeQuery(sqlFlightCheck);

				// Creates a variable for future checking
				int count = 0;
				while (myRs.next()) {
					count = count + 1;

				}

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
						AlertBox.display("Error", "This flight conflicts with other flights in your account");
					}

				}

				else {
					AlertBox.display("Error!", "Error! you have alredy booked flight number: "
							+ addFlight.getText().trim() + ". \n You cannot Book the same flight tiwce!");
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

		TableColumn<Flight, Date> column7 = new TableColumn<Flight, Date>("Arrival date");
		column7.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
		column7.setMinWidth(128.88);

		TableColumn<Flight, Time> column8 = new TableColumn<Flight, Time>("Arrival Time");
		column8.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
		column8.setMinWidth(128.88);

		TableColumn<Flight, Integer> column9 = new TableColumn<Flight, Integer>("Seats Available");
		column9.setCellValueFactory(new PropertyValueFactory<>("seatsAvailable"));
		column9.setMinWidth(128.88);

		table.setTableMenuButtonVisible(false);

		Button searchButton = new Button("Search");
		searchButton.setLayoutX(715);
		searchButton.setLayoutY(60.0);
		searchButton.setMinWidth(60);
		searchButton.setOnAction(e -> {
			try {

				String dbSearch = getChoice(dropdown).trim();
				String searchItem = searchTxt.getText().trim();
				Connection myConn = DriverManager.getConnection(
						"jdbc:mysql://35.193.248.221:3306/?verifyServerCertificate=false&useSSL=true", "root",
						"Tdgiheay12");
				String sqlUserCheck = "SELECT * FROM flights.flight WHERE " + dbSearch + " = '" + searchItem + "'";
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
							myRs.getTime("departure_time"), myRs.getDate("arrival_date"), myRs.getTime("arrival_time"),
							myRs.getInt("seats_available")));
					table.setItems(data);
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
		anchor.getChildren().addAll(dropdown, userId, searchTxt, searchButton, table, returnHome, logOut, addFlight,
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

	public String getChoice(ChoiceBox<String> dropdown) {

		String dbSearch = "";
		String choice = dropdown.getValue();

		if (choice.equals("Destnation")) {
			dbSearch = "destination_city";
		}

		else if (choice.equals("Origin")) {
			dbSearch = "origin_city";
		} else if (choice.equals("Date")) {
			dbSearch = "departure_date";
		} else if (choice.equals("Airline")) {
			dbSearch = "airline";
		}
		return dbSearch;

	}

	public int conflictCheck(Timestamp d, Timestamp a, Timestamp d1) {

		if (d1.after(d) && d1.before(a)) {
			return 1;
		} else {
			return 0;
		}

	}

}
