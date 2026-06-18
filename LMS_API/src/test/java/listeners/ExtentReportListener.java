package listeners;

import java.io.File;

import org.testng.*;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportListener implements ITestListener {

	ExtentReports extent;
	ExtentTest test;

	public void onStart(ITestContext context) {

		ExtentSparkReporter spark = new ExtentSparkReporter(System.getProperty("user.dir") + "/reports/Spark.html");

		extent = new ExtentReports();

		extent.attachReporter(spark);

	}

	public void onTestStart(ITestResult result) {

		test = extent.createTest(result.getMethod().getMethodName());

	}

	public void onTestSuccess(ITestResult result) {

		test.pass("Test Passed");

	}

	public void onTestFailure(ITestResult result) {

		test.fail(result.getThrowable());

	}

	public void onTestSkipped(ITestResult result) {

		test.skip("Test Skipped");

	}

	public void onFinish(ITestContext context) {

		extent.flush();

	}

}