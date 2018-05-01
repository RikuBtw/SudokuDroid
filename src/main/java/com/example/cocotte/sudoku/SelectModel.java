package com.example.cocotte.sudoku;

/**
 * Created by Cocotte on 25/04/2018.
 */

public class SelectModel {

    String name;
    String level;
    String percentage;
    String line;

    public SelectModel(String name, String level, String percentage, String line) {
        this.name=name;
        this.level=level;
        this.percentage=percentage;
        this.line = line;
    }

    public String getName() {
        return name;
    }

    public String getLevel() {
        return level;
    }

    public String getPercentage() {
        return percentage;
    }

    public String getLine() {
        return line;
    }
}
