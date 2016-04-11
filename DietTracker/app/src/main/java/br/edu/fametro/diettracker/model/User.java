package br.edu.fametro.diettracker.model;

public class User {

    private int age;
    private int currentDietCalories;
    private double height;
    private double weight;
    private String gender;
    private String login;
    private String name;
    private String password;

    public User(int age, int currentDietCalories, double height, double weight, String gender, String login, String name, String
            password) {
        setAge(age);
        setCurrentDietCalories(currentDietCalories);
        setHeight(height);
        setWeight(weight);
        setGender(gender);
        setLogin(login);
        setName(name);
        setPassword(password);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getCurrentDietCalories() {
        return currentDietCalories;
    }

    public void setCurrentDietCalories(int currentDietCalories) {
        this.currentDietCalories = currentDietCalories;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
