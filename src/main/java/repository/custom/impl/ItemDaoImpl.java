package repository.custom.impl;

import dto.Item;
import entity.CustomerEntity;
import entity.ItemEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;
import repository.custom.ItemDao;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ItemDaoImpl implements ItemDao {
    @Override
    public boolean save(ItemEntity item) {
        System.out.println("Item Repository : " + item);

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
    public boolean update(ItemEntity item) {
        System.out.println("Item Update Repository : " + item);
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
    public boolean delete(String itemCode) {
        System.out.println("Delete Item Repository : " + itemCode);
        String SQL = "DELETE FROM item WHERE ItemCode=?";
        try {
            return CrudUtil.execute(SQL, itemCode);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<ItemEntity> findAll() {
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

            ObservableList<ItemEntity> itemEntityObservableList= FXCollections.observableArrayList();
            itemObservableList.forEach(item -> {
                itemEntityObservableList.add(new ModelMapper().map(item, ItemEntity.class));
            });
            return itemEntityObservableList;



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object search(String itemCode) {
        System.out.println("Search Item Repository : " + itemCode);
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
    public ObservableList<String> getIds() {
        ObservableList<ItemEntity> allItems = findAll();
        ObservableList<String> idList = FXCollections.observableArrayList();

        allItems.forEach(item -> {
            idList.add(item.getItemCode());
        });
        return idList;

    }
    }




