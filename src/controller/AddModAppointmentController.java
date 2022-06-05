package controller;

import dao.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
     * Initializes available options for fields in add/modify appointment dialog.
     * User's local time zone is checked. Comboboxes for appointment start and end times are initialized with a
     * restricted set of hours, reflecting Eastern time zone corporate business hours converted to user's local time.
     * Other combobox options are set according to existing customers, users, and limited list of types.
     * @param url the url
     * @param resourceBundle the resourceBundle
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
     * Initializes add/modify appointment dialog with any presets.
     * Modify action populates fields with current data for the selected appointment. Add action leaves them blank.
     * Appointment ID textfield is disabled to prevent creation of duplicate appointment IDs.
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
     * Gets values from fields and ensures all fields are set. If not, an error is displayed. Gets a list of all
     * appointments for the selected customer and compares their start and end dates/times to the start and end
     * dates/times set in the form, to check for overlaps. If an overlap is found, an error message notifies the user so
     * a different time slot can be selected. A new appointment is added to the database using AppointmentQuery.insert(),
     * or AppointmentQuery.update() is called to update an existing appointment.
     * @param actionEvent the save product button click event
     */
    public void onSaveButtonAction(ActionEvent actionEvent) {
        try {
            String title = titleText.getText();
            String description = descriptionText.getText();
            String location = locationText.getText();
            String type = typeCombo.getValue();
            LocalDateTime start = LocalDateTime.of(startDatePicker.getValue(), LocalTime.parse(startHourCombo.getValue().toString()));
            LocalDateTime end = LocalDateTime.of(endDatePicker.getValue(), LocalTime.parse(endHourCombo.getValue().toString()));
            int customerId = customerCombo.getSelectionModel().getSelectedItem().getId();
            int userId = userCombo.getValue().getUserId();
            int contactId = contactCombo.getSelectionModel().getSelectedItem().getId();
            int rowsAffected;

            // Validation: Ensure all fields are set
            if(title.isEmpty() || description.isEmpty() || location.isEmpty() || type.isEmpty() ||
                    startHourCombo.getSelectionModel().getSelectedItem() == null ||
                    endHourCombo.getSelectionModel().getSelectedItem() == null ||
                    startDatePicker.getValue() == null || endDatePicker.getValue() == null ||
                    customerCombo.getValue() == null || userCombo.getValue() == null ||
                    contactCombo.getValue() == null
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
                LocalDateTime createDate;
                String createdBy;

                if(appointment == null) {
                    createDate = LocalDateTime.now();
                    createdBy = currentUser.getUserName();

                    rowsAffected = AppointmentQuery.insert(
                            title, description, location, type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy,
                            customerId, userId, contactId
                    );
                } else {
                    createDate = appointment.getCreateDate();
                    createdBy = appointment.getCreatedBy();

                    rowsAffected = AppointmentQuery.update(
                            appointment.getId(), title, description, location, type, start, end, createDate, createdBy,
                            lastUpdate, lastUpdatedBy, customerId, userId, contactId
                    );
                }

                if(rowsAffected != 1) {
                    MainController.showAlert("error", "Appointment add/modify error",
                            "Failed to save appointment to the database.");
                }

                MainController.toMain(actionEvent);
            }
        } catch (NullPointerException e) {
            MainController.showAlert("error", "Appointment add/modify error", "Please complete all " +
                    "fields.");
        }
    }

    /**
     * Handles cancel request.
     * Returns user to main screen without saving any entered values.
     * @param actionEvent the Cancel button click event
     */
    public void onCancelButtonAction(ActionEvent actionEvent) {
        MainController.toMain(actionEvent);
    }
}
