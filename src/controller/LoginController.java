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
        messageLabel.setText(rb.getString("message"));
        userNameLabel.setText(rb.getString("usernameLabel"));
        passwordLabel.setText(rb.getString("passwordLabel"));
        userNameText.setPromptText(rb.getString("usernamePromptText"));
        passwordText.setPromptText(rb.getString("passwordPromptText"));
        zoneIdLabel.setText(ZoneId.systemDefault().toString());
        submitButton.setText(rb.getString("submit"));
    }

    public static ResourceBundle getResourceBundle() {
        return rb;
    }

    /***
     * Handles login submit request.
     * Check that user has entered a valid username/password pair, and if so, launches
     * the main screen. If not, displays an error message prompting user to try again.
     *
     * @param actionEvent
     */
    public void onSubmitButtonAction(ActionEvent actionEvent) throws IOException {
        String logLine = LocalDateTime.now() + " - ";
        String userName = userNameText.getText();
        boolean authorized = UserQuery.checkIfAuthorized(userName, passwordText.getText());

        if (!authorized) {
            logLine += "FAILED login attempt by username '" + userName + "'";
            User.trackLoginActivity(logLine);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login failed");
            alert.setContentText("Please check your username and password and try again.");
            alert.showAndWait();
        } else {
            logLine += "Successful login by user '" + userName + "'";
            User.trackLoginActivity(logLine);

            MainController.toMain(actionEvent);
        }
    }
}