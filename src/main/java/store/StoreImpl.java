package store;

import model.ItemImpl;

import java.util.Collection;

public interface StoreImpl<T extends ItemImpl> {
    T add(T item);
    Collection<T> findAll();
    T findById(int id);
}