package org.example;

public class Command {

    private Action action;
    private String data;


    public Command(Action action) {
        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    public String getData() {
        return data;
    }

    public Command(Action action, String data) {
        this.action = action;
        this.data = data;


    }
}
