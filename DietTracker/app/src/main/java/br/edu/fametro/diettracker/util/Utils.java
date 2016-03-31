package br.edu.fametro.diettracker.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe de utilidades da aplicação
 * */

public class Utils {

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String TIME_FORMAT = "HH:mm";

    /**
     * Retorna a data atual ou o horário atual
     * @param isDate Uma variável lógica dizendo se o valor desejado é a data (verdadeiro) ou o horário (falso)
     * @return Data ou horário atual
     * */
    public static String getCurrentDateTime(boolean isDate) {
        DateFormat dateFormat = new SimpleDateFormat(isDate ? DATE_FORMAT : TIME_FORMAT);
        Date date = new Date();
        return dateFormat.format(date);
    }

}
