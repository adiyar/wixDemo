package StepDefinitions;

import Infrastructure.AndroidDemo;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import io.cucumber.java.*;
import io.cucumber.java.en.Given;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import page_objects.factories.*;

import java.net.MalformedURLException;

public class BasicSteps extends Instances {

    public BasicSteps(AndroidDemo androidDemo) {
        this.androidDemo = androidDemo;
    }

    @Before
    public void setup(Scenario scenario) throws MalformedURLException {
        androidDemo.setup(scenario);
        mainPage = new WixMainLoginPageFactory().getPageObject(androidDemo);

    }

    @Given("a logged out user")
    public void aLoggedOutUser() {
        ExtentCucumberAdapter.getCurrentStep().info("app opens and main page is displayed to the user");
    }


    @AfterStep
    @BeforeStep
    public static void takeScreenShot(Scenario scenario) {
        TakesScreenshot takesScreenshot = (TakesScreenshot) androidDemo.driver;
        byte[] src = takesScreenshot.getScreenshotAs(OutputType.BYTES);
        scenario.attach(src, "image/png", "screenshot");
    }

    @After
    public void tearDown() {
        androidDemo.teardown();
    }

}
