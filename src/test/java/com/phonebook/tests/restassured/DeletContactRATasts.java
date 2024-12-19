package com.phonebook.tests.restassured;

import com.phonebook.dto.ContactDto;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class DeletContactRATasts extends TastBase{

    String id;

    @BeforeMethod
    public void precondition(){
        ContactDto contactDto = ContactDto.builder()
                .name("Yarik")
                .lastName("Suxarik")
                .email("yarik@gm.com")
                .phone("491512368432")
                .address("Nurnberg")
                .description("medik")
                .build();

        String message = given()
                .header(AUTHORIZATION,TOKEN)
                .body(contactDto)
                .contentType(ContentType.JSON)
                .when()
                .post("contacts")
                .then()
                .extract().path("message");

       // System.out.println(message);

        String[] split = message.split(": ");
        id = split[1];
    }

    @Test
    public void deleteContactSuccessTest(){
       //String message =
                given()
                .header(AUTHORIZATION,TOKEN)
                .when()
                .delete("contacts/" + id)
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message",equalTo("Contact was deleted!"));
                //.extract().path("message");
        //System.out.println(message);
    }

    @Test
    public void DeleteContactByWrongId() {
        //ErrorDto errorDto =
        given()
                .header(AUTHORIZATION, TOKEN)
                .when()
                .delete("contacts/33d9732b-27bd-46b6-88f4-46951b691a66")
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message", containsString("not found in your contacts"));
        //  .extract().body().as(ErrorDto.class);
        //System.out.println(errorDto.getMessage());
    }
}
