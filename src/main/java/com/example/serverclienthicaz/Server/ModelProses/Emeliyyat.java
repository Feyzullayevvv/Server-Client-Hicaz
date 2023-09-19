package com.example.serverclienthicaz.Server.ModelProses;

import java.io.Serializable;

public class Emeliyyat implements Serializable {
    int nr;
    String date;




    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
