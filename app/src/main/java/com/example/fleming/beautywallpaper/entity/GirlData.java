package com.example.fleming.beautywallpaper.entity;

import java.util.ArrayList;

/**
 * GirlData
 * Created by fleming on 17-4-10.
 */

public class GirlData {

    private boolean error;
    private ArrayList<Girl> results;

    public ArrayList<Girl> getResults() {
        return results;
    }

    public void setResults(ArrayList<Girl> results) {
        this.results = results;
    }

    public boolean isError() {
        return error;
    }
}