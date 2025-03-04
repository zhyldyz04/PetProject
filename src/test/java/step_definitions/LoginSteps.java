package step_definitions;

import io.cucumber.java.en.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import pages.LoginPage;
import utilities.Config;
import utilities.Driver;

public class LoginSteps {
    private static final Log log = LogFactory.getLog(LoginSteps.class);
    LoginPage loginPage = new LoginPage();

    @Given("user is on the log in page")
    public void user_is_on_the_log_in_page() {
        Driver.getDriver().get(Config.getProperty("saucedemoURL"));

    }
    @Then("user provide valid username")
    public void user_provide_valid_username() {
        loginPage.usernameInput.sendKeys(Config.getProperty("saucedemoUsername"));


    }
    @Then("user provide valid password")
    public void user_provide_valid_password() {
        loginPage.passwordInput.sendKeys(Config.getProperty("saucedemoPassword"));
    }
    @Then("user clicks on login button")
    public void user_clicks_on_login_button() {
        loginPage.loginBtn.click();
    }
    @Then("verify user logged in")
    public void verify_user_logged_in() {
       String expectedURL = "https://www.saucedemo.com/inventory.html";
        Assert.assertEquals(expectedURL, Driver.getDriver().getCurrentUrl());
    }
}
