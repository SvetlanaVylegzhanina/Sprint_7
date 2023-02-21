package apilogic;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.Courier;

import static constants.ApiConstants.*;
import static io.restassured.RestAssured.given;

public class CourierApiLogic {

    @Step("Отправка POST-запроса на создание курьера")
    public static Response createCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(COURIER_API_CREATE);
    }

    @Step("Отправка DELETE-запроса для удаления курьера")
    public static Response deleteCourier(int courierId) {
        return given().header("Content-type", "application/json")
                .and()
                .when()
                .delete(COURIER_API_DELETE + courierId);
    }

    @Step("Отправка POST- запроса для авторизации")
    public static Response loginCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(COURIER_API_LOGIN);
    }
}
