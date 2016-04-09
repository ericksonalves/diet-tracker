package br.edu.fametro.diettracker.controller;

/*
 * Controlador da aplicação
 * */

import br.edu.fametro.diettracker.database.DatabaseHelper;

public class Controller {

    /* Instância única do controlador */
    private static Controller mInstance = new Controller();
    /* Constante para a quantidade máxima de calorias a serem ingeridas no dia */
    private int mTotalCalories = 500;

    /* Construtor interno do controlador */
    private Controller() {
    }

    /* Método para prover a instância para uma outra classe */
    public static synchronized Controller getInstance() {
        return mInstance;
    }

    public int getTotalCalories() {
        return mTotalCalories;
    }

    public void setTotalCalories(int calories) {
        mTotalCalories = calories;
    }

}
