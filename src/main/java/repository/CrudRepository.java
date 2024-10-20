package repository;

import javafx.collections.ObservableList;

import java.util.List;

public interface CrudRepository <T,ID> extends SuperDao{
    boolean save(T entity);
    boolean update(T entity);
    boolean delete(ID id);
    ObservableList<T> findAll();
    Object  search(ID id);
    ObservableList<String> getIds();


}
