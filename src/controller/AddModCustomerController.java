package controller;

import dao.CountryQuery;
import dao.CustomerQuery;
import dao.DivisionQuery;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
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

    /**
     * Initializes available options for fields in add/modify customer dialog.
     * Combobox options are set according to existing countries and divisions.
     * @param url the url
     * @param resourceBundle the resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryCombo.setItems(Country.getAllCountries());
        countryCombo.setPromptText("Select country");
        divisionCombo.setPromptText("First select country");
        divisionCombo.setVisibleRowCount(5);
    }

    /**
     * Initializes modify customer dialog with current data for the selected customer.
     * Gets current data for the selected customer, and populates the modify customer fields with
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

    /***
     * Initialize, or re-initialize, division combobox options when a country is selected.
     * When a country is set in countryCombo, this fetches a list of valid divisions for that country and sets
     * divisionCombo options accordingly.
     * @param actionEvent the countryCombo change event
     */
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

    // TODO: Get rid of this
    public void showValidateError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Customer add/modify form");
        alert.setContentText("Please complete all fields.");
        alert.showAndWait();
    }

    /***
     * Handles save customer request.
     * Gets values from fields and ensures all fields are set. If not, an error is displayed. A new customer is added to
     * the database using CustomerQuery.insert(), or CustomerQuery.update() is called to update an existing customer.
     * @param actionEvent the Save button click event
     * @throws IOException for input/output exceptions
     */
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
     * Returns user to main screen without saving any entered field values.
     * @param actionEvent the Cancel button click event
     * @throws IOException for input/output exceptions
     */
    public void onCancelButtonAction(ActionEvent actionEvent) throws IOException {
        MainController.toMain(actionEvent);
    }
}
