package br.edu.fametro.diettracker.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.fametro.diettracker.R;
import br.edu.fametro.diettracker.database.DatabaseHelper;
import br.edu.fametro.diettracker.dialog.AddMealDialog;
import br.edu.fametro.diettracker.view.PercentView;

/**
 * Atividade principal da aplicação
 */

public class MainActivity extends AppCompatActivity {

    /* Constante para a quantidade máxima de calorias a serem ingeridas no dia */
    private static final int TOTAL_CALORIES = 500;

    /* Botão de adicionar refeição do canto inferior direito */
    private FloatingActionButton mAddMealButton;
    /* Gráfico da quantidade de calorias já consumida no dia */
    private PercentView mCaloriesPercentageView;
    /* Texto dizendo a quantidade de calorias já consumida no dia */
    private TextView mAmountOfCaloriesTextView;
    /* Classe que representa o manipulador do banco de dados */
    private DatabaseHelper mDbHelper;

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
        mCaloriesPercentageView = (PercentView) findViewById(R.id.view_calories_percentage);
        mAmountOfCaloriesTextView = (TextView) findViewById(R.id.text_view_amount_of_calories);

        /* Inicialização do manipulador do banco de dados */
        mDbHelper = new DatabaseHelper(getApplicationContext());
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
                Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_SHORT).show();
                break;
            /* Item sobre */
            case R.id.action_about:
                Toast.makeText(getApplicationContext(), "About", Toast.LENGTH_SHORT).show();
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
        /* Chamada da atualização de calorias consumidas */
        updateCalories();
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
                /* Instanciação do diálogo */
                AddMealDialog dialog = new AddMealDialog(getApplicationContext());
                /* Mostrar diálogo */
                dialog.show();
                /* Configurar um escutador para quando o diálogo for fechado */
                /* TODO: Mudar esse código para ser um escutador por uma classe */
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        /* TODO: Não é necessário atualizar o valor sempre */
                        updateCalories();
                    }
                });
            }
        });
    }

    /* Método para desassociar os escutadores na atividade */
    private void dissociateListeners() {
        /* Liberação do escutador de clique do botão de adicionar refeição */
        mAddMealButton.setOnClickListener(null);
    }

    /* Método para atualizar a quantidade de calorias consumidas no texto e no gráfico */
    private void updateCalories() {
        /* Atualização dos valores de calorias consumidas pelo banco de dados */
        int calories = mDbHelper.read();
        /* Atualização do texto da quantidade de calorias consumidas */
        mAmountOfCaloriesTextView.setText(String.format(getString(R.string.amount_of_calories), calories, TOTAL_CALORIES));
        /* Atualização do gráfico de calorias consumidas */
        /* TODO: Atualizar esse valor de forma menos forçada */
        mCaloriesPercentageView.setFinalPercentage(calories * 100 / TOTAL_CALORIES);
    }
}
