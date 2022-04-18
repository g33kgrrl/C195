package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

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
     * @param submitEvent
     */
    public void onSubmitButtonAction(ActionEvent submitEvent) throws IOException {
        if (userName.getText().equals("Charlie") && password.getText().equals("foobar")) {
            System.out.println("Authenticated!");
            displayMain(submitEvent);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login failed");
            alert.setContentText("Please check your username and password and try again.");
            alert.showAndWait();
        }
    }

    public void displayMain(ActionEvent mainEvent) throws IOException {
        user = addDummyUser();
        // index = Inventory.getAllParts().indexOf(part);
        System.out.println(user.getUserId());
        System.out.println(user.getUserName());
        // TODO: Display main screen
        MainController.toMain(mainEvent);
    }

    public void onAddCustomerButtonAction(ActionEvent addCustomerEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        Stage stage = (Stage)((Node)addCustomerEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }


    public static User addDummyUser() {
        User user1 = new User(1, "Foo", "bar", LocalDateTime.now(), "Lisa", LocalDateTime.now(), "James");

        return user1;
    }
}
