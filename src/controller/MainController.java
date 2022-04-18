package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public TextField userName;
    public TextField password;
    public Label zoneIdLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Hello students");
        zoneIdLabel.setText("U.S.");
    }

    public void onSubmitButtonAction(ActionEvent actionEvent) {
        System.out.println("Submit button clicked!");
    }
}
