package controller;

import dao.AppointmentQuery;
import dao.ContactQuery;
import dao.CustomerQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;


public class ReportByContactController implements Initializable {
    public TableView AppointmentsTable;
    public TableColumn apptIdCol;
    public TableColumn apptTitleCol;
    public TableColumn apptDescriptionCol;
    public TableColumn apptTypeCol;
    public TableColumn apptStartCol;
    public TableColumn apptEndCol;
    public TableColumn apptCustomerIdCol;
    public ComboBox contactCombo;


    /**
     * Sets up and displays main screen.
     * Initializes part and product tables, and populates them with current inventory items.
     * @param url the url
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactCombo.setItems(ContactQuery.getAll());
    }

    public void onContactComboAction(ActionEvent actionEvent) {
        Contact selectedContact = (Contact) contactCombo.getSelectionModel().getSelectedItem();
        ObservableList<Appointment> contactAppointments = AppointmentQuery.getAllByContact(selectedContact.getId());

        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        if(contactAppointments.size() == 0) {
            MainController.showError("Search Appointments by Contact", "No appointments found for contact " +
                    selectedContact.getName() + "."
            );
        }
        else {
            AppointmentsTable.setItems(contactAppointments);
        }
    }

    public void onOkButtonAction(ActionEvent actionEvent) throws IOException {
        MainController.toReports(actionEvent);
    }
}
