package main;

import controller.LoginController;
import dao.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
     * @param args the args
     */
    public static void main(String[] args) {
        JDBC.makeConnection();

        launch(args);

        JDBC.closeConnection();
    }
}
