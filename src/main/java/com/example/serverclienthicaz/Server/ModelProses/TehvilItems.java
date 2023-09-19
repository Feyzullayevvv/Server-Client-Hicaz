package com.example.serverclienthicaz.Server.ModelProses;

import java.io.Serializable;
import java.text.DecimalFormat;

public class TehvilItems implements Serializable{
    int Nr;
    int MalNr;
    String Mal;
    double Ceki;
    int TehvilNr;

    public int getNr() {
        return Nr;
    }

    public void setNr(int nr) {
        Nr = nr;
    }

    public int getMalNr() {
        return MalNr;
    }

    public void setMalNr(int malNr) {
        MalNr = malNr;
    }

    public String getMal() {
        return Mal;
    }

    public void setMal(String mal) {
        Mal = mal;
    }

    public double getCeki() {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String formattedValue = decimalFormat.format(Ceki);
        return Double.parseDouble(formattedValue);
    }

    public void setCeki(double ceki) {
        Ceki = ceki;
    }

    public int getTehvilNr() {
        return TehvilNr;
    }

    public void setTehvilNr(int tehvilNr) {
        TehvilNr = tehvilNr;
    }
}
