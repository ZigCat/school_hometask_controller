package com.github.zigcat.ormlite.models;

import com.github.zigcat.Modelable;
import com.github.zigcat.ormlite.controllers.Control;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "school")
public class School implements Modelable {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String name;

    @DatabaseField
    private String level;

    @DatabaseField
    private String direction;

    public static Control<School> schoolControl = new Control<>(School.class);

    public School() {
    }

    public School(int id, String name, String level, String direction) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.direction = direction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubparam() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "School{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", level='" + level + '\'' +
                ", direction='" + direction + '\'' +
                '}';
    }
}
