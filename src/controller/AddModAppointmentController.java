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

    private final LocalTime openLt = LocalTime.of(8, 0);
    private final LocalTime closeLt = LocalTime.of(22, 0);
    private User currentUser = UserQuery.getCurrentUser();
    private Appointment appointment;

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
                Appointment.getValidApptHours(LocalDateTime.of(localDate, openLt), LocalDateTime.of(localDate, closeLt));
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
     * @param actionEvent the save product button clidk event
     * @throws IOException for input/output exceptions
     */
    public void onSaveButtonAction(ActionEvent actionEvent) throws IOException {
        try {
            String title = titleText.getText();
            String description = descriptionText.getText();
            String location = locationText.getText();
            String type = typeCombo.getValue();
            LocalDateTime start = LocalDateTime.of(startDatePicker.getValue(), LocalTime.parse(startHourCombo.getValue().toString()));
            LocalDateTime end = LocalDateTime.of(endDatePicker.getValue(), LocalTime.parse(endHourCombo.getValue().toString()));
            int customerId = ((Customer) customerCombo.getSelectionModel().getSelectedItem()).getId();
            int userId = ((User) userCombo.getValue()).getUserId();
            int contactId = ((Contact) contactCombo.getSelectionModel().getSelectedItem()).getId();
            int rowsAffected;

            // Validation: Ensure all fields are set
            if(title.isEmpty() || description.isEmpty() || location.isEmpty() || type.isEmpty() ||
                    startHourCombo.getSelectionModel().getSelectedItem() == null ||
                    endHourCombo.getSelectionModel().getSelectedItem() == null ||
                    startDatePicker.getValue() == null || endDatePicker.getValue() == null ||
                    Integer.valueOf(customerId) == null || Integer.valueOf(userId) == null ||
                    Integer.valueOf(contactId) == null
            ) {
                MainController.showAlert("error", "Appointment add/modify form", "Please complete all fields.");
            } else if(Appointment.checkOverlap(customerId, start, end, appointment)) {
                MainController.showAlert("error", "Appointment add/modify form", "The selected start and end " +
                        "times overlap one or more existing appointments for this customer. Please revise.");
            }
            else {
                LocalDateTime lastUpdate = LocalDateTime.now();
                String lastUpdatedBy = currentUser.getUserName();

                // If adding new appointment, creation is now by current user; otherwise, fetch from database
                // TODO: Move this into if/else below
                LocalDateTime createDate;
                String createdBy;

                if(appointment == null) {
                    createDate = LocalDateTime.now();
                    createdBy = currentUser.getUserName();
                } else {
                    createDate = appointment.getCreateDate();
                    createdBy = appointment.getCreatedBy();
                }

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
                    MainController.showAlert("error", "Appointment add/modify error",
                            "Unable to add/modify appointment.");

                }

                MainController.toMain(actionEvent);
            }
        } catch (NullPointerException e) {
            MainController.showAlert("error", "Appointment add/modify form", "Please complete all fields.");
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