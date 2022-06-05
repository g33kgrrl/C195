package main;

import controller.LoginController;
import dao.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;

/***
 * <h2>Acme Appointment Manager</h2>
 * <p>Acme Appointment Manager is used to track vital customer and appointment data.<br/>
 * Please see README.txt for more details.</p>
 *
 * @author <p>Lisa Georgiades<br>#0733734<br>C195 Final Project</p>
 *
 * <h2>Important Locations</h2>
 * <ol>
 *   <li>The javadoc folder is located in the C195 project folder.</li>
 *   <li>Lambda expression #1 is located in Appointment.java, in the getUpcoming() method.</li>
 *   <li>Lambda expression #2 is located in Appointment.java, in the checkOverlap method.</li>
 * </ol>
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Login.fxml")));
        primaryStage.setTitle(LoginController.getResourceBundle().getString("title"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /***
     * The main method, where the program begins executing.
     * @param args the args to pass to launch
     */
    public static void main(String[] args) {
        JDBC.makeConnection();

        launch(args);

        JDBC.closeConnection();
    }
}
