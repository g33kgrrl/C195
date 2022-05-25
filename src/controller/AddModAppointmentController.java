package controller;

import dao.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
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
    public ComboBox startMinuteCombo;
    public DatePicker endDatePicker;
    public ComboBox endHourCombo;
    public ComboBox endMinuteCombo;
    public Label userIdLabel;

    private Appointment appointment;

    User currentUser = UserQuery.getCurrentUser();

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
        ObservableList<String> hours = FXCollections.observableArrayList();
        ObservableList<String> minutes = FXCollections.observableArrayList();
        ObservableList<String> types = FXCollections.observableArrayList();


        // TODO: restrict list according to local time zone
        // Business hours 8:00 - 22:00 EST
        // Create array using "for" loop and (UTC + timeZoneOffset)
//        hours.setAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
//                "16", "17", "18", "19", "20", "21", "22", "23"
//        );

        // TODO: use LocalDateTime and convert it via a ZonedDateTime
        // I would not recommend using ZoneOffSet.


        for (int i = 0; i < 24; i++) {

        }

        hours.setAll("10", "11", "12");
        minutes.setAll("00", "15", "30", "45");

        startHourCombo.setItems(hours);
        startMinuteCombo.setItems(minutes);
        endHourCombo.setItems(hours);
        endMinuteCombo.setItems(minutes);

        customerCombo.setItems(CustomerQuery.getAll());
        contactCombo.setItems(ContactQuery.getAll());
        userCombo.setItems(UserQuery.getAll());
        userCombo.setValue(currentUser);

        types.setAll("Planning Session", "De-Briefing", "Training");
        typeCombo.setItems(types);
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
        startHourCombo.setValue(String.format("%02d",start.getHour()));
        startMinuteCombo.setValue(String.format("%02d",start.getMinute()));
        endDatePicker.setValue(end.toLocalDate());
        endHourCombo.setValue(String.format("%02d",end.getHour()));
        endMinuteCombo.setValue(String.format("%02d",end.getMinute()));
        customerCombo.setValue(CustomerQuery.getCustomer(appointment.getCustomerId()));
        userCombo.setValue(UserQuery.getUser(appointment.getUserId()));
        contactCombo.setValue(ContactQuery.getContact(appointment.getContactId()));
    }
//
//    /**
//     * Handles search parts request.
//     * Fetches text input, and calls Inventory.lookupPart() to find matching parts. If any matches are
//     * found, display them in the parts table and then clear the TextField to prepare for the next
//     * search. If no matching parts are found, display an error popup instead.
//     * @param searchPartsEvent the search parts text entry event
//     */
//    public void onSearchPartsHandler(ActionEvent searchPartsEvent) {
//        String q = SearchParts.getText();
//
//        ObservableList<Part> matchingParts = Inventory.lookupPart(q);
//
//        if (matchingParts.size() == 0) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Search parts");
//            alert.setContentText("No matching parts found.");
//            alert.showAndWait();
//        } else {
//            AllPartsTable.setItems(matchingParts);
//            SearchParts.setText("");
//        }
//    }
//
//    /**
//     * Handles add associated part request.
//     * Adds selected part to associated parts list for the new product to be added. If no part is selected,
//     * prompts user to select a part to add to associated parts.
//     * @param addPartEvent the add associated part button click event
//     */
//    public void onAddButtonAction(ActionEvent addPartEvent) {
//        Part selectedItem = (Part) AllPartsTable.getSelectionModel().getSelectedItem();
//
//        associatedParts.add(selectedItem);
//    }
//
//    /**
//     * Handles remove associated part request.
//     * First checks if there are any associated parts, and if not, displays an error that there are no
//     * associated parts to remove. Otherwise, verifies that a part has been selected for removal, prompts
//     * the user with the part's information and asks for confirmation to remove. If no part has been
//     * selected, prompt the user to select a part for removal.
//     * @param removePartEvent the remove associated part button click event
//     */
//    public void onRemoveButtonAction(ActionEvent removePartEvent) {
//        if (associatedParts.size() == 0) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Remove part");
//            alert.setContentText("No associated parts to remove.");
//            alert.showAndWait();
//        } else {
//            try {
//                Part selectedItem = (Part) AssociatedPartsTable.getSelectionModel().getSelectedItem();
//
//                String removeConfirm = "Remove this part from product " + product.getName() + "?\n\n" +
//                        "\tPart Id: " + selectedItem.getId() + "\n\n" +
//                        "\tPart Name: " + selectedItem.getName() + "\n\n" +
//                        "\tInventory Level: " + selectedItem.getStock() + "\n\n" +
//                        "\tPrice/cost per unit: " + selectedItem.getPrice() + "\n";
//
//                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, removeConfirm);
//
//                Optional<ButtonType> result = alert.showAndWait();
//
//                if (result.isPresent() && result.get() == ButtonType.OK) {
//                    associatedParts.remove(selectedItem);
//                }
//            } catch (NullPointerException e) {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Remove part");
//                alert.setContentText("Please select a part to remove.");
//                alert.showAndWait();
//            }
//        }
//    }
//
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
            int startHour = Integer.parseInt(startHourCombo.getValue().toString());
            int startMinute = Integer.parseInt(startMinuteCombo.getValue().toString());
            int endHour = Integer.parseInt(endHourCombo.getValue().toString());
            int endMinute = Integer.parseInt(endMinuteCombo.getValue().toString());
            LocalDateTime start = LocalDateTime.of(startDatePicker.getValue(), LocalTime.of(startHour, startMinute));
            LocalDateTime end = LocalDateTime.of(endDatePicker.getValue(), LocalTime.of(endHour, endMinute));
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
                    Integer.valueOf(startHour) == null || Integer.valueOf(startMinute) == null ||
                    Integer.valueOf(endHour) == null || Integer.valueOf(endMinute) == null ||
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
