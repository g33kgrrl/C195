package main;

import dao.CountryQuery;
import dao.CustomerQuery;
import dao.JDBC;
import dao.UserQuery;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;

import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        TODO: Re-enable login screen after other stuff
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Login.fxml")));
        primaryStage.setTitle("Log In");

//        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Main.fxml")));

        primaryStage.setTitle("TEMP Schedule Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException {
        // TODO: Set/test Locale.setDefault(new Locale("fr"));
        JDBC.makeConnection();

        User user1 = UserQuery.select("jayne");

        launch(args);

        JDBC.closeConnection();
    }
}
