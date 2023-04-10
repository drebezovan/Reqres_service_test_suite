package api.spec;


import api.dto.SingleUserResponseDTO;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;

import static io.restassured.RestAssured.given;

public class GetSpec {
    public static SingleUserResponseDTO getUserSuccess(String id) {
        return given()
                .spec(ReqresSpec.getRequestSpec())
                .when()
                .contentType(ContentType.JSON)
                .get(String.format("users/%S", id))
                .then()
                .spec(ReqresSpec.getResponseSpec(200))
                .extract().body().jsonPath().getObject(".", SingleUserResponseDTO.class);

    }

    public static void getUserUnsuccess(String id) {
        given()
                .spec(ReqresSpec.getRequestSpec())
                .when()
                .contentType(ContentType.JSON)
                .get(String.format("users/%S", id))
                .then()
                .spec(ReqresSpec.getResponseSpec(404))
                .extract().body().jsonPath().getObject(".", SingleUserResponseDTO.class);

    }

    public void checkGetUser(SingleUserResponseDTO response, String id) {
        Assertions.assertEquals(id, response.getData().getId());
        Assertions.assertTrue(response.getData().getEMail().endsWith("@reqres.in"));
    }
}