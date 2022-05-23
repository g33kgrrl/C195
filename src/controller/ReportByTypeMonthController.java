package controller;

import dao.AppointmentQuery;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.TypeMonthAppt;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ReportByTypeMonthController implements Initializable {
    public TableView TypeMonthApptsTable;
    public TableColumn apptCountCol;
    public TableColumn apptTypeCol;
    public TableColumn apptMonthNameCol;


    /**
     * Sets up and displays main screen.
     * Initializes part and product tables, and populates them with current inventory items.
     * @param url the url
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<TypeMonthAppt> typeMonthAppts = AppointmentQuery.getAllByTypeMonth();

        apptCountCol.setCellValueFactory(new PropertyValueFactory<>("count"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptMonthNameCol.setCellValueFactory(new PropertyValueFactory<>("monthName"));

        if(typeMonthAppts.size() == 0) {
            TypeMonthApptsTable.setItems(null);

            MainController.showError("Count Appointments by Type and Month", "No appointments found."
            );
        }
        else {
            TypeMonthApptsTable.setItems(typeMonthAppts);
        }
    }

    public void onOkButtonAction(ActionEvent actionEvent) throws IOException {
        MainController.toReports(actionEvent);
    }
}
