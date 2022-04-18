package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
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
    public TableView ProductsTable;
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

    public void onAddCustomerButtonAction(ActionEvent actionEvent) {
    }

    public void onModifyCustomerButtonAction(ActionEvent actionEvent) {
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

    public void onExitButtonAction(ActionEvent actionEvent) {
    }
}
