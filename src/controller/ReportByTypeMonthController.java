package controller;

import dao.AppointmentQuery;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
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


    /**
     * Initializes table in report by type/month screen.
     * Populates table with appointment counts grouped by type and month.
     * @param url the url
     * @param resourceBundle the resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<TypeMonthAppt> typeMonthAppts = AppointmentQuery.getAllByTypeMonth();

        apptCountCol.setCellValueFactory(new PropertyValueFactory<>("count"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptMonthNameCol.setCellValueFactory(new PropertyValueFactory<>("monthName"));

        if(typeMonthAppts.size() == 0) {
            TypeMonthApptsTable.setItems(null);

            MainController.showAlert("information", "Count Appointments by Type and Month",
                    "No appointments found.");
        }
        else {
            TypeMonthApptsTable.setItems(typeMonthAppts);
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
