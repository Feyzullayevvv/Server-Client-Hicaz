package com.example.serverclienthicaz.Server.ModelAnbar;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Medaxil implements Serializable {
    private int Nr;
    private String Tarix;
    private String Kreditor;
    private double Mebleg;


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

    public String getKreditor() {
        return Kreditor;
    }

    public void setKreditor(String kreditor) {
        Kreditor = kreditor;
    }

    public double getMebleg() {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String formattedValue = decimalFormat.format(Mebleg);
        return Double.parseDouble(formattedValue);

    }

    public void setMebleg(double mebleg) {
        Mebleg = mebleg;
    }

}
