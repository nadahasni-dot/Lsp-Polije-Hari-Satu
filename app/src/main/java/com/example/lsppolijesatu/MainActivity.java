package com.example.lsppolijesatu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lsppolijesatu.database.SQLiteHelper;

public class MainActivity extends AppCompatActivity {
    EditText inputUsername, inputPassword;
    Button btnLogin, btnRegister;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String dataLogin;

    SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputUsername = findViewById(R.id.input_username);
        inputPassword = findViewById(R.id.input_password);

        btnLogin = findViewById(R.id.login_button);
        btnRegister = findViewById(R.id.register_button);

        sharedPreferences = MainActivity.this.getSharedPreferences("Remember", MODE_PRIVATE);
        dataLogin = sharedPreferences.getString("login", "");

        sqLiteHelper = new SQLiteHelper(MainActivity.this);

        if (dataLogin.isEmpty()) {

        } else {
            startActivity(new Intent(MainActivity.this, MapsActivity.class));
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = inputUsername.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if(username.isEmpty()) {
                    inputUsername.setError("masukkan username");
                    return;
                } else {
                    inputUsername.setError(null);
                }

                if(password.isEmpty()) {
                    inputPassword.setError("masukkan password");
                    return;
                } else {
                    inputPassword.setError(null);
                }

                if (sqLiteHelper.getSingleUser(username, password)) {
                    Toast.makeText(MainActivity.this, "Selamat Datang " + username, Toast.LENGTH_SHORT).show();
                    editor = sharedPreferences.edit();
                    editor.putString("login", username);
                    editor.commit();

                    startActivity(new Intent(MainActivity.this, MapsActivity.class));
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Username atau password anda salah", Toast.LENGTH_SHORT).show();

                    inputUsername.setText("");
                    inputPassword.setText("");

                    inputUsername.requestFocus();
                }
            }
        });
    }

    public void registerAction(View view) {
        startActivity(new Intent(MainActivity.this, RegisterActivity.class));
        finish();
    }
}