package page_objects.factories;

import Infrastructure.AndroidDemo;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import page_objects.android.AndroidMainLoginPage;
import page_objects.interfaces.MainPage;

public class WixMainLoginPageFactory {

    public MainPage getPageObject(AndroidDemo androidDemo) {
        if (androidDemo.driver == null) {
            return null;
        }
        if (androidDemo.driver instanceof AndroidDriver<?>) {
            return new AndroidMainLoginPage((AndroidDriver<WebElement>) androidDemo.driver);
        }
        return null;
    }
}
