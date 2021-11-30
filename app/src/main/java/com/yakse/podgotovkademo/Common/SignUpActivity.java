package com.yakse.podgotovkademo.Common;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yakse.podgotovkademo.ApiClient;
import com.yakse.podgotovkademo.R;
import com.yakse.podgotovkademo.RegisterRequest;
import com.yakse.podgotovkademo.RegisterResponse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    Button btnSignUp, btnAccount;
    EditText edEmail, edPassword, edFirstName, edSecName, edRePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edEmail = findViewById(R.id.etEmail);
        edFirstName = findViewById(R.id.etFirstName);
        edSecName = findViewById(R.id.etSecondName);
        edPassword = findViewById(R.id.etPassword);
        edRePassword = findViewById(R.id.etRePassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnAccount = findViewById(R.id.btnAccount);

        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(edEmail.getText().toString())||
                        TextUtils.isEmpty(edPassword.getText().toString())||
                        TextUtils.isEmpty(edSecName.getText().toString())||
                        TextUtils.isEmpty(edFirstName.getText().toString())||
                        TextUtils.isEmpty(edRePassword.getText().toString()))
                {
                    ShowAlertDialogWindow("Есть пустые поля!");
                }
                else if (!edPassword.getText().toString().equals(edRePassword.getText().toString()))
                {
                    ShowAlertDialogWindow("Пароли не совпадают");
                }
             /*   else if (!emailValid(edEmail.getText().toString())){
                    ShowAlertDialogWindow("Email не удовлетворяет шаблону");
                }*/
                else {
                    registerUser(); //если условия соблюдены, перейти к функции Регистрации
                }

            }
        });
    }

    //Проверка почты
    private boolean emailValid(String email)
    {
        Pattern emailPattern = Pattern.compile("a-z.+@[a-z]+\\.[a-z]+");
        Matcher emailMatcher = emailPattern.matcher(email);
        return emailMatcher.matches();
    }
    /* Pattern emailPattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");  */

    public void registerUser(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(edEmail.getText().toString());
        registerRequest.setFirstName(edFirstName.getText().toString());
        registerRequest.setLastName(edSecName.getText().toString());
        registerRequest.setPassword(edPassword.getText().toString());

        Call<RegisterResponse> registerResponseCall = ApiClient.getRegister().registerUser(registerRequest); //Вызов Регистрации
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) { //Получили полож ответ для регистрации
                if (response.isSuccessful()){ //Ответный вызов положительный
                    String message = "Все ок...";
                    Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_LONG).show();
                    finish();
                /*    Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                    startActivity(intent);*/
                }else {
                    String message = "Что-то пошло не так";
                    Toast.makeText(SignUpActivity.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable throwable) {
                String message = "Регистрация прошла успешно";
                Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_LONG).show();
                startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
            }
        });

    }

    public void ShowAlertDialogWindow(String s){
        AlertDialog alertDialog = new AlertDialog.Builder(SignUpActivity.this).setMessage(s).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create();
        alertDialog.show();

    }


}