package dao;

import model.AbstractEntity;

import java.util.List;

public interface Dao<T extends AbstractEntity> {

    T create(T t);

    T getById(Long id);

    void delete(T t);

    T update(T t);

    List<T> getAll();
}
