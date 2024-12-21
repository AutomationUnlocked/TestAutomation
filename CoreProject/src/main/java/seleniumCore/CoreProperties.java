package seleniumCore;

public interface CoreProperties {

    String osName = "os.name";
    String browserName = "browserName";
    String hubAddress = "hub.address";
    String baseRetryCount = "base.retry";
    String userDir = "user.dir";
    String env = "base.env";
    String baseUrl = "base.url";
    String logLevel = "log.Level";
    String extentLog = "extentLog";
    String log4jLog = "log4jLog";
    String platform = "platform";

    String appiumPlatformName = "appium.platformName";
    String appiumPlatformVersion = "appium.platformVersion";
    String appiumDeviceName = "appium.deviceName";
    String appiumAutomationName = "appium.automationName";
    String appiumApp = "appium.app";
    String appPackage = "appium.appPackage";
    String appActivity = "appium.appActivity";


    String waitTimeOut = "webdriver.wait.for.timeouts";
    String implicitTimeOut = "webdriver.timeouts.implicitlywait";
    String quitAfterEachTest = "quitAfterEachTest";
    String takePassScreenShots="takePassScreenShots";
}
