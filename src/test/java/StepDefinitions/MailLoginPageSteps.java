package StepDefinitions;

import Infrastructure.AndroidDemo;
import io.cucumber.java.en.Then;

public class MailLoginPageSteps extends Instances {

    public MailLoginPageSteps(AndroidDemo androidDemo) {
        this.androidDemo = androidDemo;
    }

    @Then("app main page is shown")
    public void verifyWixMainPage() {
        mainPage.verifyWixMainPage();
    }

    @Then("user clicks on continue to skip")
    public void userClicksOnContinueToSkip() {
        mainPage.userClicksOnContinueToSkip();
    }

    @Then("Login page is shown")
    public void loginPageIsShown() {
        mainPage.verifyLoginPageIsShown();
    }


}
