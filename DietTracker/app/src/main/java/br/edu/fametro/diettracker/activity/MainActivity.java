package br.edu.fametro.diettracker.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;

import java.util.ArrayList;
import java.util.List;

import br.edu.fametro.diettracker.R;
import br.edu.fametro.diettracker.controller.Controller;
import br.edu.fametro.diettracker.dialog.AddMealDialog;
import br.edu.fametro.diettracker.dialog.UserSettingsDialog;
import br.edu.fametro.diettracker.model.User;
import br.edu.fametro.diettracker.util.Utils;

/**
 * Atividade principal da aplicação
 */

public class MainActivity extends AppCompatActivity implements AddMealDialog.AddMealDialogListener {

    /* Botão de adicionar refeição do canto inferior direito */
    private FloatingActionButton mAddMealButton;
    /* Texto dizendo a quantidade de calorias já consumida no dia */
    private TextView mAmountOfCaloriesTextView;
    /* Texto dizendo o IMC atual */
    private TextView mCurrentBMITextView;
    /* Gráfico da quantidade de calorias já consumida no dia */
    private PieChart mChart;

    /* Método chamado ao criar a atividade */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Atribui o visual da atividade de acordo com o especificado nos recursos da aplicação */
        setContentView(R.layout.activity_main);

        /* Inicialização da barra superior da atividade */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* Inicialização dos componentes visuais da atividade */
        mAddMealButton = (FloatingActionButton) findViewById(R.id.floating_action_button_add_meal);
        mAmountOfCaloriesTextView = (TextView) findViewById(R.id.text_view_amount_of_calories);
        mCurrentBMITextView = (TextView) findViewById(R.id.text_view_current_bmi);
        mChart = (PieChart) findViewById(R.id.chart_calories);
    }

    /* Método chamado ao criar o menu da atividade */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Infla o menu de acordo com a especificação nos recursos da aplicação */
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /* Método chamado ao selecionar algum item do menu da atividade */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /* Recebe o identificador do item clicado no menu */
        int id = item.getItemId();

        /* Faz a escolha apropriada do item clicado */
        switch (id) {
            /* Item configurações */
            case R.id.action_settings:
                showUserSettingsDialog();
                break;
            /* Item relatórios */
            case R.id.action_reports:
                callChartsActivity();
                break;
            /* Item sobre */
            case R.id.action_about:
                callAboutActivity();
                break;
            default:
                break;
        }

        return true;
    }

    /* Método chamado ao iniciar a atividade */
    @Override
    protected void onStart() {
        super.onStart();
        /* Chamada da atribuição de escutadores */
        assignListeners();
        /* Chamada da atualização do gráfico */
        prepareChart();
        /* Chamada da atualização de calorias consumidas */
        updateCalories();
        /* Chamada da atualização do IMC */
        updateBMI();
    }

    /* Método chamado ao terminar a atividade */
    @Override
    protected void onStop() {
        super.onStop();
        /* Chamada da desassociação de escutadores */
        dissociateListeners();
    }

    /* Método para atribuir os escutadores dos componentes */
    private void assignListeners() {
        /* Atribuição do escutador para o botão de adicionar refeição */
        mAddMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddMealDialog();
            }
        });
    }

    /* Método para chamar a atividade Sobre */
    private void callAboutActivity() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    /* Método para chamar a atividade Gráficos */
    private void callChartsActivity() {
        Intent intent = new Intent(this, ChartsActivity.class);
        startActivity(intent);
    }

    /* Método para desassociar os escutadores na atividade */
    private void dissociateListeners() {
        /* Liberação do escutador de clique do botão de adicionar refeição */
        mAddMealButton.setOnClickListener(null);
    }

    private void prepareChart() {
        Utils.preparePieChart(mChart, getString(R.string.calories_chart_center_text));
    }

    /* Método para mostrar o diálogo para adicionar uma refeição */
    private void showAddMealDialog() {
        /* Instanciação do diálogo */
        AddMealDialog addMealDialog = new AddMealDialog(MainActivity.this);
        /* Mostrar diálogo */
        addMealDialog.show();
        /* Atribui a atividade como escutadora do diálogo */
        addMealDialog.addListener(MainActivity.this);
    }

    /* Método para mostrar o diálogo das configurações do usuário */
    private void showUserSettingsDialog() {
        /* Instanciação do diálogo */
        UserSettingsDialog userSettingsDialog = new UserSettingsDialog(MainActivity.this);
        /* Mostrar diálogo */
        userSettingsDialog.show();
    }

    /* Método para atualizar o IMC atual */
    private void updateBMI() {
        User user = Controller.getInstance().getUser(MainActivity.this);
        double bmi = user.getWeight() / (user.getHeight() * user.getHeight());
        mCurrentBMITextView.setText(String.format(getString(R.string.current_bmi),
                Utils.getTwoDecimalPlacesString(bmi),
                Utils.getBMILabel(MainActivity.this, bmi)));
    }

    /* Método para atualizar a quantidade de calorias consumidas no texto e no gráfico */
    private void updateCalories() {
        /* Atualização dos valores de calorias consumidas pelo banco de dados */
        int calories = Controller.getInstance().getAlreadyConsumedCalories(getApplicationContext());
        int totalCalories = Controller.getInstance().getTotalCalories(this);
        /* Atualização do texto da quantidade de calorias consumidas */
        mAmountOfCaloriesTextView.setText(String.format(getString(R.string.amount_of_calories), calories,
                totalCalories));
        /* Atualização do gráfico de calorias consumidas */
        updateChartData(calories, totalCalories);
    }

    /* Método para atualizar o gráfico de consumo */
    private void updateChartData(int calories, int totalCalories) {
        List<String> x = new ArrayList<>();
        x.add(getString(R.string.consumed));
        x.add(getString(R.string.to_consume));
        List<Integer> y = new ArrayList<>();
        y.add(calories);
        y.add(totalCalories);
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(ContextCompat.getColor(this, R.color.colorAccent));
        colors.add(Color.BLACK);
        Utils.setChartData(mChart, x, y, getString(R.string.meal_calories), colors);
    }

    @Override
    public void onConfirmed() {
        updateCalories();
    }
}
