package api.spec;

import api.dto.RegisterRequestDTO;
import api.dto.RegisterResponseDTO;
import api.dto.RegisterResponseErrorDTO;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;

import static io.restassured.RestAssured.given;

public class PostSpec {
    public static RegisterResponseDTO postRegisterSuccess(RegisterRequestDTO register) {
        return given()
                .spec(ReqresSpec.getRequestSpec())
                .when()
                .body(register)
                .contentType(ContentType.JSON)
                .post("register")
                .then()
                .spec(ReqresSpec.getResponseSpec(200))
                .extract().body().jsonPath().getObject(".", RegisterResponseDTO.class);
    }

    public void checkId(RegisterResponseDTO response, String id) {
        Assertions.assertEquals(id, response.getId());
    }

    public static RegisterResponseErrorDTO postRegisterUnsuccess(RegisterRequestDTO register) {
        return given()
                .spec(ReqresSpec.getRequestSpec())
                .when()
                .body(register)
                .contentType(ContentType.JSON)
                .post("register")
                .then()
                .spec(ReqresSpec.getResponseSpec(400))
                .extract().body().jsonPath().getObject(".", RegisterResponseErrorDTO.class);
    }

    public void checkErrorMsg(RegisterResponseErrorDTO response, String msg) {
        Assertions.assertEquals(msg, response.getError());
    }
}