package api;

import entities.RequestBody;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import utilities.CashWiseToken;
import utilities.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiTest {

@Test
        public void testToken(){
    String endpoint = "https://backend.cashwise.us/api/myaccount/auth/login";
    RequestBody requestBody = new RequestBody();
    requestBody.setEmail("jika41@gmail.com");
    requestBody.setPassword("password1234");

    Response response = RestAssured.given().contentType(ContentType.JSON)
            .body(requestBody).post(endpoint);
    int statusCode = response.statusCode();
    Assert.assertEquals(200, statusCode);
    response.prettyPrint();
    String token = response.jsonPath().getString("jwt_token");
    System.out.println(token);


}

@Test
    public void getSingleSeller(){
    String url = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/sellers/" + 6217;
    String token = CashWiseToken.GetToken();

    Response response = RestAssured.given().auth().oauth2(token).get(url);
    response.prettyPrint();

    String expectedEmail = response.jsonPath().getString("email");

    Assert.assertFalse(expectedEmail.isEmpty());
    Assert.assertTrue(expectedEmail.endsWith(".com"));

}

@Test
    public void getAllSellers(){
    String url = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/sellers";
    String token = CashWiseToken.GetToken();

    Map<String, Object> params = new HashMap<>();
    params.put("isArchived", false);
    params.put("size", 10);
    params.put("page", 1);

    Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);
    int statusCode = response.statusCode();

    Assert.assertEquals(200, statusCode);
    //response.prettyPrint();
    String email = response.jsonPath().get("responses[0].email");
    Assert.assertFalse(email.isEmpty());


    String email3 = response.jsonPath().getString("responses[2].email");
    Assert.assertFalse(email3.isEmpty());

    String email5 = response.jsonPath().get("responses[4].email");
}

@Test
    public void getAllSellersLoop(){
    String url = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/sellers";
    String token = CashWiseToken.GetToken();

    Map <String, Object> params = new HashMap<>();
    params.put("isArchived", false);
    params.put("page", 1);
    params.put("size", 10);

    Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);
    response.prettyPrint();

    List<String> listOfEmails = response.jsonPath().getList("responses.email");
    for (String emails : listOfEmails) {
        Assert.assertFalse(emails.isEmpty());

    }

}




}
