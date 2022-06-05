package controller;

import dao.AppointmentQuery;
import dao.ContactQuery;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;
import model.Appointment;
import model.Contact;


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
    public Button Back;

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
     */
    public void onContactComboAction() {
        Contact selectedContact = (Contact) contactCombo.getSelectionModel().getSelectedItem();
        ObservableList<Appointment> contactAppointments = AppointmentQuery.selectByContact(selectedContact.getId());

        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("formattedStart"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("formattedEnd"));
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
     * Handles Back button click.
     * When Back button is clicked, dismiss report and return user to reports screen.
     * @param actionEvent the Back button click event
     */
    public void onBackButtonAction(ActionEvent actionEvent) {
        MainController.toReports(actionEvent);
    }
}
