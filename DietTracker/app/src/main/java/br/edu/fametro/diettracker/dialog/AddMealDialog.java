package br.edu.fametro.diettracker.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.edu.fametro.diettracker.R;
import br.edu.fametro.diettracker.database.DatabaseHelper;
import br.edu.fametro.diettracker.model.Meal;
import br.edu.fametro.diettracker.util.Utils;

/**
 * Diálogo para adicionar uma refeição ao controle de calorias
 */

public class AddMealDialog extends Dialog {

    /* Botão para confirmar a adição da refeição ao banco de dados */
    private Button mConfirmButton;
    /* Entrada de texto para inserção do número de calorias */
    private EditText mCaloriesEditText;
    /* Entrada de texto para inserção do nome da refeição */
    private EditText mNameEditText;

    /* Construtor da classe */
    public AddMealDialog(Context context) {
        super(context);
    }

    /* Método chamado ao criar o diálogo */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /* Atribui o visual do diálogo de acordo com o especificado nos recursos da aplicação */
        setContentView(R.layout.dialog_add_meal);
        /* Instanciação de um objeto para configurar os parâametros visuais da tela */
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        /* Obtenção de um objeto representando a tela */
        Window window = getWindow();
        /* Cópia dos atributos da tela */
        layoutParams.copyFrom(window.getAttributes());
        /* Atribuição da largura para o tamanho disponível na tela */
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        /* Atribuição da altura para o tamanho necessário para os componentes */
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        /* Atribuição dos atributos visuais para o diálogo */
        window.setAttributes(layoutParams);
        /* Inicialização dos componentes visuais do diálogo */
        mConfirmButton = (Button) findViewById(R.id.button_confirm);
        mCaloriesEditText = (EditText) findViewById(R.id.edit_text_meal_calories);
        mNameEditText = (EditText) findViewById(R.id.edit_text_meal_name);
    }

    /* Método chamado ao iniciar o diálogo */
    @Override
    protected void onStart() {
        super.onStart();
        /* Chamada da atribuição de escutadores */
        assignListeners();
    }

    /* Método chamado ao terminar o diálogo */
    @Override
    protected void onStop() {
        super.onStop();
        /* Chamada da desassociação de escutadores */
        dissociateListeners();
    }

    /* Método chamado ao criar o diálogo */
    private void assignListeners() {
        /* Liberação do escutador de clique do botão de adicionar refeição */
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Obtenção do texto inserido no campo de inserção de nome */
                String name = mNameEditText.getText().toString();
                /* Obtenção do texto inserido no campo de inserção de calorias */
                String caloriesText = mCaloriesEditText.getText().toString();
                /* Caso ambos não sejam vazios, inserir no banco. Caso contrário, mostrar um erro */
                if (!name.isEmpty() && !caloriesText.isEmpty()) {
                    /* TODO: Tratar exceção na conversão de texto para inteiro */
                    int calories = Integer.parseInt(caloriesText);
                    /* Instanciação de um objeto representando uma refeição */
                    Meal meal = new Meal(name, calories, Utils.getCurrentDateTime(true), Utils.getCurrentDateTime(false));
                    /* Instanciação do manipulador do banco de dados */
                    /* TODO: Esse processo deve ser feito pelo controlador */
                    DatabaseHelper dbHelper = new DatabaseHelper(getContext());
                    /* Inserção de um novo dado no banco de dados */
                    dbHelper.insertRow(meal);
                    /* Fechar o diálogo */
                    dismiss();
                } else {
                    /* Mostra uma mensagem de erro */
                    Toast.makeText(getContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                    /* Requisição do foco pela entrada de texto para o nome */
                    mNameEditText.requestFocus();
                }
            }
        });
    }

    /* Método para desassociar os escutadores no diálogo */
    private void dissociateListeners() {
        /* Liberação do escutador de clique do botão de confirmar a adição da refeição */
        mConfirmButton.setOnClickListener(null);
    }
}
