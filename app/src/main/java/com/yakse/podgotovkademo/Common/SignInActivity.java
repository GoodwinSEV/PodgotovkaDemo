package com.yakse.podgotovkademo.Common;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yakse.podgotovkademo.ApiClient;
import com.yakse.podgotovkademo.LoginRequest;
import com.yakse.podgotovkademo.LoginResponse;
import com.yakse.podgotovkademo.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {
    EditText edEmail, edPassword;
    Button btnSignIn, btnSignUp1;
 //   SharedPreferences sPref;
//    final String saveg = "key";

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
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
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
                   /*  LoginResponse loginResponse = response.body();

                     sPref = getSharedPreferences("pref", MODE_PRIVATE);
                     SharedPreferences.Editor ed = sPref.edit();
                     int message = loginResponse.getToken();
                     ed.putString(saveg, String.valueOf(message));
                     ed.apply();*/

                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent);

                    Toast.makeText(SignInActivity.this, "???? ?????????????? ??????????!", Toast.LENGTH_LONG).show();
                    finish();
                }
                else {
                    String message = "??????-???? ?????????? ???? ??????";
                    Toast.makeText(SignInActivity.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                //ShowAlertDialogWindow("???? ?????????? ???????????????? ????????????!");
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean isEmailValid(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void ShowAlertDialogWindow(String s){
        AlertDialog alertDialog = new AlertDialog.Builder(SignInActivity.this).setMessage(s).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create();
        alertDialog.show();

    }
}