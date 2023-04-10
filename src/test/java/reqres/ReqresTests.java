package reqres;

import api.dto.*;
import api.spec.DeleteSpec;
import api.spec.GetSpec;
import api.spec.PostSpec;
import api.spec.PutSpec;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

public class ReqresTests {
    PostSpec postSpec = new PostSpec();
    PutSpec putSpec = new PutSpec();
    DeleteSpec deleteSpec = new DeleteSpec();
    GetSpec getSpec = new GetSpec();

    @Test
    @DisplayName("Позитивный тест для POST/register с проверкой status code = 204 и id = 4, тест прошел")
    public void successCheckId() {
        RegisterRequestDTO register = new RegisterRequestDTO("eve.holt@reqres.in", "pistol");
        RegisterResponseDTO response = PostSpec.postRegisterSuccess(register);
        postSpec.checkId(response, "4");
    }

    @Test
    @DisplayName("Негативный тест для POST/register с проверкой status code = 400 и error message, тест прошел")
    public void unsuccessCheckStatusCode() {
        RegisterRequestDTO register = new RegisterRequestDTO("eve.holt@reqres.in", null);
        RegisterResponseErrorDTO response = PostSpec.postRegisterUnsuccess(register);
        postSpec.checkErrorMsg(response, "Missing password");
    }

    @Test
    @DisplayName("Позитивный тест для Put/users/{id} с проверкой обновления пользователя, тест прошел ")
    public void successUpdateUser() {
        UpdateRequestDTO updateUser = new UpdateRequestDTO("Natasha", "student");
        Response response = PutSpec.putUpdateSuccess(updateUser);
        putSpec.checkUpdateUser(response, updateUser);
    }

    @Test
    @DisplayName("Негативный тест для Put/users/{id} с проверкой обновления пользователя, тест упал ")
    public void unuccessUpdateUser() {
        UpdateRequestDTO updateUser = new UpdateRequestDTO(null, "student");
        Response response = PutSpec.putUpdateUnsuccess(updateUser);
        putSpec.checkUpdateUser(response, updateUser);
    }

    @ParameterizedTest
    @DisplayName("Позитивная проверка для DELETE/users/{id} status code = 204, тест прошел")
    @ValueSource(strings = {"1", "2"})
    public void successDeleteUser(String id) {
        deleteSpec.deleteUserSuccess(id);
    }

    @ParameterizedTest
    @DisplayName("Негативная проверка для DELETE/users/{id} status code = 404, тест упал ")
    @NullSource
    @ValueSource(strings = {"110", "-15"})
    public void unsuccessDeleteUser(String id) {
        deleteSpec.deleteUserUnsuccess(id);
    }

    @Test
    @DisplayName("Позитивная проверка для Get/users/{id} id и формата почты, тест прошел")
    public void successGetSingleUser() {
        String id = "2";
        SingleUserResponseDTO singleUserResponseDTO = GetSpec.getUserSuccess(id);
        getSpec.checkGetUser(singleUserResponseDTO, id);
    }

    @Test
    @DisplayName("Негативная проверка для Get/users/{id} status code = 404, тест прошел")
    public void unsuccessGetSingleUser() {
        String id = "100";
        GetSpec.getUserUnsuccess(id);
    }
}