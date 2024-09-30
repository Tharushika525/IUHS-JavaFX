package controller.item;

import model.Customer;
import model.Item;

public interface ItemService {
    boolean addItem(Item item);
    boolean updateItem(Item item);
    Item searchItem(String itemCode);
    boolean deleteItem(String itemCode);
}
