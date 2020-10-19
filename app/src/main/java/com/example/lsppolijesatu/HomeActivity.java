package com.example.lsppolijesatu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lsppolijesatu.database.SQLiteHelper;
import com.example.lsppolijesatu.model.ModelDaftar;

public class HomeActivity extends AppCompatActivity {
    Button logoutButton;
    TextView textNama, textUsername, textEmail, textPassword;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    SQLiteHelper sqLiteHelper;

    String nama, email, username, password, savedUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sqLiteHelper = new SQLiteHelper(HomeActivity.this);

        sharedPreferences = HomeActivity.this.getSharedPreferences("Remember", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        savedUsername = sharedPreferences.getString("login", null);

        ModelDaftar modelDaftar = sqLiteHelper.getUserByUsername(savedUsername);

        nama = "Nama: " + modelDaftar.getNama();
        email = "Email: " + modelDaftar.getEmail();
        username = "Username: " + modelDaftar.getUsername();
        password = "Password: " + modelDaftar.getPassword();

        textNama = findViewById(R.id.text_nama);
        textEmail = findViewById(R.id.text_email);
        textUsername = findViewById(R.id.text_username);
        textPassword = findViewById(R.id.text_password);

        textNama.setText(nama);
        textEmail.setText(email);
        textUsername.setText(username);
        textPassword.setText(password);

        logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.commit();

                startActivity(new Intent(HomeActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}