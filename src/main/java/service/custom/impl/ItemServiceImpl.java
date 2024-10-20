package service.custom.impl;

import dto.Customer;
import entity.CustomerEntity;
import entity.ItemEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import dto.Item;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.SuperDao;
import repository.custom.ItemDao;
import service.custom.ItemService;
import util.DaoType;

import java.util.List;

public class ItemServiceImpl implements ItemService {

    ItemDao itemDao = DaoFactory.getInstance().getDaoType(DaoType.ITEM);
    @Override
    public boolean addItem(Item item) {
        System.out.println("Item Service : "+item);

        return itemDao.save(new ModelMapper().map(item, ItemEntity.class));

    }

    @Override
    public boolean updateItem(Item item) {
        System.out.println("Item Update Service : "+item);
         return itemDao.update(new ModelMapper().map(item, ItemEntity.class));
    }

    @Override
    public Item searchItem(String itemCode) {
        System.out.println("Item Search Service : "+itemCode);
        return new ModelMapper().map(itemDao.search(itemCode), Item.class);
    }

    @Override
    public boolean deleteItem(String itemCode) {

        System.out.println("Update Item Service : "+itemCode);
        return itemDao.delete(itemCode);
    }

    @Override
    public ObservableList<Item> getAllItem() {

        List<ItemEntity> itemEntities = itemDao.findAll();
        ObservableList<Item> items = FXCollections.observableArrayList();
        itemEntities.forEach(itemEntity -> {
            items.add(new ModelMapper().map(itemEntity, Item.class));
        });
        return items;
    }
}
