package com.uniavan.todolist.model;

public class Task {
    private int id;
    private String description;
    private int priority;

    private Enum<TaskStatus> status;

    public Task() {
        this.status = TaskStatus.PENDING;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Enum<TaskStatus> getStatus() {
        return status;
    }

    public void setStatus(Enum<TaskStatus> status) {
        this.status = status;
    }
}
