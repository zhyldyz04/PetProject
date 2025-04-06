package step_definitions;

import com.github.javafaker.Faker;
import entities.RequestBody;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apiguardian.api.API;
import org.junit.Assert;
import utilities.APIRunner;

import java.util.HashMap;
import java.util.Map;

public class SellerApiSteps {

    Faker faker = new Faker();
    String email;
    String sellerName;
    int sellerID;

    @Given("user hits get single seller api with {string}")
    public void user_hits_get_single_seller_api_with(String endpoint) {
        APIRunner.runGET(endpoint, 6334);
        sellerID = APIRunner.getCustomResponse().getSeller_id();

    }
    @Then("verify seller email is not empty")
    public void verify_seller_email_is_not_empty() {
        email = APIRunner.getCustomResponse().getEmail();
        Assert.assertFalse(email.isEmpty());
    }

    @Given("user hits get all seller api with {string}")
    public void user_hits_get_all_seller_api_with(String endpoint) {
        Map<String, Object> params = new HashMap<>();
        params.put("isArchived", false);
        params.put("page", 1);
        params.put("size", 100);

       APIRunner.runGET(endpoint, params);
    }
    @Then("verify sellers ids are not equal {int}")
    public void verify_sellers_ids_are_not_equal(int id) {

        int size =APIRunner.getCustomResponse().getResponses().size();

        for(int i = 0; i < size; i++){
            sellerID = APIRunner.getCustomResponse().getResponses().get(i).getSeller_id();
            Assert.assertNotEquals(id, sellerID);

        }

    }



    @Then("user hits PUT api with {string}")
    public void user_hits_put_api_with(String endpoint) {
        RequestBody requestBody = new RequestBody();
        requestBody.setCompany_name(faker.company().name());
        requestBody.setSeller_name(faker.name().fullName());
        requestBody.setEmail(faker.internet().emailAddress());
        requestBody.setPhone_number(faker.phoneNumber().cellPhone());
        requestBody.setAddress(faker.address().streetAddress());
       APIRunner.runPUT(endpoint, requestBody, 6239);
       email =APIRunner.getCustomResponse().getEmail();
       sellerName = APIRunner.getCustomResponse().getSeller_name();
    }
    @Then("verify user email was updated")
    public void verify_user_email_was_updated() {
        Assert.assertFalse(email.isEmpty());

    }
    @Then("verify user first name was updated")
    public void verify_user_first_name_was_updated() {
        Assert.assertFalse(sellerName.isEmpty());
    }


    @Then("user hits archive api with {string}")
    public void user_hits_archive_api_with(String endpoint) {

        HashMap <String, Object> params =new HashMap<>();
        params.put("sellersIdsForArchive", sellerID);
        params.put("archive", true);
        APIRunner.runPOST(endpoint, params);


    }
    @Then("user hit get all seller api with {string}")
    public void user_hit_get_all_seller_api_with(String endpoint) {

        HashMap <String, Object> params =  new HashMap<>();
        params.put("isArchived", true);
        params.put("page", 1);
        params.put("size", 100);
        APIRunner.runGET(endpoint, params);

        boolean isPresent = false;
        int size = APIRunner.getCustomResponse().getResponses().size();
        for(int i = 0; i < size; i++){
            int IDs = APIRunner.getCustomResponse().getResponses().get(i).getSeller_id();
            if(sellerID == IDs){
                isPresent = true;
                break;
            }
            }
        Assert.assertTrue(isPresent);

        }
    @Given("user hits POST api with {string}")
    public void user_hits_post_api_with(String endpoint) {

        //"company_name": "string",
        //  "seller_name": "string",
        //  "email": "string",
        //  "phone_number": "string",
        //  "address": "string"
        RequestBody requestBody = new RequestBody();
        requestBody.setCompany_name(faker.company().name());
        requestBody.setSeller_name(faker.name().fullName());
        requestBody.setEmail(faker.internet().emailAddress());
        requestBody.setPhone_number(faker.phoneNumber().phoneNumber());
        requestBody.setAddress(faker.address().streetAddress());

        APIRunner.runPOST(endpoint, requestBody);
        sellerID = APIRunner.getCustomResponse().getSeller_id();
        sellerName = APIRunner.getCustomResponse().getSeller_name();

    }
    @Then("verify seller ID was generated")
    public void verify_seller_id_was_generated() {
        Assert.assertTrue(sellerID != 0);
    }
    @Then("verify sellerName is not empty")
    public void verify_seller_name_is_not_empty() {
        Assert.assertFalse(sellerName.isEmpty());
    }
    @Then("DELETE the seller with {string}")
    public void delete_the_seller_with(String endpoint) {
        APIRunner.runDELETE(endpoint + sellerID);

    }
    @Then("verify deleted seller is not on the list")
    public void verify_deleted_seller_is_not_on_the_list() {



    }





}



