package com.example.sanskriti.todoapp;

public class Todo {

    private String name;
    private String description;
    private long id;
    private String date;

    public Todo(String name, String description, String date, String time) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.time = time;
    }

    public Todo(long id) {
        this.id = id;

    }

    public String getDate() {

        return date;
    }

    public Todo(String name, String description, long id, String date, String time) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.date = date;
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Todo(String name) {

        this.name = name;
    }

    private String time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Todo(String name, String description, long id) {

        this.name = name;
        this.description = description;
        this.id = id;
    }
}
