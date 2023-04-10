package api.spec;

import api.dto.UpdateRequestDTO;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import static io.restassured.RestAssured.given;

public class PutSpec {
    public static Response putUpdateSuccess(UpdateRequestDTO updateUser) {
        return given()
                .spec(ReqresSpec.getRequestSpec())
                .pathParam("id", 2)
                .when()
                .body(updateUser)
                .contentType(ContentType.JSON)
                .put("users/{id}")
                .then()
                .spec(ReqresSpec.getResponseSpec(200))
                .extract().response();
    }

    public static Response putUpdateUnsuccess(UpdateRequestDTO updateUser) {
        return given()
                .spec(ReqresSpec.getRequestSpec())
                .pathParam("id", 2)
                .when()
                .body(updateUser)
                .contentType(ContentType.JSON)
                .put("users/{id}")
                .then()
                .spec(ReqresSpec.getResponseSpec(400))
                .extract().response();
    }

    public void checkUpdateUser(Response response, UpdateRequestDTO updateUser) {
        Assertions.assertEquals(updateUser.getName(), response.jsonPath().get("name"));
        Assertions.assertEquals(updateUser.getJob(), response.jsonPath().get("job"));
    }
}