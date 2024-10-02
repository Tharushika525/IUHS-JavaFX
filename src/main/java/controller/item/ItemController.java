package controller.item;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Item;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemController implements ItemService{
    @Override
    public boolean addItem(Item item) {
        String SQL = "INSERT INTO item VALUES(?,?,?,?,?)";
        try {
           return CrudUtil.execute(
                    SQL,
                    item.getItemCode(),
                    item.getDescription(),
                    item.getSize(),
                    item.getPrice(),
                    item.getQty());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateItem(Item item) {
        String SQL = "UPDATE item SET Description=?,PackSize=?,UnitPrice=?,QtyOnHand=? WHERE ItemCode=?";
        try {
            return CrudUtil.execute(
                    SQL,
                    item.getDescription(),
                    item.getSize(),
                    item.getPrice(),
                    item.getQty(),
                    item.getItemCode());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Item searchItem(String itemCode) {
        String SQL = "SELECT * FROM item WHERE ItemCode=?";
        ResultSet resultSet = null;
        try {
            resultSet = CrudUtil.execute(SQL, itemCode);
            resultSet.next();
            return new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getInt(5)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteItem(String itemCode) {
        String SQL = "DELETE FROM item WHERE ItemCode=?";
        try {
            return CrudUtil.execute(SQL,itemCode);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Item> getAllItem() {
        String SQL = "select * from item";
        ObservableList<Item> itemObservableList = FXCollections.observableArrayList();
        try {

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
           return itemObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
