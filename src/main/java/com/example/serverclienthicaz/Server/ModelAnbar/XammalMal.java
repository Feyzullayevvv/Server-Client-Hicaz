package com.example.serverclienthicaz.Server.ModelAnbar;

import java.io.Serializable;
import java.text.DecimalFormat;

public class XammalMal implements Serializable {
    private int Nr;
    private String Ad;

    private String Vahid;

    private double OrtaGiymet;

    public double getOrtaGiymet() {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String formattedValue = decimalFormat.format(OrtaGiymet);
        return Double.parseDouble(formattedValue);
    }

    public void setOrtaGiymet(double ortaGiymet) {
        OrtaGiymet = ortaGiymet;
    }

    public int getNr() {
        return Nr;
    }

    public void setNr(int nr) {
        Nr = nr;
    }

    public String getAd() {
        return Ad;
    }

    public void setAd(String ad) {
        Ad = ad;
    }

    public String getVahid() {
        return Vahid;
    }

    public void setVahid(String vahid) {
        Vahid = vahid;
    }
}
