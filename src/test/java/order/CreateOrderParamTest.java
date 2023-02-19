package order;

import apiLogic.OrderApiLogic;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderParamTest {
    private final String[] color;

    public CreateOrderParamTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {new String[]{""}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK"}},
                {new String[]{"BLACK", "GREY"}},
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Проверка успешного создания заказа с разными цветами")
    public void createOrderPassed() {
        Order order = new Order("Master", "TimeLord", "Universe", "1", "741258", 2, "2023-02-23", "Comment",
                color);

        Response response = OrderApiLogic.createOrder(order);

        response.then().statusCode(201)
                .assertThat().body("track", notNullValue());
    }
}
