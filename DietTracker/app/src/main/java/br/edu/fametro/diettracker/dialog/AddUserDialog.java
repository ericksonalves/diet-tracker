package br.edu.fametro.diettracker.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import br.edu.fametro.diettracker.R;
import br.edu.fametro.diettracker.controller.Controller;
import br.edu.fametro.diettracker.model.User;

/**
 * Diálogo para configuração do usuário
 */

public class AddUserDialog extends Dialog {

    /* Botão para salvar os dados do usuário */
    private Button mSaveDataButton;
    /* Entrada de texto para inserção da idade do usuário */
    private EditText mAgeEditText;
    /* Entrada de texto para inserção da confirmação da senha do usuário */
    private EditText mConfirmPasswordEditText;
    /* Entrada de texto para inserção da dieta do usuário */
    private EditText mCurrentDietEditText;
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
    /* Seletor para inserção do gênero do usuário */
    private Spinner mGenderEditText;
    /* Identificador do diálogo */
    private static final String TAG = "AddUserDialog";

    /* Construtor da classe */
    public AddUserDialog(Context context) {
        super(context);
    }

    /* Método chamado ao criar o diálogo */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /* Atribui o visual do diálogo de acordo com o especificado nos recursos da aplicação */
        setContentView(R.layout.dialog_add_user);
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
        mSaveDataButton = (Button) findViewById(R.id.button_save_data);
        mAgeEditText = (EditText) findViewById(R.id.edit_text_new_user_age);
        mConfirmPasswordEditText = (EditText) findViewById(R.id.edit_text_new_user_confirm_password);
        mCurrentDietEditText = (EditText) findViewById(R.id.edit_text_new_user_current_diet);
        mHeightEditText = (EditText) findViewById(R.id.edit_text_new_user_height);
        mNameEditText = (EditText) findViewById(R.id.edit_text_new_user_name);
        mLoginEditText = (EditText) findViewById(R.id.edit_text_new_user_login);
        mPasswordEditText = (EditText) findViewById(R.id.edit_text_new_user_password);
        mWeightEditText = (EditText) findViewById(R.id.edit_text_new_user_weight);
        mGenderEditText = (Spinner) findViewById(R.id.spinner_new_user_gender);
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
        mSaveDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = mLoginEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                String passwordConfirmation = mConfirmPasswordEditText.getText().toString();
                String name = mNameEditText.getText().toString();
                String ageInput = mAgeEditText.getText().toString();
                String weightInput = mWeightEditText.getText().toString();
                String heightInput = mHeightEditText.getText().toString();
                String gender = mGenderEditText.getSelectedItem().toString();
                String currentDietInput = mCurrentDietEditText.getText().toString();
                int age = -1, currentDiet = -1;
                double height = -1, weight = -1;
                try {
                    age = Integer.parseInt(ageInput);
                } catch (Exception e) {
                    android.util.Log.e(TAG, getContext().getString(R.string.error_exception_caught), e);
                }
                try {
                    weight = Double.parseDouble(weightInput);
                } catch (Exception e) {
                    android.util.Log.e(TAG, getContext().getString(R.string.error_exception_caught), e);
                }
                try {
                    height = Double.parseDouble(heightInput);
                } catch (Exception e) {
                    android.util.Log.e(TAG, getContext().getString(R.string.error_exception_caught), e);
                }
                try {
                    currentDiet = Integer.parseInt(currentDietInput);
                } catch (Exception e) {
                    android.util.Log.e(TAG, getContext().getString(R.string.error_exception_caught), e);
                }
                boolean valid = !login.isEmpty() && (!password.isEmpty() && password.equals(passwordConfirmation)) &&
                        !name.isEmpty() && age != -1 && weight != -1 && height != -1 && !gender.isEmpty() && currentDiet != -1;
                if (valid) {
                    User user = new User(age, currentDiet, height, weight, gender, login, name, password);
                    Controller.getInstance().insertUserToDatabase(getContext(), user);
                    dismiss();
                    Toast.makeText(getContext(), getContext().getString(R.string.added_user_confirmation), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), getContext().getString(R.string.error_invalid_data), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /* Método para desassociar os escutadores no diálogo */
    private void dissociateListeners() {
        mSaveDataButton.setOnClickListener(null);
    }

}
