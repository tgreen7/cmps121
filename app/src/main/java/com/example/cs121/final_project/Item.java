package com.example.cs121.final_project;

/**
 * Created by Taoh on 3/7/2016.
 */
public class Item {
    String ing_type;
    String name;
    String type;
    Float color;
    Float potential;
    Float alpha;
    String lab;
    String form;
    String use;
    Boolean wort;
    Boolean dry;
    String time;
    Float weight;



    Item (String ing_type, String name,  String type, Float color,
          Float potential, Float alpha, String lab,
          String form, String use, Boolean wort, Boolean dry,
          String time, Float weight){

        this.ing_type   = ing_type;
        this.name       = name;
        this.type       = type;
        this.color      = color;
        this.potential  = potential;
        this.alpha      = alpha;
        this.lab        = lab;
        this.form       = form;
        this.use        = use;
        this.wort       = wort;
        this.dry        = dry;
        this.time       = time;
        this.weight     = weight;

    }
}
