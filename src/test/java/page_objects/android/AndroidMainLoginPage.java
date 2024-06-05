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

    @FindBy(xpath = "//*[contains(@text,'Thousands of personalized health and wellness options')]")
    private WebElement title2;

    @FindBy(xpath = "//*[contains(@text,'No more reimbursements - pay easily with your employer-funded card')]")
    private WebElement title3;

    @FindBy(xpath = "//*[contains(@text,'Tap to Reload')]")
    private WebElement tapToReloadButton;

    @FindBy(xpath = "//*[contains(@text,'Work email')]")
    private WebElement loginUserMail;

    @FindBy(xpath = "//*[contains(@text,'Password')]")
    private WebElement loginPassword;

    @FindBy(xpath = "//*[contains(@text,'Sign in')]")
    private WebElement signInButton;


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
    public void verifyLoginPageIsShown() {
        verifyElementDisplayed("Work email field", loginUserMail);
        verifyElementDisplayed("Password field", loginPassword);
        verifyElementDisplayed("Sign in button", signInButton);

    }

    @Override
    public void userClicksOnContinueToSkip() {
        verifyElementDisplayed("first title", alreadyHavedButton);
        clickOnWebElement("Continue", getStartedButton);
        verifyElementDisplayed("2nd title", title2);
        clickOnWebElement("Continue", getStartedButton);
        verifyElementDisplayed("3rd title", title3);
        clickOnWebElement("Continue", getStartedButton);

    }

}
