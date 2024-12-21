package logging;


import com.aventstack.extentreports.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reporter.ExtentListener;
import seleniumCore.CoreProperties;

import java.lang.invoke.MethodHandles;


public class Log {


    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    // This is to print log for the beginning of the test case, as we usually run so many test cases as a test suite

    public static void startTestCase(String sTestCaseName) {

        logger.info("****************************************************************************************");

        logger.info("****************************************************************************************");

        logger.info("$$$$$$$$$$$$$$$$$$$$$                 " + sTestCaseName + "       $$$$$$$$$$$$$$$$$$$$$$$$$");

        logger.info("****************************************************************************************");

        logger.info("****************************************************************************************");

    }

    //This is to print log for the ending of the test case

    public static void endTestCase(String sTestCaseName) {

        logger.info("XXXXXXXXXXXXXXXXXXXXXXX             " + "-E---N---D-" + "             XXXXXXXXXXXXXXXXXXXXXX");

    }

    // Need to create these methods, so that they can be called

    public static void info(String message) {
        if (System.getProperty(CoreProperties.logLevel) != null &&
                System.getProperty(CoreProperties.logLevel).equalsIgnoreCase(CoreProperties.extentLog)) {
            if (ExtentListener.extentMethod.get() != null)
                ExtentListener.extentMethod.get().info(message);
        }
        logger.info(message);


    }

    public static void link(String message, String link) {
        if (System.getProperty(CoreProperties.logLevel) != null &&
                System.getProperty(CoreProperties.logLevel).equalsIgnoreCase(CoreProperties.extentLog)) {
            if (ExtentListener.extentMethod.get() != null)
                ExtentListener.extentMethod.get().log(Status.INFO, message + " : " + " <a href='" + link + "'>LINK</a>");
            else
                logger.info(message + " : " + link);
        } else
            logger.info(message + " : " + link);
    }

    public static void warn(String message) {

        if (System.getProperty(CoreProperties.logLevel) != null &&
                System.getProperty(CoreProperties.logLevel).equalsIgnoreCase(CoreProperties.extentLog)) {
            if (ExtentListener.extentMethod.get() != null)
                ExtentListener.extentMethod.get().warning(message);
            else
                logger.info(message);
        } else
            logger.warn(message);
    }


}
