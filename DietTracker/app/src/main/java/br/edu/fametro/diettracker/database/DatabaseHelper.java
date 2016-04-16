package br.edu.fametro.diettracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import br.edu.fametro.diettracker.model.Meal;
import br.edu.fametro.diettracker.model.User;
import br.edu.fametro.diettracker.util.Utils;

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
                    MealTable.COLUMN_NAME_LOGIN + TEXT_TYPE + COMMA_SEPARATOR +
                    MealTable.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEPARATOR +
                    MealTable.COLUMN_NAME_AMOUNT + TEXT_TYPE + COMMA_SEPARATOR +
                    MealTable.COLUMN_NAME_CALORIES + INTEGER_TYPE + COMMA_SEPARATOR +
                    MealTable.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEPARATOR +
                    MealTable.COLUMN_NAME_TIME + TEXT_TYPE + COMMA_SEPARATOR +
                    FOREIGN_KEY + "(" + MealTable.COLUMN_NAME_LOGIN + ")" + REFERENCES + " " +
                    UserTable.TABLE_NAME + "(" + UserTable.COLUMN_NAME_LOGIN + ")" + " )";
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
    private static final String SELECT_BY_DATE_AND_LOGIN_ARG = "date=? AND login=?";

    /* Construtor do banco de dados */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private void insetDefaultMeals(SQLiteDatabase db) {
        List<String> meals = new ArrayList<>();
        meals.add("INSERT INTO meals VALUES(?, '', 'Abacate', '1 porção (100g)', 177, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Abacaxi', '1 fatia', 80, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Abobrinha verde cozida', '1 xícara de chá', 30, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Abóbora', '100g', 40, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Acelga cozida', '100g', 20, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Acelga crua', '100g', 21, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Acerola', '1 unidade', 4, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Acém assado', '4 fatias (100g)', 185, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Acém frito', '4 fatias (100g)', 220, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Agrião', '100g', 28, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Aguardente', '100 ml', 231, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Aipo', '1 xícara de chá', 30, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Alcatra assada', '2 fatias (100g)', 235, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Alface', '2 folhas', 4, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Almeirão', '1 pires', 14, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Almôndega ao molho Swif', '1 unidade', 43, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Almôndega ao sugo', '1 unidade', 58, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Almôndega bovina', '1 unidade', 50, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Almôndega caseira', '1 unidade', 54, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Almôndega de frango', '1 unidade', 55, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Almôndega de peru', '1 unidade', 46, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Ameixa amarela', '1 unidade', 33, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Ameixa preta', '1 unidade', 36, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Ameixa vermelha', '1 unidade', 13, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Amora silvestre', '100g', 61, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Amêndoas', '10 unidades', 640, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Anchova cozida', '1 pedaço (100g)', 118, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Anchova à milanesa', '1 pedaço (100g)', 210, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Angu', '1 colher de sopa', 25, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Antecoxa de frango assada', '2 unidades (100g)', 109, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Apresuntado', '1 fatia', 22, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Arroz branco cozido', '1 colher de sopa', 22, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Arroz integral cozido', '1 colher de sopa', 26, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Arroz polido', '1 colher de sopa', 33, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Arroz preto', '1 colher de sopa', 68, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Arroz selvagem', '1 colher de sopa', 58, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Asa de frango assada', '2 unidades (100g)', 135, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Aspargos frescos', '2 talos', 4, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Atum cru', '1 pedaço (100g)', 146, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Aveia em flocos', '1 colher de sopa', 55, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Avelã', '10 unidades', 633, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Azeite de dendê', '1 colher de sopa', 89, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Azeite de oliva', '1 colher de sopa', 90, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Açúcar granulado', '1 colher de sopa', 80, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Açúcar mascavo', '1 colher de sopa', 71, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Açúcar refinado', '1 colher de sopa', 80, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Baby beef', '100g', 94, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Bacalhau cozido', '1 pedaço (100g)', 100, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Bacon fatiado', '1 fatia (10g)', 55, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Bacon frito', '2 cubos (30g)', 198, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Banana da terra', '1 unidade', 117, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Banana maçã', '1 unidade', 72, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Banana nanica ou d´água', '1 unidade', 87, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Banana ouro', '1 unidade', 78, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Banana prata', '1 unidade', 55, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Banha de galinha', '1 colher de sopa', 126, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Banha de porco', '1 colher de sopa', 180, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Batata Sautée', '1 colher de sopa', 35, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Batata baroa cozida', '1 colher de sopa', 45, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Batata doce cozida', '1 colher de sopa', 31, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Batata frita', '1 colher de sopa', 70, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Batata inglesa cozida', '1 colher de sopa', 26, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Batata palha', '1 colher de sopa', 37, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Batidas', '100 ml', 252, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Berinjela', '1 unidade', 489, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Bertalha', '100g', 19, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Beterraba', '1 pequena', 55, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Biscoito cream craker', '1 unidade', 25, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Biscoito água e sal', '1 unidade', 30, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Bisteca de carneiro assada', '1 unidade (100g)', 355, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Bisteca de lombo de boi', '1 unidade (100g)', 272, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Bisteca de porco', '1 unidade (150g)', 360, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Blanquet de peru', '1 fatia', 10, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Broa de milho', '1 unidade', 150, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Brócolis', '1 pires', 23, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Bucho de boi', '100g', 99, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Cabrito', '3 fatias (100g)', 357, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Caju', '1 unidade', 37, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Camarão cozido', '100g', 82, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Camarão frito', '100g', 310, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Cana de açúcar', '100g', 64, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Canelone a bolonhesa', '1 unidade média', 75, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Capelete de carne', '1 colher de sopa', 84, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Capelete de frango', '1 colher de sopa', 84, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Caqui chocolate', '1 unidade', 74, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Caqui japonês', '1 unidade', 86, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Caqui paulista', '1 unidade', 63, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Carambola', '1 unidade', 1, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Carne de boi cozida', '1 pedaço (100g)', 235, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Carne de boi gorda enlatada', '3 colheres de sopa (100g)', 271, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Carne de boi magra assada', '2 fatias (100g)', 288, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Carne de porco salgada', '1 fatia (50g)', 135, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Carne de sol', '4 pedaços (100g)', 213, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Carne seca', '100g', 296, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Carneiro', '3 fatias (100g)', 122, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Carpaccio', '1 prato (250g)', 407, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Carré', '100g', 298, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Casquinha de caranguejo', '1 unidade', 250, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Casquinha de siri', '1 unidade', 413, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Castanha de caju picada', '1 xícara de chá (150g)', 835, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Castanha do Pará', '100g', 1049, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Caviar', '1 colher de chá', 24, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Cação cozido', '1 pedaço (100g)', 129, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Cebola cozida', '1 unidade', 54, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Cebola', '1 unidade', 32, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Cenoura cozido', '1 unidade', 54, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Cenoura', '1 unidade', 45, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Cereal matinal com açúcar', '1 prato', 234, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Cereal matinal sem açúcar', '1 prato', 134, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Cereja', '100g', 97, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Cerveja light', '100 ml', 41, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Cerveja', '100 ml', 42, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Champanhe', '100 ml', 85, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Chicória', '1 pires', 21, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Chope', '100 ml', 60, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Chuchu', '½ unidade', 91, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Chuleta de carneiro assada', '3 fatias (100g)', 356, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Chuleta de carneiro cozida', '3 fatias (100g)', 350, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Chuleta de porco', '1 porção (100g)', 338, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Chuleta de vitela crua', '1 porção (100g)', 151, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Cidra', '100 ml', 50, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Coalhada integral', '1 colher de sopa', 52, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Coco ralado fresco', '1 colher de sopa', 50, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Coelho ensopado', '2 pedaços', 205, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Coelho', '2 pedaços (100g)', 175, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Conhaque', '100 ml', 150, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Contra filé', '1 pedaço (100g)', 186, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Coração de boi', '1 colher de sopa', 36, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Coração de carneiro', '1 colher de sopa', 37, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Coração de frango', '1 colher de sopa', 33, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Coração de vitela', '1 colher de sopa', 31, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Costela assada de boi', '2 unidades (100g)', 380, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Costela de carneiro cozida', '2 unidades (100g)', 217, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Costela de vitela', '2 unidades (100g)', 139, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Costela ponta de agulha assada', '1 unidade (100g)', 185, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Costeleta de carneiro', '2 unidades (100g)', 358, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Costeleta de cordeiro', '1 unidade (100g)', 352, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Costeleta de porco', '1 unidade (100g)', 390, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Couve flor', '100g', 41, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Couve', '1 pires', 55, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Coxa de frango assada com pele', '1 unidade (100g)', 110, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Coxa de frango assada sem pele', '1 unidade (100g)', 98, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Coxa de frango cozida', '1 unidade', 110, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Coxa de frango', '1 unidade (100g)', 144, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Coxão duro assado', '4 fatias (100g)', 200, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Coxão duro frito', '1 pedaço (100g)', 235, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Coxão mole assado', '1 pedaço (100g)', 200, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Coxão mole frito', '1 pedaço (100g)', 235, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Cream cheese light', '1 colher de sopa', 40, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Cream cheese', '1 colher de sopa', 89, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Croissant', '1 unidade', 120, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Cupim', '2 fatias (150g)', 375, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Cupuaçu', '100g', 72, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Daiquiri', '100 ml', 116, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Damasco fresco', '1 unidade', 19, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Damasco seco', '1 unidade', 26, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Dourado', '1 pedaço (100g)', 88, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Ervilha em conserva', '1 colher de sopa', 19, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Ervilha seca', '1 colher de sopa', 72, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Ervilha vagem verde', '1 colher de sopa', 28, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Escarola', '2 folhas', 7, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Espinafre', '1 pires', 38, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Farelo de aveia', '1 colher de sopa', 55, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Farelo de trigo', '1 colher de sopa', 32, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Farinha de araruta', '1 colher de sopa', 52, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Farinha de arroz', '1 colher de sopa', 64, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Farinha de aveia', '1 colher de sopa', 65, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Farinha de banana', '1 colher de sopa', 64, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Farinha de cará', '1 colher de sopa', 57, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Farinha de centeio', '1 colher de sopa', 61, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Farinha de mandioca crua', '1 colher de sopa', 57, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Farinha de mandioca tostada', '1 colher de sopa', 58, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Farinha de mandioca', '1 colher de sopa', 57, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Farinha de milho', '1 colher de sopa', 64, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Farinha de rosca', '1 colher de sopa', 61, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Farinha de sarraceno', '1 colher de sopa', 57, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Farinha de soja', '1 colher de sopa', 70, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Farinha de tapioca', '1 colher de sopa', 70, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Farinha de trigo integral', '1 colher de sopa', 65, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Farinha de trigo', '1 colher de sopa', 71, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Farinha láctea', '1 colher de sopa', 85, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Farofa', '1 colher de sopa', 72, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Feijão adzuki', '1 colher de sopa', 86, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Feijão branco', '1 colher de sopa', 72, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Feijão carioca', '1 colher de sopa', 20, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Feijão fradinho', '1 colher de sopa', 82, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Feijão jalo', '1 colher de sopa', 88, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Feijão mulatinho', '1 colher de sopa', 88, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Feijão preto cozido', '1 colher de sopa', 23, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Feijão rosinha', '1 colher de sopa', 69, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Feijão roxo', '1 colher de sopa', 70, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Feijão tropeiro', '1 colher de sopa', 84, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Figo maduro', '1 unidade', 68, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Filé de boi', '1 pedaço (100g)', 185, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Filé de frango', '2 pedaços (100g)', 101, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Filé de porco', '1 pedaço (100g)', 120, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Filé mignon', '1 pedaço (100g)', 121, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Fraldinha de boi assada', '2 fatias (100g)', 185, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Framboesa', '1 colher de sopa', 12, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Frango a passarinho', '2 pedaços (100g)', 156, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Frango cozido sem pele', '1 sobrecoxa (100g)', 188, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Frango cozido', '1 sobrecoxa (100g)', 220, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Frango defumado', '2 fatias (100g)', 157, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Frango grelhado', '1 pedaço (100g)', 146, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Fruta do conde', '1 unidade', 200, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Fruta pão', '1 unidade', 96, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Fígado de boi frito', '1 pedaço (100g)', 210, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Gim', '100 ml', 200, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Goiaba branca', '1 unidade', 58, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Goiaba vermelha', '1 unidade', 43, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Gordura vegetal hidrogenada', '1 colher de sopa', 180, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Graviola', '1 unidade', 60, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Grão de bico cozido', '1 colher de sopa', 46, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Grão de bico', '1 colher de sopa', 69, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Guaraná', '100g', 69, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Haddock cozido', '1 pedaço (100g)', 100, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Inhame cozido', '1 colher de sopa', 20, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Iogurte de frutas desnatado', '1 copo', 80, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Iogurte de frutas integral', '1 copo', 172, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Iogurte natural integral', '1 copo', 140, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Iogurte natutal desnatado', '1 copo', 84, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Jabuticaba', '15 unidades', 94, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Jaca', '1 gomo', 9, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Jiló', '5 unidades', 38, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Kani', 'kama', 1, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Kir (vinho com licor de cassis)', '100 ml', 33, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Kiwi', '1 unidade', 46, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Lagarto de boi assado', '3 fatias (100g)', 170, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Lagarto de boi cozido', '3 fatias (100g)', 117, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Lagosta cozido sem molho', '1 unidade (200g)', 196, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Laranja', '1 unidade', 46, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Lasanha a bolonhesa', '1 colher de sopa', 68, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Lasanha', '1 colher de sopa', 91, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Leite condensado', '1 lata', 1328, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Leite de cabra integral', '1 copo', 161, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Leite de coco', '1 copo', 530, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Leite de soja', '1 copo', 71, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Leite de vaca desnatado', '1 copo', 72, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Leite de vaca em pó desnatado', '1 colher de sopa', 70, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Leite de vaca em pó integral', '1 colher de sopa', 90, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Leite de vaca integral', '1 copo', 122, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Leite de vaca semi desnatado', '1 copo', 108, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Leite fermentado', '1 copo', 174, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Leitão', '1 pedaço (100g)', 380, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Lentilha', '1 colher de sopa', 39, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Licor de jenipapo', '100 ml', 450, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Licores', '100 ml', 345, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Lima da pérsia', '1 unidade', 32, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Lima', '2 unidades', 51, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Limão', '1 unidade', 12, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Linguado assado ou grelhado', '1 pedaço (100g)', 90, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Lingüiça calabresa', '100g', 300, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Lingüiça de frango', '100g', 166, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Lingüiça de peru defumada', '100g', 148, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Lingüiça toscana', '100g', 255, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Lombo assado', '1 pedaço (100g)', 272, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Lombo canadense', '1 fatia', 15, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Lombo de boi assado', '1 pedaço (100g)', 290, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Lombo de carneiro assado', '1 pedaço (100g)', 361, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Lombo de cordeiro assado', '1 pedaço (100g)', 336, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Lombo de porco', '1 pedaço (100g)', 362, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Lombo defumado', '1 fatia', 29, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Lula cozida', '100g', 93, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Lula frita empanada', '100g', 373, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Língua de boi cozida', '2 pedaços (100g)', 287, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Macadêmia', '30g', 210, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Macarrão cozido a bolonhesa', '1 colher de sopa', 25 , " +
                "'16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Macarrão cozido ao molho de tomate', '1 colher de sopa', 22, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Macarrão cozido', '1 colher de sopa', 38, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Macarrão integral cozido', '1 colher de sopa', 31, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Maminha', '1 pedaço (100g)', 160, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Mamão maduro', '1 fatia', 36, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Mandioca cozida', '1 colher de sopa', 37, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Mandioca frita', '1 colher de sopa', 100, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Manga', '1 unidade', 230, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Manteiga com sal', '1 colher de café', 38, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Manteiga com sal', '1 colher de sopa', 77, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Manteiga sem sal', '1 colher de café', 38, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Maracujá comum (polpa)', '1 unidade', 28, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Margarina', '1 colher de chá', 74, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Mariscos cozidos', '100g', 96, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Maxixe', '1 pires', 5, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Maçã verde', '1 unidade', 79, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Maçã vermelha', '1 unidade', 85, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Melancia', '1 fatia', 24, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Melão', '1 fatia', 19, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Mexilhão cozido', '100g', 79, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Milho verde cozido', '1 colher de sopa', 30, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Milho verde cru', '1 colher de sopa', 44, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Milho verde enlatado', '1 colher de sopa', 23, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Morango', '9 unidades', 43, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Mortadela de frango', '1 fatia', 20, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Mortadela', '1 fatia', 41, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Músculo assado', '3 pedaços (100g)', 185, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Músculo cozido', '3 pedaços (100g)', 180, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Nabo', '1 pires', 35, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Namorado cozido', '1 pedaço (100g)', 122, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Nhoque', '1 colher de sopa', 55, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Nozes', '1 unidade', 71, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Omelete', '100g', 170, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Ostras', '3 unidades', 81, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Ovas de peixes cruas', '100g', 125, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Ovo de codorna', '1 unidade', 33, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Ovo de galinha cozido', '1 unidade', 78, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Ovo de galinha frito', '1 unidade', 108, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Ovo de galinha mexido', '100g', 195, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Paio', '1 unidade (100g)', 314, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Palmito', '1 unidade', 22, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Panetone', '1 fatia', 283, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Panqueca de carne moída', '1 unidade média', 130, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Patinho de boi assado', '3 fatias (100g)', 200, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Patinho de boi frito', '1 pedaço (100g)', 235, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Peito de boi com pouca gordura', '1 pedaço (100g)', 211, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Peito de frango assado com pele', '1 pedaço (100g)', 107, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Peito de frango assado sem pele', '1 pedaço (100g)', 98, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Peito de frango assado', '1 pedaço (100g)', 109, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Peito de peru defumado', '1 fatia', 14, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Pepino com casca', '1 unidade', 21, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Pepino sem casca', '1 unidade', 5, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Peru assado com pele', '2 pedaços (100g)', 210, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Peru assado sem pele', '2 pedaços (100g)', 178, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Peru', '2 pedaços (100g)', 180, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Pescada cozida', '1 pedaço (100g)', 97, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Picanha', '1 pedaço (100g)', 287, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Pimentão verde cozido', '2 unidades', 31, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Pimentão verde', '2 unidades', 29, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Pintado grelhado', '1 pedaço (100g)', 104, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Pirão', '1 colher de sopa', 24, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Pitanga', '10 unidades', 47, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Poche', '100 ml', 50, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Polvo cru', '100g', 64, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Presunto cozido', '1 fatia', 18, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Presunto cru', '1 fatia', 54, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Purê de batatas', '1 colher de sopa', 28, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Pão bisnaguinha', '1 unidade', 60, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Pão ciabata', '1 unidade', 150, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Pão de batata', '1 unidade', 90, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Pão de cachorro-quente', '1 unidade', 168, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Pão de centeio integral', '1 unidade', 58, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Pão de centeio', '1 unidade', 62, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Pão de cevada', '1 unidade', 91, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Pão de forma branco', '1 fatia', 70, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Pão de forma diet', '1 unidade', 62, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Pão de forma integral', '1 fatia', 70, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Pão de forma light', '1 unidade', 63, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Pão de forma preto', '1 unidade', 80, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Pão de hambúrguer', '1 unidade', 168, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Pão de leite', '1 unidade', 70, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Pão de mel com cobertura de chocolate', '1 unidade', 91, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Pão de queijo', '1 unidade', 68, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Pão doce', '1 unidade', 220, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Pão francês com miolo', '1 unidade', 135, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Pão francês sem miolo', '1 unidade', 83, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Pão italiano', '1 unidade', 154, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Pão sem glúten', '1 unidade', 58, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Pão sírio', '1 unidade', 88, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Pêra', '1 unidade', 68, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Pêssego', '1 unidade', 63, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Queijo Brie', '1 fatia', 110, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Queijo camembert', '1 fatia', 65 , '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Queijo catupiry', '1 colher de sopa', 63, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Queijo cheddar', '1 fatia', 106, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Queijo cottage', '1 colher de sopa', 21, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Queijo emental', '1 fatia', 71, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Queijo fundido', '1 colher de sopa', 88, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Queijo gorgonzola', '1 fatia', 99, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Queijo minas frescal', '1 fatia', 61, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Queijo minas padrão', '1 fatia', 75, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Queijo mussarela', '1 fatia', 81, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Queijo parmesão ralado', '1 colher de sopa', 81, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Queijo parmesão', '1 fatia', 101, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Queijo prato', '1 fatia', 88, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Queijo provolone', '1 fatia', 84, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Queijo ricota', '1 fatia', 45, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Queijo suíço', '1 fatia', 101, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Quiabo cozido', '10 unidades', 30, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Quiabo', '1 pires', 39, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Rabanete', '1 pires', 16, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Repolho cozido', '100g', 13, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Repolho', '100g', 33, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Requeijão light', '1 colher de sopa', 36, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Requeijão', '1 colher de chá', 54, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Rim de boi', '100g', 111, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Robalo', '1 pedaço (100g)', 72, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Romã', '1 unidade', 62, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Rosbife', '100g', 166, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Rum', '100 ml', 220, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Rã', '200g', 128, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Rúcula', '1 pires', 7, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Salame italiano', '1 fatia', 10, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Salaminho', '1 fatia', 10, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Salmão assado ou grelhado', '1 pedaço (100g)', 292, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Salmão cru', '1 pedaço (100g)', 211, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Salsicha light de chester', '1 unidade', 64, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Salsicha', '1 unidade', 120, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Salsição', '1 fatia', 30, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Saquê', '100 ml', 137, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Sardinha em conserva com azeite', '3 unidades (100g)', 298, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Sardinha em óleo comestível', '4 unidades (100g)', 174, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Sardinha grelhada', '1 unidade', 97, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Semente de linhaça', '1 colher de sopa', 39, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Suco de abacaxi com hortelã', '200 ml', 104, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Suco de abacaxi com maçã', '200 ml', 106, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Suco de abacaxi', '200 ml', 100, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Suco de acerola', '200 ml', 30, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Suco de ameixa', '200 ml', 158, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Suco de beterraba com cenoura e laranja', '200 ml', 180, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Suco de caju', '200 ml', 78, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Suco de cereja', '200 ml', 190, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Suco de framboesa', '200 ml', 128, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Suco de fruta do conde', '200 ml', 192, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Suco de goiaba', '200 ml', 100, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Suco de graviola', '200 ml', 128, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Suco de kiwi', '200 ml', 95, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Suco de laranja', '200 ml', 113, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Suco de lima', '200 ml', 74, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Suco de limão', '200 ml', 60, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Suco de mamão', '200 ml', 90, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Suco de manga', '200 ml', 91, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Suco de maracujá', '200 ml', 120, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Suco de maçã com abacaxi', '200 ml', 110, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Suco de maçã com morango', '200 ml', 84, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Suco de maçã', '200 ml', 128, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Suco de melancia com morango', '200 ml', 100, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Suco de melancia', '200 ml', 56, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Suco de melão com cenoura', '200 ml', 80, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Suco de melão', '200 ml', 50, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Suco de morango', '200 ml', 84, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Suco de pêra com abacaxi', '200 ml', 120, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Suco de pêra com maracujá', '200 ml', 95, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Suco de pêssego', '200 ml', 64, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Suco de romã', '200 ml', 196, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Suco de tangerina', '200 ml', 150, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Suco de tomate', '200 ml', 23, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Suco de uva', '200 ml', 146, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Suco e morango com amora', '200 ml', 113, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Tainha cozida', '1 pedaço (100g)', 204, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Tamarindo polpa', '100g', 232, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Tangerina', '1 unidade', 50, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Tapioca', '1 unidade', 270, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Tender', '4 fatias (100g)', 210, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Tomate', '1 unidade', 20, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Torrada de pão francês', '1 unidade', 33, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Torrada integral', '1 unidade', 34, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Torrada', ' 1 unidade', 64, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Toucinho defumado', '1 fatia (20g)', 138, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Truta assada ou grelhada', '1 pedaço (100g)', 189, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Tâmara ao natural', '100g', 178, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Uva branca nacional', '15 bagos', 76, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Uva passa', '1 colher de sopa', 54, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Vagem', '1 pires', 40, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Vinho branco doce', '100 ml', 142, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Vinho branco seco', '100 ml', 82, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Vinho de maçã', '100 ml', 32, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Vinho moscatel', '100 ml', , '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Vinho rose', '100 ml', 37, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Vinho tinto', '100 ml', 65, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Vodca', '100 ml', 240, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Whisky', '100 ml', 240, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Óleo de algodão', '1 colher de sopa', 90, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Óleo de amendoim', '1 colher de sopa', 90, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Óleo de canola', '1 colher de sopa', 90, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Óleo de fígado de bacalhau', '1 colher de sopa', 130, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Óleo de gergelim', '1 colher de sopa', 90, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Óleo de girassol', '1 colher de sopa', 90, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Óleo de milho', '1 colher de sopa', 90, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Óleo de peixe', '1 colher de sopa', 90, '16/04/2016', '00:00');");
        meals.add("INSERT INTO meals VALUES(?, '', 'Óleo de soja', '1 colher de sopa', 90, '16/04/2016', '00:00');");
        for (String meal : meals) {
            db.execSQL(meal);
        }
    }

    /* Evento iniciado ao criar o banco */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_MEALS);
        db.execSQL(SQL_CREATE_USERS);
        db.execSQL(SQL_CREATE_DIETS);
        db.execSQL(SQL_CREATE_MEASUREMENTS);
        insetDefaultMeals(db);
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
        values.put(MealTable.COLUMN_NAME_LOGIN, meal.getLogin());
        values.put(MealTable.COLUMN_NAME_NAME, meal.getName());
        values.put(MealTable.COLUMN_NAME_AMOUNT, meal.getAmount());
        values.put(MealTable.COLUMN_NAME_CALORIES, meal.getCalories());
        values.put(MealTable.COLUMN_NAME_DATE, meal.getDate());
        values.put(MealTable.COLUMN_NAME_TIME, meal.getTime());
        database.insert(MealTable.TABLE_NAME, null, values);
    }

    /* Inserir um usuário no banco */
    public void insertUser(User user) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues dietValues = new ContentValues();
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
        dietValues.put(DietTable.COLUMN_NAME_LOGIN, user.getLogin());
        dietValues.put(DietTable.COLUMN_NAME_CALORIES, user.getCurrentDietCalories());
        dietValues.put(DietTable.COLUMN_NAME_DATE, Utils.getCurrentDateTime(true));
        database.insert(DietTable.TABLE_NAME, null, dietValues);
    }

    /* Obter o total de calorias consumidas no dia especificado */
    public int getDailyCalories(String login, String date) {
        SQLiteDatabase database = getReadableDatabase();
        String[] args = {date, login};
        /* Consulta o banco */
        Cursor cursor = database.query(MealTable.TABLE_NAME, MealTable.PROJECTION,
                SELECT_BY_DATE_AND_LOGIN_ARG, args,
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

    /* Obter as refeições adicionadas por todos os usuários da aplicação */
    public List<Meal> getMeals() {
        List<Meal> meals = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        /* Consulta o banco */
        Cursor cursor = database.query(MealTable.TABLE_NAME, MealTable.PROJECTION, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String name = cursor.getString(cursor.getColumnIndex(MealTable.COLUMN_NAME_NAME));
            String amount = cursor.getString(cursor.getColumnIndex(MealTable.COLUMN_NAME_AMOUNT));
            int calories = cursor.getInt(cursor.getColumnIndex(MealTable.COLUMN_NAME_CALORIES));
            String date = cursor.getString(cursor.getColumnIndex(MealTable.COLUMN_NAME_DATE));
            String time = cursor.getString(cursor.getColumnIndex(MealTable.COLUMN_NAME_TIME));
            Meal meal = new Meal("", name, amount, calories, date, time);
            boolean isNew = true;
            for (Meal m : meals) {
                if (m.getName().equals(meal.getName())) {
                    isNew = false;
                    break;
                }
            }
            if (isNew) {
                meals.add(meal);
            }
            cursor.moveToNext();
        }
        cursor.close();
        return meals;
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
        public static final String COLUMN_NAME_LOGIN = "login";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_AMOUNT = "amount";
        public static final String COLUMN_NAME_CALORIES = "calories";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String[] PROJECTION = {_ID,
                COLUMN_NAME_LOGIN,
                COLUMN_NAME_NAME,
                COLUMN_NAME_AMOUNT,
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
