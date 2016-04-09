package br.edu.fametro.diettracker.util;

import android.graphics.Color;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Classe de utilidades da aplicação
 */

public class Utils {

    /* Formato de data */
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    /* Formato de hora */
    private static final String TIME_FORMAT = "HH:mm";
    /* Desconto esquerdo do gráfico */
    private static final float LEFT = 5;
    /* Desconto superior do gráfico */
    private static final float TOP = 10;
    /* Desconto direito do gráfico */
    private static final float RIGHT = 5;
    /* Desconto inferior do gráfico */
    private static final float BOTTOM = 5;
    /* Coeficiente de desaceleração do gráfico */
    private static final float DRAG_DECELERATION_FRICTION_COEFFICIENT = 0.95f;
    /* Raio central do gráfico*/
    private static final float HOLE_RADIUS = 58f;
    /* Raio do círculo transparente do gráfico */
    private static final float TRANSPARENT_CIRCLE_RADIUS = 61f;
    /* Espaço entre as partes */
    private static final float SLICE_SPACE = 3f;
    /* Tamanho da fonte dos valores */
    private static final float VALUE_TEXT_SIZE = 7.5f;
    /* Transparência alfa do círculo do gráfico */
    private static final int TRANSPARENT_CIRCLE_ALPHA = 110;
    /* Ângulo de rotação do gráfico */
    private static final int ROTATION_ANGLE = 0;
    /* Duração da animação do gráfico */
    private static final int ANIMATION_DURATION = 1500;

    /* Retorna a data atual ou o horário atual */
    public static String getCurrentDateTime(boolean isDate) {
        DateFormat dateFormat = new SimpleDateFormat(isDate ? DATE_FORMAT : TIME_FORMAT, Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    /* Prepara gráfico para exibição */
    public static void preparePieChart(PieChart chart, String centerText) {
        chart.setUsePercentValues(true);
        chart.setExtraOffsets(LEFT, TOP, RIGHT, BOTTOM);
        chart.setDragDecelerationFrictionCoef(DRAG_DECELERATION_FRICTION_COEFFICIENT);
        chart.setCenterText(centerText);
        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);
        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(TRANSPARENT_CIRCLE_ALPHA);
        chart.setHoleRadius(HOLE_RADIUS);
        chart.setTransparentCircleRadius(TRANSPARENT_CIRCLE_RADIUS);
        chart.setDrawCenterText(true);
        chart.setHighlightPerTapEnabled(false);
        chart.setRotationAngle(ROTATION_ANGLE);
        chart.setRotationEnabled(false);
        animatePieChart(chart);
    }

    /* Anima gráfico */
    public static void animatePieChart(PieChart chart) {
        chart.animateY(ANIMATION_DURATION, Easing.EasingOption.EaseInOutCirc);
    }

    /* Atualiza os valores do gráfico */
    public static void setChartData(PieChart chart, List<String> x, List<Integer> y, String legend, List<Integer>
            colors) {
        ArrayList<Entry> yAxisValues = new ArrayList<>();
        for (int i = 0; i < y.size(); ++i) {
            yAxisValues.add(new Entry(y.get(i), i));
        }
        ArrayList<String> xAxisValues = new ArrayList<>();
        for (int i = 0; i < x.size(); ++i) {
            xAxisValues.add(x.get(i));
        }
        PieDataSet dataSet = new PieDataSet(yAxisValues, legend);
        dataSet.setSliceSpace(SLICE_SPACE);
        dataSet.setColors(colors);
        PieData data = new PieData(xAxisValues, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(VALUE_TEXT_SIZE);
        data.setValueTextColor(Color.WHITE);
        chart.setData(data);
        chart.setDescription("");
        chart.invalidate();
        chart.getLegend().setEnabled(false);
        animatePieChart(chart);
    }

}
