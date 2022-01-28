package com.yakse.podgotovkademo;

import androidx.annotation.AnyRes;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface UserService {

    @POST("/auth/register")
    Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);

    @POST("/auth/login")
    Call<LoginResponse> loginUser  (@Body LoginRequest loginRequest);
}
