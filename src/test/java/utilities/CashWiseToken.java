package utilities;

import entities.RequestBody;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CashWiseToken {

    public static String GetToken(){
        String endpoint = "https://backend.cashwise.us/api/myaccount/auth/login";

        RequestBody requestBody = new RequestBody();
        requestBody.setEmail("jika41@gmail.com");
        requestBody.setPassword("password1234");

        Response response = RestAssured.given().contentType(ContentType.JSON)
                .body(requestBody).post(endpoint);
        return response.jsonPath().getString("jwt_token");


    }
}
