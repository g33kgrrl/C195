package controller;

import dao.CountryQuery;
import dao.CustomerQuery;
import dao.DivisionQuery;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import model.Country;
import model.Customer;
import model.Division;


public class AddModCustomerController implements Initializable {
    public RadioButton inHouseRadio;
    public RadioButton outsourcedRadio;
    public ToggleGroup sourceGroup;
    public TextField idText;
    public TextField nameText;
    public TextField addressText;
    public TextField postalCodeText;
    public TextField phoneText;
    public ComboBox<Country> countryCombo;
    public ComboBox<Division> divisionCombo;
    public Label sourceLabel;

    private Customer customer;

//    private ObservableList<Appointments> associatedAppts = FXCollections.observableArrayList();


//    private int nextPartId = Inventory.getNextPartId();

    /***
     *   When adding and updating a customer, text fields are used to collect the following data: customer name, address, postal code, and phone number.
     *
     * -  Customer IDs are auto-generated, and first-level division (i.e., states, provinces) and country data are collected using separate combo boxes.
     */

    /**
     * Initializes part ID textfield.
     * The Customer ID textfield is populated with a unique customer ID that cannot be edited by the user. This prevents
     * creating a duplicate Customer ID when adding a new customer.
     * @param url the url
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<Country> countries = Country.getAllCountries();

        countryCombo.setItems(countries);
        countryCombo.setPromptText("Select country");
//        System.out.println(countryCombo.getValue().toString());

        divisionCombo.setPromptText("First select country");
        divisionCombo.setVisibleRowCount(5);

//        countryCombo.setSelectionModel(SingleSelectionModel<Country> singleSelectionModel);
//        divisionCombo.setItems(DivisionQuery.selectAllForCountry(1));

//        idText.setText(String.valueOf(nextPartId));
//        System.out.println("Customer: " + customer);
    }

    /**
     * Initializes modify customer dialog with current data for the selected customer.
     * Gets current data for the selected customer, and populates the modify customer textfields with
     * that data. Customer ID textfield is disabled to prevent creation of duplicate customer IDs.
     * @param customer1 the selected customer to modify
     */
    public void displayCustomer (Customer customer1) {
        this.customer = customer1;
        int divisionId = customer.getDivisionId();
        int countryId = DivisionQuery.getCountryId(divisionId);

        idText.setText(String.valueOf(customer.getId()));
        nameText.setText(customer.getName());
        addressText.setText(String.valueOf(customer.getAddress()));
        postalCodeText.setText(String.valueOf(customer.getPostalCode()));
        phoneText.setText(String.valueOf(customer.getPhone()));
        countryCombo.setItems(CountryQuery.selectAll());
        countryCombo.setValue(CountryQuery.getCountry(countryId));
        divisionCombo.setItems(DivisionQuery.selectAllForCountry(countryId));
        divisionCombo.setValue(DivisionQuery.getDivision(divisionId));
    }

    public void onCountryCombo(ActionEvent countryComboEvent) {

        try {
            int countryIdFK = countryCombo.getSelectionModel().getSelectedItem().getId();

//            System.out.println("Division (before): " + divisionCombo.getSelectionModel().getSelectedItem().getName());

            divisionCombo.setItems(DivisionQuery.selectAllForCountry(countryIdFK));
//            divisionCombo.getSelectionModel().clearSelection();
            divisionCombo.setValue(null);
            divisionCombo.setPromptText("Select division");
//            System.out.println("Division (after): " + divisionCombo.getSelectionModel().getSelectedItem().getName());
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
        }

        divisionCombo.setPromptText("Select division");
    }

    public void showValidateError(String formName) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(formName + " add/modify form");
        alert.setContentText("Please complete all fields.");
        alert.showAndWait();
    }

    public void onSaveButtonAction(ActionEvent saveEvent) throws IOException {
        int id, divId, rowsAffected;
        String name, address, postalCode, phone;

        try {
            name = nameText.getText();
            address = addressText.getText();
            postalCode = postalCodeText.getText();
            phone = phoneText.getText();
            divId = divisionCombo.getSelectionModel().getSelectedItem().getId();

            if(name.isEmpty() || address.isEmpty() || postalCode.isEmpty() || phone.isEmpty() || Integer.valueOf(divId) == null) {
                showValidateError("Customer");
            }
            else {

                if (customer == null) {
                    rowsAffected = CustomerQuery.insert(name, address, postalCode, phone, divId);
                } else {
                    id = this.customer.getId();

                    rowsAffected = CustomerQuery.update(id, name, address, postalCode, phone, divId);
                }

                if (rowsAffected > 0) {
                    System.out.println("Customer added.");
                } else {
                    System.out.println("Failed to add customer!");
                }

                MainController.toMain(saveEvent);
            }
        }
        catch (NullPointerException e) {
            showValidateError("Customer");
        }



//        if(Integer.valueOf(id) == null || name == null || address == null || postalCode == null || phone == null || Integer.valueOf(divId) == null) {
//
//        }

//        int rowsAffected;

//        try {


//        }
//        catch (NullPointerException e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Customer add/modify form");
//            alert.setContentText("Please complete all fields.");
//            alert.showAndWait();
//        }
    }

    /**
     * Handles cancel request.
     * Returns user to main screen without saving any entered textfield values.
     * @param cancelEvent the Cancel button click event
     * @throws IOException for input/output exceptions
     */
    public void onCancelButtonAction(ActionEvent cancelEvent) throws IOException {
        MainController.toMain(cancelEvent);
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


