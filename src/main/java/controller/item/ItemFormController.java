package controller.item;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import dto.Item;
import service.ServiceFactory;
import service.custom.ItemService;
import service.custom.impl.ItemServiceImpl;
import util.ServiceType;

import java.net.URL;
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

    ItemService  itemService = ServiceFactory.getInstance().getServiceType(ServiceType.ITEM);
    @FXML
    void btnAddOnAction(ActionEvent event) {
        Item item = new Item(
                txtItemCode.getText(),
                txtDescription.getText(),
                txtPackSize.getText(),
                Double.parseDouble(txtPrice.getText()),
                Integer.parseInt(txtQty.getText())
        );


        if(itemService.addItem(item)){
                new Alert(Alert.AlertType.INFORMATION,"Item Added Successfully!!").show();
                loadTable();
                clearText();
        } else {
            new Alert(Alert.AlertType.ERROR,"Item Not Added!!").show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if(itemService.deleteItem(txtItemCode.getText())){
            new Alert(Alert.AlertType.INFORMATION,"Item Deleted!!").show();
            loadTable();
            clearText();
    } else {
        new Alert(Alert.AlertType.ERROR,"Item Not Deleted!!").show();
    }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        Item item = itemService.searchItem(txtItemCode.getText());
        setValue(item);
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
        if(itemService.updateItem(item)){
            new Alert(Alert.AlertType.INFORMATION,"Item Updated!!").show();
            loadTable();
            clearText();
    } else{
        new Alert(Alert.AlertType.ERROR,"Item Not Updated!!").show();
    }
    }

    public void loadTable(){
        ObservableList<Item> items = itemService.getAllItem();
        tblItem.setItems(items);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));

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
