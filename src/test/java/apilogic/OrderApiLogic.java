package apilogic;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.Order;

import static constants.ApiConstants.ORDER_API_ROOT;
import static io.restassured.RestAssured.given;

public class OrderApiLogic {

    @Step("Отправка POST-запроса для создания заказа")
    public static Response createOrder(Order order) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post(ORDER_API_ROOT);
    }

    @Step("Отправка GET-запроса для получения списка заказов")
    public static Response getOrderList() {
        return given().get(ORDER_API_ROOT);
    }
}
