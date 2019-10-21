package com.framework.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.framework.constUtill.Platforms;
import com.framework.jsonConfigurtor.ConfigFactory;
import com.framework.webdriver.mobile.AndroidDriverProvider;
import com.framework.webdriver.mobile.IOSDriverProvider;
import com.framework.webdriver.mobile.MobileDriverProvider;

public class GenericDriverProvider implements DriverPath {
	private static GenericDriverProvider driverProvider;
	public WebDriver driver;

	public static GenericDriverProvider getInstance() {
		if(driverProvider==null){
			driverProvider=new GenericDriverProvider();
		}
		return driverProvider;
	}

	public WebDriver getDriverForIID(String driverIID) {
		if(driverIID.equals("CHROME")||driverIID.equals("FIREFOX")){
			this.registerDriver(driverIID);
		}
		switch (driverIID) {
		case "CHROME":
			this.driver = this.getChromeDriver();
			break;
		case "FIREFOX":
			this.driver=this.getFirefoxDriver();
			break;
		case "ANDROID":
				this.driver=AndroidDriverProvider.instance().getDriver();
				break;
		case "IOS":
			this.driver=IOSDriverProvider.getInstance().getDriver();
			break;
		default:
			System.out.println("No driverIID options availible");
			this.driver = null;
			break;
		}
		return this.driver;
	}

	private WebDriver getChromeDriver() {
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--start-maximized");
		chromeOptions.addArguments("--incognito");
		chromeOptions.addArguments("--disable-infobars");
		return driver == null ? driver = new ChromeDriver(chromeOptions) : driver;
	}
	private WebDriver getFirefoxDriver(){
		
		return new FirefoxDriver();
	}
	
	private void registerDriver(String driverIID){
		switch(driverIID){
		case "CHROME":
			if(com.sun.jna.Platform.isWindows())
				System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
			else if(com.sun.jna.Platform.isLinux())
				System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH_UBUNTU);
			break;
		case "FIREFOX":
			if(com.sun.jna.Platform.isWindows())
				System.setProperty("webdriver.gecko.driver", FIREFOX_DRIVER_PATH);
		else if(com.sun.jna.Platform.isLinux())
			System.setProperty("webdriver.gecko.driver", FIREFOX_DRIVER_PATH_UBUNTU);
			break;
		}
	}
	public void closeDriver(){
		if(ConfigFactory.getInstance().getTestSuiteConfig().getPlatform().equals(Platforms.MOBILE.toString())){
			MobileDriverProvider.instance().stopServer();
		}else
				driver.quit();
	}
	
}
