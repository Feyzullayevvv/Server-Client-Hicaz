package com.example.serverclienthicaz.Server.ModelAnbar;

import java.io.Serializable;

public class Kreditor implements Serializable {
    private int nr;
    private String name;

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
