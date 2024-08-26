package Resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {

public static ExtentReports getReportObject()
{

    String path= System.getProperty("user.dir")+"/reports/index2.html";
    ExtentSparkReporter reporter=new ExtentSparkReporter(path); //This is for the Configuration and the report path
    reporter.config().setReportName("Web Automation Results");
    reporter.config().setDocumentTitle("Test Results");

    ExtentReports extentReports= new ExtentReports();
    extentReports.attachReporter(reporter);
    extentReports.setSystemInfo("Tester", "AbhayVerma");
    extentReports.createTest(path);
    return extentReports;
}
}
