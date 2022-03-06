package service;

import java.util.ArrayList;
import java.util.UUID;

public interface ServiceI<T> {

    public void insert(T param);
    public T findById(UUID id);
    public ArrayList<T> dbSet();
    public void update(T model);
    public void delete(UUID id);
}
