package controller;

import dao.*;
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


public class MainController implements Initializable {
    public Button AddCustomerButton;
    public Button ModifyCustomerButton;
    public Button DeleteCustomerButton;
    public Button AddAppointmentButton;
    public Button ModifyAppointmentButton;
    public Button DeleteAppointmentButton;
    public Button ViewReportsButton;
    public Button SignOutButton;
    public TableView AppointmentsTable;
    public TableView CustomersTable;
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
    public TableColumn apptCustomerIdCol;
    public TableColumn apptUserIdCol;
    public TableColumn apptContactIdCol;
    public RadioButton allRadio;
    public RadioButton weekRadio;
    public RadioButton monthRadio;
    public ToggleGroup reportSelectionGroup;


    /**
     * Sets up and displays main screen.
     * Initializes Customers and Appointments tables, and populates them with all existing items of each type.
     * @param url the url
     * @param resourceBundle the resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Customers table
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
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("formattedStart"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("formattedEnd"));
        apptContactIdCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        apptCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        apptUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

        AppointmentsTable.setItems(AppointmentQuery.selectAll());
    }

    /**
     * Navigates user back to the main screen.
     * @param actionEvent an event requiring redirect to main screen
     * @throws IOException for input/output exceptions
     */
    public static void toMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(MainController.class.getResource("/view/Main.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Schedule Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Navigates user to the reports screen.
     * @param actionEvent the Reports button click event
     * @throws IOException for input/output exceptions
     */
    public static void toReports(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(MainController.class.getResource("/view/Reports.fxml"));
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Reports");
        stage.setScene(scene);
        stage.show();
    }

    /***
     * Displays an alert of the specified type, with the specified title and content.
     * @param alertType the alert type
     * @param title the alert title
     * @param content the alert content
     */
    public static void showAlert(String alertType, String title, String content) {
        Alert alert = new Alert(Alert.AlertType.valueOf(alertType.toUpperCase()));
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Handles add customer request.
     * Launches add customer dialog.
     * @param actionEvent the add part button click event
     * @throws IOException for input/output exceptions
     */
    public void onAddCustomerButtonAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddModCustomer.fxml"));
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Handles modify customer request.
     * Checks that a customer has been selected. If so, it fetches that customer's information, launches
     * the modify customer dialog, and pre-populates the fields with that information. If no customer
     * has been selected, displays an error message instead prompting user to select a customer.
     * @param actionEvent the modify customer button click event
     * @throws IOException for input/output exceptions
     */
    public void onModifyCustomerButtonAction(ActionEvent actionEvent) throws IOException {
        try {
            Customer selectedItem = (Customer) CustomersTable.getSelectionModel().getSelectedItem();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/AddModCustomer.fxml"));
            loader.load();

            AddModCustomerController addModCustController = loader.getController();
            addModCustController.displayCustomer(selectedItem);

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Parent root = loader.getRoot();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (NullPointerException e) {
            showAlert("error", "Modify customer", "Please select a customer to modify.");
        }
    }

    /**
     * Handles delete customer request.
     * Checks that a customer has been selected. If so, it fetches that customer's information and displays an alert
     * with the customer's information, including the number of associated appointments, to confirm the user wants to
     * delete. If user confirms, all of the customer's appointments are deleted first, and then the customer, to avoid
     * database issues with the Customer_ID foreign key.
     */
    public void onDeleteCustomerButtonAction() {
        try {
            Customer selectedCustomer = (Customer) CustomersTable.getSelectionModel().getSelectedItem();
            int selectedCustomerId = selectedCustomer.getId();
            ObservableList<Appointment> associatedAppts = AppointmentQuery.selectByCustomerId(selectedCustomerId);
            int associatedApptsSize = (associatedAppts == null) ? 0 : associatedAppts.size();

            String deleteConfirm = "Are you sure you want to delete this customer and any associated appointments?\n\n" +
                    "\tId: " + selectedCustomerId + "\n\n" +
                    "\tName: " + selectedCustomer.getName() + "\n\n" +
                    "\tAddress: " + selectedCustomer.getAddress() + "\n\n" +
                    "\tPostal Code: " + selectedCustomer.getPostalCode() + "\n\n" +
                    "\tPhone: " + selectedCustomer.getPhone() + "\n\n" +
                    "\tDivision ID: " + selectedCustomer.getDivisionId() + "\n\n" +
                    "\tAssociated appointments: " + associatedApptsSize;

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, deleteConfirm);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                AppointmentQuery.deleteAllForCustomerId(selectedCustomerId);
                CustomerQuery.delete(selectedCustomerId);
                CustomersTable.setItems(CustomerQuery.getAll());
                AppointmentsTable.setItems(AppointmentQuery.selectAll());
            }
        } catch (NullPointerException e) {
            showAlert("error", "Delete customer", "Please select a customer to delete.");
        }
    }

    /**
     * Handles add appointment request.
     * Launches add/modify appointment dialog with no appointment data.
     * @param actionEvent the add appointment button click event
     * @throws IOException for input/output exceptions
     */
    public void onAddAppointmentButtonAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddModAppointment.fxml"));
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Handles modify appointment request.
     * Checks that an appointment has been selected. If so, it fetches that appointment's information, launches
     * the modify appointment dialog, and pre-populates the fields with that information. If no appointment
     * has been selected, displays an error message instead prompting user to select an appointment.
     * @param actionEvent the modify appointment button click event
     * @throws IOException for input/output exceptions
     */
    public void onModifyAppointmentButtonAction(ActionEvent actionEvent) throws IOException {
        try {
            Appointment selectedItem = (Appointment) AppointmentsTable.getSelectionModel().getSelectedItem();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/AddModAppointment.fxml"));
            loader.load();

            AddModAppointmentController addModAppointmentController = loader.getController();
            addModAppointmentController.displayAppointment(selectedItem);

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Parent root = loader.getRoot();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (NullPointerException e) {
            showAlert("error", "Modify appointment", "Please select an appointment to modify.");
        }
    }

    /**
     * Handles delete appointment request.
     * Checks that an appointment has been selected. If so, it fetches that appointment's information and displays an
     * alert with the appointment's information, to confirm the user wants to delete. If user confirms, the appointment
     * is deleted from the database.
     */
    public void onDeleteAppointmentButtonAction() {
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
                AppointmentsTable.setItems(AppointmentQuery.selectAll());

                String deletedApptConfirm = "Deleted appointment:\n\n" +
                        "\tId: " + selectedAppointmentId + "\n\n" +
                        "\tType: " + selectedAppointment.getType() + "\n\n";

                showAlert("confirmation", "Deleted appointment", deletedApptConfirm);
            }
        } catch (NullPointerException e) {
            showAlert("error", "Delete appointment", "Please select an appointment to delete.");
        }
    }

    /**
     * Handles sign out request.
     * Displays a confirmation dialog asking the user if they really want to sign out. If confirmed, the current (i.e.,
     * authenticated) user is reset to null and the login screen is shown. Otherwise the user is returned to the main screen.
     * @param actionEvent the Sign Out button click event
     */
    public void onSignOutButtonAction(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to sign out?");
        alert.setTitle("Sign Out");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            UserQuery.resetUser();

            Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
            Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle(LoginController.getResourceBundle().getString("title"));
            stage.setScene(scene);
            stage.show();
        }
    }

    /***
     * Set Appointments table to show all appointments.
     */
    public void onAllRadioAction() {
        AppointmentsTable.setItems(AppointmentQuery.selectAll());
    }

    /***
     * Set Appointments table to show this week's appointments.
     */
    public void onWeekRadioAction() {
        AppointmentsTable.setItems(AppointmentQuery.selectCurrentWeek());
    }

    /***
     * Set Appointments table to show this month's appointments.
     */
    public void onMonthRadioAction() { AppointmentsTable.setItems(AppointmentQuery.selectCurrentMonth()); }

    /**
     * Handles view reports request.
     * Launches view reports screen.
     * @param actionEvent the view reports button click event
     * @throws IOException for input/output exceptions
     */
    public void onViewReportsButtonAction(ActionEvent actionEvent) throws IOException { toReports(actionEvent); }
}
