package com.example.cs121.final_project.Recipes;

import java.io.Serializable;

/**
 * Created by Taoh on 3/7/2016.
 */
public class MetaInfo implements Serializable {
    public String name;
    public Double effic;
    public String type;
    public String style;
    public Double boilTime;
    public Double batch;
    public MetaInfo (String name, Double effic, String type, String style, Double boilTime, Double batch ){
        this.name = name;
        this.effic = effic;
        this.type = type;
        this.style = style;
        this.boilTime = boilTime;
        this.batch = batch;
    }
}
