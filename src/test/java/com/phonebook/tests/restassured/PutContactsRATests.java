package com.phonebook.tests.restassured;

import com.phonebook.dto.ContactDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class PutContactsRATests extends TastBase{

   private String id;

    @BeforeMethod
    public void precondition() {
        RestAssured.defaultParser = Parser.JSON;
        ContactDto contactDto = ContactDto.builder()
                .name("Yarik")
                .lastName("Suxarik")
                .email("yarik@gm.com")
                .phone("491512368432")
                .address("Nurnberg")
                .description("medik")
                .build();

        String message = given()
                .header(AUTHORIZATION, TOKEN)
                .body(contactDto)
                .contentType(ContentType.JSON)
                .when()
                .post("contacts")
                .then()
                .extract().path("message");

        String[] split = message.split(": ");
        id = split[1];
    }

    @Test
    public void updateContactSuccessTest() {
        ContactDto updatedContactDto = ContactDto.builder()
                .id(id)
                .name("Yarik")
                .lastName("Suxarik")
                .email("yarik@gmail.com")
                .phone("491512368432")
                .address("Nurnberg")
                .description("medik")
                .build();

        given()
                .header(AUTHORIZATION, TOKEN)
                .body(updatedContactDto)
                .contentType(ContentType.JSON)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(200);
         // .assertThat().body("message", equalTo("Contact was updated!"));
    }



    @Test
    public void updateContactWithEmptyFieldsTest() {
        ContactDto updatedContactDto = ContactDto.builder()
                .id(id)
                .name("")
                .lastName("")
                .email("")
                .phone("")
                .address("")
                .description("")
                .build();

        given()
                .header(AUTHORIZATION, TOKEN)
                .body(updatedContactDto)
                .contentType(ContentType.JSON)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(400);

    }

    @Test
    public void updateContactUnauthorizedTest() {
        ContactDto updatedContactDto = ContactDto.builder()
                .id(id)
                .name("Vaselisa")
                .lastName("Premudraya")
                .email("vasya@gm.com")
                .phone("9365421890")
                .address("Rot")
                .description("denser")
                .build();

        given()
                .body(updatedContactDto)
                .contentType(ContentType.JSON)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(403);
         //.assertThat().body("message",containsString("Unauthorized"));
    }



}
