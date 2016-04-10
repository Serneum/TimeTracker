package com.example.db;

import java.sql.ResultSet;

public interface Dao<T extends Persistent> {
    public void createTableIfNeeded();

    public void insert(Persistent p);

    public void update(Persistent p);

    public void delete(Persistent p);

    public T restore(ResultSet rs);
}
