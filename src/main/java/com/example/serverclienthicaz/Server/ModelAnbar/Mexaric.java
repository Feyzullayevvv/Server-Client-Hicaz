package com.example.serverclienthicaz.Server.ModelAnbar;

import java.io.Serializable;

public class Mexaric implements Serializable {
    private int Nr;
    private String Tarix;
    private String TehvilAlan;



    public int getNr() {
        return Nr;
    }

    public void setNr(int nr) {
        Nr = nr;
    }

    public String getTarix() {
        return Tarix;
    }

    public void setTarix(String tarix) {
        Tarix = tarix;
    }

    public String getTehvilAlan() {
        return TehvilAlan;
    }

    public void setTehvilAlan(String tehvilAlan) {
        TehvilAlan = tehvilAlan;
    }






}
