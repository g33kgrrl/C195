package controller;

import dao.ContactQuery;
import dao.DivisionQuery;
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
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
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
        custCountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        custDivIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));

        CustomersTable.setItems(CustomerQuery.getAll());

        // Appointments table
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptContactIdCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        apptCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        apptUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

        AppointmentsTable.setItems(AppointmentQuery.getAll());
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
            int selectedCustomerId = selectedCustomer.getId();
            ObservableList<Appointment> customerAppointments = AppointmentQuery.getAllForCustomerId(selectedCustomerId);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");

            String deleteConfirm = "Are you sure you want to delete this customer and any associated appointments?\n\n" +
                    "\tId: " + selectedCustomerId + "\n\n" +
                    "\tName: " + selectedCustomer.getName() + "\n\n" +
                    "\tAddress: " + selectedCustomer.getAddress() + "\n\n" +
                    "\tPostal Code: " + selectedCustomer.getPostalCode() + "\n\n" +
                    "\tPhone: " + selectedCustomer.getPhone() + "\n\n" +
                    "\tDivision ID: " + selectedCustomer.getDivisionId() + "\n\n" +
                    "\tAssociated appointments: " + AppointmentQuery.getAllForCustomerId(selectedCustomerId).size();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, deleteConfirm);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                AppointmentQuery.deleteAllForCustomerId(selectedCustomerId);
                CustomerQuery.delete(selectedCustomerId);
                CustomersTable.setItems(CustomerQuery.getAll());
                AppointmentsTable.setItems(AppointmentQuery.getAll());
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

    /**
     * Handles modify customer request.
     * Checks that a customer has been selected. If so, it fetches that product's information, launches
     * the modify customer dialog, and pre-populates the TextFields with that information. If no customer
     * has been selected, displays an error message instead prompting user to select a customer.
     * @param modifyApptEvent the modify customer button click event
     * @throws IOException for input/output exceptions
     */
    public void onModifyAppointmentButtonAction(ActionEvent modifyApptEvent) throws IOException {
        try {
            Appointment selectedItem = (Appointment) AppointmentsTable.getSelectionModel().getSelectedItem();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/AddModAppointment.fxml"));
            loader.load();

            AddModAppointmentController addModAppointmentController = loader.getController();
            addModAppointmentController.displayAppointment(selectedItem);

            Stage stage = (Stage) ((Node) modifyApptEvent.getSource()).getScene().getWindow();
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

    public void onDeleteAppointmentButtonAction(ActionEvent deleteAppointmentEvent) {
        try {
            Appointment selectedAppointment = (Appointment) AppointmentsTable.getSelectionModel().getSelectedItem();
            int selectedAppointmentId = selectedAppointment.getId();
            int selectedCustomerId = selectedAppointment.getCustomerId();

            String toDeleteMsg = "Permanently delete this appointment?\n\n" +
                    "\tId: " + selectedAppointmentId + "\n\n" +
                    "\tTitle: " + selectedAppointment.getTitle() + "\n\n" +
                    "\tDescription: " + selectedAppointment.getDescription() + "\n\n" +
                    "\tLocation: " + selectedAppointment.getLocation() + "\n\n" +
                    "\tType: " + selectedAppointment.getType() + "\n\n" +
                    "\tStart: " + selectedAppointment.getStart() + "\n\n" +
                    "\tEnd: " + selectedAppointment.getEnd() + "\n\n" +
                    "\tCreate Date: " + selectedAppointment.getCreateDate() + "\n\n" +
                    "\tCreated By: " + selectedAppointment.getCreatedBy() + "\n\n" +
                    "\tLast Update: " + selectedAppointment.getLastUpdate() + "\n\n" +
                    "\tLast Updated By: " + selectedAppointment.getLastUpdatedBy() + "\n\n" +
                    "\tCustomer ID: " + selectedCustomerId + "\n\n" +
                    "\tUser ID: " + selectedAppointment.getUserId() + "\n\n" +
                    "\tContact ID: " + selectedAppointment.getContactId() + "\n\n";

            Alert toDeleteAlert = new Alert(Alert.AlertType.CONFIRMATION, toDeleteMsg);

            Optional<ButtonType> toDeletePrompt = toDeleteAlert.showAndWait();

            if (toDeletePrompt.isPresent() && toDeletePrompt.get() == ButtonType.OK) {
                AppointmentQuery.delete(selectedAppointmentId);
                AppointmentsTable.setItems(AppointmentQuery.getAll());

                String deletedApptConfirm = "Deleted appointment:\n\n" +
                        "\tId: " + selectedAppointmentId + "\n\n" +
                        "\tType: " + selectedAppointment.getType() + "\n\n";

                Alert deletedAlert = new Alert(Alert.AlertType.CONFIRMATION, deletedApptConfirm);

                Optional<ButtonType> deletedPrompt = deletedAlert.showAndWait();
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete customer");
            alert.setContentText("Please select an appointment to delete.");
            alert.showAndWait();
        }
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

    public void onAllRadioAction(ActionEvent onAllEvent) {
        AppointmentsTable.setItems(AppointmentQuery.getAll());
    }

    public void onWeekRadioAction(ActionEvent onWeekEvent) {
        AppointmentsTable.setItems(AppointmentQuery.getWeek());
    }

    public void onMonthRadioAction(ActionEvent onMonthEvent) {
        AppointmentsTable.setItems(AppointmentQuery.getMonth());
    }

    public void showReport(String title, String report) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(report);
        alert.showAndWait();
    }

    public void onReportCustomerCountButtonAction(ActionEvent actionEvent) {
        // an additional report of your choice that is different from the two other required reports in this prompt
        // and from the user log-in date and time stamp that will be tracked in part C
        int customerCount = CustomerQuery.getCustomerCount();

        showReport("Report: Customer Count", "Total customer count is " + customerCount + ".");
    }

    public void onReportByTypeMonthButtonAction(ActionEvent actionEvent) {
        // the total number of customer appointments by type and month
        showReport("Report: Appointments by type and month", AppointmentQuery.getAllByTypeMonthReport());
    }

    public void onReportByContactButtonAction(ActionEvent reportByContactButtonEvent) throws IOException {
        // a schedule for each contact in your organization that includes appointment ID, title, type and description,
        // start date and time, end date and time, and customer ID
//        showReport("Report: Appointments by contact", "Foo bar baz qux");
        // NO. Instead, go to report by contact view
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportByContact.fxml"));
        Stage stage = (Stage)((Node)reportByContactButtonEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Report: Appointments by contact");
        stage.setScene(scene);
        stage.show();
    }
}
