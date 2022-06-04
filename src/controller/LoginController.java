package controller;

import dao.UserQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.Locale;
import java.util.ResourceBundle;

import model.Appointment;
import model.User;


public class LoginController implements Initializable {
    @FXML
    private Label messageLabel;
    @FXML
    private Label userNameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private TextField userNameText;
    @FXML
    private TextField passwordText;
    @FXML
    private Label zoneIdLabel;
    @FXML
    private Button submitButton;

    private static ResourceBundle rb = ResourceBundle.getBundle("languages/Lang", Locale.getDefault());


    /***
     * Initializes login screen.
     * Checks the user's system zone setting and displays it in a label on the login form.
     * Checks the user's system language setting for English or French, and automatically uses the appropriate language
     * to display the text, labels, buttons, and errors on the form.
     * @param url the url
     * @param resourceBundle matching resourceBundle containing language phrases
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messageLabel.setText(rb.getString("message"));
        userNameLabel.setText(rb.getString("usernameLabel"));
        passwordLabel.setText(rb.getString("passwordLabel"));
        userNameText.setPromptText(rb.getString("usernamePromptText"));
        passwordText.setPromptText(rb.getString("passwordPromptText"));
        zoneIdLabel.setText(ZoneId.systemDefault().toString());
        submitButton.setText(rb.getString("submit"));
    }

    /***
     * Fetches the appropriate resourceBundle according to user's system settings, to get the proper phrases to display
     * for each language.
     * @return resourceBundle according to system language
     */
    public static ResourceBundle getResourceBundle() {
        return rb;
    }

    /***
     * Handles login submit request.
     * Gets username and password from textfields and compares against users in the database. If there is no match,
     * display an error asking the user to try again. Otherwise, run a check and display an alert to notify the user
     * whether there is or is not an upcoming appointment, and navigate to the main screen. Log details, including
     * given username and timestamp, for failed and successful login attempts.
     * @param actionEvent the Submit button click event
     * @throws IOException for input/output exceptions
     */
    public void onSubmitButtonAction(ActionEvent actionEvent) throws IOException {
        String logLine = LocalDateTime.now() + " - ";
        String userName = userNameText.getText();
        boolean authorized = UserQuery.validateUser(userName, passwordText.getText());

        if (!authorized) {
            logLine += "FAILED login attempt by username '" + userName + "'";
            User.writeToActivityLog(logLine);

            MainController.showAlert("error", "Login failed",
                    "Please check your username and password and try again.");
        } else {
            logLine += "Successful login by user '" + userName + "'";
            User.writeToActivityLog(logLine);
            
            MainController.showAlert("information", "Upcoming appointments", Appointment.getUpcoming());

            MainController.toMain(actionEvent);
        }
    }
}
