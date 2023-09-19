package com.example.serverclienthicaz.Server.ModelProses;

import java.io.Serializable;

public class Tehvil implements Serializable {
    int nr;
    String Date;
    String tehvilci;
    int mexaricNr;

    public int getMexaricNr() {
        return mexaricNr;
    }

    public void setMexaricNr(int mexaricNr) {
        this.mexaricNr = mexaricNr;
    }

    public String getTehvilci() {
        return tehvilci;
    }

    public void setTehvilci(String tehvilci) {
        this.tehvilci = tehvilci;
    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
