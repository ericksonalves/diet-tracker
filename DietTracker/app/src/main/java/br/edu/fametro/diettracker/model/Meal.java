package br.edu.fametro.diettracker.model;

/* Classe que representa cada refeição do usuário */

public class Meal {

    private String login;
    private String name;
    private String amount;
    private int calories;
    private String date;
    private String time;

    public Meal(String login, String name, String amount, int calories, String date, String time) {
        setLogin(login);
        setName(name);
        setAmount(amount);
        setCalories(calories);
        setDate(date);
        setTime(time);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    @Override
    public String toString() {
        return name;
    }
}
