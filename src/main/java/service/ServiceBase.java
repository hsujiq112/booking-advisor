package service;

import repository.RepositoryBase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class ServiceBase<T> {

    private final RepositoryBase<T> repository;

    public ServiceBase() {
        this.repository = new RepositoryBase<T>() {
            @Override
            public void insert(T param) {
                super.insert(param);
            }

            @Override
            public T findById(UUID id) {
                return super.findById(id);
            }

            @Override
            public List<T> dbSet() {
                return super.dbSet();
            }

            @Override
            public void update(T model) {
                super.update(model);
            }

            @Override
            public void delete(UUID id) {
                super.delete(id);
            }
        };
    }

    public void insert(T model) {
        repository.insert(model);
    }

    public ArrayList<T> dbSet() {
        var models = new ArrayList<T>();
        repository.dbSet().forEach(i -> {
            if (i != null) {
                models.add(i);
            }
        });
        return models;
    }

    public T getByID(UUID id) {
        return repository.findById(id);
    }

    public void update(T model) {
        repository.update(model);
    }

    public void delete(UUID id) {
        repository.delete(id);
    }

}
