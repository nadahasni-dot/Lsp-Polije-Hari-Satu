package com.example.lsppolijesatu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lsppolijesatu.database.SQLiteHelper;
import com.example.lsppolijesatu.model.ModelDaftar;

public class RegisterActivity extends AppCompatActivity {
    EditText inputNama, inputEmail, inputUsername, inputPassword;
    Button buttonRegister, buttonClear;

    SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputNama = findViewById(R.id.input_registrasi_nama);
        inputEmail = findViewById(R.id.input_registrasi_email);
        inputUsername = findViewById(R.id.input_registrasi_username);
        inputPassword = findViewById(R.id.input_registrasi_password);

        buttonRegister = findViewById(R.id.register_save_button);
        buttonClear = findViewById(R.id.register_clear_button);

        sqLiteHelper = new SQLiteHelper(RegisterActivity.this);

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputNama.setText("");
                inputUsername.setText("");
                inputPassword.setText("");
                inputEmail.setText("");
                inputNama.requestFocus();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = inputNama.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String username = inputUsername.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if(nama.isEmpty()) {
                    inputNama.setError("masukkan nama");
                    return;
                } else {
                    inputNama.setError(null);
                }

                if(email.isEmpty()) {
                    inputEmail.setError("masukkan email");
                    return;
                } else {
                    inputEmail.setError(null);
                }

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

                if (sqLiteHelper.insertUser(new ModelDaftar(nama, email, username, password))) {
                    Toast.makeText(RegisterActivity.this, "berhasil daftar", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Username telah digunakan", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        finish();
    }
}