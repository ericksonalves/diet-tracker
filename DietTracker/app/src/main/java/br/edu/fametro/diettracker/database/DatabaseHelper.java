package br.edu.fametro.diettracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import br.edu.fametro.diettracker.model.Meal;

/*
 * Classe para auxiliar a manipulação do banco de dados
 * */

public class DatabaseHelper extends SQLiteOpenHelper {

    /* Versão do banco de dados */
    public static final int DATABASE_VERSION = 1;
    /* Nome do banco de dados */
    public static final String DATABASE_NAME = "DietTracker.db";
    /* Tipos usados na consulta SQL */
    private static final String COMMA_SEPARATOR = ",";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String INTEGER_PRIMARY_KEY_TYPE = " INTEGER PRIMARY KEY";
    private static final String TEXT_TYPE = " TEXT";
    /* Consultas SQL */
    private static final String SQL_CREATE_MEALS =
            "CREATE TABLE " + MealTable.TABLE_NAME +
                    " (" + MealTable._ID + INTEGER_PRIMARY_KEY_TYPE + COMMA_SEPARATOR +
                    MealTable.COLUMN_NAME_MEAL_NAME + TEXT_TYPE + COMMA_SEPARATOR +
                    MealTable.COLUMN_NAME_MEAL_CALORIES + INTEGER_TYPE + COMMA_SEPARATOR +
                    MealTable.COLUMN_NAME_MEAL_DATE + TEXT_TYPE + COMMA_SEPARATOR +
                    MealTable.COLUMN_NAME_MEAL_TIME + " )";
    private static final String SQL_DELETE_MEALS =
            "DROP TABLE IF EXISTS" + MealTable.TABLE_NAME;
    private static final String SELECT_BY_DATE_ARG = "date=?";

    /* Construtor do banco de dados */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /* Evento iniciado ao criar o banco */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_MEALS);
    }

    /* Evento iniciado ao atualizar o banco */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_MEALS);
        onCreate(db);
    }

    /* Inserir uma refeição no banco */
    public void insertMeal(Meal meal) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MealTable.COLUMN_NAME_MEAL_NAME, meal.getName());
        values.put(MealTable.COLUMN_NAME_MEAL_CALORIES, meal.getCalories());
        values.put(MealTable.COLUMN_NAME_MEAL_DATE, meal.getDate());
        values.put(MealTable.COLUMN_NAME_MEAL_TIME, meal.getTime());
        database.insert(MealTable.TABLE_NAME, null, values);
    }

    /* Obter o total de calorias consumidas no dia especificado */
    public int getDailyCalories(String date) {
        SQLiteDatabase database = getReadableDatabase();
        String[] args = {date};
        /* Consulta o banco */
        Cursor cursor = database.query(MealTable.TABLE_NAME, MealTable.PROJECTION,
                SELECT_BY_DATE_ARG, args,
                null, null, null);
        cursor.moveToFirst();
        /* Iteração sobre os itens obtidos do banco */
        int calories = 0;
        while (!cursor.isAfterLast()) {
            calories += cursor.getInt(cursor.getColumnIndex(MealTable.COLUMN_NAME_MEAL_CALORIES));
            cursor.moveToNext();
        }
        cursor.close();
        return calories;
    }

    /* Classe que representa a tabela de refeições */
    private static class MealTable implements BaseColumns {
        public static final String TABLE_NAME = "meals";
        public static final String COLUMN_NAME_MEAL_NAME = "name";
        public static final String COLUMN_NAME_MEAL_CALORIES = "calories";
        public static final String COLUMN_NAME_MEAL_DATE = "date";
        public static final String COLUMN_NAME_MEAL_TIME = "time";
        public static final String[] PROJECTION = {MealTable._ID,
                MealTable.COLUMN_NAME_MEAL_NAME,
                MealTable.COLUMN_NAME_MEAL_CALORIES,
                MealTable.COLUMN_NAME_MEAL_DATE,
                MealTable.COLUMN_NAME_MEAL_TIME};
    }
}
