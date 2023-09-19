package com.example.serverclienthicaz.Server.ModelProses;

import java.io.Serializable;
import java.text.DecimalFormat;

public class VirtualAnbar implements Serializable {
    int nr;
    int malNr;

    String mal;
    String vahid;
    double miqdar;

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public String getMal() {
        return mal;
    }

    public void setMal(String mal) {
        this.mal = mal;
    }

    public String getVahid() {
        return vahid;
    }

    public void setVahid(String vahid) {
        this.vahid = vahid;
    }

    public double getMiqdar() {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String formattedValue = decimalFormat.format(miqdar);
        return Double.parseDouble(formattedValue);
    }

    public void setCeki(double miqdar) {
        this.miqdar = miqdar;
    }
    public int getMalNr() {
        return malNr;
    }

    public void setMalNr(int malNr) {
        this.malNr = malNr;
    }
}
