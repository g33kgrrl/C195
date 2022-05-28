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
    public TextField idText;
    public TextField nameText;
    public TextField addressText;
    public TextField postalCodeText;
    public TextField phoneText;
    public ComboBox<Country> countryCombo;
    public ComboBox<Division> divisionCombo;

    private Customer customer;


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
        divisionCombo.setPromptText("First select country");
        divisionCombo.setVisibleRowCount(5);
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

    public void onCountryCombo(ActionEvent actionEvent) {

        try {
            int countryIdFK = countryCombo.getSelectionModel().getSelectedItem().getId();

            divisionCombo.setItems(DivisionQuery.selectAllForCountry(countryIdFK));
            divisionCombo.setValue(null);
            divisionCombo.setPromptText("Select division");
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
        }

        divisionCombo.setPromptText("Select division");
    }

    public void showValidateError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Customer add/modify form");
        alert.setContentText("Please complete all fields.");
        alert.showAndWait();
    }

    public void onSaveButtonAction(ActionEvent actionEvent) throws IOException {
        try {
            String name = nameText.getText();
            String address = addressText.getText();
            String postalCode = postalCodeText.getText();
            String phone = phoneText.getText();
            String country = countryCombo.getSelectionModel().getSelectedItem().toString();
            int divId = divisionCombo.getSelectionModel().getSelectedItem().getId();
            int rowsAffected;

            // Validation: Ensure all fields are set
            if(name.isEmpty() || address.isEmpty() || postalCode.isEmpty() || phone.isEmpty() ||
                    Integer.valueOf(divId) == null) {
                showValidateError();
            }
            else {
                if (customer == null) {
                    rowsAffected = CustomerQuery.insert(name, address, postalCode, phone, divId);
                } else {
                    int id = customer.getId();

                    rowsAffected = CustomerQuery.update(id, name, address, postalCode, phone, divId);
                }

                if(rowsAffected != 1) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Customer add/modify error");
                    alert.setContentText("Unable to add/modify customer.");
                    alert.showAndWait();
                }

                MainController.toMain(actionEvent);
            }
        }
        catch (NullPointerException e) {
            showValidateError();
        }
    }

    /**
     * Handles cancel request.
     * Returns user to main screen without saving any entered textfield values.
     * @param actionEvent the Cancel button click event
     * @throws IOException for input/output exceptions
     */
    public void onCancelButtonAction(ActionEvent actionEvent) throws IOException {
        MainController.toMain(actionEvent);
    }
}