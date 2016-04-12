package br.edu.fametro.diettracker.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import br.edu.fametro.diettracker.R;
import br.edu.fametro.diettracker.controller.Controller;
import br.edu.fametro.diettracker.model.User;
import br.edu.fametro.diettracker.util.Utils;

/**
 * Diálogo para configuração do usuário
 */

public class UserSettingsDialog extends Dialog {

    /* Botão para atualizar os dados do usuário */
    private Button mUpdateDataButton;
    /* Entrada de texto para inserção da idade do usuário */
    private EditText mAgeEditText;
    /* Entrada de texto para inserção da dieta do usuário */
    private EditText mCurrentDietEditText;
    /* Entrada de texto para inserção do gênero do usuário */
    private EditText mGenderEditText;
    /* Entrada de texto para inserção da altura do usuário */
    private EditText mHeightEditText;
    /* Entrada de texto para inserção do nome do usuário */
    private EditText mNameEditText;
    /* Entrada de texto para inserção do login do usuário */
    private EditText mLoginEditText;
    /* Entrada de texto para inserção da senha do usuário */
    private EditText mPasswordEditText;
    /* Entrada de texto para inserção do peso do usuário */
    private EditText mWeightEditText;

    /* Construtor da classe */
    public UserSettingsDialog(Context context) {
        super(context);
    }

    /* Método chamado ao criar o diálogo */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /* Atribui o visual do diálogo de acordo com o especificado nos recursos da aplicação */
        setContentView(R.layout.dialog_user_settings);
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
        mUpdateDataButton = (Button) findViewById(R.id.button_update_data);
        mAgeEditText = (EditText) findViewById(R.id.edit_text_user_age);
        mCurrentDietEditText = (EditText) findViewById(R.id.edit_text_user_current_diet);
        mHeightEditText = (EditText) findViewById(R.id.edit_text_user_height);
        mNameEditText = (EditText) findViewById(R.id.edit_text_user_name);
        mLoginEditText = (EditText) findViewById(R.id.edit_text_user_login);
        mPasswordEditText = (EditText) findViewById(R.id.edit_text_user_password);
        mWeightEditText = (EditText) findViewById(R.id.edit_text_user_weight);
        mGenderEditText = (EditText) findViewById(R.id.edit_text_user_gender);
    }

    /* Método chamado ao iniciar o diálogo */
    @Override
    protected void onStart() {
        super.onStart();
        /* Chamada da atribuição de escutadores */
        assignListeners();
        /* Chamada da preparação dos dados */
        prepareData();
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
        mUpdateDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    /* Método para desassociar os escutadores no diálogo */
    private void dissociateListeners() {
        mUpdateDataButton.setOnClickListener(null);
    }

    /* Método para preparar os dados do usuário */
    private void prepareData() {
        User user = Controller.getInstance().getUser();
        mAgeEditText.setText(String.valueOf(user.getAge()));
        mCurrentDietEditText.setText(String.valueOf(user.getCurrentDietCalories()));
        mHeightEditText.setText(Utils.getTwoDecimalPlacesString(user.getHeight()));
        mNameEditText.setText(user.getName());
        mLoginEditText.setText(user.getLogin());
        mPasswordEditText.setText(user.getPassword());
        mWeightEditText.setText(Utils.getTwoDecimalPlacesString(user.getWeight()));
        mGenderEditText.setText(user.getGender());
    }

}
