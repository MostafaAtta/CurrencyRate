package com.atta.currencyrate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText username, password;
    Button login, register;
    DbHelper db;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);
        if (sessionManager.isLoggedIn()){
            goToMain();
        }

        db = new DbHelper(LoginActivity.this);
        username = findViewById(R.id.user);
        password = findViewById(R.id.password);
        login = findViewById(R.id.btn_login);
        register = findViewById(R.id.btn_to_register);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    public int checkUser(User user){
        return db.checkUser(user);
    }

    @Override
    public void onClick(View view) {
        if (view == login) {
            String name = username.getText().toString();
            String pass = password.getText().toString();

            User user = new User(name, pass);

            int id = checkUser(user);

            if (id == -1) {
                Toast.makeText(LoginActivity.this, "User Does Not Exist", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoginActivity.this, "User Exist " + name, Toast.LENGTH_SHORT).show();

                sessionManager.saveUser(user);
                goToMain();
            }
        }else if (view == register){
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void goToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
