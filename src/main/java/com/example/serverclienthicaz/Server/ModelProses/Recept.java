package com.example.serverclienthicaz.Server.ModelProses;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Recept implements Serializable {
    int nr;
    String name;
    double itki;
    double qaliq;

    double sonQaliq;

    public double getSonQaliq() {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String formattedValue = decimalFormat.format(sonQaliq);
        return Double.parseDouble(formattedValue);
    }

    public void setSonQaliq(double sonQaliq) {
        this.sonQaliq = sonQaliq;
    }

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

    public double getItki() {
        return itki;
    }

    public void setItki(double itki) {
        this.itki = itki;
    }

    public double getQaliq() {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String formattedValue = decimalFormat.format(qaliq);
        return Double.parseDouble(formattedValue);
    }

    public void setQaliq(double qaliq) {
        this.qaliq = qaliq;
    }
}
