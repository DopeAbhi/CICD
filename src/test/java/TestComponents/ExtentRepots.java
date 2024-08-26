package TestComponents;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ExtentRepots {
    ExtentReports extent;
    @BeforeTest
    public void ExtentReportDemo()
    {
        String path = System.getProperty("user.dir") + "/reports/index.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("Web Automation Results");
        reporter.config().setDocumentTitle("Test Results");

       extent= new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester", "Rahul Shetty");
    }
@Test
    public void intialDemo()
{
  ExtentTest test=  extent.createTest("Inital Demo");
     WebDriver driver = new ChromeDriver();
     driver.get("https://demo.nopcommerce.com/#");
    System.out.println(driver.getTitle());

   test.fail("Result Do not Match");

   driver.close();
    extent.flush();

}

}
