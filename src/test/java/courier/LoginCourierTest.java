package courier;

import apiLogic.CourierApiLogic;
import constants.ApiConstants;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Courier;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest {
    private static final String login = "DoctorWho";
    private static final String password = "DW123";
    private static final String wrongPassword = "wrongPsw";
    private static final String firstName = "William";
    static Courier courier;

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = ApiConstants.URL_QA_SCOOTER;
        courier = new Courier(login, password, firstName);
        Response response = CourierApiLogic.createCourier(courier);
        response.then()
                .statusCode(201)
                .assertThat()
                .body("ok", equalTo(true));
    }

    @AfterClass
    public static void clearAfter() {
        Response response = CourierApiLogic.loginCourier(courier);
        int courierId = response.body().jsonPath().getInt("id");

        response = CourierApiLogic.deleteCourier(courierId);
        response.then().statusCode(200)
                .assertThat().body("ok", equalTo(true));

    }

    @Test
    @DisplayName("Успешная авторизация")
    public void loginCourierPassed() {
        Response response = CourierApiLogic.loginCourier(courier);
        response.then()
                .statusCode(200)
                .assertThat()
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Авторизация с неверным паролем")
    public void loginCourierWithWrongPasswordFailed() {
        Courier courierWithWrongPassword = new Courier(login, wrongPassword, firstName);
        Response response = CourierApiLogic.loginCourier(courierWithWrongPassword);

        response.then()
                .statusCode(404)
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация без поля логин")
    public void loginCourierWithoutLoginFailed() {
        Courier courierWithoutLogin = new Courier();
        courierWithoutLogin.setFirstName(firstName);
        courierWithoutLogin.setPassword(password);

        Response response = CourierApiLogic.loginCourier(courierWithoutLogin);
        response.then()
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация по несуществующему логину")
    public void loginNoExistCourierFailed() {
        Courier noExistCourier = new Courier("Anonimov", "123456", "ivan");

        Response response = CourierApiLogic.loginCourier(noExistCourier);
        response.then()
                .statusCode(404)
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"));
    }
}
