package com.yakse.podgotovkademo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {
    EditText edEmail, edPassword;
    Button btnSignIn, btnSignUp1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp1 = findViewById(R.id.btnSignUp1);
        edEmail = findViewById(R.id.etEmail1);
        edPassword = findViewById(R.id.etPassword1);

        btnSignUp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginUser();
            }
        });
    }

    private void loginUser(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(edEmail.getText().toString());
        loginRequest.setPassword(edPassword.getText().toString());

       Call<LoginResponse> loginResponseCall = ApiClient.getLogin().loginUser(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()){

                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent);

                    Toast.makeText(SignInActivity.this, "Вы успешно вошли!", Toast.LENGTH_LONG).show();
                    finish();
                }
                else {
                    Toast.makeText(SignInActivity.this, "Что-то пошло не так", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                Toast.makeText(SignInActivity.this, "Такого пользователя не существует", Toast.LENGTH_LONG).show();

                //Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}