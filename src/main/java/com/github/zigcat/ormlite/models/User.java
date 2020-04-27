package com.github.zigcat.ormlite.models;

import com.github.zigcat.Modelable;
import com.github.zigcat.ormlite.controllers.Control;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@DatabaseTable(tableName = "user")
public class User implements Modelable {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String name;

    @DatabaseField
    private String surname;

    @DatabaseField
    private String email;

    @DatabaseField
    private String password;

    @DatabaseField(dataType = DataType.ENUM_STRING)
    private Role role;

    @DatabaseField
    private String birthday;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private School school;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Form form;

    @DatabaseField
    private boolean isAccepted;

    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("YYYY MM dd");
    public static Control<User> userControl = new Control<>(User.class);

    public User() {
    }

    public User(int id, String name, String surname, String email, String password, Role role, String birthday, School school, Form form, boolean isAccepted) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.birthday = birthday;
        this.school = school;
        this.form = form;
        this.isAccepted = isAccepted;
    }

    public User(int id, String name, String surname, String email, String password, Role role, LocalDate birth, School school, Form form, boolean isAccepted) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.birthday = birth.format(dateTimeFormatter);
        this.school = school;
        this.form = form;
        this.isAccepted = isAccepted;
    }

    public User(int id, String name, String surname, String email, String password, Role role, String birthday) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.birthday = birthday;
        this.isAccepted = false;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubparam() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", role=" + role +
                '}';
    }
}
