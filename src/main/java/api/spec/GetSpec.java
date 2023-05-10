package api.spec;


import api.dto.SingleUserResponseDTO;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;

import static io.restassured.RestAssured.given;

public class GetSpec {
    @Step("Отправить зарос Get/users/{id} с параметром id = {0}")
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
    @Step("Отправить зарос Get/users/{id} с параметром id = {0}" )
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
    @Step("Проверить id и на что оканчивается почта пользователя")
    public void checkGetUser(SingleUserResponseDTO response, String id) {
        Assertions.assertEquals(id, response.getData().getId());
        Assertions.assertTrue(response.getData().getEMail().endsWith("@reqres.in"));
    }
}