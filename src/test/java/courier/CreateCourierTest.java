package courier;

import apilogic.CourierApiLogic;
import constants.ApiConstants;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Courier;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;


public class CreateCourierTest {
    static Courier firstCourierWithAllFields;
    static Courier secondCourierWithAllFields;

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = ApiConstants.URL_QA_SCOOTER;

        firstCourierWithAllFields = new Courier("DoctorWho", "DW123", "William");
        secondCourierWithAllFields = new Courier("Spok", "Spok123", "Leonard");
    }

    @AfterClass
    @DisplayName("Удаление курьера")
    public static void clearAfter() {
        Response response = CourierApiLogic.loginCourier(firstCourierWithAllFields);
        int firstCourierLoginId = response.body().jsonPath().getInt("id");

        response = CourierApiLogic.deleteCourier(firstCourierLoginId);
        response.then().statusCode(200)
                .assertThat().body("ok", equalTo(true));

        response = CourierApiLogic.loginCourier(secondCourierWithAllFields);
        int secondCourierLoginId = response.body().jsonPath().getInt("id");

        response = CourierApiLogic.deleteCourier(secondCourierLoginId);
        response.then().statusCode(200)
                .assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Проверка создания курьера")
    public void createNewCourierPassed() {
        Response response = CourierApiLogic.createCourier(firstCourierWithAllFields);
        response.then()
                .statusCode(201)
                .assertThat()
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Проверка создания курьера с существующим логином")
    public void createCourierWithSameLoginFailed() {
        Response response = CourierApiLogic.createCourier(secondCourierWithAllFields);
        response.then()
                .statusCode(201)
                .assertThat()
                .body("ok", equalTo(true));

        response = CourierApiLogic.createCourier(secondCourierWithAllFields);
        response.then()
                .statusCode(409)
                .assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Проверка создания курьера без обязательного поля")
    public void createCourierWithoutRequiredFieldFailed() {
        Courier courierWithoutLogin = new Courier();
        courierWithoutLogin.setPassword("David123");
        courierWithoutLogin.setFirstName("David");

        Response response = CourierApiLogic.createCourier(courierWithoutLogin);
        response.then()
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

        Courier courierWithoutPassword = new Courier();
        courierWithoutPassword.setLogin("Leo46");
        courierWithoutPassword.setFirstName("Leon");

        response = CourierApiLogic.createCourier(courierWithoutPassword);
        response.then()
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}
