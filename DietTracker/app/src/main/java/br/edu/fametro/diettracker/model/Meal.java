package br.edu.fametro.diettracker.model;

/* Classe que representa cada refeição do usuário */

public class Meal {

    private String name;
    private int calories;
    private String date;
    private String time;

    public Meal(String name, int calories, String date, String time) {
        setName(name);
        setCalories(calories);
        setDate(date);
        setTime(time);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
