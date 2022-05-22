package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.ResourceBundle;

import dao.UserQuery;
import model.User;

public class LoginController implements Initializable {

    public TextField userName;
    public TextField password;
    public Label zoneIdLabel;
//    private static User currentUser = UserQuery.getUserByName();

    /***
     * Initializes login screen.
     * Determines the user’s location and displays it in a label on the login form.
     * Displays the login form in English or French based on the user’s computer
     * language setting to translate all the text, labels, buttons, and errors on the
     * form. Automatically translates error control messages into English or French
     * based on the user’s computer language setting.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        zoneIdLabel.setText(ZoneId.systemDefault().toString());
//            zoneIdLabel.setText(String.valueOf(ZoneOffset.systemDefault().getRules().getOffset(Instant.now())));
//            zoneIdLabel.setText("FOO!!!!!");
//        ZoneOffset o = OffsetDateTime.now().getOffset();
//        System.out.println("Zone Offset: " + o);
//        LocalDateTime ldtMidnight = LocalDateTime.of(2022, 05, 19, 0, 0);
//        zoneIdLabel.setText(String.valueOf(ldtMidnight.atOffset(o)));
//
//
//        ZonedDateTime zdt = LocalDateTime.parse( "2018-01-23T01:23:45.123456789" )  // Parse string, lacking an offset-from-UTC and lacking a time zone, as a `LocalDateTime`.
//                .atZone(ZoneId.systemDefault());              // Assign the time zone for which you are certain this date-time was intended. Instantiates a `ZonedDateTime` object.
//
//        System.out.println(zdt);

    }

    /***
     * Handles login submit request.
     * Check that user has entered a valid username/password pair, and if so, launches
     * the main screen. If not, displays an error message prompting user to try again.
     *
     * @param submitEvent
     */
    public void onSubmitButtonAction(ActionEvent submitEvent) throws IOException {
        boolean authorized = UserQuery.checkIfAuthorized(userName.getText(), password.getText());

        if (authorized == false) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login failed");
            alert.setContentText("Please check your username and password and try again.");
            alert.showAndWait();
        } else {
//            System.out.println("Authenticated!");
            MainController.toMain(submitEvent);
        }
    }
}
