package com.framework.reporter;

import java.io.File;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.framework.utill.SeleniumUtil;

public class ReportGenerator {
private static ExtentReports extentReports;

public static ExtentReports getReportInstance(){
	ExtentHtmlReporter extentHtmlReporter=new ExtentHtmlReporter(new File("Reports/TestReport.html"));
	extentReports=new ExtentReports();
	extentReports.attachReporter(extentHtmlReporter);
	extentReports.flush();
	extentReports.setSystemInfo("Operating System", System.getProperty("os.name"));
	extentReports.setSystemInfo("Host", SeleniumUtil.getHostName());
	extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
	extentHtmlReporter.config().setTheme(Theme.STANDARD);
	extentHtmlReporter.config().setDocumentTitle("Test Session Report");
	extentHtmlReporter.config().setLevel(Status.INFO);
//	 extentHtmlReporter.setAppendExisting(true);
	extentHtmlReporter.setAnalysisStrategy(AnalysisStrategy.TEST);
	return extentReports;
}

}
