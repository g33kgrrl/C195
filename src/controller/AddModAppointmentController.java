package controller;

import dao.AppointmentQuery;
import dao.ContactQuery;
import dao.CountryQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

import model.*;

public class AddModAppointmentController implements Initializable {
    public TableView AssociatedPartsTable;
    public TableView AllPartsTable;
    public TextField SearchParts;
    public TextField idText;
    public TextField nameText;
    public TextField inventoryText;
    public TextField priceText;
    public TextField maxText;
    public TextField minText;
    public TableColumn partIdCol;
    public TableColumn partNameCol;
    public TableColumn partInvCol;
    public TableColumn partPriceCol;
    public TableColumn assocPartIdCol;
    public TableColumn assocPartNameCol;
    public TableColumn assocPartInvCol;
    public TableColumn assocPartPriceCol;
    public TextField titleText;
    public TextField descriptionText;
    public TextField locationText;
    public ComboBox contactCombo;
    public TextField typeText;
    public DatePicker startDatePicker;
    public ComboBox startHourCombo;
    public ComboBox startMinuteCombo;
    public DatePicker endDatePicker;
    public ComboBox endHourCombo;
    public ComboBox endMinuteCombo;
    public Label customerIdLabel;
    public Label userIdLabel;

    //    private Product product;
//    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private Contact appointmentContact;
    private Appointment appointment;


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
        contactCombo.setItems(ContactQuery.selectAll());

        ObservableList<String> hours = FXCollections.observableArrayList();
        ObservableList<String> minutes = FXCollections.observableArrayList();

        hours.setAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
                "16", "17", "18", "19", "20", "21", "22", "23"
        );

        minutes.setAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
                "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32",
                "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49",
                "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"
        );

        startHourCombo.setItems(hours);
        startMinuteCombo.setItems(minutes);
        endHourCombo.setItems(hours);
        endMinuteCombo.setItems(minutes);
    }

    public void onSaveButtonAction(ActionEvent actionEvent) {
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
        contactCombo.setValue(ContactQuery.select(appointment.getContactId()));
        typeText.setText(String.valueOf(appointment.getType()));
        startDatePicker.setValue(start.toLocalDate());
        startHourCombo.setValue(String.format("%02d",start.getHour()));
        startMinuteCombo.setValue(String.format("%02d",start.getMinute()));
        endDatePicker.setValue(end.toLocalDate());
        endHourCombo.setValue(String.format("%02d",end.getHour()));
        endMinuteCombo.setValue(String.format("%02d",end.getMinute()));
        customerIdLabel.setText("CustomerId: " + appointment.getCustomerId());
        userIdLabel.setText("UserId: " + appointment.getUserId());

//        // Appointment_ID, title, description, location, contact, type, start date and time, end date and time, Customer_ID, and User_ID

//        associatedParts.setAll(product.getAllAssociatedParts());
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
//    /**
//     * Handles save product request.
//     * Gets values from textfields, ensures input is valid, gets the list of associated parts, and then
//     * calls Inventory.updateProduct() to replace the current product object with a new product using the
//     * given data. Tracks next unique product ID to be used, and returns user to main screen. If input is
//     * invalid, an error message is displayed instead.
//     * @param saveEvent the save product button clidk event
//     * @throws IOException for input/output exceptions
//     */
//    public void onSaveButtonAction(ActionEvent saveEvent) throws IOException {
//        try {
//            int index = Inventory.getAllProducts().indexOf(product);
//            int id = Integer.parseInt(idText.getText());
//            String name = nameText.getText();
//            float price = Float.parseFloat(priceText.getText());
//            int stock = Integer.parseInt(inventoryText.getText());
//            int min = Integer.parseInt(minText.getText());
//            int max = Integer.parseInt(maxText.getText());
//
//            if (min < 0 || min >= max || stock < min || stock > max) {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Invalid inventory settings");
//                alert.setContentText("Must set 0 < Min < Inv < Max.");
//                alert.showAndWait();
//            } else {
//                Product product1 = new Product(id, name, price, stock, min, max);
//
//                for (Part p : associatedParts) {
//                    product1.addAssociatedPart(p);
//                }
//
//                Inventory.updateProduct(index, product1);
//
//                MainController.toMain(saveEvent);
//            }
//        } catch (NumberFormatException e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Modify product form");
//            alert.setContentText("Please enter valid values in text fields.");
//            alert.showAndWait();
//        }
//    }
//
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
