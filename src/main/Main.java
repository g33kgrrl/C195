package main;

import dao.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        TODO: Re-enable login screen after other stuff
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Login.fxml")));
        primaryStage.setTitle("Log In");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException {
        // TODO: Set/test Locale.setDefault(new Locale("fr"));
        JDBC.makeConnection();

        AppointmentQuery.insert("Appt1", "My first appt", "Bahamas", "Pleasure cruise",
                LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), "Lisa", LocalDateTime.now(),
                "Michael", 2, 1, 3);

        launch(args);

        JDBC.closeConnection();
    }
}
