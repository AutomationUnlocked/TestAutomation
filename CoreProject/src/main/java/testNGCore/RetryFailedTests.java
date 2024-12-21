package testNGCore;

import logging.Log;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import seleniumCore.CoreProperties;

public class RetryFailedTests implements IRetryAnalyzer {



    int retryCount = 0;
    int maxRetryCount = 0;

    @Override
    public boolean retry(ITestResult result) {
        try{
            maxRetryCount=Integer.parseInt(System.getProperty(CoreProperties.baseRetryCount));
        }catch(NumberFormatException nfe){
            maxRetryCount=0;
            Log.warn("Please set a number to retryCount in coreFramework.properties file");
        }

        if (retryCount < maxRetryCount) {
            retryCount++;
            Log.warn("Retrying the failed tests");
            return true;
        }
        return false;
    }
}
