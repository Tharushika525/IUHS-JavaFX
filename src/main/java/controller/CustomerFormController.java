package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.beans.value.ObservableValue;
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
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pS = connection.prepareStatement(SQL);
            pS.setObject(1,customer.getId());
            pS.setObject(2,customer.getTitle());
            pS.setObject(3,customer.getName());
            pS.setObject(4,customer.getDob());
            pS.setObject(5,customer.getSalary());
            pS.setObject(6,customer.getAddress());
            pS.setObject(7,customer.getCity());
            pS.setObject(8,customer.getProvince());
            pS.setObject(9,customer.getPostalCode());
            boolean isAdded = pS.executeUpdate()>0;
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
          String SQL = "DELETE FROM customer WHERE CustID = '"+txtCustomerID.getText()+"'";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            boolean isDeleted = connection.createStatement().executeUpdate(SQL)>0;
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
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pS = connection.prepareStatement(SQL);
            pS.setObject(1,txtCustomerID.getText());
            ResultSet resultSet = pS.executeQuery();
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
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pS = connection.prepareStatement(SQL);
            pS.setObject(1,customer.getName());
            pS.setObject(2,customer.getTitle());
            pS.setObject(3,customer.getDob());
            pS.setObject(4,customer.getSalary());
            pS.setObject(5,customer.getAddress());
            pS.setObject(6,customer.getCity());
            pS.setObject(7,customer.getProvince());
            pS.setObject(8,customer.getPostalCode());
            pS.setObject(9,customer.getId());
            boolean isUpdated = pS.executeUpdate()>0;
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
            Connection connection = DBConnection.getInstance().getConnection();
            String SQL = "Select * from customer";
            ResultSet resultSet = connection.createStatement().executeQuery(SQL);
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


