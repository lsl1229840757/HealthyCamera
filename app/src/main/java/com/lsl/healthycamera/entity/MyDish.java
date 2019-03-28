package com.lsl.healthycamera.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class MyDish {
    @Id(autoincrement = true)
    private Long id;

    private String name;

    private Float calorie;

    private Date date;

    private String username;

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCalorie(Float calorie) {
        this.calorie = calorie;
    }

    public Float getCalorie() {
        return this.calorie;
    }

    @Generated(hash = 251637970)
    public MyDish(Long id, String name, Float calorie, Date date, String username) {
        this.id = id;
        this.name = name;
        this.calorie = calorie;
        this.date = date;
        this.username = username;
    }

    @Generated(hash = 541252587)
    public MyDish() {
    }
}
