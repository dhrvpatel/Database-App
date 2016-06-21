package com.example.dpatel3756.studentdb;
/**
 * Created by Dpatel3756 on 6/17/2016.
 */


public class Student {

    //variable listing
    private long _id;
    private String name;
    private String mark;

    //Student class with all getter and setter methods which will help us to maintain single Student as an object
    public Student() {    }

    public void setId(long _id) {
        this._id = _id;
    }
    public long getId() {
        return _id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
    public String getMark() {
        return mark;
    }
}