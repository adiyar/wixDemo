package Infrastructure;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.cucumber.java.Scenario;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class AndroidDemo {
    public AndroidDriver driver;
    public Scenario scenario;

    private static AppiumDriverLocalService appiumLocalService;


    public void setup(Scenario scenario) {
        this.scenario = scenario;
        driver = getAndroidDriver();
    }

    public void teardown() {
        if (driver != null) driver.quit();
    }

    private AndroidDriver<WebElement> getAndroidDriver() {
        int port = 4723; // specify the port you want to check

        if (isPortInUse(port)) {
            System.out.println("Port " + port + " is in use.");
            if (terminateProcessUsingPort(port)) {
                System.out.println("Process using port " + port + " has been terminated.");
            } else {
                System.out.println("Failed to terminate the process using port " + port + ".");
            }
        } else {
            System.out.println("Port " + port + " is not in use.");
        }

        appiumLocalService = new AppiumServiceBuilder().usingPort(4723).withArgument(() -> "--base-path", "/wd/hub").build();
        appiumLocalService.start();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "RZCT7098N3V");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("platformVersion", "14.0");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appPackage", "com.wix.admin");
        capabilities.setCapability("appActivity", "com.wix.MainActivity");
        capabilities.setCapability("autoAcceptAlerts", false);
        capabilities.setCapability("newCommandTimeout", "120");
        for (int driverStart = 0; driverStart < 3; driverStart++) {
            try {
                driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
                new WebDriverWait(driver, Timeouts.WAIT_FOR_ELEMENT_TO_UPDATE).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@text,'No thanks')]"))).click();
                return (AndroidDriver<WebElement>) driver;
            } catch (WebDriverException | MalformedURLException unsupportedCommandException) {
                driver.quit();
            }
        }
        ExtentCucumberAdapter.getCurrentStep().fail("Couldn't start driver");
        return null;
    }

    public static boolean isPortInUse(int port) {
        try {
            Process process = Runtime.getRuntime().exec("netstat -an");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(":" + port)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean terminateProcessUsingPort(int port) {
        try {
            Process process = Runtime.getRuntime().exec("netstat -an -o");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(":" + port)) {
                    String[] parts = line.trim().split("\\s+");
                    String pid = parts[parts.length - 1];
                    Process killProcess = Runtime.getRuntime().exec("taskkill /F /PID " + pid);
                    killProcess.waitFor();
                    Thread.sleep(2000);
                    return true;
                }
            }
            Thread.sleep(2000);
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}