package com.example.boris.showon.model;

/**
 * Created by Boris on 22-Jul-17.
 */

public class Season {
    private Long id;
    private int number;

    public Season() {}
    public Season(long id, int number) {
        this.id = id;
        this.number = number;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
