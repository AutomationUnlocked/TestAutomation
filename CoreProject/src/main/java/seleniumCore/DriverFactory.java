package seleniumCore;

import enums.OSType;
import enums.SupportedOS;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import logging.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static seleniumCore.CorePage.sleep;

public class DriverFactory {

    private static final Map<Integer, WebDriver> driverMap = new HashMap<>();

    private static OptionsManager optionsManager = new OptionsManager();

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private static ThreadLocal<String> selectedBrowser = new ThreadLocal<String>();

    private DriverFactory() {

    }


    public static synchronized void setDrivers(String driver) {
        String driverInPropertyFile = System.getProperty(CoreProperties.browserName);
        if (driver.contentEquals("optional")) {
            if (driverInPropertyFile.isEmpty())
                driver = "chrome";
            else
                driver = driverInPropertyFile;
        }
        if (System.getProperty(CoreProperties.hubAddress) != null) {
            if (!System.getProperty(CoreProperties.hubAddress).isEmpty()) {
                setRemoteDriver(driver);
            }
        } else {
            setLocalDriver(driver);
        }
        setTimeOuts();


    }

    private static synchronized void setRemoteDriver(String browser) {
        String hubAddress = System.getProperty(CoreProperties.hubAddress);
        try {
            if (System.getProperty(CoreProperties.platform).equalsIgnoreCase(OSType.APP.toString())) {
                if(System.getProperty(CoreProperties.appiumPlatformName).equalsIgnoreCase(SupportedOS.ANDROID.toString()))
                    driver.set(new AndroidDriver<>(new URL(hubAddress), optionsManager.getAppiumOptions()));
                else
                    driver.set(new IOSDriver<>(new URL(hubAddress), optionsManager.getAppiumOptions()));
            } else if (System.getProperty(CoreProperties.platform).equalsIgnoreCase(OSType.WEB.toString())) {
                if (browser.equalsIgnoreCase(CoreConstants.firefox)) {
                    driver.set(new RemoteWebDriver(new URL(hubAddress), optionsManager.getFirefoxOptions()));
                } else if (browser.equalsIgnoreCase(CoreConstants.chrome)) {
                    driver.set(new RemoteWebDriver(new URL(hubAddress), optionsManager.getChromeOptions()));
                } else if (browser.equalsIgnoreCase(CoreConstants.ie)) {
                    driver.set(new RemoteWebDriver(new URL(hubAddress), optionsManager.getIEOptions()));
                } else if (browser.equalsIgnoreCase(CoreConstants.edge)) {
                    driver.set(new RemoteWebDriver(new URL(hubAddress), optionsManager.getEdgeOptions()));
                } else
                    Assert.fail("Please choose a web browser to run a test");
            } else {
                Assert.fail("Please choose a valid Platform  , either APP or WEB");
            }
        } catch (Exception e) {
            Log.warn("Exception Happened in Hub node configuration");
            e.printStackTrace();
            Assert.fail("Check the hub node configuration");
        }
    }

    private static synchronized void setLocalDriver(String browser) {

        if (browser.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
            selectedBrowser.set(browser);

        } else if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver.set(new ChromeDriver(optionsManager.getChromeOptions()));
            selectedBrowser.set(browser);

        } else if (browser.equals("ie")) {
            WebDriverManager.iedriver().setup();
            driver.set(new InternetExplorerDriver(optionsManager.getIEOptions()));
            selectedBrowser.set(browser);

        } else if (browser.equals("edge")) {
            WebDriverManager.edgedriver().setup();
            driver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
            selectedBrowser.set(browser);

        } else {
            Log.warn("No browser is mentioned ");
            Assert.fail("Unable to create a driver");
        }
        driverMap.put((int) Thread.currentThread().getId(), driver.get());
    }

    public static synchronized WebDriver getDriver() {
        return driver.get();
    }

    public static synchronized String getBrowserName() {
        return selectedBrowser.get();
    }

    public static synchronized void closeBrowser() {
        if (driver.get() != null) {
            Log.info("Quitting the driver");
            getDriver().quit();
            selectedBrowser.set(null);
            driver.remove();
            driver.set(null);
        }

    }

    private static synchronized void setTimeOuts() {
        int implicitWait, timeouts;
        try {
            implicitWait = Integer.parseInt(System.getProperty(CoreProperties.implicitTimeOut)) / 1000;
            timeouts = Integer.parseInt(System.getProperty(CoreProperties.waitTimeOut)) / 1000;
        } catch (NumberFormatException e) {
            implicitWait = 10;
            timeouts = 30;
        }
        getDriver().manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
        if (!System.getProperty(CoreProperties.platform).equalsIgnoreCase(OSType.APP.toString())) {
            getDriver().manage().timeouts().pageLoadTimeout(timeouts, TimeUnit.SECONDS);
            getDriver().manage().timeouts().setScriptTimeout(timeouts, TimeUnit.SECONDS);
            getDriver().manage().window().maximize();
        }

    }
}
