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

//        System.out.println(LocalDateTime.parse("2017-10-06T17:48:23.558"));

//        CustomerQuery.insert("Trinity Matrix", "1010 Battery Drive", "54321",
//                "888-333-4444", 10);
//
//        AppointmentQuery.insert("Doc Hollywood", "Doctor's appt", "Anytown", "Medical",
//                LocalDateTime.parse("2022-05-10T11:15:23.558"), LocalDateTime.parse("2022-05-10T12:30:41.369"),
//                LocalDateTime.parse("2022-04-06T17:48:23.558"), "Michael",
//                LocalDateTime.parse("2017-05-01T10:20:23.558"), "Test", 20, 1, 2
//        );
//
//        AppointmentQuery.insert("Captain Marvel", "Costume fitting", "New York", "Hero stuff",
//                LocalDateTime.parse("2022-05-10T11:15:23.558"), LocalDateTime.parse("2022-05-10T12:30:41.369"),
//                LocalDateTime.parse("2022-04-06T17:48:23.558"), "Stan Lee",
//                LocalDateTime.parse("2017-05-01T10:20:23.558"), "Admin", 20, 2, 3
//        );

//        AppointmentQuery.deleteAllForCustomerId(17);

        launch(args);

        JDBC.closeConnection();
    }
}
