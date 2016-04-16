package br.edu.fametro.diettracker.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.edu.fametro.diettracker.R;
import br.edu.fametro.diettracker.controller.Controller;
import br.edu.fametro.diettracker.model.Meal;
import br.edu.fametro.diettracker.util.Utils;

/**
 * Diálogo para adicionar uma refeição ao controle de calorias
 */

public class AddMealDialog extends Dialog implements SearchMealsDialog.SearchMealDialogListener {

    /* Botão para confirmar a adição da refeição ao banco de dados */
    private Button mConfirmButton;
    private Button mSearchMealButton;
    /* Entrada de texto para inserção do número de calorias */
    private EditText mCaloriesEditText;
    /* Entrada de texto para inserção do nome da refeição */
    private EditText mNameEditText;
    /* Escutadores do diálogo */
    private List<AddMealDialogListener> mListeners;
    /* Identificador do diálogo */
    private static final String TAG = "AddMealDialog";

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
        mSearchMealButton = (Button) findViewById(R.id.button_search_meal);
        mCaloriesEditText = (EditText) findViewById(R.id.edit_text_meal_calories);
        mNameEditText = (EditText) findViewById(R.id.edit_text_meal_name);
        mListeners = new ArrayList<>();
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                for (int i = 0; i < mListeners.size(); ++i) {
                    mListeners.remove(i);
                }
            }
        });
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

    public void addListener(AddMealDialogListener listener) {
        mListeners.add(listener);
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
                    int calories = -1;
                    try {
                        calories = Integer.parseInt(caloriesText);
                    } catch (Exception e) {
                        android.util.Log.e(TAG, getContext().getString(R.string.error_exception_caught), e);
                    }
                    if (calories != -1) {
                        /* Instanciação de um objeto representando uma refeição */
                        Meal meal = new Meal(Controller.getInstance().getUser().getLogin(), name, "amount", calories,
                                Utils.getCurrentDateTime(true), Utils.getCurrentDateTime(false));
                        /* Inserção de um novo dado no banco de dados */
                        Controller.getInstance().insertMealToDatabase(getContext(), meal);
                        /* Notifica os escutadores sobre a atualização dos dados */
                        for (AddMealDialogListener listener : mListeners) {
                            listener.onConfirmed();
                        }
                        /* Fechar o diálogo */
                        dismiss();
                    } else {
                        /* Mostra uma mensagem de erro */
                        Toast.makeText(getContext(), getContext().getString(R.string.error_non_integer_calories_value), Toast.LENGTH_SHORT).show();
                        /* Requisição do foco pela entrada de texto para o nome */
                        mCaloriesEditText.requestFocus();
                    }
                } else {
                    /* Mostra uma mensagem de erro */
                    Toast.makeText(getContext(), getContext().getString(R.string.error_empty_fields), Toast.LENGTH_SHORT).show();
                    /* Requisição do foco pela entrada de texto para o nome */
                    mNameEditText.requestFocus();
                }
            }
        });
        mSearchMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchMealsDialog searchMealsDialog = new SearchMealsDialog(getContext(), AddMealDialog.this);
                searchMealsDialog.show();
            }
        });
    }

    /* Método para desassociar os escutadores no diálogo */
    private void dissociateListeners() {
        /* Liberação do escutador de clique do botão de confirmar a adição da refeição */
        mConfirmButton.setOnClickListener(null);
        mSearchMealButton.setOnClickListener(null);
    }

    @Override
    public void onMealSelected(Meal meal) {
        meal.setDate(Utils.getCurrentDateTime(true));
        meal.setTime(Utils.getCurrentDateTime(false));
        meal.setLogin(Controller.getInstance().getUser().getLogin());
        Controller.getInstance().insertMealToDatabase(getContext(), meal);
        for (AddMealDialogListener listener : mListeners) {
            listener.onConfirmed();
        }
        dismiss();
    }

    public interface AddMealDialogListener {
        void onConfirmed();
    }
}
