package com.example.denemeApi.model;

import java.io.Serializable;

public class MyModel {
    private int id;
    private String name;
    private String code;

    public MyModel(){

    }
    public MyModel(String code, String name, int id) {
        this.code = code;
        this.name = name;
        this.id = id;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
