package com.yakse.podgotovkademo;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
   @SerializedName("status_code")
    private Integer statuscode;

    @SerializedName("auth_token")
    private String authToken;


    /*
    private int token;

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }*/
}
