package com.framework.executor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.framework.constUtill.FWConstants;
import com.framework.constUtill.Platforms;
import com.framework.excel.ExcelFactory;
import com.framework.excel.TestCaseSheet;
import com.framework.excel.TestStepEntity;
import com.framework.excel.TestStepGroup;
import com.framework.jsonConfigurtor.ConfigFactory;
import com.framework.logger.LoggerUtil;
import com.framework.methods.KeywordMethodContainer;
import com.framework.methods.MobileMethodContainer;
import com.framework.methods.GenericApp;
import com.framework.methods.KeywordFactory;
import com.framework.reporter.ReportGenerator;
import com.framework.setupEntities.TestSuiteSetup;
import com.framework.webdriver.GenericDriverProvider;

public class ExecuteSuite implements TestConfigInfoProvider {
	public static HashMap<String, String> stepMap = new HashMap<>();
	private static HashMap<String, String> testDataRefMap;
	ConfigFactory configFactory;
	ExcelFactory excelFactory;
	ExtentTest testNode;
	TestSuiteSetup testSuiteSetupconfig;
	ExtentReports reports;
	private Logger logger = Logger.getLogger(ExecuteSuite.class);
	List<TestStepEntity> stepList;
	ExecuteSuite suite;
	Object container;
	KeywordFactory keywordFactory;

	public ExecuteSuite() {
		keywordFactory = new KeywordFactory();
		configFactory = ConfigFactory.getInstance();
		excelFactory = ExcelFactory.getInstance();
		testSuiteSetupconfig = configFactory.getTestSuiteConfig();
		this.initobject();
		
		suite = this;
	}

	public void initobject() {
		excelFactory.initObject(testSuiteSetupconfig.getExcelPath());
		try {
			configFactory.fillLoactorMaps();
			reports = ReportGenerator.getReportInstance();
			testDataRefMap = excelFactory.getTestDataRefMap(TEST_DATA_SHEET);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<ArrayList<TestStepGroup>> testGroupArray() {
		ArrayList<ArrayList<TestStepGroup>> group = new ArrayList<>();
		ArrayList<TestCaseSheet> caseSheets = excelFactory
				.getActiveSheetGroup(excelFactory.loadSheets((testSuiteSetupconfig.getModuleIID())));
		for (int i = 0; i < caseSheets.size(); i++) {
			group.add(excelFactory.getTestCaseGroups(caseSheets.get(i)));
		}
		excelFactory.closeWorkbook();
		return group;
	}

	public void executeSuite() {
		List<ArrayList<TestStepGroup>> groupArray = this.testGroupArray();
		GenericApp.getInstance().createDriver();
		this.instantiate();
		for (int i = 0; i < groupArray.size(); i++) {
			try {
				executeTestCaseGroup(this.getActiveTestGroup(groupArray.get(i)));
			} catch (Exception exception) {
				LoggerUtil.logError(exception.getMessage());
			}
		}
		GenericDriverProvider.getInstance().closeDriver();
	}

	private ArrayList<TestStepGroup> getActiveTestGroup(ArrayList<TestStepGroup> testStepGroups) {
		ArrayList<TestStepGroup> active = new ArrayList<>();
		for (TestStepGroup group : testStepGroups) {
			if (group.isActive()) {
				active.add(group);
			}
		}
		logger.info("Total Number of Active Test Case Groups are ::: " + active.size() + " for case group array "
				+ active.get(0).getSheetID());
		Collections.sort(active);
		return active;
	}

	public void executeTestCaseGroup(ArrayList<TestStepGroup> testStepGroups) {
		TestStepGroup testStepGroup = null;
		ExecuteTestSteps executeTestSteps = null;
		try{
		for (int i = 0; i < testStepGroups.size(); i++) {
			testStepGroup = testStepGroups.get(i);
			logger.info("#################----------Starting Test Case Group-------------"
					+ testStepGroup.getTestCaseID() + " #################");
			testNode = reports.createTest(testStepGroup.getTestCaseID());
			testNode.assignCategory(testStepGroup.getSheetID());
			stepList = testStepGroup.getTestSteplist();
			String caseInfo = this.getFormattedCaseInfo(testStepGroup);
			testNode.info(caseInfo);
			executeTestSteps = new ExecuteTestSteps(suite, stepList, testDataRefMap);
			executeTestSteps.runTestCase();
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private String getFormattedCaseInfo(TestStepGroup testStepGroup) {
		StringBuffer stringBuffer = new StringBuffer();
		return stringBuffer.append("<br>" + testStepGroup.getTestCaseDesc()).toString().replace(FWConstants.SPACE,
				FWConstants.HTML_SPACE);
	}

	public void instantiate() {
		if (testSuiteSetupconfig.getPlatform().equals(Platforms.WEB.toString())) {
			container = new KeywordMethodContainer();
		} else if (testSuiteSetupconfig.getPlatform().equals(Platforms.MOBILE.toString())) {
			container = new MobileMethodContainer();
		}
	}
}
