package controller.item;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Item;
import util.CrudUtil;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ItemFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colPackSize;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableView tblItem;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtItemCode;

    @FXML
    private JFXTextField txtPackSize;

    @FXML
    private JFXTextField txtPrice;

    @FXML
    private JFXTextField txtQty;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        Item item = new Item(
                txtItemCode.getText(),
                txtDescription.getText(),
                txtPackSize.getText(),
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtQty.getText())
        );

        String SQL = "INSERT INTO item VALUES(?,?,?,?,?)";
        try {
            boolean isAdded = CrudUtil.execute(
                    SQL,
                    item.getItemCode(),
                    item.getDescription(),
                    item.getSize(),
                    item.getPrice(),
                    item.getQty());
            if(isAdded){
                new Alert(Alert.AlertType.INFORMATION,"Item Added Successfully!!").show();
                loadTable();
                clearText();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Item Not Added!!").show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
           String SQL = "DELETE FROM item WHERE ItemCode=?";

        try {
            boolean isDeleted = CrudUtil.execute(SQL,txtItemCode.getText());
            if(isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Item Deleted!!").show();
                loadTable();
                clearText();
            }
        } catch (SQLException e) {
           new Alert(Alert.AlertType.ERROR,"Item Not Deleted!!").show();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String SQL = "SELECT * FROM item WHERE ItemCode=?";

        try {
            ResultSet resultSet = CrudUtil.execute(SQL,txtItemCode.getText());
            resultSet.next();
            setValue(new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getInt(5)
            ));

        } catch (SQLException e) {
           new Alert(Alert.AlertType.ERROR,"Item Not Found!!").show();
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        Item item = new Item(
                txtItemCode.getText(),
                txtDescription.getText(),
                txtPackSize.getText(),
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtQty.getText())
        );

          String SQL = "UPDATE item SET Description=?,PackSize=?,UnitPrice=?,QtyOnHand=? WHERE ItemCode=?";
        try {
            boolean isUpdated = CrudUtil.execute(
                    SQL,
                    item.getDescription(),
                    item.getSize(),
                    item.getPrice(),
                    item.getQty(),
                    item.getItemCode());
            if(isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Item Updated!!").show();
                loadTable();
                clearText();

            }
        } catch (SQLException e) {
           new Alert(Alert.AlertType.ERROR,"Item Not Updated!!").show();
        }

    }

    public void loadTable(){
        ObservableList<Item> itemObservableList = FXCollections.observableArrayList();

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));

        try {
            String SQL = "select * from item";
            ResultSet resultSet = CrudUtil.execute(SQL);
            while(resultSet.next()){
                Item item = new Item(
                        resultSet.getString("ItemCode"),
                        resultSet.getString("Description"),
                        resultSet.getString("PackSize"),
                        resultSet.getDouble("UnitPrice"),
                        resultSet.getInt("QtyOnHand")

                );
                itemObservableList.add(item);

            }
            tblItem.setItems(itemObservableList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTable();

        tblItem.getSelectionModel().selectedItemProperty().addListener((observableValue, Item, itemValues) ->{
            if(itemValues!=null){
                setValue((Item) itemValues);
            }
                }
                );
    }

    private void setValue(Item itemValues) {
        txtItemCode.setText(itemValues.getItemCode());
        txtDescription.setText(itemValues.getDescription());
        txtPackSize.setText(itemValues.getSize());
        txtPrice.setText(itemValues.getPrice().toString());
        txtQty.setText(itemValues.getQty().toString());
    }

    public void clearText(){
        txtItemCode.clear();
        txtDescription.clear();
        txtPackSize.clear();
        txtPrice.clear();
        txtQty.clear();
    }
}
