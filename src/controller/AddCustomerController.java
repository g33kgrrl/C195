package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
//import model.InHouse;
//import model.Inventory;
//import model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {
    public RadioButton inHouseRadio;
    public RadioButton outsourcedRadio;
    public ToggleGroup sourceGroup;
    public TextField idText;
    public TextField nameText;
    public TextField addressText;
    public TextField postalCodeText;
    public TextField phone;
    public ComboBox divisionCombo;
    public ComboBox countryCombo;
    public Label sourceLabel;

//    private int nextPartId = Inventory.getNextPartId();

    /***
     *   When adding and updating a customer, text fields are used to collect the following data: customer name, address, postal code, and phone number.
     *
     * -  Customer IDs are auto-generated, and first-level division (i.e., states, provinces) and country data are collected using separate combo boxes.
     */

    /**
     * Initializes part ID textfield.
     * The Part ID textfield is assigned a unique part ID that cannot be edited by the user. This prevents
     * creating a duplicate part ID when adding a new part.
     * @param url the url
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        idText.setText(String.valueOf(nextPartId));
    }

//    /**
//     * Handles save part request.
//     * Gets values from textfields, ensures input is valid, and then calls Inventory.addPart() to
//     * create a new outsourced or in-house part according to user's selection. Tracks next unique part
//     * ID to be used, and returns user to main screen. If input is invalid, an error message is
//     * displayed instead.
//     * @param saveEvent the Save button click event
//     * @throws IOException for input/output exceptions
//     */
//    public void onSaveButtonAction(ActionEvent saveEvent) throws IOException {
////        int id = nextPartId;
//
//        try {
//            String name = nameText.getText();
//            float price = Float.parseFloat(postalCodeText.getText());
//            int stock = Integer.parseInt(addressText.getText());
//            int min = Integer.parseInt(divisionCombo.getText());
//            int max = Integer.parseInt(phone.getText());
//
//            if (min < 0 || min >= max || stock < min || stock > max) {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Invalid inventory settings");
//                alert.setContentText("Must set 0 < Min < Inv < Max.");
//                alert.showAndWait();
//            } else {
//                if (inHouseRadio.isSelected()) {
//                    int machineId = Integer.parseInt(countryCombo.getText());
//
//                    Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
//                } else {
//                    String companyName = countryCombo.getText();
//
//                    Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
//                }
//
//                Inventory.setNextPartId(nextPartId + 1);
//
//                MainController.toMain(saveEvent);
//            }
//        } catch (NumberFormatException e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Add part form");
//            alert.setContentText("Please enter valid values in text fields.");
//            alert.showAndWait();
//        }
//    }
//
//    /**
//     * Handles cancel request.
//     * Returns user to main screen without saving any entered textfield values.
//     * @param cancelEvent the Cancel button click event
//     * @throws IOException for input/output exceptions
//     */
//    public void onCancelButtonAction(ActionEvent cancelEvent) throws IOException {
//        MainController.toMain(cancelEvent);
//    }
//
//    /**
//     * Displays appropriate textfield for an in-house part.
//     * If the "In-House" radio button is selected, this is called to display the"Machine ID" field.
//     * @param inHouseEvent the In-House radio button click event
//     */
//    public void onInHouseRadioAction(ActionEvent inHouseEvent) {
//        sourceLabel.setText("Machine ID");
//        countryCombo.promptTextProperty().setValue("Machine ID");
//    }
//
//    /**
//     * Displays appropriate textfield for an outsourced part.
//     * If the "Outsourced" radio button is selected, this is called to display the "Company Name" field.
//     * @param outsourcedEvent the Outsourced radio button click event
//     */
//    public void onOutsourcedRadioAction(ActionEvent outsourcedEvent) {
//        System.out.println("Outsourced!");
//        sourceLabel.setText("Company Name");
//        countryCombo.promptTextProperty().setValue("Company Name");
//    }
}


