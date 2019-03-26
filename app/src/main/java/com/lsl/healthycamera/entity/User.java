package com.lsl.healthycamera.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

@Entity
public class User {
    @Id(autoincrement=true)
    private Long id;
    @Unique
    private String name;
    private String username;
    private String password;
    private Double weight;
    private Double height;
    public Double getHeight() {
        return this.height;
    }
    public void setHeight(Double height) {
        this.height = height;
    }
    public Double getWeight() {
        return this.weight;
    }
    public void setWeight(Double weight) {
        this.weight = weight;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
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
    @Generated(hash = 2139527398)
    public User(Long id, String name, String username, String password,
            Double weight, Double height) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.weight = weight;
        this.height = height;
    }
    @Generated(hash = 586692638)
    public User() {
    }
}
