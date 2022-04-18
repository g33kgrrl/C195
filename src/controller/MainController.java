package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public TextField userName;
    public TextField password;
    public Label zoneIdLabel;

    private int zoneId = 1; // TODO: Query location?
    private User user;

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
        if (zoneId == 1) {
            zoneIdLabel.setText("Anytown USA");
        }
    }

    /***
     * Handles login submit request.
     * Check that user has entered a valid username/password pair, and if so, launches
     * the main screen. If not, displays an error message prompting user to try again.
     *
     * @param actionEvent
     */
    public void onSubmitButtonAction(ActionEvent actionEvent) {
        if (userName.getText().equals("foo") && password.getText().equals("bar")) {
            System.out.println("Authenticated!");
            displayUser();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login failed");
            alert.setContentText("Please check your username and password and try again.");
            alert.showAndWait();
        }
    }

    public void displayUser() {
        user = addDummyData();
        // index = Inventory.getAllParts().indexOf(part);
        System.out.println(user.getUserId());
        System.out.println(user.getUserName());
    }


    public static User addDummyData() {
        User user1 = new User(1, "Ann", "foobar", LocalDateTime.now(), "Lisa", LocalDateTime.now(), "James");

        return user1;
    }
}
