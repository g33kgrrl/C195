package controller;

import dao.AppointmentQuery;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import model.TypeMonthAppt;


public class ReportByTypeMonthController implements Initializable {
    public TableView TypeMonthApptsTable;
    public TableColumn apptCountCol;
    public TableColumn apptTypeCol;
    public TableColumn apptMonthNameCol;
    public Button Back;


    /**
     * Initializes table in report by type/month screen.
     * Populates table with appointment counts grouped by type and month (regardless of year).
     * @param url the url
     * @param resourceBundle the resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<TypeMonthAppt> typeMonthAppts = AppointmentQuery.selectByTypeMonth();

        apptCountCol.setCellValueFactory(new PropertyValueFactory<>("count"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptMonthNameCol.setCellValueFactory(new PropertyValueFactory<>("monthName"));

        if(typeMonthAppts == null) {
            TypeMonthApptsTable.setItems(null);

            MainController.showAlert("information", "Count Appointments by Type and Month",
                    "No appointments found.");
        }
        else {
            TypeMonthApptsTable.setItems(typeMonthAppts);
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
