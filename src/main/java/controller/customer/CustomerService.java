package controller.customer;

import javafx.collections.ObservableList;
import model.Customer;
import model.Item;

public interface CustomerService {
    boolean addCustomer(Customer customer);
    boolean updateCustomer(Customer customer);
    Customer searchCustomer(String id);
    boolean deleteCustomer(String id);
    ObservableList<Customer> getAllCustomer();

}
