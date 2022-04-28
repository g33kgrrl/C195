package controller;

import dao.CustomerQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.io.IOException;


public class MainController implements Initializable {
    public Button AddPartButton;
    public Button ModifyPartButton;
    public Button DeletePartButton;
    public Button AddProductButton;
    public Button ModifyProductButton;
    public Button DeleteProductButton;
    public TableView PartsTable;
    public TableView CustomersTable;
    public TextField SearchParts;
    public TextField SearchProducts;
    public TableColumn partIdCol;
    public TableColumn partNameCol;
    public TableColumn partInvCol;
    public TableColumn partPriceCol;
    public TableColumn prodIdCol;
    public TableColumn prodNameCol;
    public TableColumn prodInvCol;
    public TableColumn prodPriceCol;

    /**
     * Sets up and displays main screen.
     * Initializes part and product tables, and populates them with current inventory items.
     * @param url the url
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Parts table
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

//        PartsTable.setItems(Inventory.getAllParts());

        // Products table
        prodIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        prodInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

//        ProductsTable.setItems(Inventory.getAllProducts());
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
        stage.setTitle("Main");
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
//            Customer selectedItem = (Customer) CustomersTable.getSelectionModel().getSelectedItem();

            Customer selectedItem = CustomerQuery.select(1);


            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/AddModCustomer.fxml"));
            loader.load();

            AddModCustomerController addModCustController = loader.getController();
            addModCustController.displayCustomer(selectedItem);

            Stage stage = (Stage) ((Node) modifyCustomerEvent.getSource()).getScene().getWindow();
            Parent root = loader.getRoot();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (NullPointerException | SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Modify customer");
            alert.setContentText("Please select a customer to modify.");
            alert.showAndWait();
        }
    }

    public void onDeleteCustomerButtonAction(ActionEvent actionEvent) {
    }

    public void onSearchCustomerHandler(ActionEvent actionEvent) {
    }

    public void onAddApptButtonAction(ActionEvent actionEvent) {
    }

    public void onModifyApptButtonAction(ActionEvent actionEvent) {
    }

    public void onDeleteApptButtonAction(ActionEvent actionEvent) {
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
