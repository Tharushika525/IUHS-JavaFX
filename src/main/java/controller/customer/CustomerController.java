package controller.customer;

import model.Customer;

public class CustomerController implements CustomerService{
    @Override
    public boolean addCustomer(Customer customer) {
        return false;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return false;
    }

    @Override
    public Customer searchCustomer(String id) {
        return null;
    }

    @Override
    public boolean deleteCustomer(String id) {
        return false;
    }
}
