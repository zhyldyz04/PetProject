package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sellers {
    @Test
    public void createSellers() throws JsonProcessingException {
        Faker faker = new Faker();
        String url = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/sellers";
        String token = CashWiseToken.GetToken();

        //company_name": "string",
        //  "seller_name": "string",
        //  "email": "string",
        //  "phone_number": "string",
        //  "address": "string"
        List<String> sellers = new ArrayList<>();
        RequestBody body = new RequestBody();
        Response response;
        for (int i = 0; i <= 15; i++) {

            body.setCompany_name(faker.company().name());
            body.setSeller_name(faker.name().fullName());
            body.setEmail(faker.internet().emailAddress());
            body.setPhone_number(faker.phoneNumber().cellPhone());
            body.setAddress(faker.address().streetAddress());

            response = RestAssured.given().auth().oauth2(token)
                    .contentType(ContentType.JSON).body(body).post(url);

            Assert.assertEquals(201, response.statusCode());

        }


        HashMap<String, Object> params = new HashMap<>();
        params.put("isArchived", false);
        params.put("page", 1);
        params.put("size", 100);

        Response response1 = RestAssured.given().auth().oauth2(token)
                .contentType(ContentType.JSON).params(params).get(url);

        int status = response1.statusCode();
        Assert.assertEquals(200, status);

        ObjectMapper mapper = new ObjectMapper();
        CustomResponse customResponse = mapper.readValue(response1.asString(), CustomResponse.class);

        int size = customResponse.getResponses().size();
        for (int i = 0; i < size; i++) {
            int id = customResponse.getResponses().get(i).getCategory_id();


            String email = customResponse.getResponses().get(i).getEmail();
            Assert.assertFalse(email.isEmpty());
        }


    }

    @Test
    public void createSellerWithNoEmail() {
        String url = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/sellers";
        String token = CashWiseToken.GetToken();

        RequestBody body = new RequestBody();
        body.setCompany_name("TomTom and Co");
        body.setSeller_name("Tom");
        body.setPhone_number("4245235347");
        body.setAddress("123 Chicago st");

        Response response = RestAssured.given().auth().oauth2(token)
                .contentType(ContentType.JSON).body(body).post(url);

        response.prettyPrint();
        Assert.assertEquals(201, response.statusCode());
        System.out.println(response.statusCode());
    }

    @Test
    public void unarchiveArchiveSeller() {
        String url = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/sellers/archive/unarchive";
        String token = CashWiseToken.GetToken();

        HashMap<String, Object> params = new HashMap<>();
        params.put("archive", true);
        params.put("sellersIdsForArchive", 6328);

        Response response = RestAssured.given().auth().oauth2(token)
                .params(params).post(url);

        response.statusCode();
        Assert.assertEquals(200, response.statusCode());

    }

    @Test
    public void archiveMultipleSellers() throws JsonProcessingException {
        String getUrl = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/sellers";
        String token = CashWiseToken.GetToken();

        HashMap<String, Object> params = new HashMap<>();
        params.put("isArchived", false);
        params.put("page", 1);
        params.put("size", 50);

        Response response = RestAssured.given().auth().oauth2(token)
                .contentType(ContentType.JSON).params(params).get(getUrl);

        Assert.assertEquals(200, response.statusCode());

        ObjectMapper mapper = new ObjectMapper();
        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);


        int size = customResponse.getResponses().size();

        String archiveUrl = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/sellers/archive/unarchive";

        HashMap<String, Object> archiveParams = new HashMap<>();


        for (int i = 0; i < size; i++) {

            archiveParams.put("sellersIdsForArchive", customResponse.getResponses().get(i).getSeller_id());
            archiveParams.put("archive", true);

            Response archiveResponse = RestAssured.given().auth().oauth2(token)
                    .params(archiveParams).post(archiveUrl);

            Assert.assertEquals(200, archiveResponse.statusCode());
        }
    }

    @Test
    public void unArchiveSeller() throws JsonProcessingException {
        String getUrl = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/sellers";
        String token = CashWiseToken.GetToken();

        HashMap<String, Object> params = new HashMap<>();
        params.put("isArchived", true);
        params.put("page", 1);
        params.put("size", 100);


        Response response = RestAssured.given().auth().oauth2(token).params(params).get(getUrl);
        Assert.assertEquals(200, response.statusCode());

        ObjectMapper mapper = new ObjectMapper();
        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);

        int size = customResponse.getResponses().size();

        String archiveURl = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/sellers/archive/unarchive";

        for (int i = 0; i < size; i++) {
            if(customResponse.getResponses().get(i).getEmail() != null){

            if (customResponse.getResponses().get(i).getEmail().endsWith("@hotmail.com")) {
                int id = customResponse.getResponses().get(i).getSeller_id();

                Map<String, Object> archiveParams = new HashMap<>();
                archiveParams.put("sellersIdsForArchive", id);
                archiveParams.put("archive", false);

                Response response1 = RestAssured.given().auth().oauth2(token)
                        .params(archiveParams).post(archiveURl);
                Assert.assertEquals(200, response1.statusCode());

            }
            }


        }


    }

    @Test
    public void verifyCreatedSeller() throws JsonProcessingException {
        String postUrl = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/sellers";
        String token = CashWiseToken.GetToken();
        Faker faker = new Faker();

        //"company_name": "string",
        //  "seller_name": "string",
        //  "email": "string",
        //  "phone_number": "string",
        //  "address": "string"

        RequestBody body = new RequestBody();
        body.setCompany_name(faker.company().name());
        body.setSeller_name(faker.name().fullName());
        body.setEmail(faker.internet().emailAddress());
        body.setPhone_number(faker.phoneNumber().cellPhone());
        body.setAddress(faker.address().streetAddress());

        Response response = RestAssured.given().auth().oauth2(token)
                .contentType(ContentType.JSON).body(body).post(postUrl);

        Assert.assertEquals(201, response.statusCode());


        ObjectMapper mapper = new ObjectMapper();
        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        int newSellerId = customResponse.getSeller_id();

        String getUrl = Config.getProperty("cashwiseApiUrl") + "/api/myaccount/sellers";
        Map <String, Object> params = new HashMap<>();
        params.put("isArchived", false);
        params.put("page", 1);
        params.put("size", 100);


        Response response1 = RestAssured.given().auth().oauth2(token).params(params).get(getUrl);
        Assert.assertEquals(200, response1.statusCode());

        CustomResponse customResponse1 = mapper.readValue(response1.asString(), CustomResponse.class);
        int size = customResponse1.getResponses().size();

        boolean isCreated = false;
        for(int i = 0; i < size; i++){
            if(customResponse1.getResponses().get(i).getSeller_id() == newSellerId){
                isCreated = true;
                break;
            }
        }
        Assert.assertTrue(isCreated);





    }




}
