package main;

import dao.CountryQuery;
import dao.CustomerQuery;
import dao.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Locale;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        primaryStage.setTitle("Log In");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException {
        // TODO: Set/test Locale.setDefault(new Locale("fr"));
        JDBC.makeConnection();
        launch(args);
//        CustomerQuery.select(60);
//        CountryQuery.select("Canada");
        JDBC.closeConnection();
    }
}
