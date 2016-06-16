package com.bwidlarz.todolist.Database;

import java.util.List;

/**
 * Created by Dell on 2016-06-05.
 */
public interface Dao<T> {
    long save(T type);
    void update(T type);
    void delete(T type);
    T get(long id);
    List<T> getAll();
}
