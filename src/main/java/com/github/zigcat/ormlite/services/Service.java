package com.github.zigcat.ormlite.services;

import com.github.zigcat.Modelable;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;


public class Service<T extends Modelable> {
    public Service(){}

    public List<T> listAll(Dao<T, Integer> dao) throws SQLException{
        return dao.queryForAll();
    }

    protected T getById(Dao<T, Integer> dao, int id) throws SQLException{
        for(T t: listAll(dao)){
            if(t.getId() == id){
                return t;
            }
        }
        return null;
    }
}
