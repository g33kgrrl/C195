package controller;

import dao.AppointmentQuery;
import dao.ContactQuery;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.Contact;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ReportByContactController implements Initializable {
    public TableView TypeMonthApptsTable;
    public TableColumn apptIdCol;
    public TableColumn apptTitleCol;
    public TableColumn apptDescriptionCol;
    public TableColumn apptTypeCol;
    public TableColumn apptStartCol;
    public TableColumn apptEndCol;
    public TableColumn apptCustomerIdCol;
    public ComboBox contactCombo;


    /**
     * Initializes available field options in report by contact screen.
     * @param url the url
     * @param resourceBundle the resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactCombo.setItems(ContactQuery.getAll());
    }

    /***
     * Handles change contact action.
     * When a contact is selected, displays all appointments associated with that contact. If there are no appointments
     * for that contact, display an alert to notify the user.
     * @param actionEvent the change contact combobox action
     */
    public void onContactComboAction(ActionEvent actionEvent) {
        Contact selectedContact = (Contact) contactCombo.getSelectionModel().getSelectedItem();
        ObservableList<Appointment> contactAppointments = AppointmentQuery.selectAllByContact(selectedContact.getId());

        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        if(contactAppointments == null) {
            TypeMonthApptsTable.setItems(null);

            MainController.showAlert("information", "Search Appointments by Contact",
                    "No appointments found for contact " +
                            selectedContact.getName() + ".");
        }
        else {
            TypeMonthApptsTable.setItems(contactAppointments);
        }
    }

    /***
     * Handles OK button click.
     * When OK button is clicked, dismiss report and return user to reports screen.
     * @param actionEvent the OK button click event
     * @throws IOException for input/output exceptions
     */
    public void onOkButtonAction(ActionEvent actionEvent) throws IOException {
        MainController.toReports(actionEvent);
    }
}
