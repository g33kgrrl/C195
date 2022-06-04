package controller;

import dao.CustomerQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ReportsController implements Initializable {
    /**
     * Overrides initialize method.
     * @param url the url
     * @param resourceBundle the resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /***
     * Launch report showing the total number of customer appointments by type and month.
     * @param actionEvent the report by type/month button click action
     * @throws IOException for input/output exceptions
     */
    public void onReportByTypeMonthButtonAction(ActionEvent actionEvent) throws IOException {
        // the total number of customer appointments by type and month
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportByTypeMonth.fxml"));
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        stage.setTitle("Report: Appt Counts");
        stage.setScene(scene);
        stage.show();
    }

    /***
     * Launch report showing all appointments for the selected contact.
     * @param actionEvent the report by type/month button click action
     * @throws IOException for input/output exceptions
     */
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

    /***
     * Launch report showing total count of all customers.
     */
    public void onReportCustomerCountButtonAction() {
        // an additional report of your choice that is different from the two other required reports in this prompt
        // and from the user log-in date and time stamp that will be tracked in part C
        int customerCount = CustomerQuery.getCustomerCount();

        MainController.showAlert("information", "Report: Customer Count", "Total customer count " +
                "is " + customerCount + ".");
    }

    /***
     * Handles Back button click.
     * When Back button is clicked, dismiss report and return user to main screen.
     * @param actionEvent the Back button click event
     * @throws IOException for input/output exceptions
     */
    public void onBackButtonAction(ActionEvent actionEvent) throws IOException {
        MainController.toMain(actionEvent);
    }
}
