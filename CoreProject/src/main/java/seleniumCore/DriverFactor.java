package seleniumCore;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class DriverFactor {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void setDriver(WebDriver driverInstance) {
        driver.set(driverInstance);
    }

    public static void initializeDriver(String platform, String browserOrDevice, String appiumServerUrl) {
        try {
            WebDriver driverInstance;

            if (platform.equalsIgnoreCase("web")) {
                // For Web Browsers
                switch (browserOrDevice.toLowerCase()) {
                    case "chrome":
                        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
                        driverInstance = new ChromeDriver();
                        break;
                    case "firefox":
                        System.setProperty("webdriver.gecko.driver", "path/to/geckodriver");
                        driverInstance = new FirefoxDriver();
                        break;
                    case "edge":
                        System.setProperty("webdriver.edge.driver", "path/to/edgedriver");
                        driverInstance = new EdgeDriver();
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported browser: " + browserOrDevice);
                }
            } else if (platform.equalsIgnoreCase("mobile")) {
                // For Mobile Devices
                DesiredCapabilities capabilities = new DesiredCapabilities();

                if (browserOrDevice.equalsIgnoreCase("android")) {
                    capabilities.setCapability("platformName", "Android");
                    capabilities.setCapability("deviceName", "Pixel7");
                    capabilities.setCapability("app", "/Users/beetroot/Documents/Automize/MobileTestModule/src/main/resources/appFile/android/WikipediaSample.apk");
                    capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
                    driverInstance = new AndroidDriver<>(new URL(appiumServerUrl), capabilities);
                } else if (browserOrDevice.equalsIgnoreCase("ios")) {
                    capabilities.setCapability("platformName", "iOS");
                    capabilities.setCapability("deviceName", "YouriOSDeviceName");
                    capabilities.setCapability("app", "path/to/your/ios/app.app");
                    driverInstance = new IOSDriver<>(new URL(appiumServerUrl), capabilities);
                } else {
                    throw new IllegalArgumentException("Unsupported mobile device: " + browserOrDevice);
                }
            } else {
                throw new IllegalArgumentException("Unsupported platform: " + platform);
            }

            setDriver(driverInstance);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize the driver");
        }
    }

    public static void quitDriver() {
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();
        }
    }
}

