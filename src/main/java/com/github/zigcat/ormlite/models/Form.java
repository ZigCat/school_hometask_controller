package com.github.zigcat.ormlite.models;

import com.github.zigcat.Modelable;
import com.github.zigcat.ormlite.controllers.Control;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "class")
public class Form implements Modelable {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String number;

    @DatabaseField
    private String liter;

    @DatabaseField
    private String direction;

    public static Control<Form> formControl = new Control<>(Form.class);

    public Form() {
    }

    public Form(int id, String number, String liter, String direction) {
        this.id = id;
        this.number = number;
        this.liter = liter;
        this.direction = direction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSubparam() {
        return liter;
    }

    public void setLiter(String liter) {
        this.liter = liter;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
