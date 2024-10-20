package repository.custom.impl;

import dto.Customer;
import entity.CustomerEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.modelmapper.ModelMapper;
import repository.custom.CustomerDao;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public boolean save(CustomerEntity customer) {
        System.out.println("Repository : "+customer);
        String SQL =  "INSERT INTO customer VALUES(?,?,?,?,?,?,?,?,?)";
        try {
            return CrudUtil.execute(
                    SQL,
                    customer.getId(),
                    customer.getTitle(),
                    customer.getName(),
                    customer.getDob(),
                    customer.getSalary(),
                    customer.getAddress(),
                    customer.getCity(),
                    customer.getProvince(),
                    customer.getPostalCode()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean update(CustomerEntity customer) {
        System.out.println("Update Repository : "+customer);
        String SQL = "UPDATE customer SET CustName=?,CustTitle=?,DOB=?,salary=?,CustAddress=?,City=?,Province=?,PostalCode=? WHERE CustID=?";
        try {
            return CrudUtil.execute(
                    SQL,
                    customer.getName(),
                    customer.getTitle(),
                    customer.getDob(),
                    customer.getSalary(),
                    customer.getAddress(),
                    customer.getCity(),
                    customer.getProvince(),
                    customer.getPostalCode(),
                    customer.getId()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String id) {
        System.out.println("Delete Repository : "+id);
        String SQL = "DELETE FROM customer WHERE CustID =?";

        try {
            return CrudUtil.execute(SQL,id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<CustomerEntity> findAll() {

       ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();


        String SQL = "select * from customer";
        try {
            ResultSet resultSet = CrudUtil.execute(SQL);
            while (resultSet.next()) {
                Customer customer = new Customer(
                        resultSet.getString("custID"),
                        resultSet.getString("custTitle"),
                        resultSet.getString("custName"),
                        resultSet.getDate("dob").toLocalDate(),
                        resultSet.getDouble("salary"),
                        resultSet.getString("custAddress"),
                        resultSet.getString("city"),
                        resultSet.getString("province"),
                        resultSet.getString("postalCode")
                );
                customerObservableList.add(customer);
            }
            ObservableList<CustomerEntity> customerEntityList = FXCollections.observableArrayList();
            customerObservableList.forEach(customer -> {
                customerEntityList.add(new ModelMapper().map(customer,CustomerEntity.class));
            });
            return customerEntityList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Object search(String id) {
        System.out.println("Search Repository : "+id);
        String SQL = "SELECT * FROM customer WHERE CustID=?";
        ResultSet resultSet = null;
        try {
            resultSet = CrudUtil.execute(SQL,id);
            resultSet.next();
            return new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4).toLocalDate(),
                    resultSet.getDouble(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9)

            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<String> getIds() {
        ObservableList<CustomerEntity> allCustomers = findAll();
        ObservableList<String> idList = FXCollections.observableArrayList();

        allCustomers.forEach(customer -> {
            idList.add(customer.getId());
        });
        return idList;

    }


}
