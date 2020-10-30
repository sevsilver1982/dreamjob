package store;

import model.ItemImpl;

import java.util.Collection;
import java.util.function.Predicate;

public interface Store<T extends ItemImpl> {
    boolean add(T item);
    Collection<T> findAll();
    Collection<T> find(Predicate<T> predicate);
    T findById(int id);
    boolean delete(int id);
}