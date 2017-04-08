package com.hai.gui.model;

/**
 * Created by mrsfy on 05-Apr-17.
 */
public class ClueDataModel {

    private int clueNum;
    private int clueStart;
    private String value;
    private int clueEnd;

    public ClueDataModel() {
    }

    public int getClueNum() {
        return clueNum;
    }

    public void setClueNum(int clueNum) {
        this.clueNum = clueNum;
    }

    public int getClueStart() {
        return clueStart;
    }

    public void setClueStart(int clueStart) {
        this.clueStart = clueStart;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getClueEnd() {
        return clueEnd;
    }

    public void setClueEnd(int clueEnd) {
        this.clueEnd = clueEnd;
    }

    @Override
    public String toString() {
        return "ClueDataModel{" +
                "clueNum=" + clueNum +
                ", clueStart=" + clueStart +
                ", value='" + value + '\'' +
                ", clueEnd=" + clueEnd +
                '}';
    }
}
