package base;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.*;
import listeners.ConfigReader;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import listeners.AllureUtility;

import java.net.MalformedURLException;
import java.net.URL;

public class BaseTest {

    public AppiumDriver driver;

    @BeforeClass
    @Step("Setup Test Environment")
    public void setUp() throws MalformedURLException {
        // Add environment information to Allure
        Allure.addAttachment("Device", ConfigReader.getDeviceName());
        Allure.addAttachment("Platform", ConfigReader.getPlatformName());
        Allure.addAttachment("Platform Version", ConfigReader.getPlatformVersion());

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", ConfigReader.getPlatformName());
        caps.setCapability("appium:automationName", ConfigReader.getAutomationName());
        caps.setCapability("appium:platformVersion", ConfigReader.getPlatformVersion());
        caps.setCapability("appium:app", System.getProperty("user.dir") + ConfigReader.getAppPath());
        caps.setCapability("appium:deviceName", ConfigReader.getDeviceName());
        driver = new AppiumDriver(new URL(ConfigReader.getAppiumServerUrl()), caps);

        new AllureUtility(driver);

        AllureUtility.addStep("App launched successfully on " + ConfigReader.getDeviceName());
    }

    @AfterClass
    @Step("Cleanup Test Environment")
    public void tearDown(){
        if (null != driver){
            AllureUtility.addStep("Closing application");
            driver.quit();
        }
    }
}