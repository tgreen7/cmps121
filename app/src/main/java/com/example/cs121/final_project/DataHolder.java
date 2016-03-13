package com.example.cs121.final_project;

import android.app.Application;

/**
 * Created by Halsifer on 3/12/16.
 */
public class DataHolder {
    private String data;
    private Double IBU, OG, FG, Color;

    public Double getIBU() {return IBU;}
    public void setIBU(Double IBU) {this.IBU = IBU;}

    public Double getOG() {return OG;}
    public void setOG(Double OG) {this.OG = OG;}

    public Double getFG() {return FG;}
    public void setFG(Double FG) {this.FG = FG;}

    public Double getColor() {return Color;}
    public void setColor(Double Color) {this.Color = Color;}

    private static final DataHolder holder = new DataHolder();
    public static DataHolder getInstance() {return holder;}
}