package com.example.alarmaad.Model;

public class Todo {

    private String task;
    private String priority;
    private String Dateortime;

    public Todo() {
    }

    public Todo(String task, String priority, String Dateortime) {
        this.task = task;
        this.priority = priority;
        this.Dateortime = Dateortime;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDateortime() {
        return Dateortime;
    }

    public void setDateortime(String dateortime) {
        Dateortime = dateortime;
    }
}
