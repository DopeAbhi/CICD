package TestComponents;

import Resources.ExtentReporterNG;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;

public class Listners extends BaseTest implements ITestListener {
    ExtentTest test;
    ExtentReports extentReports = ExtentReporterNG.getReportObject();  //Extent ReporterNG method call

    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>(); //To Solve Concurrency Issue

    @Override
    public void onTestStart(ITestResult result) {

        test = extentReports.createTest(result.getMethod().getMethodName()); //It gets test name
        extentTest.set(test); //uniques thread id to the running test //In Java every instance has it own thread ID



        try {
            // Start recording when test starts
            ScreenRecorderUtil.startRecord(result.getMethod().getMethodName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, "Test Pass");
        test.log(Status.PASS, "Test Pass");

        try {
            // Stop recording on test skip
            ScreenRecorderUtil.stopRecord();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String base64Video = null;
        try {
            base64Video = ((ScreenRecorderUtil) ScreenRecorderUtil.screenRecorder).convertMovieToBase64();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Add video to Extent Report (assuming you have a report instance set up)
       extentTest.get().log(Status.INFO, "Pass",
                MediaEntityBuilder.createScreenCaptureFromBase64String(base64Video, "Recorded Video").build());


    }

    @Override
    public void onTestFailure(ITestResult result) {
        String filePath;
        extentTest.get().log(Status.FAIL, "Test Fail");
//        test.fail(result.getThrowable());//Throw the Error Message of the Test Failure


        try {
            driver = (WebDriver) result.getTestClass().getRealClass().getField("driver")  //Method to get the driver life from the test
                    .get(result.getInstance());
            filePath = getScreenshot(result.getMethod().getMethodName(), driver);

            //            test.addScreenCaptureFromPath(filePath,result.getMethod().getMethodName());//Attaching screenshot to the test report
            extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());

            //Fields are associated with the class level not the method level
            //In above method we are test class of testNG->real class of test-> then getting driver field from the class
        } catch (Exception e) {  //Generic Exception for all type of exception
            e.printStackTrace();
        }
        extentTest.get().fail(result.getThrowable());

        try {
            // Stop recording on test skip
            ScreenRecorderUtil.stopRecord();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String base64Video = null;
        try {
            base64Video = ((ScreenRecorderUtil) ScreenRecorderUtil.screenRecorder).convertMovieToBase64();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String videoHTML = "<video width='320' height='240' controls>" +
                "<source src='data:video/mp4;base64," + base64Video + "' type='video/mp4'>" +
                "Your browser does not support the video tag." +
                "</video>";
        test.log(Status.INFO, videoHTML); // Add the video tag to the log

        // Add video to Extent Report (assuming you have a report instance set up)
            extentReports.attachReporter();
    }
        @Override
        public void onTestSkipped (ITestResult result){
            ITestListener.super.onTestSkipped(result);
        }

        @Override
        public void onTestFailedButWithinSuccessPercentage (ITestResult result){
            ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
        }

        @Override
        public void onTestFailedWithTimeout (ITestResult result){
            ITestListener.super.onTestFailedWithTimeout(result);
        }

        @Override
        public void onStart (ITestContext context){
            ITestListener.super.onStart(context);
        }

        @Override
        public void onFinish (ITestContext context){
            extentReports.getStats();
            extentReports.flush();
        }
    }
