package GUI;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;

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

public class Update extends Application implements EventHandler<ActionEvent> {

	public static void main(String[] args) {
		launch(args);

	}

	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

		Button deleteFlights = new Button();
		AnchorPane anchor = new AnchorPane();
		anchor.setPadding(new Insets(20, 20, 20, 20));
		TableView<Flight> table = new TableView<>();
		final ObservableList<Flight> data = FXCollections.observableArrayList();

		ChoiceBox<String> dropdown = new ChoiceBox<>();
		dropdown.getItems().addAll("Flight Number", "Airline", "Destnation", "Origin", "Departure Date",
				"Departure Time", "Arrival Date", "Arrival Time");
		dropdown.setValue("Flight Number");
		dropdown.setLayoutY(60);
		dropdown.setLayoutX(535);

		ChoiceBox<String> dropdown2 = new ChoiceBox<>();
		dropdown2.getItems().addAll("Delete", "Update");
		dropdown2.setValue("Update");
		dropdown2.setLayoutY(60);
		dropdown2.setLayoutX(340);

		TextField flightNumberField = new TextField();
		flightNumberField.setLayoutX(430);
		flightNumberField.setLayoutY(60);
		flightNumberField.setPromptText("Flight Number");
		flightNumberField.setPrefWidth(100);

		TextField info = new TextField();
		info.setLayoutY(60);
		info.setLayoutX(655);
		info.setPromptText("Info");
		info.setPrefWidth(100);

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

		Button submit = new Button("Submit");
		submit.setLayoutX(765);
		submit.setLayoutY(60);
		submit.setOnAction(e -> {
			if (getOption(dropdown2).trim().equals("UPDATE")) {

				try {

					Connection myConn;
					myConn = DriverManager.getConnection(
							"jdbc:mysql://35.193.248.221:3306/?verifyServerCertificate=false&useSSL=true", "root",
							"Tdgiheay12");

					String update = getOption(dropdown2).trim() + " `flights`.`flight` SET `"
							+ getChoice(dropdown).trim() + "` = '" + info.getText().trim() + "' WHERE `number` = '"
							+ flightNumberField.getText().trim() + "' ";
					Statement myStat = myConn.createStatement();
					myStat.executeUpdate(update);
				} catch (SQLException e1) {
					System.out.println(e1.getMessage());
				}

			}

			if (getOption(dropdown2).trim().equals("DELETE")) {
				try {

					Connection myConn;
					myConn = DriverManager.getConnection(
							"jdbc:mysql://35.193.248.221:3306/?verifyServerCertificate=false&useSSL=true", "root",
							"Tdgiheay12");

					String delete = "DELETE FROM `flights`.`flight` WHERE `number`='"
							+ flightNumberField.getText().trim() + "'";
					Statement myStat = myConn.createStatement();
					myStat.executeUpdate(delete);
				}

				catch (SQLException e1) {
					System.out.println(e1.getMessage());
				}

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
		column7.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
		column7.setMinWidth(128.88);

		TableColumn<Flight, Time> column8 = new TableColumn<Flight, Time>("Arrival Time");
		column8.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
		column8.setMinWidth(128.88);

		try {

			String dbSearch = getChoice(dropdown).trim();
			Connection myConn = DriverManager.getConnection(
					"jdbc:mysql://35.193.248.221:3306/?verifyServerCertificate=false&useSSL=true", "root",
					"Tdgiheay12");
			String sqlUserCheck = "SELECT * FROM flights.flight";
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

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());

		}

		table.setTableMenuButtonVisible(false);

		table.setLayoutY(100);
		table.setLayoutX(20);
		table.setMinWidth(1160 - 128.88);
		table.setMinHeight(580);
		table.getColumns().addAll(column1, column2, column3, column4, column5, column6, column7, column8);
		anchor.getChildren().addAll(dropdown, table, dropdown2, flightNumberField, info, submit, returnHome);
		Scene scene = new Scene(anchor, 1200, 700);

		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setResizable(false);
		primaryStage.setMaximized(false);
		primaryStage.centerOnScreen();

	}

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub

	}

	public String getChoice(ChoiceBox<String> dropdown) {

		String dbSearch = "";
		String choice = dropdown.getValue();

		if (choice.equals("Destnation")) {
			dbSearch = "destination_city";
		} else if (choice.equals("Flight Number")) {
			dbSearch = "number";
		}

		else if (choice.equals("Origin")) {
			dbSearch = "origin_city";
		} else if (choice.equals("Departure Date")) {
			dbSearch = "departure_date";
		} else if (choice.equals("Departure Time")) {
			dbSearch = "departure_time";
		} else if (choice.equals("Arrival Date")) {
			dbSearch = "arrival_date";
		} else if (choice.equals("Airline")) {
			dbSearch = "airline";
		}
		return dbSearch;

	}

	public String getOption(ChoiceBox<String> dropdown2) {

		String dbquery = "";
		String choice = dropdown2.getValue();

		if (choice.equals("Delete")) {
			dbquery = "DELETE";
		}

		else if (choice.equals("Update")) {
			dbquery = "UPDATE";
		}

		return dbquery;

	}
	
	public int schedulingCheck(Timestamp d, Timestamp a) {

		if (d.compareTo(a) >= 0) {
			return 1;
		} else {
			return 0;
		}

	}

}
