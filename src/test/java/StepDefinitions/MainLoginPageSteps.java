package StepDefinitions;

import Infrastructure.AndroidDemo;
import io.cucumber.java.en.Then;

public class MainLoginPageSteps extends Instances {

    public MainLoginPageSteps(AndroidDemo androidDemo) {
        this.androidDemo = androidDemo;
    }

    @Then("app main page is shown")
    public void verifyWixMainPage() {
        mainPage.verifyWixMainPage();
    }


    @Then("user clicks on Get Started")
    public void userClicksOnGetStarted() {
        mainPage.userClickOnGetStarted();
    }

    @Then("Lets get Started page is shown")
    public void letsGetStartedPageIsShown() {
        mainPage.verifyLetsGetStartedPageIsShown();
    }


}
