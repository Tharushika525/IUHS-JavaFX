package controller.customer;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;
import util.CrudUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {

    @FXML
    private JFXButton btnReload;

    @FXML
    private JFXComboBox<String> cmbTitle;

    @FXML
    private TableColumn<?,?> colAddress;

    @FXML
    private TableColumn<?,?> colCity;

    @FXML
    private TableColumn<?,?> colDOB;

    @FXML
    private TableColumn<?,?> colID;

    @FXML
    private TableColumn<?,?> colName;

    @FXML
    private TableColumn<?,?> colPostalCode;

    @FXML
    private TableColumn<?,?> colProvince;

    @FXML
    private TableColumn<?,?> colSalary;

    @FXML
    private TableColumn<?,?> colTitle;

    @FXML
    private DatePicker dateDOB;

    @FXML
    private TableView<Customer> tblCustomers;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtCity;

    @FXML
    private JFXTextField txtCustomerID;

    @FXML
    private JFXTextField txtCustomerName;

    @FXML
    private JFXTextField txtPostalCode;

    @FXML
    private JFXTextField txtProvince;

    @FXML
    private JFXTextField txtSalary;

    CustomerService customerController = new CustomerController();
    @FXML
    void btnAddOnAction(ActionEvent event) {
        Customer customer = new Customer(
                txtCustomerID.getText(),
                cmbTitle.getValue(),
                txtCustomerName.getText(),
                dateDOB.getValue(),
                Double.parseDouble(txtSalary.getText()),
                txtAddress.getText(),
                txtCity.getText(),
                txtProvince.getText(),
                txtPostalCode.getText()
        );
        if(customerController.addCustomer(customer)){
            new Alert(Alert.AlertType.INFORMATION,"Customer Added Successfully").show();
            loadTable();
    } else {
        new Alert(Alert.AlertType.ERROR,"Customer Not Added").show();
    }
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if(customerController.deleteCustomer(txtCustomerID.getText())){
            new Alert(Alert.AlertType.INFORMATION,"Deleted Successfully!!").show();
            loadTable();
    } else {
        new Alert(Alert.AlertType.ERROR,"Not Deleted!!").show();
    }
    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchOnAction(ActionEvent event)  {
       setValueToText(customerController.searchCustomer(txtCustomerID.getText()));

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        Customer customer = new Customer(
                txtCustomerID.getText(),
                cmbTitle.getValue(),
                txtCustomerName.getText(),
                dateDOB.getValue(),
                Double.parseDouble(txtSalary.getText()),
                txtAddress.getText(),
                txtCity.getText(),
                txtProvince.getText(),
                txtPostalCode.getText()

        );
        if(customerController.updateCustomer(customer)){
            new Alert(Alert.AlertType.INFORMATION,"Update Successfully!!").show();
            loadTable();

    } else{
        new Alert(Alert.AlertType.ERROR,"Customer Not Updated!!").show();
    }
    }
    public void btnNextOnAction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/item_form.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
    }

    public void loadTable(){
        ObservableList<Customer> customers = customerController.getAllCustomer();
        tblCustomers.setItems(customers);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> customerTitleList = FXCollections.observableArrayList();
        customerTitleList.add("Mr");
        customerTitleList.add("Mrs");
        customerTitleList.add("Miss");
        customerTitleList.add("Ms");
        cmbTitle.setItems(customerTitleList);

        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));

        loadTable();

        tblCustomers.getSelectionModel().selectedItemProperty().addListener((observableValue, Customer , newValue) -> {
            if(newValue!=null){
               setValueToText(newValue);
            }
        });
    }
    private void setValueToText(Customer newValue) {
        txtCustomerID.setText(newValue.getId());
        txtCustomerName.setText(newValue.getName());
        cmbTitle.setValue(newValue.getTitle());
        txtProvince.setText(newValue.getProvince());
        txtPostalCode.setText(newValue.getPostalCode());
        txtSalary.setText(newValue.getSalary().toString());
        dateDOB.setValue(newValue.getDob());
        txtAddress.setText(newValue.getAddress());
        txtCity.setText(newValue.getCity());

    }
   
}


