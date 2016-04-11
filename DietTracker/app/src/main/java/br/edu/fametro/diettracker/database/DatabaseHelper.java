package br.edu.fametro.diettracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import br.edu.fametro.diettracker.model.Meal;
import br.edu.fametro.diettracker.model.User;

/*
 * Classe para auxiliar a manipulação do banco de dados
 * */

public class DatabaseHelper extends SQLiteOpenHelper {

    /* Versão do banco de dados */
    public static final int DATABASE_VERSION = 1;
    /* Nome do banco de dados */
    public static final String DATABASE_NAME = "DietTracker.db";
    /* Tipos usados na consulta SQL */
    private static final String COMMA_SEPARATOR = ", ";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String REAL_TYPE = " REAL";
    private static final String INTEGER_PRIMARY_KEY_TYPE = " INTEGER PRIMARY KEY";
    private static final String FOREIGN_KEY = " FOREIGN KEY";
    private static final String REFERENCES = " REFERENCES";
    private static final String TEXT_TYPE = " TEXT";
    /* Consultas SQL */
    private static final String SQL_CREATE_DIETS =
            "CREATE TABLE " + DietTable.TABLE_NAME +
                    " (" + DietTable._ID + INTEGER_PRIMARY_KEY_TYPE + COMMA_SEPARATOR +
                    DietTable.COLUMN_NAME_LOGIN + TEXT_TYPE + COMMA_SEPARATOR +
                    DietTable.COLUMN_NAME_CALORIES + INTEGER_TYPE + COMMA_SEPARATOR +
                    DietTable.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEPARATOR +
                    FOREIGN_KEY + "(" + DietTable.COLUMN_NAME_LOGIN + ")" + REFERENCES + " " +
                    UserTable.TABLE_NAME + "(" + UserTable.COLUMN_NAME_LOGIN + ")" + " )";
    private static final String SQL_CREATE_MEALS =
            "CREATE TABLE " + MealTable.TABLE_NAME +
                    " (" + MealTable._ID + INTEGER_PRIMARY_KEY_TYPE + COMMA_SEPARATOR +
                    MealTable.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEPARATOR +
                    MealTable.COLUMN_NAME_CALORIES + INTEGER_TYPE + COMMA_SEPARATOR +
                    MealTable.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEPARATOR +
                    MealTable.COLUMN_NAME_TIME + TEXT_TYPE + " )";
    private static final String SQL_CREATE_MEASUREMENTS =
            "CREATE TABLE " + MeasurementsTable.TABLE_NAME +
                    " (" + MeasurementsTable._ID + INTEGER_PRIMARY_KEY_TYPE + COMMA_SEPARATOR +
                    MeasurementsTable.COLUMN_NAME_LOGIN + TEXT_TYPE + COMMA_SEPARATOR +
                    MeasurementsTable.COLUMN_NAME_AGE + INTEGER_TYPE + COMMA_SEPARATOR +
                    MeasurementsTable.COLUMN_NAME_WEIGHT + REAL_TYPE + COMMA_SEPARATOR +
                    MeasurementsTable.COLUMN_NAME_HEIGHT + REAL_TYPE + COMMA_SEPARATOR +
                    MeasurementsTable.COLUMN_NAME_GENDER + TEXT_TYPE + COMMA_SEPARATOR +
                    FOREIGN_KEY + "(" + MeasurementsTable.COLUMN_NAME_LOGIN + ")" + REFERENCES + " " +
                    UserTable.TABLE_NAME + "(" + UserTable.COLUMN_NAME_LOGIN + ")" + " )";
    private static final String SQL_CREATE_USERS =
            "CREATE TABLE " + UserTable.TABLE_NAME +
                    " (" + UserTable._ID + INTEGER_PRIMARY_KEY_TYPE + COMMA_SEPARATOR +
                    UserTable.COLUMN_NAME_LOGIN + TEXT_TYPE + COMMA_SEPARATOR +
                    UserTable.COLUMN_NAME_PASSWORD + TEXT_TYPE + COMMA_SEPARATOR +
                    UserTable.COLUMN_NAME_NAME + TEXT_TYPE + " )";
    private static final String SQL_DELETE_DIETS =
            "DROP TABLE IF EXISTS" + DietTable.TABLE_NAME;
    private static final String SQL_DELETE_MEALS =
            "DROP TABLE IF EXISTS" + MealTable.TABLE_NAME;
    private static final String SQL_DELETE_MEASUREMENTS =
            "DROP TABLE IF EXISTS" + MeasurementsTable.TABLE_NAME;
    private static final String SQL_DELETE_USERS =
            "DROP TABLE IF EXISTS" + UserTable.TABLE_NAME;
    private static final String SQL_SELECT_BY_USER_LOGIN_INFO = "SELECT users.login, users.password, users.name, " +
            "measurements.age, measurements.weight, measurements.height, measurements.gender, diets.calories FROM " +
            "users, " +
            "measurements, diets WHERE users.login='%1$s' AND password='%2$s' AND users.login = measurements" +
            ".user_login AND users.login = diets.login";
    private static final String SQL_INSERT_MOCK_DIET = "INSERT INTO diets VALUES (null, 'bruce', 1000, '10/04/2016');";
    private static final String SQL_INSERT_MOCK_MEASUREMENTS = "INSERT INTO measurements VALUES (null, 'bruce', " +
            "17, 70, 1.7, 'Masculino');";
    private static final String SQL_INSERT_MOCK_USER = "INSERT INTO users VALUES (null, 'bruce', 'wayne', " +
            "'Bruce Wayne')";
    private static final String SELECT_BY_DATE_ARG = "date=?";

    /* Construtor do banco de dados */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /* Evento iniciado ao criar o banco */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_MEALS);
        db.execSQL(SQL_CREATE_USERS);
        db.execSQL(SQL_CREATE_DIETS);
        db.execSQL(SQL_CREATE_MEASUREMENTS);
        db.execSQL(SQL_INSERT_MOCK_USER);
        db.execSQL(SQL_INSERT_MOCK_DIET);
        db.execSQL(SQL_INSERT_MOCK_MEASUREMENTS);
    }

    /* Evento iniciado ao atualizar o banco */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_MEALS);
        db.execSQL(SQL_DELETE_MEASUREMENTS);
        db.execSQL(SQL_DELETE_DIETS);
        db.execSQL(SQL_DELETE_USERS);
        onCreate(db);
    }

    /* Inserir uma refeição no banco */
    public void insertMeal(Meal meal) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MealTable.COLUMN_NAME_NAME, meal.getName());
        values.put(MealTable.COLUMN_NAME_CALORIES, meal.getCalories());
        values.put(MealTable.COLUMN_NAME_DATE, meal.getDate());
        values.put(MealTable.COLUMN_NAME_TIME, meal.getTime());
        database.insert(MealTable.TABLE_NAME, null, values);
    }

    /* Inserir um usuário no banco */
    public void insertUser(User user) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues measurementsValues = new ContentValues();
        ContentValues userValues = new ContentValues();
        userValues.put(UserTable.COLUMN_NAME_LOGIN, user.getLogin());
        userValues.put(UserTable.COLUMN_NAME_PASSWORD, user.getPassword());
        userValues.put(UserTable.COLUMN_NAME_NAME, user.getName());
        database.insert(UserTable.TABLE_NAME, null, userValues);
        measurementsValues.put(MeasurementsTable.COLUMN_NAME_AGE, user.getAge());
        measurementsValues.put(MeasurementsTable.COLUMN_NAME_GENDER, user.getGender());
        measurementsValues.put(MeasurementsTable.COLUMN_NAME_HEIGHT, user.getHeight());
        measurementsValues.put(MeasurementsTable.COLUMN_NAME_LOGIN, user.getLogin());
        measurementsValues.put(MeasurementsTable.COLUMN_NAME_WEIGHT, user.getWeight());
        database.insert(MeasurementsTable.TABLE_NAME, null, measurementsValues);
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
            calories += cursor.getInt(cursor.getColumnIndex(MealTable.COLUMN_NAME_CALORIES));
            cursor.moveToNext();
        }
        cursor.close();
        return calories;
    }

    /* Obter o usuário com os dados providos de login */
    public User getUserByLoginData(String login, String password) {
        SQLiteDatabase database = getReadableDatabase();
        /* Consulta o banco */
        Cursor cursor = database.rawQuery(String.format(SQL_SELECT_BY_USER_LOGIN_INFO, login, password), null);
        cursor.moveToFirst();
        /* Iteração sobre os itens obtidos do banco */
        User user = null;
        while (!cursor.isAfterLast()) {
            int age = cursor.getInt(cursor.getColumnIndex(MeasurementsTable.COLUMN_NAME_AGE));
            int currentDietCalories = cursor.getInt(cursor.getColumnIndex(DietTable.COLUMN_NAME_CALORIES));
            String gender = cursor.getString(cursor.getColumnIndex(MeasurementsTable.COLUMN_NAME_GENDER));
            String name = cursor.getString(cursor.getColumnIndex(UserTable.COLUMN_NAME_NAME));
            double height = cursor.getDouble(cursor.getColumnIndex(MeasurementsTable.COLUMN_NAME_HEIGHT));
            double weight = cursor.getDouble(cursor.getColumnIndex(MeasurementsTable.COLUMN_NAME_WEIGHT));
            user = new User(age, currentDietCalories, height, weight, gender, login, name, password);
            cursor.moveToNext();
        }
        cursor.close();
        return user;
    }

    /* Classe que representa a tabela de dieta */
    private static class DietTable implements BaseColumns {
        public static final String TABLE_NAME = "diets";
        public static final String COLUMN_NAME_LOGIN = "login";
        public static final String COLUMN_NAME_CALORIES = "calories";
        public static final String COLUMN_NAME_DATE = "date";
    }

    /* Classe que representa a tabela de refeições */
    private static class MealTable implements BaseColumns {
        public static final String TABLE_NAME = "meals";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_CALORIES = "calories";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String[] PROJECTION = {_ID,
                COLUMN_NAME_NAME,
                COLUMN_NAME_CALORIES,
                COLUMN_NAME_DATE,
                COLUMN_NAME_TIME};
    }

    /* Classe que representa a tabela de medidas */
    private static class MeasurementsTable implements BaseColumns {
        public static final String TABLE_NAME = "measurements";
        public static final String COLUMN_NAME_LOGIN = "user_login";
        public static final String COLUMN_NAME_AGE = "age";
        public static final String COLUMN_NAME_WEIGHT = "weight";
        public static final String COLUMN_NAME_HEIGHT = "height";
        public static final String COLUMN_NAME_GENDER = "gender";
    }

    /* Classe que representa a tabela de usuário */
    private static class UserTable implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME_LOGIN = "login";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_NAME = "name";
    }
}
