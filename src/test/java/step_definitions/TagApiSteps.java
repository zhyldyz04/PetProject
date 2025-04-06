package step_definitions;

import com.github.javafaker.Faker;
import entities.RequestBody;
import io.cucumber.java.en.*;
import utilities.APIRunner;

public class TagApiSteps {
    Faker faker = new Faker();
    RequestBody body = new RequestBody();

    @Given("user hits Post with api {string}")
    public void user_hits_post_with_api(String endpoint) {
        body.setName_tag(faker.funnyName().name());
        body.setDescription(faker.animal().name());
        APIRunner.runPOST(endpoint, body);



    }
    @Then("user hits Get with api {string} to get all tags")
    public void user_hits_get_with_api_to_get_all_tags(String endpoint) {
    

    }
    @Then("verify that tag has been created")
    public void verify_that_tag_has_been_created() {

    }





}
