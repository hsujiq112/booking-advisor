package service;

import java.util.ArrayList;
import java.util.UUID;

public interface ServiceI<T> {

    public void insert(T param) throws Exception;
    public T findById(UUID id) throws Exception;
    public ArrayList<T> dbSet() throws Exception;
    public void update(T model) throws Exception;
    public void delete(UUID id) throws Exception;
}
