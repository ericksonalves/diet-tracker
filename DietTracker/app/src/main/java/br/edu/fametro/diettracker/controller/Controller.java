package br.edu.fametro.diettracker.controller;

/*
 * Controlador da aplicação
 * */

import android.content.Context;

import br.edu.fametro.diettracker.database.DatabaseHelper;
import br.edu.fametro.diettracker.model.Meal;
import br.edu.fametro.diettracker.model.User;
import br.edu.fametro.diettracker.util.Utils;

public class Controller {

    /* Instância única do controlador */
    private static Controller mInstance = new Controller();
    /* Classe que representa o manipulador do banco de dados */
    private DatabaseHelper mDbHelper;
    /* Classe que representa o usuário */
    private User mUser;

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

    /* Método para adicionar refeição na base de dados */
    public void insertUserToDatabase(Context context, User user) {
        /* Inicialização do manipulador do banco de dados */
        mDbHelper = new DatabaseHelper(context);
        /* Inserção do usuário no banco de dados */
        mDbHelper.insertUser(user);
    }

    public User getUser() {
        return mUser;
    }

    public User getUser(Context context, String login, String password) {
        /* Inicialização do manipulador do banco de dados */
        mDbHelper = new DatabaseHelper(context);
        /* Obtenção dos dados do banco de dados */
        User user = mDbHelper.getUserByLoginData(login, password);
        return user;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public int getAlreadyConsumedCalories(Context context) {
        mDbHelper = new DatabaseHelper(context);
        return mDbHelper.getDailyCalories(Utils.getCurrentDateTime(true));
    }

    public int getTotalCalories() {
        return mUser.getCurrentDietCalories();
    }

}
