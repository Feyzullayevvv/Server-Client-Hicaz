package com.example.serverclienthicaz.Server.ModelProses;

import java.io.Serializable;
import java.text.DecimalFormat;

public class EmeliyyatItem implements Serializable {
    int nr;
    String mal;
    String vahid;
    double miqdar;
    int receptNr;

    int emeliyyatNr;

    public int getEmeliyyatNr() {
        return emeliyyatNr;
    }

    public void setEmeliyyatNr(int emeliyyatNr) {
        this.emeliyyatNr = emeliyyatNr;
    }

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

    public void setMiqdar(double miqdar) {
        this.miqdar = miqdar;
    }

    public int getReceptNr() {
        return receptNr;
    }

    public void setReceptNr(int receptNr) {
        this.receptNr = receptNr;
    }
}

