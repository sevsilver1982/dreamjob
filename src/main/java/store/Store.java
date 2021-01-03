package store;

import model.Item;

import java.util.Collection;
import java.util.function.Predicate;

public interface Store<T extends Item> {

    boolean add(T item);

    Collection<T> findAll();

    Collection<T> find(Predicate<T> predicate);

    T findById(int id);

    boolean delete(int id);

}