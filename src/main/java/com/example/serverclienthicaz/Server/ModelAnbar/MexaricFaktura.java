package com.example.serverclienthicaz.Server.ModelAnbar;

import java.io.Serializable;
import java.text.DecimalFormat;

public class MexaricFaktura implements Serializable {
    private int Nr;
    private int MalNr;
    private String Mal;
    private String Vahid;
    private double Ceki;
    private int MexaricNr;

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

    public String getVahid() {
        return Vahid;
    }

    public void setVahid(String vahid) {
        Vahid = vahid;
    }

    public double getCeki() {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String formattedValue = decimalFormat.format(Ceki);
        return Double.parseDouble(formattedValue);
    }

    public void setCeki(double ceki) {
        Ceki = ceki;
    }

    public int getMexaricNr() {
        return MexaricNr;
    }

    public void setMexaricNr(int mexaricNr) {
        MexaricNr = mexaricNr;
    }
}
