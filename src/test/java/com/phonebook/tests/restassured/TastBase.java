package com.phonebook.tests.restassured;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;

public class TastBase {

    public static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibWFzaGExQGdtYWlsLmNvbSIsImlzcyI6IlJlZ3VsYWl0IiwiZXhwIjoxNzM1MTQ5MzU2LCJpYXQiOjE3MzQ1NDkzNTZ9.KxT356a2HExkC9tfcrGv_yYjAkQy4Vc9VHE3QLB4eDk";
    public static final String AUTHORIZATION = "Authorization";

    @BeforeMethod
    public void init(){
        RestAssured.baseURI="https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath="v1";
    }
}
