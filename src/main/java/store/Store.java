package store;

import model.ItemImpl;

import java.util.Collection;

public interface Store<T extends ItemImpl> {
    boolean add(T item);
    Collection<T> find();
    boolean delete(int id);
}