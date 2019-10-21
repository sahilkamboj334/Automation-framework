package com.framework.executor;

import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.framework.constUtill.FWConstants;
import com.framework.excel.TestStepEntity;
import com.framework.logger.LoggerUtil;
import com.framework.methods.Keyword;
import com.framework.methods.GenericApp;
import com.framework.utill.SeleniumUtil;
import com.gargoylesoftware.htmlunit.javascript.host.Map;
import com.testautomationguru.ocular.Ocular;

public class ExecuteTestSteps implements RowHeaders {
	private HashMap<String, String> stepMap = new HashMap<>();
	private HashMap<String, String> testDataRefMap;
	private List<TestStepEntity> stepList;
	ExecuteSuite suite;
	private static Logger logger = Logger.getLogger(ExecuteTestSteps.class);

	public ExecuteTestSteps(ExecuteSuite caseGroup, List<TestStepEntity> stepList,
			HashMap<String, String> testDataRefMap) {
		this.stepList = stepList;
		this.testDataRefMap = testDataRefMap;
		this.suite = caseGroup;
	}

	public void runTestCase() {
		TestStepEntity entity = null;
		for (int i = 0; i < stepList.size(); i++) {
			entity = stepList.get(i);
			fillTestStepMap(entity, testDataRefMap);
			logger.info("Executing the test Step " + entity.getTestStepIID() + "-----" + entity.getTestStepDesc());
			if (entity.getIsTestStepActive()) {
				executeTestStep(entity);
			} else
				logger.info("Test Step " + entity.getTestStepIID() + " is Inactive.Skipping....Test Step #####");
		}
		suite.reports.flush();
	}

	private String buildTestStepInfo(TestStepEntity entity) {
		String keyword = stepMap.get(KEYWORD);
		String testData = stepMap.get(TEST_DATA);
		String extraparam1 = stepMap.get(EXTRAPARAM1);
		String extraparam2 = stepMap.get(EXTRAPARAM2);
		String webEleIID = stepMap.get(WEB_ELEMENT_IID);
		return new StringBuilder().append("<br>WebElement ID\t\t:\t\t<b>" + webEleIID + "</b>")
				.append("<br>TestData\t\t:\t\t<b>" + testData + "</b>").toString();
	}

	void executeTestStep(TestStepEntity entity) {
		String keyword = stepMap.get(KEYWORD);
		ExtentTest testReportNode = suite.testNode;
		try {
			Object result = null;
			if (suite.keywordFactory.getMethodName(keyword) == null) {
				LoggerUtil.logInfo("Null method name returned!!!!"+ keyword );
				testReportNode.log(Status.ERROR,
						"No Keyword exist with the given name.!!!! " + "<b>" + keyword + "</b>");
				return;
			}
			Method method = suite.container.getClass().getMethod(suite.keywordFactory.getMethodName(keyword),
					new Class[] { HashMap.class });
			if (method != null) {
				result = method.invoke(suite.container, stepMap);
				
				if (method.getReturnType().equals(Boolean.TYPE)) {
					if (Boolean.valueOf((boolean) result)) {
						testReportNode.log(Status.PASS, buildTestStepInfo(entity),
								MediaEntityBuilder.createScreenCaptureFromPath(SeleniumUtil.takeScreenShot(
										GenericApp.getInstance().getDriver(), entity.getTestStepIID()))
										.build());

					} else {
						testReportNode.log(Status.FAIL, buildTestStepInfo(entity),
								MediaEntityBuilder.createScreenCaptureFromPath(SeleniumUtil.takeScreenShot(
										GenericApp.getInstance().getDriver(), entity.getTestStepIID()))
										.build());

					}
				} else {
					testReportNode.log(Status.INFO, buildTestStepInfo(entity),
							MediaEntityBuilder
									.createScreenCaptureFromPath(SeleniumUtil.takeScreenShot(
											GenericApp.getInstance().getDriver(), entity.getTestStepIID()))
									.build());
				}

			} else {
				testReportNode.log(Status.ERROR,
						"No Method exist for the given Keyword.!!!! " + "<b>" + keyword + "</b>");
			}
			/**
			 * Oculor for response and change in ui . it will make screenshot as baseline and compare current image with baseline dir.
			 */
			/*	Ocular.snapshot().from(Paths.get(FWConstants.CURRENT_WDIR+"/Screenshots/"+entity.getTestStepIID()+".png")).sample().
				using(GenericApp.getInstance().getDriver()).compare();*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void fillTestStepMap(TestStepEntity testStepEntity, HashMap<String, String> testDataRefMap) {
		stepMap.put(KEYWORD, testStepEntity.getKeyword());
		stepMap.put(EXTRAPARAM2, testDataRefMap.get(testStepEntity.getExtraParam2()) != null
				? testDataRefMap.get(testStepEntity.getExtraParam2()) : testStepEntity.getExtraParam2());
		stepMap.put(EXTRAPARAM1, testDataRefMap.get(testStepEntity.getExtraParam1()) != null
				? testDataRefMap.get(testStepEntity.getExtraParam1()) : testStepEntity.getExtraParam1());
		stepMap.put(TEST_DATA, testDataRefMap.get(testStepEntity.getTestData()) != null
				? testDataRefMap.get(testStepEntity.getTestData()) : testStepEntity.getTestData());
		stepMap.put(WEB_ELEMENT_IID, testStepEntity.getWebelementIID());

	}

}
