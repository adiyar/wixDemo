package Infrastructure;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import io.appium.java_client.MobileDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Extend this class in your page object to use common element operations.
 */
public class CommonPageObject {
    public MobileDriver<WebElement> driver;

    public void verifyElementDisplayed(String text, WebElement element) {
        waitForElement(text, element);
    }

    public void clickOnWebElement(String text, WebElement element) {
        try {
            new WebDriverWait(driver, Timeouts.WAIT_FOR_ELEMENT_TO_UPDATE).until(ExpectedConditions.visibilityOf(element)).click();
            ExtentCucumberAdapter.getCurrentStep().pass(text + " was clicked");
        } catch (Exception exception) {
            ExtentCucumberAdapter.getCurrentStep().fail(text + " wasn't element");
        }
    }

    public void waitForElement(String text, WebElement element) {
        try {
            new WebDriverWait(driver, Timeouts.WAIT_FOR_ELEMENT_TO_UPDATE).until(ExpectedConditions.visibilityOf(element));
            ExtentCucumberAdapter.getCurrentStep().pass(text + " is shown on screen");
        } catch (Exception exception) {
            ExtentCucumberAdapter.getCurrentStep().fail(text + " isn't shown on screen");
        }
    }

}

