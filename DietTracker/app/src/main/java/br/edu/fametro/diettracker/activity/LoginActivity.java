package br.edu.fametro.diettracker.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ResourceBundle;

import br.edu.fametro.diettracker.R;
import br.edu.fametro.diettracker.controller.Controller;
import br.edu.fametro.diettracker.model.User;

public class LoginActivity extends AppCompatActivity {

    private Button mLoginButton;
    private EditText mLoginEditText;
    private EditText mPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLoginButton = (Button) findViewById(R.id.button_login);
        mLoginEditText = (EditText) findViewById(R.id.edit_text_login);
        mPasswordEditText = (EditText) findViewById(R.id.edit_text_password);
    }

    @Override
    protected void onStart() {
        super.onStart();
        assignListeners();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dissociateListeners();
        finish();
    }

    private void assignListeners() {
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = mLoginEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                if (!login.isEmpty() && !password.isEmpty()) {
                    User user = Controller.getInstance().getUser(LoginActivity.this, login, password);
                    if (user != null) {
                        Controller.getInstance().setUser(user);
                        callMainActivity();
                    } else {
                        Toast.makeText(LoginActivity.this, getString(R.string.error_invalid_user), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.error_empty_fields), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void callMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void dissociateListeners() {
        mLoginButton.setOnClickListener(null);
    }
}
