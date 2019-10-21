package com.framework.webdriver.mobile;

import java.io.File;

import com.framework.jsonConfigurtor.ConfigFactory;
import com.framework.setupEntities.MobDriverConfig;
import com.sun.jna.Platform;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class AppiumServerWorker extends Thread {
	MobDriverConfig mobConfig;
	AppiumDriverLocalService appiumService;
	AppiumServiceBuilder builder;

	@Override
	public void run() {
		mobConfig = ConfigFactory.getInstance().getMobDriverConfig();
		this.startServer();
	}

	private void startServer() {
		if (Platform.isWindows() || Platform.isMac()) {
			builder = new AppiumServiceBuilder().withAppiumJS(Platform.isMac()
					? new File("/Applications/Appium.app/Contents/Resources/app/node_modules/appium/build/lib/main.js")
					: new File(System.getenv("APPIUM_HOME")
							+ "\\resources\\app\\node_modules\\appium\\build\\lib\\main.js"))
					.withIPAddress(mobConfig.getIp()).usingPort(mobConfig.getPort())
					.withArgument(GeneralServerFlag.LOG_NO_COLORS).withArgument(GeneralServerFlag.RELAXED_SECURITY)
					.withArgument(GeneralServerFlag.LOCAL_TIMEZONE).withArgument(GeneralServerFlag.LOG_NO_COLORS)
					.withArgument(GeneralServerFlag.LOG_LEVEL, "warn");
			this.appiumService = AppiumDriverLocalService.buildService(builder);
			this.appiumService.start();
		} else
			throw new IllegalArgumentException("Unknown Platform!!!" + System.getProperty("user.os"));
	}

	public void stopServer() {
		if (Platform.isWindows())
			this.appiumService.stop();
	}
}
