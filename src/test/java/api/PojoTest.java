package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.ConfigFeature;
import com.github.javafaker.Faker;
import entities.CustomResponse;
import entities.RequestBody;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.checkerframework.checker.units.qual.C;
import org.junit.Assert;
import org.junit.Test;
import utilities.CashWiseToken;
import utilities.Config;

public class PojoTest {

    int categoryID;


    @Test
    public void createCategory() throws JsonProcessingException {
        Faker faker = new Faker();
        String url = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/categories";
        String token = CashWiseToken.GetToken();

        RequestBody body = new RequestBody();
        body.setCategory_title(faker.name().title());
        body.setCategory_description(faker.company().profession());
        body.setFlag(true);

        Response response = RestAssured.given().auth().oauth2(token)
                .contentType(ContentType.JSON).body(body).post(url);

        int status = response.statusCode();
        Assert.assertEquals(201, status);

        ObjectMapper mapper = new ObjectMapper();
        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        System.out.println(customResponse.getCategory_id());
        categoryID = customResponse.getCategory_id();


        System.out.println(categoryID);
        Response response1 = RestAssured.given().auth().oauth2(token)
                .contentType(ContentType.JSON).get(url + "/" + categoryID);
        response1.getStatusCode();


        CustomResponse customResponse1 = mapper.readValue(response1.asString(), CustomResponse.class);
        response1.getStatusCode();
        Assert.assertEquals(200, response1.statusCode());
        Assert.assertEquals(customResponse.getCategory_id(), customResponse1.getCategory_id());

    }

}
