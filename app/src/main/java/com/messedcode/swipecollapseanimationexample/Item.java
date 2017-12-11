package com.messedcode.swipecollapseanimationexample;

public class Item {

    public String name;
    public Status status;

    public Item(String name) {
        this.name = name;
        this.status = Status.ACTIVE;
    }

    public enum Status {
        ACTIVE,
        CHECKED,
        DELETED
    }

}
