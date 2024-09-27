package com.nour.core.repositories.impl;

import java.util.ArrayList;
import java.util.List;

import com.nour.core.repositories.Repository;

public class RepositoryListImpl<T> implements Repository<T> {

    protected List<T> list = new ArrayList<>();

    @Override
    public boolean insert(T t) {
        return this.list.add(t);
    }

    @Override
    public List<T> select() {
        return this.list;
    }
}