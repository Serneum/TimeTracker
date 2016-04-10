package com.example.db;

public interface Dao {
    public void createTableIfNeeded();

    public void insert(Persistent p);

    public void update(Persistent p);

    public void delete(Persistent p);
}
