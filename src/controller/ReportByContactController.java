package controller;

import dao.AppointmentQuery;
import dao.CustomerQuery;
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
    public TableColumn apptLocationCol;
    public TableColumn apptDescriptionCol;
    public TableColumn apptTypeCol;
    public TableColumn apptStartCol;
    public TableColumn apptEndCol;
    public TableColumn apptCreateDateCol;
    public TableColumn apptCreatedByCol;
    public TableColumn apptLastUpdateCol;
    public TableColumn apptLastUpdatedByCol;
    public TableColumn apptCustomerIdCol;
    public TableColumn apptUserIdCol;
    public TableColumn apptContactIdCol;


    /**
     * Sets up and displays main screen.
     * Initializes part and product tables, and populates them with current inventory items.
     * @param url the url
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
//        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
//        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
//        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
//        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
//        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
//        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
//        apptContactIdCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
//        apptCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
//        apptUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
//
//        AppointmentsTable.setItems(AppointmentQuery.getAll());
    }

    public void onAllRadioAction(ActionEvent onAllEvent) {
        AppointmentsTable.setItems(AppointmentQuery.getAll());
    }

    public void onWeekRadioAction(ActionEvent onWeekEvent) {
        AppointmentsTable.setItems(AppointmentQuery.getWeek());
    }

    public void onMonthRadioAction(ActionEvent onMonthEvent) {
        AppointmentsTable.setItems(AppointmentQuery.getMonth());
    }

    public String onContactReportButtonAction(ActionEvent actionEvent) {
//        try {
////            int totalContacts = ContactQuery.getAll().
//
//        }
//        catch (SQLException ex) {
//            ex.printStackTrace();
//        }
        return "Foo";
    }

    public void onTypeMonthReportButtonAction(ActionEvent actionEvent) {
    }

//    public void onCustomerCountReportButtonAction(ActionEvent actionEvent) {
//        int customerCount = CustomerQuery.getCustomerCount();
//
//        showReport("Customer Count Report", "Total customer count is " + customerCount + ".");
//    }
//
//    public void showReport(String title, String report) {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle(title);
//        alert.setContentText(report);
//        alert.showAndWait();
//    }
}
