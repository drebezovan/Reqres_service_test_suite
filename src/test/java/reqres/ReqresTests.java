package reqres;

import api.dto.*;
import api.spec.DeleteSpec;
import api.spec.GetSpec;
import api.spec.PostSpec;
import api.spec.PutSpec;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
@Epic("Api tests")
@Feature("Reqres service")
@Story("Методы для взаимодействия с пользователем")
@Link(name = "Документация сервиса", url = "https://reqres.in/")
@Owner("Дребезова Наталья")
public class ReqresTests {
    PostSpec postSpec = new PostSpec();
    PutSpec putSpec = new PutSpec();
    DeleteSpec deleteSpec = new DeleteSpec();
    GetSpec getSpec = new GetSpec();

    @Test
    @Description("Проверка значений status code и id при вводе email и password")
    @DisplayName("Позитивный тест для POST/register")
    public void successCheckId() {
        RegisterRequestDTO register = new RegisterRequestDTO("eve.holt@reqres.in", "pistol");
        RegisterResponseDTO response = PostSpec.postRegisterSuccess(register);
        postSpec.checkId(response, "4");
    }

    @Test
    @Description("Проверка значений status code и error message при вводе только email, без ввода пароля")
    @DisplayName("Негативный тест для POST/register")
    public void unsuccessCheckStatusCode() {
        RegisterRequestDTO register = new RegisterRequestDTO("eve.holt@reqres.in", null);
        RegisterResponseErrorDTO response = PostSpec.postRegisterUnsuccess(register);
        postSpec.checkErrorMsg(response, "Missing password");
    }

    @Test
    @Description("Проверка обновления пользователя при заполнении полей name и job")
    @DisplayName("Позитивный тест для Put/users/{id}")
    public void successUpdateUser() {
        UpdateRequestDTO updateUser = new UpdateRequestDTO("Natasha", "student");
        Response response = PutSpec.putUpdateSuccess(updateUser);
        putSpec.checkUpdateUser(response, updateUser);
    }

    @Test
    @Description("Проверка обновления пользователя при заполнении поля job, но без заполнения поля name ")
    @DisplayName("Негативный тест для Put/users/{id}")
    @Severity(SeverityLevel.CRITICAL)
    public void unuccessUpdateUser() {
        UpdateRequestDTO updateUser = new UpdateRequestDTO(null, "student");
        Response response = PutSpec.putUpdateUnsuccess(updateUser);
        putSpec.checkUpdateUser(response, updateUser);
    }

    @ParameterizedTest
    @Description("Проверка status code при удалении пользователя с существующими id")
    @DisplayName("Позитивный тест для DELETE/users/{id}")
    @ValueSource(strings = {"1", "2"})
    public void successDeleteUser(String id) {
        deleteSpec.deleteUserSuccess(id);
    }

    @ParameterizedTest
    @Description("Проверка status code при удалении пользователя с несуществующими id")
    @DisplayName("Негативный тест для DELETE/users/{id}")
    @Severity(SeverityLevel.CRITICAL)
    @NullSource
    @ValueSource(strings = {"110", "-15"})
    public void unsuccessDeleteUser(String id) {
        deleteSpec.deleteUserUnsuccess(id);
    }

    @Test
    @Description("Проверка совпадения id при вводе и в ответе и проверка того, что почта заканчивается на @reqres.in")
    @DisplayName("Позитивный тест для Get/users/{id}")
    public void successGetSingleUser() {
        String id = "2";
        SingleUserResponseDTO singleUserResponseDTO = GetSpec.getUserSuccess(id);
        getSpec.checkGetUser(singleUserResponseDTO, id);
    }

    @Test
    @Description("Проверка status code при вводе несуществующего id")
    @DisplayName("Негативный тест для Get/users/{id}")
    public void unsuccessGetSingleUser() {
        String id = "100";
        GetSpec.getUserUnsuccess(id);
    }
}