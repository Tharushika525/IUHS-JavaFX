package controller.order;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.customer.CustomerController;
import controller.item.ItemController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import model.*;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {

    public JFXTextField txtOrderID;
    @FXML
    private JFXComboBox<String> cmbCustomerId;

    @FXML
    private JFXComboBox<String> cmbItemCode;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colQTY;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblTime;

    @FXML
    private TableView<CartTM> tblOrders;

    @FXML
    private JFXTextField txtCity;

    @FXML
    private JFXTextField txtCustomerID;

    @FXML
    private JFXTextField txtItemDescription;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtQTY;

    @FXML
    private JFXTextField txtSalary;

    @FXML
    private JFXTextField txtStock;

    @FXML
    private JFXTextField txtUnitPrice;

    ObservableList<CartTM> cart = FXCollections.observableArrayList();
    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        Double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        Integer qty = Integer.parseInt(txtQTY.getText());
        Double total = unitPrice * qty;
        cart.add(new CartTM(
                cmbItemCode.getValue(),
                txtItemDescription.getText(),
                qty,
                unitPrice,
                total
        ));
        tblOrders.setItems(cart);
        calcTotal();
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws SQLException {
         String orderId = txtOrderID.getText();
         String customerId = cmbCustomerId.getValue();

        List<OrderDatail> orderDetails = new ArrayList<>();

        for (CartTM cartTM : cart){
            String itemCode = cartTM.getItemCode();
             Integer qty = cartTM.getQty();
            orderDetails.add(new OrderDatail(orderId,itemCode,qty,0.0));
        }

        LocalDate now = LocalDate.now();

        if(new OrderController().placeOrder(new Order(orderId,now,customerId,orderDetails))){
            new Alert(Alert.AlertType.INFORMATION,"Order Placed!!").show();
        }else{
            new Alert(Alert.AlertType.ERROR,"Order Not Placed!!").show();
        }

    }
    private void loadDateAndTime(){
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblOrderDate.setText("Date : " + f.format(date));

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime now = LocalTime.now();
            lblTime.setText(
                    "Time : " + now.getHour() + " : " + now.getMinute() + " : " + now.getSecond());

        }),
                new KeyFrame(Duration.seconds(1))
                );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((ObservableValue,s,newValue) -> {
            loadItemData(newValue);
        });

        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((ObservableValue,s,newValue) -> {
            loadCustomerData(newValue);
        });

        loadDateAndTime();
        loadCustomerIds();
        loadItemCodes();

    }

    private void loadCustomerIds(){
       cmbCustomerId.setItems(new CustomerController().getCustomerIds());
    }
    private void loadItemCodes(){
        cmbItemCode.setItems(new ItemController().getItemCodes());
    }
    public  void loadItemData(String itemCode){
        Item item = new ItemController().searchItem(itemCode);

        txtItemDescription.setText(item.getDescription());
        txtStock.setText(item.getQty().toString());
        txtUnitPrice.setText(item.getPrice().toString());
    }

    public  void loadCustomerData(String customerID){
        Customer customer = new CustomerController().searchCustomer(customerID);

       txtName.setText(customer.getName());
       txtCity.setText(customer.getCity());
       txtSalary.setText(customer.getSalary().toString());
    }

    private void calcTotal(){
        Double netTotal = 0.0;
        for(CartTM cartTM : cart){
            netTotal += cartTM.getTotal();
        }
        lblNetTotal.setText("Net Total : "+netTotal.toString());
    }
}
