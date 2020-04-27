package com.github.zigcat.ormlite.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.zigcat.DatabaseConfiguration;
import com.github.zigcat.Modelable;
import com.github.zigcat.exceptions.IncorrectRequestException;
import com.github.zigcat.ormlite.models.User;
import com.github.zigcat.ormlite.services.Pagination;
import com.github.zigcat.ormlite.services.Security;
import com.github.zigcat.ormlite.services.Service;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Control<T extends Modelable> {
    private Dao<T, Integer> dao;
    private Service<T> service;
    private Class<T> valueClass;
    private Pagination pagination;
    private Logger l;

    public Control(Class<T> valueClass) {
        this.service = new Service<>();
        this.valueClass = valueClass;
        this.pagination = new Pagination();
        this.l = LoggerFactory.getLogger(Control.class);
        initDao(getValueClass());
    }

    public Dao<T, Integer> getDao() {
        return dao;
    }

    public Service<T> getService() {
        return service;
    }

    private Class<T> getValueClass() {
        return valueClass;
    }

    private void initDao(Class<T> valueClass){
        try {
            this.dao = DaoManager.createDao(DatabaseConfiguration.source, valueClass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getAll(Context ctx, ObjectMapper om){
        l.info("@GETTING MODELS INFO@");
        Map map = ctx.queryParamMap();
        long page = 0;
        List<T> ts;
        T t;
        try{
            if(map.containsKey("page")){
                page = Long.parseLong(ctx.queryParam("page"));
            }
            if(map.containsKey("searchType")){
                switch (ctx.queryParam("searchType")){
                    case "1":
                        l.info("GET models by alphabet");
//                        ts = getService().listAll(getDao());
//                        for(int i=0;i<ts.size();i++){
//                            for(int j=0;j<ts.size();j++){
//                                if(ts.get(i).getName().charAt(0) < ts.get(j).getName().charAt(0)){
//                                    t = ts.get(i);
//                                    ts.remove(i);
//                                    ts.add(i, ts.get(j));
//                                    ts.add(j, t);
//                                } else {
//                                    t = ts.get(j);
//                                    ts.remove(j);
//                                    ts.add(j, ts.get(i));
//                                    ts.add(i, t);
//                                }
//                            }
//                        }
//                        ctx.status(200);
//                        ctx.result(om.writeValueAsString(ts));
                        break;
                    case "2":
                        l.info("GET models info by parameters");
                        ArrayList<T> foundT = new ArrayList<>();
                        String name = "", surname = "";
                        boolean isUser = false;
                        if(map.containsKey("name")){
                            name = ctx.queryParam("name");
                        } else if(map.containsKey("surname") && getValueClass().equals(User.class)){
                            surname = ctx.queryParam("surname");
                            isUser = true;
                        }
                        for(T t1: getService().listAll(getDao())){
                            if(isUser && t1.getSubparam().equalsIgnoreCase(surname) && t1.getName().equalsIgnoreCase(name)){
                                foundT.add(t1);
                            } else if(t1.getName().equalsIgnoreCase(name)){
                                foundT.add(t1);
                            }
                        }
                        ctx.status(200);
                        ctx.result(om.writeValueAsString(foundT));
                        break;
                    case "3":
                        l.info("GET model by id");
                        int id;
                        if(map.containsKey("id")){
                            id = Integer.parseInt(ctx.queryParam("id"));
                        } else {
                            throw new NullPointerException();
                        }
                        T t2 = getService().getById(getDao(), id);
                        ctx.status(200);
                        ctx.result(om.writeValueAsString(t2));
                        break;
                }
            } else {
                l.info("GET models info simplify");
                ctx.status(200);
                ctx.result(om.writeValueAsString(pagination.pagination(getDao(), page, 10)));
            }
        } catch(NullPointerException e){
            ctx.status(400);
            ctx.result(Security.queryParamMessage);
            l.warn(Security.queryParamMessage);
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
            ctx.status(500);
            ctx.result(Security.serverErrorMessage);
            l.warn(Security.serverErrorMessage);
        }
        l.info("@QUERY DONE@");
    }

    public void create(Context ctx, ObjectMapper om){
        l.info("@CREATING MODEL@");
        try {
            T t = om.readValue(ctx.body(), getValueClass());
            getDao().create(t);
            ctx.status(201);
            ctx.result(om.writeValueAsString(t));
        } catch (JsonProcessingException | SQLException e) {
            e.printStackTrace();
            l.warn(Security.serverErrorMessage);
            ctx.status(500);
            ctx.result(Security.serverErrorMessage);
        } catch (IncorrectRequestException e){
            e.printStackTrace();
            l.warn(Security.badRequestMessage);
            ctx.status(400);
            ctx.result(Security.badRequestMessage);
        }
        l.info("@QUERY DONE@");
    }
}
