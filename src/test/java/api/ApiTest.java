package api;

import com.github.javafaker.Faker;
import entities.RequestBody;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.checkerframework.checker.units.qual.C;
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

@Test
    public void createSeller(){
    String url = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/sellers";
    String token = CashWiseToken.GetToken();
    RequestBody requestBody = new RequestBody();
    requestBody.setCompany_name("Marlen & Co");
    requestBody.setSeller_name("Marlen");
    requestBody.setPhone_number("1235546576");
    requestBody.setAddress("12 Corday 32");
    requestBody.setEmail("jimjim@gmail.com");

    Response response = RestAssured.given().auth().oauth2(token)
            .contentType(ContentType.JSON).body(requestBody).post(url);
    int statusCode = response.statusCode();
    Assert.assertEquals(201, statusCode);

}

@Test
    public void verifyCreatedSeller(){
    /*
    "company_name": "string",
  "seller_name": "string",
  "email": "string",
  "phone_number": "string",
  "address": "string"
}
     */
    Faker faker = new Faker();
    String url = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/sellers";
    String token = CashWiseToken.GetToken();
    RequestBody body = new RequestBody();
    body.setCompany_name(faker.company().name());
    body.setSeller_name(faker.name().fullName());
    body.setPhone_number(faker.phoneNumber().cellPhone());
    body.setAddress(faker.address().streetAddress());
    body.setEmail(faker.internet().emailAddress());

    Response response = RestAssured.given().auth().oauth2(token)
            .contentType(ContentType.JSON).body(body).post(url);

    int status = response.statusCode();
    System.out.println(response.getStatusCode());
    Assert.assertEquals(201,status);
    String sellerID = response.jsonPath().getString("seller_id");
    System.out.println(sellerID);


    Response response1 = RestAssured.given().auth().oauth2(token)
            .contentType(ContentType.JSON).get(url + "/" + sellerID);

    response1.prettyPrint();

    int statusCode = response.statusCode();
    Assert.assertEquals(201, statusCode);



    }




}
