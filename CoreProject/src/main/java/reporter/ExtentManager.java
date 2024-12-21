package reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;
import seleniumCore.CoreConstants;


public class ExtentManager {

    private static ExtentReports extentReport;

    public static ExtentReports getInstance() {
        return extentReport;
    }

    public static ExtentReports getInstance(String fileName) {
        if (extentReport == null)
            extentReport = createInstance(CoreConstants.pathToExtentReports + fileName + ".html");

        return extentReport;
    }

    private static ExtentReports createInstance(String fileName) {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(fileName).viewConfigurer()
                .viewOrder()
                .as(new ViewName[]{
                        ViewName.DASHBOARD,
                        ViewName.TEST,
                        ViewName.CATEGORY,
                        ViewName.AUTHOR,
                        ViewName.DEVICE,
                        ViewName.EXCEPTION,
                        ViewName.LOG

                })
                .apply();
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setTimeStampFormat("dd-MM-yy HH:mm:ss a");
        sparkReporter.config().setDocumentTitle(fileName);
        sparkReporter.config().setEncoding("utf-8");
        sparkReporter.config().setReportName(fileName);

        extentReport = new ExtentReports();
        extentReport.attachReporter(sparkReporter);
        return extentReport;
    }
}
