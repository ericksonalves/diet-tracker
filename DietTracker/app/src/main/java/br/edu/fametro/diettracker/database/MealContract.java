package br.edu.fametro.diettracker.database;

import android.provider.BaseColumns;

/* TODO: Comentar e refatorar esse código */

public final class MealContract {

    public MealContract() {
    }

    public static abstract class MealEntry implements BaseColumns {
        public static final String TABLE_NAME = "meals";
        public static final String COLUMN_NAME_MEAL_NAME = "name";
        public static final String COLUMN_NAME_MEAL_CALORIES = "calories";
        public static final String COLUMN_NAME_MEAL_DATE = "date";
        public static final String COLUMN_NAME_MEAL_TIME = "time";
    }

}
