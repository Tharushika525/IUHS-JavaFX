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
        String SQL = "INSERT INTO customer VALUES(?,?,?,?,?,?,?,?,?)";
        try {
            boolean isAdded = CrudUtil.execute(
                    SQL,
                    customer.getId(),
                    customer.getTitle(),
                    customer.getName(),
                    customer.getDob(),
                    customer.getSalary(),
                    customer.getAddress(),
                    customer.getCity(),
                    customer.getProvince(),
                    customer.getPostalCode()
            );
            if(isAdded){
               new Alert(Alert.AlertType.INFORMATION,"Customer Added Successfully").show();
               loadTable();


            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Customer Not Added").show();
        }
    }



    @FXML
    void btnDeleteOnAction(ActionEvent event) {
          String SQL = "DELETE FROM customer WHERE CustID =?";
        try {
            boolean isDeleted = CrudUtil.execute(SQL,txtCustomerID.getText());
            if(isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Deleted Successfully!!").show();
                loadTable();
            }
        } catch (SQLException e) {
          new Alert(Alert.AlertType.ERROR,"Not Deleted!!").show();
        }
    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchOnAction(ActionEvent event)  {
        String SQL = "SELECT * FROM customer WHERE CustID=?";

        try {
            ResultSet resultSet = CrudUtil.execute(SQL,txtCustomerID.getText());
            resultSet.next();
            setValueToText(new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4).toLocalDate(),
                    resultSet.getDouble(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9)

            ));
        } catch (SQLException e) {
           new Alert(Alert.AlertType.ERROR,"Customer Not Found").show();
        }

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
        String SQL = "UPDATE customer SET CustName=?,CustTitle=?,DOB=?,salary=?,CustAddress=?,City=?,Province=?,PostalCode=? WHERE CustID=?";
        try {
            boolean isUpdated = CrudUtil.execute(
                    SQL,
                    customer.getName(),
                    customer.getTitle(),
                    customer.getDob(),
                    customer.getSalary(),
                    customer.getAddress(),
                    customer.getCity(),
                    customer.getProvince(),
                    customer.getPostalCode(),
                    customer.getId()
                    );
            if(isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Update Successfully!!").show();
                loadTable();
            }
        } catch (SQLException e) {
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
        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();

        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        try {
            String SQL = "select * from customer";
            ResultSet resultSet = CrudUtil.execute(SQL);
            while(resultSet.next()) {
                Customer customer = new Customer(
                        resultSet.getString("custID"),
                        resultSet.getString("custTitle"),
                        resultSet.getString("custName"),
                        resultSet.getDate("dob").toLocalDate(),
                        resultSet.getDouble("salary"),
                        resultSet.getString("custAddress"),
                        resultSet.getString("city"),
                        resultSet.getString("province"),
                        resultSet.getString("postalCode")
                );
                customerObservableList.add(customer);
            }
            tblCustomers.setItems(customerObservableList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> customerTitleList = FXCollections.observableArrayList();
        customerTitleList.add("Mr");
        customerTitleList.add("Mrs");
        customerTitleList.add("Miss");
        customerTitleList.add("Ms");
        cmbTitle.setItems(customerTitleList);
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


