package br.edu.fametro.diettracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.edu.fametro.diettracker.model.Meal;

/* TODO: Comentar e refatorar esse código */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DietTracker.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String INTEGER_PRIMARY_KEY_TYPE = " INTEGER PRIMARY KEY";
    private static final String COMMA_SEPARATOR = ",";
    private static final String SQL_CREATE_MEALS =
            "CREATE TABLE " + MealContract.MealEntry.TABLE_NAME +
                    " (" + MealContract.MealEntry._ID + INTEGER_PRIMARY_KEY_TYPE + COMMA_SEPARATOR +
                    MealContract.MealEntry.COLUMN_NAME_MEAL_NAME + TEXT_TYPE + COMMA_SEPARATOR +
                    MealContract.MealEntry.COLUMN_NAME_MEAL_CALORIES + INTEGER_TYPE + COMMA_SEPARATOR +
                    MealContract.MealEntry.COLUMN_NAME_MEAL_DATE + TEXT_TYPE + COMMA_SEPARATOR +
                    MealContract.MealEntry.COLUMN_NAME_MEAL_TIME + " )";
    private static final String SQL_DELETE_MEALS =
            "DROP TABLE IF EXISTS" + MealContract.MealEntry.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_MEALS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_MEALS);
        onCreate(db);
    }

    public void insertRow(Meal meal) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(MealContract.MealEntry.COLUMN_NAME_MEAL_NAME, meal.getName());
        values.put(MealContract.MealEntry.COLUMN_NAME_MEAL_CALORIES, meal.getCalories());
        values.put(MealContract.MealEntry.COLUMN_NAME_MEAL_DATE, meal.getDate());
        values.put(MealContract.MealEntry.COLUMN_NAME_MEAL_TIME, meal.getTime());

        db.insert(MealContract.MealEntry.TABLE_NAME, null, values);
    }

    public int read() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {MealContract.MealEntry._ID,
                MealContract.MealEntry.COLUMN_NAME_MEAL_NAME,
                MealContract.MealEntry.COLUMN_NAME_MEAL_CALORIES,
                MealContract.MealEntry.COLUMN_NAME_MEAL_DATE,
                MealContract.MealEntry.COLUMN_NAME_MEAL_TIME};
        Cursor cursor = db.query(MealContract.MealEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        int calories = 0;
        while (!cursor.isAfterLast()) {
            calories += cursor.getInt(cursor.getColumnIndex(MealContract.MealEntry.COLUMN_NAME_MEAL_CALORIES));
            cursor.moveToNext();
        }
        return calories;
    }
}
