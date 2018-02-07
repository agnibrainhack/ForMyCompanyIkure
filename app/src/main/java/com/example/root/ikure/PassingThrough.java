package com.example.root.ikure;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hp on 06-02-2018.
 */

public class PassingThrough implements Serializable {

    ArrayList<String> diab_fasting;
    ArrayList<String> diab_fasting_date;
    ArrayList<String> diab_pp;
    ArrayList<String> diab_pp_date;
    ArrayList<String> diab_random;
    ArrayList<String> diab_random_date;

    public PassingThrough(ArrayList<String> diab_fasting,
                          ArrayList<String> diab_fasting_date,
                          ArrayList<String> diab_pp,
                          ArrayList<String> diab_pp_date,
                          ArrayList<String> diab_random,
                          ArrayList<String> diab_random_date) {
        this.diab_fasting = diab_fasting;
        this.diab_fasting_date = diab_fasting_date;
        this.diab_pp = diab_pp;
        this.diab_pp_date = diab_pp_date;
        this.diab_random = diab_random;
        this.diab_random_date = diab_random_date;
    }
}
