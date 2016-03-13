package com.example.cs121.final_project;

import java.io.Serializable;

/**
 * Created by Taoh on 3/7/2016.
 */
public class MetaInfo implements Serializable {
    public Integer ing_type, time;
    public String name;
    public String type;
    public String str1;
    public String str2;
    public String str3;
    public Float flt1, flt2, weight;
    public Boolean wort, dry;
    public MetaInfo (String name, Double effic, ){

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
