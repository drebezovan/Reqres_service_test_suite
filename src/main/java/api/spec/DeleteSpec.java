package api.spec;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class DeleteSpec {
    public void deleteUserSuccess(String id) {
        given()
                .spec(ReqresSpec.getRequestSpec())
                .when()
                .contentType(ContentType.JSON)
                .delete(String.format("users/%s", id))
                .then()
                .spec(ReqresSpec.getResponseSpec(204));
    }

    public void deleteUserUnsuccess(String id) {
        given()
                .spec(ReqresSpec.getRequestSpec())
                .when()
                .contentType(ContentType.JSON)
                .delete(String.format("users/%s", id))
                .then()
                .spec(ReqresSpec.getResponseSpec(404));
    }
}