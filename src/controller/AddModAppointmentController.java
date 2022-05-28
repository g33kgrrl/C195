package controller;

import dao.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

import model.*;

public class AddModAppointmentController implements Initializable {
    public TextField idText;
    public TextField titleText;
    public TextField descriptionText;
    public TextField locationText;
    public ComboBox<Customer> customerCombo;
    public ComboBox<User> userCombo;
    public ComboBox<Contact> contactCombo;
    public ComboBox<String> typeCombo;
    public DatePicker startDatePicker;
    public ComboBox startHourCombo;
    public DatePicker endDatePicker;
    public ComboBox endHourCombo;
    public Label userIdLabel;

    private Appointment appointment;

    User currentUser = UserQuery.getCurrentUser();
    LocalTime openLt = LocalTime.of(8, 0);
    LocalTime closeLt = LocalTime.of(22, 0);

//  When adding and updating an appointment, record the following data: Appointment_ID, title, description, location,
//  contact, type, start date and time, end date and time, Customer_ID, and User_ID.
//
//  All of the original appointment information is displayed on the update form in local time zone.

    /**
     * Initializes add product dialog.
     * The Product ID textfield is assigned a unique product ID that cannot be edited by the user. This
     * prevents creating a duplicate product ID when adding a new part. The All Parts table is
     * initialized with a list of all existing parts. The Associated Parts table is initialized with
     * associated parts for this product, if any, and ready for parts to be added or removed as needed.
     * @param url the url
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDate localDate = LocalDate.now();
        ObservableList<String> validApptHours =
                AppointmentQuery.getValidApptHours(LocalDateTime.of(localDate, openLt), LocalDateTime.of(localDate, closeLt));
        startHourCombo.setItems(validApptHours);
        endHourCombo.setItems(validApptHours);

        ObservableList<String> types = FXCollections.observableArrayList();
        types.setAll("Planning Session", "De-Briefing", "Training");
        typeCombo.setItems(types);

        customerCombo.setItems(CustomerQuery.getAll());
        contactCombo.setItems(ContactQuery.getAll());
        userCombo.setItems(UserQuery.getAll());
        userCombo.setValue(currentUser);
    }

    /**
     * Initializes modify product dialog with current data for the selected product.
     * Gets current data for the selected product, and populates the modify product textfields with that
     * data. Product ID textfield is disabled to prevent creation of duplicate product IDs.
     * @param appointment1 the selected appointment to modify
     */
    public void displayAppointment(Appointment appointment1) {
        this.appointment = appointment1;

        LocalDateTime start = appointment.getStart();
        LocalDateTime end = appointment.getEnd();

        idText.setText(String.valueOf(appointment.getId()));
        titleText.setText(appointment.getTitle());
        descriptionText.setText(String.valueOf(appointment.getDescription()));
        locationText.setText(String.valueOf(appointment.getLocation()));
        typeCombo.setValue(String.valueOf(appointment.getType()));
        startDatePicker.setValue(start.toLocalDate());
        startHourCombo.setValue(String.format("%02d", start.getHour()) + ":00");
        endDatePicker.setValue(end.toLocalDate());
        endHourCombo.setValue(String.format("%02d", end.getHour()) + ":00");
        customerCombo.setValue(CustomerQuery.select(appointment.getCustomerId()));
        userCombo.setValue(UserQuery.select(appointment.getUserId()));
        contactCombo.setValue(ContactQuery.getContact(appointment.getContactId()));
    }

    /**
     * Handles save appointment request.
     * Gets values from textfields, ensures input is valid, gets the list of associated parts, and then
     * calls Inventory.updateProduct() to replace the current product object with a new product using the
     * given data. Tracks next unique product ID to be used, and returns user to main screen. If input is
     * invalid, an error message is displayed instead.
     * @param saveEvent the save product button clidk event
     * @throws IOException for input/output exceptions
     */
    public void onSaveButtonAction(ActionEvent saveEvent) throws IOException {
        try {
            String title = titleText.getText();
            String description = descriptionText.getText();
            String location = locationText.getText();
            String type = typeCombo.getValue();
            LocalDateTime start = LocalDateTime.of(startDatePicker.getValue(), LocalTime.parse(startHourCombo.getValue().toString()));
            LocalDateTime end = LocalDateTime.of(endDatePicker.getValue(), LocalTime.parse(endHourCombo.getValue().toString()));
//            int startHour = Integer.parseInt(startHourCombo.getValue().toString());
//            int endHour = Integer.parseInt(endHourCombo.getValue().toString());
//            LocalDateTime start = LocalDateTime.of(startDatePicker.getValue(), LocalTime.of(startHour, 0));
//            LocalDateTime end = LocalDateTime.of(endDatePicker.getValue(), LocalTime.of(endHour, 0));
//            LocalDateTime start = LocalDateTime.of(startDatePicker.getValue(), LocalTime.of(startHour, 0));
//            LocalDateTime end = LocalDateTime.of(endDatePicker.getValue(), LocalTime.of(endHour, 0));
            LocalDateTime lastUpdate = LocalDateTime.now();
            String lastUpdatedBy = currentUser.getUserName();
            int customerId = ((Customer) customerCombo.getSelectionModel().getSelectedItem()).getId();
            int userId = ((User) userCombo.getValue()).getUserId();
            int contactId = ((Contact) contactCombo.getSelectionModel().getSelectedItem()).getId();
            int rowsAffected;

            // If adding, create info is now; if modding, fetch create info
            LocalDateTime createDate;
            String createdBy;

            if(appointment == null) {
                createDate = LocalDateTime.now();
                createdBy = currentUser.getUserName();
            } else {
                createDate = appointment.getCreateDate();
                createdBy = appointment.getCreatedBy();
            }

            // Validation: Ensure all fields are set
            if(title.isEmpty() || description.isEmpty() || location.isEmpty() || type.isEmpty() ||
//                    startHourCombo.getSelectionModel().getSelectedItem() == null ||
//                    endHourCombo.getSelectionModel().getSelectedItem() == null ||
                    startDatePicker.getValue() == null || endDatePicker.getValue() == null ||
                    lastUpdate == null || lastUpdatedBy.isEmpty() ||
                    Integer.valueOf(customerId) == null || Integer.valueOf(userId) == null ||
                    Integer.valueOf(contactId) == null || createDate == null || createdBy.isEmpty()
            ) {
                showValidateError();
            }
            else {
//                System.out.println("Title: " + title + " | Description: " + description + " | Location: " +
//                        location + " | Type: " + type + "\n | Start: " + start.toString() +
//                        " | End: " + end.toString() + "\n | CreateDate: " + createDate.toString() + " | CreatedBy: " +
//                        createdBy + " | LastUpdate : " + lastUpdate + " | LastUpdatedBy: " +
//                        lastUpdatedBy + " | CustomerID: " + customerId + " | UserID: " + userId + " | ContactID: " + contactId
//                );

                if(appointment == null) {
                    rowsAffected = AppointmentQuery.insert(
                            title, description, location, type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy,
                            customerId, userId, contactId
                    );
                } else {
                    rowsAffected = AppointmentQuery.update(
                            appointment.getId(), title, description, location, type, start, end, createDate, createdBy,
                            lastUpdate, lastUpdatedBy, customerId, userId, contactId
                    );
                }

                if(rowsAffected != 1) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Appointment add/modify error");
                    alert.setContentText("Unable to add/modify appointment.");
                    alert.showAndWait();
                }

                MainController.toMain(saveEvent);
            }
        } catch (NullPointerException e) {
            showValidateError();
        }
    }

    public void showValidateError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Appointment add/modify form");
        alert.setContentText("Please complete all fields.");
        alert.showAndWait();
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
}
