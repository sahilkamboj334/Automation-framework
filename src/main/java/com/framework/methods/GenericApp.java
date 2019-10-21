package com.framework.methods;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.framework.executor.RowHeaders;
import com.framework.jsonConfigurtor.ConfigFactory;
import com.framework.webdriver.GenericDriverProvider;

public class GenericApp implements RowHeaders {
	private static GenericApp app;
	private RemoteWebDriver driver;

	public static GenericApp getInstance() {
		if(app==null)
			app=new GenericApp();
		return app;
	}

	public RemoteWebDriver createDriver() {
		this.driver = (RemoteWebDriver) GenericDriverProvider.getInstance()
				.getDriverForIID(ConfigFactory.getInstance().getTestSuiteConfig().getDriverIID());
		this.driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		return this.driver;
	}

	public  RemoteWebDriver getDriver() {
		return app.driver;
	}

	public String webElementIID(HashMap<String, String> map) {
		return map.get(WEB_ELEMENT_IID);
	}

	public String testDataForStep(HashMap<String, String> map) {
		return map.get(TEST_DATA);
	}

	public String extraParam1ForTestStep(HashMap<String, String> map) {
		return map.get(EXTRAPARAM1);
	}

	public String extraParam2ForTestStep(HashMap<String, String> map) {
		return map.get(EXTRAPARAM2);
	}

	public String switchDataInDynXpath(HashMap<String, String> map) {
		String elString = null;
		try {
			elString = ConfigFactory.getElementLocator(webElementIID(map));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (elString.contains("DYN1") || elString.contains("DYN2"))
			elString = elString.replaceAll("DYN1", extraParam1ForTestStep(map)).replaceAll("DYN2",
					extraParam2ForTestStep(map));
		return elString;
	}

	public By getByObjectOfElement(String element, String IID) {
		if (ConfigFactory.xpathMap.containsKey(IID))
			return By.xpath(element);
		else if (ConfigFactory.idMap.containsKey(IID))
			return By.id(element);
		return null;
	}

	public void handleAlert() {
		if (driver.switchTo().alert() != null) {
			driver.switchTo().alert().accept();
		}
	}

	public void waitForDomToLoad() {
		while (driver.executeScript("return document.readyState").equals("loading")) {
			this.sleep(5);
		}
	}

	public void sleep(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
