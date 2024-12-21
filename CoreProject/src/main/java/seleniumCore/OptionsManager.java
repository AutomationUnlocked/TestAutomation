package seleniumCore;

import enums.SupportedOS;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.ElementScrollBehavior;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

import static seleniumCore.CorePage.sleep;

public class OptionsManager {
    public ChromeOptions getChromeOptions() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-notifications");
        return options;
    }

    // Get Firefox Options
    public FirefoxOptions getFirefoxOptions() {

        FirefoxOptions options = new FirefoxOptions();
        FirefoxProfile profile = new FirefoxProfile();
        // Accept Untrusted Certificates
        profile.setAcceptUntrustedCertificates(true);
        profile.setAssumeUntrustedCertificateIssuer(false);
        // Use No Proxy Settings
        profile.setPreference("network.proxy.type", 0);
        return options;
    }

    public InternetExplorerOptions getIEOptions() {

        InternetExplorerOptions options = new InternetExplorerOptions();
        options.setCapability("ignoreZoomSetting", true);
        options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        options.introduceFlakinessByIgnoringSecurityDomains();
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
        return options;
    }


    public EdgeOptions getEdgeOptions() {

        EdgeOptions options = new EdgeOptions();
        options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        options.setCapability(CapabilityType.ELEMENT_SCROLL_BEHAVIOR,
                ElementScrollBehavior.BOTTOM);
        return options;
    }

    public Capabilities getAppiumOptions() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, System.getProperty(CoreProperties.appiumPlatformName));
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, System.getProperty(CoreProperties.appiumPlatformVersion));
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, System.getProperty(CoreProperties.appiumDeviceName));
        if (System.getProperty(CoreProperties.appiumPlatformName).equalsIgnoreCase(Platform.IOS.toString()))
            caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
        else{
            caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
            caps.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS,true);
        }
        if (System.getProperty(CoreProperties.browserName) == null || System.getProperty(CoreProperties.browserName).isEmpty()) {
            if (System.getProperty(CoreProperties.appActivity) != null && !System.getProperty(CoreProperties.appActivity).isEmpty()) {
                caps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, System.getProperty(CoreProperties.appActivity));
                caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, System.getProperty(CoreProperties.appPackage));
            } else {
                File apk = new File(System.getProperty(CoreProperties.appiumApp));
                caps.setCapability(MobileCapabilityType.APP, apk.getAbsolutePath());
            }
        } else
            caps.setCapability(MobileCapabilityType.BROWSER_NAME, System.getProperty(CoreProperties.browserName));
        return caps;
    }
}
