package com.phonebook.tests.okhttp;

import com.google.gson.Gson;
import com.phonebook.dto.AuthRequestDto;
import com.phonebook.dto.AuthResponseDto;
import com.phonebook.dto.ErrorDto;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginOkhttpTests {

    OkHttpClient client = new OkHttpClient();
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");

    @Test
    public void loginSuccess() throws IOException {

        AuthRequestDto auth = AuthRequestDto.builder()
                .username("masha1@gmail.com")
                .password("Masha123$")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(auth), JSON);

        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);

        AuthResponseDto responseDto = gson.fromJson(response.body().string(), AuthResponseDto.class);
        System.out.println(responseDto.getToken());
    }

    @Test
    public void loginWithWrongEmail() throws IOException{

    AuthRequestDto auth = AuthRequestDto.builder()
            .username("masha1gmail.com")
            .password("Masha123$")
            .build();

    RequestBody body = RequestBody.create(gson.toJson(auth), JSON);

    Request request = new Request.Builder()
            .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
            .post(body)
            .build();

    Response response = client.newCall(request).execute();
    Assert.assertEquals(response.code(),401);
        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
        Assert.assertEquals(errorDto.getMessage(),"Login or Password incorrect");
    }
}

//eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibWFzaGExQGdtYWlsLmNvbSIsImlzcyI6IlJlZ3VsYWl0IiwiZXhwIjoxNzM1MTQ5MzU2LCJpYXQiOjE3MzQ1NDkzNTZ9.KxT356a2HExkC9tfcrGv_yYjAkQy4Vc9VHE3QLB4eDk