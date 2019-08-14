package com.atta.currencyrate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {


    EditText username, password;
    Button register;
    DbHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        username = findViewById(R.id.register_user);
        password = findViewById(R.id.register_password);
        register = findViewById(R.id.btn_register);

        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        String name = username.getText().toString();
        String pass = password.getText().toString();


        if (name.isEmpty()) {
            username.setError("Username is required");
            username.requestFocus();
            return;
        }
        if (pass.isEmpty()) {
            password.setError("Password is required");
            password.requestFocus();
            return;
        }

        db = new DbHelper(RegisterActivity.this);
        //inserting user
        db.addUser(new User(name, pass));


        Toast.makeText(RegisterActivity.this, "User Created " + name, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
