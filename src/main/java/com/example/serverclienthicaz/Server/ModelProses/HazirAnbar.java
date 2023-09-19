package com.example.serverclienthicaz.Server.ModelProses;

import java.io.Serializable;
import java.text.DecimalFormat;

public class HazirAnbar implements Serializable {
    int id;
    String mal;
    String vahid;
    double miqdar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
