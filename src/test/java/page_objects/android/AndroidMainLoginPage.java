package page_objects.android;

import Infrastructure.CommonPageObject;
import Infrastructure.Timeouts;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import page_objects.interfaces.MainPage;

public class AndroidMainLoginPage extends CommonPageObject implements MainPage {

    @FindBy(xpath = "//*[contains(@text,'Get Started')]")
    private WebElement getStartedButton;

    @FindBy(xpath = "//*[contains(@text,'I Already Have a Wix Account')]")
    private WebElement alreadyHavedButton;

    @FindBy(xpath = "//*[contains(@text,'Create a Wix account to continue')]")
    private WebElement createAnAccount;

    @FindBy(xpath = "//*[contains(@text,'Continue with Google')]")
    private WebElement continueWithGoogle;

    @FindBy(xpath = "//*[contains(@text,'Continue with Facebook')]")
    private WebElement continueWithFacebook;


    public AndroidMainLoginPage(AndroidDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, Timeouts.WAIT_FOR_ELEMENT_TO_UPDATE), this);
    }

    @Override
    public void verifyWixMainPage() {
        verifyElementDisplayed("Continue button", getStartedButton);
        verifyElementDisplayed("main title ", alreadyHavedButton);
    }

    @Override
    public void userClickOnGetStarted() {
        clickOnWebElement("Get Started", getStartedButton);
    }


    @Override
    public void verifyLetsGetStartedPageIsShown() {
        verifyElementDisplayed("Let's get started", createAnAccount);
        verifyElementDisplayed("Continue with Google", continueWithGoogle);
        verifyElementDisplayed("Sign in button", continueWithFacebook);
    }

}
