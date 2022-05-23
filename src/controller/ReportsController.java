package controller;

import dao.AppointmentQuery;
import dao.CustomerQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ReportsController implements Initializable {
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
    }

    public void showReport(String title, String report) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(report);
        alert.showAndWait();
    }

    public void onReportByTypeMonthButtonAction(ActionEvent actionEvent) throws IOException {
        // the total number of customer appointments by type and month
//        showReport("Report: Appointments by type and month", AppointmentQuery.getAllByTypeMonth());
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportByTypeMonth.fxml"));
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        stage.setTitle("Report: Appt Counts");
        stage.setScene(scene);
        stage.show();
    }

    public void onReportByContactButtonAction(ActionEvent actionEvent) throws IOException {
        // a schedule for each contact in your organization that includes appointment ID, title, type and description,
        // start date and time, end date and time, and customer ID
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportByContact.fxml"));
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        stage.setTitle("Report: Appts by Contact");
        stage.setScene(scene);
        stage.show();
    }

    public void onReportCustomerCountButtonAction(ActionEvent actionEvent) {
        // an additional report of your choice that is different from the two other required reports in this prompt
        // and from the user log-in date and time stamp that will be tracked in part C
        int customerCount = CustomerQuery.getCustomerCount();

        showReport("Report: Customer Count", "Total customer count is " + customerCount + ".");
    }

    public void onBackButtonAction(ActionEvent actionEvent) throws IOException {
        MainController.toMain(actionEvent);
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
