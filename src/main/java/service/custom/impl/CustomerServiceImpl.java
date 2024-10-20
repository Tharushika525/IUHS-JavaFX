package service.custom.impl;

import entity.CustomerEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import dto.Customer;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.SuperDao;
import repository.custom.CustomerDao;
import repository.custom.impl.CustomerDaoImpl;
import service.custom.CustomerService;
import util.CrudUtil;
import util.DaoType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {

    CustomerDao customerDao = DaoFactory.getInstance().getDaoType(DaoType.CUSTOMER);

    @Override
    public boolean addCustomer(Customer customer) {
        System.out.println("Service : " + customer);
        CustomerEntity entity = new ModelMapper().map(customer, CustomerEntity.class);
        return customerDao.save(entity);

    }

    @Override
    public boolean updateCustomer(Customer customer) {
        System.out.println("Update Service : " + customer);
        CustomerEntity entity = new ModelMapper().map(customer, CustomerEntity.class);
        return customerDao.update(entity);

    }

    @Override
    public Customer searchCustomer(String id) {
        System.out.println("Search Service : " + id);
        return new ModelMapper().map(customerDao.search(id), Customer.class);

    }

    @Override
    public boolean deleteCustomer(String id) {
        System.out.println("Delete Service : " + id);
        return customerDao.delete(id);
    }

    @Override
    public ObservableList<Customer> getAllCustomer() {
        List<CustomerEntity> customerEntities = customerDao.findAll();
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        customerEntities.forEach(customerEntity -> {
            customers.add(new ModelMapper().map(customerEntity, Customer.class));
        });
        return customers;
    }

    public ObservableList<String> getIds(){
        return customerDao.getIds();
    }
}
