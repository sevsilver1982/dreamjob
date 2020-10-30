package store;

import model.Item;

import java.util.Collection;

public interface Store<T extends Item> {

    boolean add(T item);

    Collection<T> findAll();

    T findById(int id);

    boolean delete(int id);

}