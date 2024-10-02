package controller.item;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        return false;
    }

    @Override
    public Item searchItem(String itemCode) {
        return null;
    }

    @Override
    public boolean deleteItem(String itemCode) {
        return false;
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
