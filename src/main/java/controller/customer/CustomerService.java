package controller.customer;

import model.Customer;

public interface CustomerService {
    boolean addCustomer(Customer customer);
    boolean updateCustomer(Customer customer);
    Customer searchCustomer(String id);
    boolean deleteCustomer(String id);
}
