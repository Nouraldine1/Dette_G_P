package com.nour.core.repositories;

import java.util.List;

public interface Repository <T>{
    boolean  insert(T t);
    List<T> select();
}
