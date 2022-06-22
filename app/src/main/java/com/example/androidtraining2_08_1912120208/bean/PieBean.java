package com.example.androidtraining2_08_1912120208.bean;

public class PieBean {
    private String salaries;
    private int percentage;

    public PieBean(String salaries, int percentage) {
        this.salaries = salaries;
        this.percentage = percentage;
    }

    public String getSalaries() {
        return salaries;
    }

    public void setSalaries(String salaries) {
        this.salaries = salaries;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
}
