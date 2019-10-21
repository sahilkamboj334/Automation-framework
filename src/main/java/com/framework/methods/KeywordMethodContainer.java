package com.framework.methods;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class KeywordMethodContainer extends GenericApp {
	private WebDriverWait wait;
	private static String elementLocator;
	private WebDriver driver;

	public KeywordMethodContainer() {
		this.driver = getDriver();
	}

	public void refreshPage() {
		driver.navigate().refresh();
	}

	public void navigateBack() {
		driver.navigate().back();
	}

	public void sendKeys(HashMap<String, String> rowmap) {
		elementLocator = this.switchDataInDynXpath(rowmap);
		driver.findElement(getByObjectOfElement(elementLocator, webElementIID(rowmap)))
				.sendKeys(testDataForStep(rowmap));

	}

	public void clickButton(HashMap<String, String> rowmap) {
		elementLocator = this.switchDataInDynXpath(rowmap);
		driver.findElement(getByObjectOfElement(elementLocator, webElementIID(rowmap))).click();
	}

	public void navigateToURL(HashMap<String, String> rowmap) {
		driver.get(testDataForStep(rowmap));
	}

	public void waitForPageToLoad(HashMap<String, String> rowmap) {
		elementLocator = this.switchDataInDynXpath(rowmap);
		wait = new WebDriverWait(driver, 180);
		wait.until(ExpectedConditions
				.invisibilityOfElementLocated(getByObjectOfElement(elementLocator, webElementIID(rowmap))));

	}

	public void sleep(HashMap<String, String> rowmap) {
		this.sleep(Integer.valueOf(extraParam1ForTestStep(rowmap)));
	}
}
