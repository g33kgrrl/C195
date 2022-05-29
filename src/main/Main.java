package main;

import controller.LoginController;
import dao.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Login.fxml")));
        primaryStage.setTitle(LoginController.getResourceBundle().getString("title"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /***
     *
     * TODO: Figure out where to put this
     * Submission notes:
     * Database Driver version #: 10.00.19041.01
     *
     * @param args
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException, FileNotFoundException {
        JDBC.makeConnection();

//        Locale.setDefault(new Locale("fr"));

//        AppointmentQuery.insert("Fifteen", "15m Test", "HQ", "Test",
//                LocalDateTime.now().plusMinutes(10), LocalDateTime.now().plusMinutes(25), LocalDateTime.parse("2022-04-06T17:48:23.558"), "Michael", LocalDateTime.parse("2017-05-01T10:20:23.558"),
//                "Lisa", 1, 1, 2);
//
//        AppointmentQuery.insert("Fifteen 2", "15m Test 2", "HQ", "Test",
//                LocalDateTime.now().plusMinutes(15), LocalDateTime.now().plusMinutes(30), LocalDateTime.parse("2022-04-06T17:48:23.558"), "Michael", LocalDateTime.parse("2017-05-01T10:20:23.558"),
//                "Lisa", 1, 1, 2);

        launch(args);

        JDBC.closeConnection();
    }
}
