package com.example.serverclienthicaz.Server.ModelAnbar;

import java.io.Serializable;
import java.text.DecimalFormat;

public class AnbarItem implements Serializable {
    private int Nr;
    private String Mal;
    private String Vahid;
    private double Ceki;
    private double mebleg;

    public int getNr() {
        return Nr;
    }

    public void setNr(int nr) {
        Nr = nr;
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

    public double getMebleg() {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String formattedValue = decimalFormat.format(mebleg);
        return Double.parseDouble(formattedValue);
    }

    public void setMebleg(double mebleg) {
        this.mebleg = mebleg;
    }

}
