package com.github.zigcat.ormlite.services;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

public class Pagination {
    public Pagination(){}

    public <T> List<T> pagination(Dao<T, Integer> dao, long page, long pageSize) throws SQLException {
        QueryBuilder<T, Integer> queryBuilder = dao.queryBuilder();
        queryBuilder.offset(page * pageSize).limit(pageSize);
        return dao.query(queryBuilder.prepare());
    }

}
