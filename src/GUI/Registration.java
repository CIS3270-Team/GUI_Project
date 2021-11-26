package GUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

public class Registration {

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Add Flight");
		AnchorPane anchor = new AnchorPane();
		anchor.setPadding(new Insets(20, 20, 20, 20));

		Text airline = new Text("Airline");
		airline.setLayoutX(200);
		airline.setLayoutY(45);

		Text flightNumber = new Text("Flight Number");
		flightNumber.setLayoutX(200);
		flightNumber.setLayoutY(80);

		Text originCity = new Text("Origin City");
		originCity.setLayoutX(200);
		originCity.setLayoutY(115);

		Text destinationCity = new Text("Destination City");
		destinationCity.setLayoutX(200);
		destinationCity.setLayoutY(150);

		Text departureDate = new Text("Departure Date");
		departureDate.setLayoutX(200);
		departureDate.setLayoutY(185);

		Text departureTime = new Text("Departure Time");
		departureTime.setLayoutX(200);
		departureTime.setLayoutY(220);

		Text arrivalDate = new Text("Arrival Date");
		arrivalDate.setLayoutX(200);
		arrivalDate.setLayoutY(255);

		Text arrivalTime = new Text("Arrival Time");
		arrivalTime.setLayoutX(200);
		arrivalTime.setLayoutY(290);

		Text capacity = new Text("Capacity");
		capacity.setLayoutX(200);
		capacity.setLayoutY(330);

		TextField airlineTxtField = new TextField();
		airlineTxtField.setLayoutX(300);
		airlineTxtField.setLayoutY(25);
		airlineTxtField.setPromptText("Airline");

		TextField flightNumberTxtField = new TextField();
		flightNumberTxtField.setLayoutX(300);
		flightNumberTxtField.setLayoutY(60);
		flightNumberTxtField.setPromptText("Flight Number");

		TextField originCityTxtField = new TextField();
		originCityTxtField.setLayoutX(300);
		originCityTxtField.setLayoutY(95);
		originCityTxtField.setPromptText("Origin City");

		TextField destinationCityTxtField = new TextField();
		destinationCityTxtField.setLayoutX(300);
		destinationCityTxtField.setLayoutY(130);
		destinationCityTxtField.setPromptText("Destination City");

		TextField departureDateTxtField = new TextField();
		departureDateTxtField.setLayoutX(300);
		departureDateTxtField.setLayoutY(165);
		departureDateTxtField.setPromptText("YYYY-MM-DD");

		TextField departureTimeTxtField = new TextField();
		departureTimeTxtField.setLayoutX(300);
		departureTimeTxtField.setLayoutY(200);
		departureTimeTxtField.setPromptText("HH:MM:SS");

		TextField arrivalDateTxtField = new TextField();
		arrivalDateTxtField.setLayoutX(300);
		arrivalDateTxtField.setLayoutY(235);
		arrivalDateTxtField.setPromptText("YYYY-MM-DD");

		TextField arrivalTimeTxtField = new TextField();
		arrivalTimeTxtField.setLayoutX(300);
		arrivalTimeTxtField.setLayoutY(270);
		arrivalTimeTxtField.setPromptText("HH:MM:SS");

		TextField capacityTxtField = new TextField();
		capacityTxtField.setLayoutX(300);
		capacityTxtField.setLayoutY(310);
		capacityTxtField.setPromptText("Capacity");

		Button returnHome = new Button("Return to main Page");
		returnHome.setLayoutX(250);
		returnHome.setLayoutY(450);
		returnHome.setOnAction(e -> {
			mainPage home = new mainPage();
			try {
				home.start(primaryStage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		Button create = new Button("Create Flight");
		create.setLayoutX(250);
		create.setLayoutY(400);
		create.setOnAction(e -> {
			java.sql.Timestamp departure = java.sql.Timestamp
					.valueOf(departureDateTxtField.getText().concat(" " + departureTimeTxtField.getText()));
			java.sql.Timestamp arrival = java.sql.Timestamp
					.valueOf(arrivalDateTxtField.getText().concat(" " + arrivalTimeTxtField.getText()));

			if (schedulingCheck(departure, arrival) == 0) {

				try {

					Connection myConn;
					myConn = DriverManager.getConnection(
							"jdbc:mysql://35.193.248.221:3306/?verifyServerCertificate=false&useSSL=true", "root",
							"Tdgiheay12");
					String sqlFightCheck = "select * From `flights`.`flight` where number = '"
							+ flightNumberTxtField.getText() + "'";

					String sqlFlightCreate = "INSERT INTO `flights`.`flight`(`id`,`airline`,`number`,`origin_city`,`destination_city`,`departure_date`,"
							+ "`departure_time`,`arrival_date`,`arrival_time`,`capacity`,`seats_available`) VALUES('"
							+ flightNumberTxtField.getText() + "', '"+ airlineTxtField.getText() + "', '" + flightNumberTxtField.getText() + "', '"
							+ originCityTxtField.getText() + "', '" + destinationCityTxtField.getText() + "' , '"
							+ departureDateTxtField.getText() + "', '" + departureTimeTxtField.getText() + "', '"
							+ arrivalDateTxtField.getText() + "', '" + arrivalTimeTxtField.getText() + "', '"
							+ capacityTxtField.getText() + "', ' " + capacityTxtField.getText() + "')";

					Statement myStat = myConn.createStatement();
					// execute a query
					;
					ResultSet myRs;
					myRs = myStat.executeQuery(sqlFightCheck);
					int count = 0;
					while (myRs.next()) {
						count += 1;
					}
					if (count == 0) {
						myStat.executeUpdate(sqlFlightCreate);
						AlertBox.display("Success", "FLight Successfully Added!");

					} else {
						AlertBox.display("Error",
								"Flight number " + flightNumberTxtField.getText() + " already exists.");
					}

				} catch (SQLException exc) {
					System.out.println(exc.getMessage());
				}
			}

			else {
				AlertBox.display("Error", "Error: departure cannot be before a arrival");
			}
		});

		anchor.getChildren().addAll(airline, flightNumber, originCity, destinationCity, departureDate, departureTime,
				arrivalDate, arrivalTime, capacity, airlineTxtField, flightNumberTxtField, originCityTxtField,
				destinationCityTxtField, departureDateTxtField, departureTimeTxtField, arrivalDateTxtField,
				arrivalTimeTxtField, capacityTxtField, create, returnHome);

		Scene scene = new Scene(anchor, 550, 550);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setResizable(false);
		primaryStage.setMaximized(false);
		primaryStage.centerOnScreen();
	}

	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	public int schedulingCheck(Timestamp d, Timestamp a) {

		if (d.compareTo(a) >= 0) {
			return 1;
		} else {
			return 0;
		}

	}

}

}
