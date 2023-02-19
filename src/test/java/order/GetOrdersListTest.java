package order;

import apiLogic.OrderApiLogic;
import constants.ApiConstants;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

public class GetOrdersListTest {
    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = ApiConstants.URL_QA_SCOOTER;
    }

    @Test
    @DisplayName("Проверка получения списка заказов")
    public void getOrderListPassed() {
        Response response = OrderApiLogic.getOrderList();

        response.then().statusCode(200)
                .assertThat().body("orders", notNullValue());
    }
}
