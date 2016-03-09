package com.example.cs121.final_project;

import java.io.Serializable;

/**
 * Created by Taoh on 3/7/2016.
 */
public class Item implements Serializable{
    Integer ing_type, time;
    String name, type, str1, str2, str3;
    Float flt1, flt2, weight;
    Boolean wort, dry;
    Item (Integer ing_type, Integer time, String name, String type, String str1, String str2,
          String str3, Float flt1, Float flt2, Float weight, Boolean wort, Boolean dry){

        this.ing_type   = ing_type;
        this.time       = time;
        this.name       = name;
        this.type       = type;
        this.str1       = str1;
        this.str2       = str2;
        this.str3       = str3;
        this.flt1       = flt1;
        this.flt2       = flt2;
        this.weight     = weight;
        this.wort       = wort;
        this.dry        = dry;
    }
}
