package reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import fileReaders.ImageUtils;
import fileReaders.PropertyReader;
import logging.Log;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.*;
import seleniumCore.CoreConstants;
import seleniumCore.CoreProperties;
import seleniumCore.CoreVariables;
import seleniumCore.DriverFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ExtentListener implements ITestListener, ISuiteListener {

    private static ExtentReports extentReport;
    public static final ThreadLocal<ExtentTest> extentMethod = new ThreadLocal<>();


    /**
     * This method is invoked before the SuiteRunner starts.
     *
     * @param suite The suite
     */
    @Override
    public void onStart(ISuite suite) {
//        extentReport = ExtentManager.getInstance(suite.getName());
        PropertyReader.setProperty(CoreConstants.pathToPropertyFile);
        Log.info("on Start Extent Listener");
        extentReport = ExtentManager.getInstance("index");
        extentReport.setSystemInfo(CoreProperties.browserName, System.getProperty(CoreProperties.browserName));
    }


    /**
     * Invoked before running all the test methods belonging to the classes inside the &lt;test&gt; tag
     * and calling all their Configuration methods.
     *
     * @param context The test context
     */
    @Override
    public synchronized void onStart(ITestContext context) {
    }

    /**
     * Invoked each time before a test will be invoked. The <code>ITestResult</code> is only partially
     * filled with the references to class, method, start millis and status.
     *
     * @param result the partially filled <code>ITestResult</code>
     * @see ITestResult#STARTED
     */
    @Override
    public synchronized void onTestStart(ITestResult result) {
        String className = result.getTestClass().getRealClass().getSimpleName();
        String methodName = result.getMethod().getMethodName();
        Object[] parameters = result.getParameters();
        if (parameters != null && parameters.length > 0) {
            methodName = methodName + "_" + parameters[0].toString();
        }
        extentMethod.set(extentReport.createTest(methodName)
                .assignCategory(getCategory(result))
                .assignDevice(getDeviceName())
                .assignAuthor(className));
        Log.info("The objective of this test is to " +
                result.getMethod().getDescription());
    }

    /**
     * Invoked each time a test succeeds.
     *
     * @param result <code>ITestResult</code> containing information about the run test
     * @see ITestResult#SUCCESS
     */
    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        Object[] parameters = result.getParameters();
        if (parameters != null && parameters.length > 0) {
            methodName = methodName + "_" + parameters[0].toString();
        }
        if (DriverFactory.getDriver() != null) {
            if (System.getProperty(CoreProperties.takePassScreenShots) != null && System.getProperty(CoreProperties.takePassScreenShots).contains("true")) {
                String imagePath = ImageUtils.takeScreenShot(DriverFactory.getDriver(), methodName);
                extentMethod.get().pass(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(imagePath).build());
            }
        } else
            extentMethod.get().pass("Test is passed "+result.getMethod().getMethodName());
    }

    /**
     * Invoked each time a test fails.
     *
     * @param result <code>ITestResult</code> containing information about the run test
     * @see ITestResult#FAILURE
     */
    @Override
    public synchronized void onTestFailure(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        Object[] parameters = result.getParameters();
        if (parameters != null && parameters.length > 0) {
            methodName = methodName + "_" + parameters[0].toString();
        }
        if (DriverFactory.getDriver() != null) {
            String imagePath;
            if (CoreVariables.isImageTest()) {
                if (CoreVariables.getImagePath() != null) {
                    imagePath = CoreVariables.getImagePath();
                    extentMethod.get().fail(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(imagePath).build());
                } else if (CoreVariables.getImagePaths() != null) {
                    for (String imgPath : CoreVariables.getImagePaths()) {
                        extentMethod.get().fail(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(imgPath).build());
                    }
                } else
                    extentMethod.get().fail("Test Case failed");

            } else {
                imagePath = ImageUtils.takeScreenShot(DriverFactory.getDriver(), methodName);
                extentMethod.get().fail(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(imagePath).build());
            }

        } else
            extentMethod.get().fail("Test "+result.getMethod().getMethodName()+" is failed due to "+result.getThrowable());


    }

    /**
     * Invoked each time a test is skipped.
     *
     * @param result <code>ITestResult</code> containing information about the run test
     * @see ITestResult#SKIP
     */
    @Override
    public synchronized void onTestSkipped(ITestResult result) {
        extentMethod.get().skip("Test "+result.getMethod().getMethodName()+" is skipped due to "+result.getThrowable());
    }

    /**
     * Invoked each time a method fails but has been annotated with successPercentage and this failure
     * still keeps it within the success percentage requested.
     *
     * @param result <code>ITestResult</code> containing information about the run test
     * @see ITestResult#SUCCESS_PERCENTAGE_FAILURE
     */
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    /**
     * Invoked each time a test fails due to a timeout.
     *
     * @param result <code>ITestResult</code> containing information about the run test
     */
    @Override
    public void onTestFailedWithTimeout(ITestResult result) {

    }


    /**
     * Invoked after all the test methods belonging to the classes inside the &lt;test&gt; tag have run
     * and all their Configuration methods have been called.
     *
     * @param context The test context
     */
    @Override
    public synchronized void onFinish(ITestContext context) {
    }


    /**
     * This method is invoked after the SuiteRunner has run all the tests in the suite.
     *
     * @param suite The suite
     */
    @Override
    public void onFinish(ISuite suite) {
        extentReport.flush();
    }


    private static String getDeviceName() {
        WebDriver driver = DriverFactory.getDriver();
        String platform;
        String browser;
        String version;
        if (driver != null) {
            Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
//            platform = caps.getPlatformName().toString();
            platform = caps.getPlatform().name();
            browser = caps.getBrowserName();
//            version = caps.getBrowserVersion();
            version = caps.getVersion();
            return platform + "_" + browser + "_" + version;
        } else return System.getProperty(CoreProperties.osName);


    }


    private static String getCategory(ITestResult result) {
        String groupNames;
        List<String> groupName = new ArrayList<>();
        String[] groups = result.getMethod().getGroups();
        if (groups.length > 1) {
            Collections.addAll(groupName, groups);
            groupNames = String.join(" And ", groupName);
        } else if (groups.length == 1)
            groupNames = groups[0];
        else
            groupNames = "No groups";
        return groupNames;
    }

}
