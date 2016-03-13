package com.example.cs121.final_project;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Taoh on 3/7/2016.
 */
public class Item implements Serializable {
    public Integer ing_type, time;
    public String name;
    public String type;
    public String str1;
    public String str2;
    public String str3;
    public Double dbl1, dbl2, weight;
    public Boolean wort, dry;
    public Item(Integer ing_type, Integer time, String name, String type, String str1, String str2,
                String str3, Double dbl1, Double dbl2, Double weight, Boolean wort, Boolean dry){

        this.ing_type   = ing_type;
        this.time       = time;
        this.name       = name;
        this.type       = type;
        this.str1       = str1;
        this.str2       = str2;
        this.str3       = str3;
        this.dbl1       = dbl1;
        this.dbl2       = dbl2;
        this.weight     = weight;
        this.wort       = wort;
        this.dry        = dry;
    }
}
