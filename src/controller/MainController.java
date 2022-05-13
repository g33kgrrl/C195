package controller;

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
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.io.IOException;

import model.Appointment;
import model.Customer;
import dao.AppointmentQuery;
import dao.CustomerQuery;


public class MainController implements Initializable {
    public Button AddCustomerButton;
    public Button ModifyCustomerButton;
    public Button DeleteCustomerButton;
    public Button AddAppointmentButton;
    public Button ModifyAppointmentButton;
    public Button DeleteAppointmentButton;
    public TableView AppointmentsTable;
    public TableView CustomersTable;
    public TextField SearchCustomers;
    public TextField SearchProducts;
    public TableColumn custIdCol;
    public TableColumn custNameCol;
    public TableColumn custAddressCol;
    public TableColumn custPostalCodeCol;
    public TableColumn custPhoneCol;
    public TableColumn custCountryCol;
    public TableColumn custDivIdCol;
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
        // Customer table
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        custAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        custPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
//        custCountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        custDivIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

        // TODO: How to add Country data in TableColumn?
//        custCountryCol.setCellValueFactory(new PropertyValueFactory<>());
//        TableColumn<Country, String> firstNameColumn= =
//                TableColumn<>("");

        ObservableList<Customer> allCustomers = CustomerQuery.getAll();


//        ObservableList<String> countries;

//        TableColumn<String, country> countries;

//        allCustomers.forEach(custDivIdCol -> CustomerQuery.getcustDivIdCol.getDivisionId()
//        );

//        allCustomers.forEach(custDivIdCol -> CountryQuery.getCountryByDivId(custDivIdCol.getDivisionId()));

//        TableColumn<String, country> count = allCustomers.forEach(custDivIdCol -> DivisionQuery.getCountryId(custDivIdCol.getDivisionId()));

//        ObservableList<Country> data = ...
//        TableView<Customer> tableView = new TableView<Customer>(allCustomers);

//        custCountryCol.setCellValueFactory((Callback<TableColumn.CellDataFeatures, ObservableValue>) cellDataFeatures -> CountryQuery.getCountryByDivId(custDivIdCol.getCellFactory()));


//        TableColumn<Country,String> custCountryCol = new TableColumn<Country,String>("Country");
//        custCountryCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Division, Integer>, String>() {
//            public String call(TableColumn.CellDataFeatures<Division, Integer> div) {
//                // p.getValue() returns the Person instance for a particular TableView row
//                return CountryQuery.getCountryByDivId(div.getValue().getCountryId()).getName();
//            }
//        });
//
//        CustomersTable.getColumns().add(custCountryCol);}

//        custCountryCol.setCellValueFactory(
//                new PropertyValueFactory<Country, String>(CountryQuery.getCountry(DivisionQuery.getCountryId(Integer.parseInt(custDivIdCol.getId()))).getName()));

//        for (Customer customer : allCustomers) {
//
//        }
//        static List<TableColumn> getFlattenedColumns(TableView<?> table) {
//            List<TableColumn> l = new ArrayList<>();
//            table.getColumns()
//                    .forEach(c -> l.addAll(flatten(c)));
//            return l;
//        }

        CustomersTable.setItems(allCustomers);

        // Appointments table
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptCreateDateCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        apptCreatedByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        apptLastUpdateCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        apptLastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        apptCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        apptUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        apptContactIdCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));

        AppointmentsTable.setItems(AppointmentQuery.getAll());

//        DivisionQuery.selectAllForCountry();
    }

    /**
     * Navigates user back to the main screen.
     * @param mainEvent an event requiring redirect to main screen
     * @throws IOException for input/output exceptions
     */
    public static void toMain(ActionEvent mainEvent) throws IOException {
        Parent root = FXMLLoader.load(MainController.class.getResource("/view/Main.fxml"));
        Stage stage = (Stage)((Node)mainEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Schedule Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Handles add customer request.
     * Launches add customer dialog.
     * @param addCustomerEvent the add part button click event
     * @throws IOException for input/output exceptions
     */
    public void onAddCustomerButtonAction(ActionEvent addCustomerEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddModCustomer.fxml"));
        Stage stage = (Stage)((Node)addCustomerEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Handles modify customer request.
     * Checks that a customer has been selected. If so, it fetches that product's information, launches
     * the modify customer dialog, and pre-populates the TextFields with that information. If no customer
     * has been selected, displays an error message instead prompting user to select a customer.
     * @param modifyCustomerEvent the modify customer button click event
     * @throws IOException for input/output exceptions
     */
    public void onModifyCustomerButtonAction(ActionEvent modifyCustomerEvent) throws IOException {
        try {
            Customer selectedItem = (Customer) CustomersTable.getSelectionModel().getSelectedItem();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/AddModCustomer.fxml"));
            loader.load();

            AddModCustomerController addModCustController = loader.getController();
            addModCustController.displayCustomer(selectedItem);

            Stage stage = (Stage) ((Node) modifyCustomerEvent.getSource()).getScene().getWindow();
            Parent root = loader.getRoot();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Modify customer");
            alert.setContentText("Please select a customer to modify.");
            alert.showAndWait();
        }
    }

    public void onDeleteCustomerButtonAction(ActionEvent deleteCustomerEvent) {
        try {
            Customer selectedCustomer = (Customer) CustomersTable.getSelectionModel().getSelectedItem();

            String deleteConfirm = "Permanently delete this customer?\n\n" +
                    "\tId: " + selectedCustomer.getId() + "\n\n" +
                    "\tName: " + selectedCustomer.getName() + "\n\n" +
                    "\tAddress: " + selectedCustomer.getAddress() + "\n\n" +
                    "\tPostal Code: " + selectedCustomer.getPostalCode() + "\n\n" +
                    "\tPhone: " + selectedCustomer.getPhone() + "\n\n" +
                    "\tDivision ID: " + selectedCustomer.getDivisionId() + "\n";

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, deleteConfirm);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
//                AppointmentQuery.delete()
                CustomerQuery.delete(selectedCustomer.getId());
                CustomersTable.setItems(CustomerQuery.getAll());
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete customer");
            alert.setContentText("Please select a customer to delete.");
            alert.showAndWait();
        }
    }

    public void onSearchCustomerHandler(ActionEvent actionEvent) {
    }

    public void onAddAppointmentButtonAction(ActionEvent addAppointmentEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddModAppointment.fxml"));
        Stage stage = (Stage)((Node)addAppointmentEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
        stage.show();
    }

    public void onModifyAppointmentButtonAction(ActionEvent addModApptEvent) throws IOException {
        try {
            Appointment selectedItem = (Appointment) AppointmentsTable.getSelectionModel().getSelectedItem();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/AddModAppointment.fxml"));
            loader.load();

            AddModAppointmentController addModAppointmentController = loader.getController();
            addModAppointmentController.displayAppointment(selectedItem);

            Stage stage = (Stage) ((Node) addModApptEvent.getSource()).getScene().getWindow();
            Parent root = loader.getRoot();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Modify appointment");
            alert.setContentText("Please select an appointment to modify.");
            alert.showAndWait();
        }
    }

    public void onDeleteAppointmentButtonAction(ActionEvent actionEvent) {
    }

    public void onSearchApptsHandler(ActionEvent actionEvent) {
    }

    /**
     * Handles exit program request.
     * Displays a confirmation dialog asking the user if they really want to exit. If confirmed, the program
     * exits. Otherwise the user is returned to the main screen.
     * @param exitEvent the exit program button click event
     */
    public void onExitButtonAction(ActionEvent exitEvent) {
        System.exit(0);
        // TODO: add this back in later
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit the program?");
//
//        Optional<ButtonType> result = alert.showAndWait();
//
//        if (result.isPresent() && result.get() == ButtonType.OK) {
//            System.exit(0);
//        }
    }
}
