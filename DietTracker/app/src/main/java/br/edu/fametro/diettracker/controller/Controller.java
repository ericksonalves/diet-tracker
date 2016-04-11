package br.edu.fametro.diettracker.controller;

/*
 * Controlador da aplicação
 * */

import android.content.Context;

import br.edu.fametro.diettracker.database.DatabaseHelper;
import br.edu.fametro.diettracker.model.Meal;
import br.edu.fametro.diettracker.util.Utils;

public class Controller {

    /* Instância única do controlador */
    private static Controller mInstance = new Controller();
    /* Constante para a quantidade máxima de calorias a serem ingeridas no dia */
    private int mTotalCalories = 500;
    /* Classe que representa o manipulador do banco de dados */
    private DatabaseHelper mDbHelper;

    /* Construtor interno do controlador */
    private Controller() {
    }

    /* Método para prover a instância para uma outra classe */
    public static synchronized Controller getInstance() {
        return mInstance;
    }

    /* Método para adicionar refeição na base de dados */
    public void insertMealToDatabase(Context context, Meal meal) {
        /* Inicialização do manipulador do banco de dados */
        mDbHelper = new DatabaseHelper(context);
        /* Inserção da refeição no banco de dados */
        mDbHelper.insertMeal(meal);
    }

    public int getAlreadyConsumedCalories(Context context) {
        mDbHelper = new DatabaseHelper(context);
        int calories = mDbHelper.getDailyCalories(Utils.getCurrentDateTime(true));
        return calories;
    }

    public int getTotalCalories() {
        return mTotalCalories;
    }

    public void setTotalCalories(int calories) {
        mTotalCalories = calories;
    }

}
