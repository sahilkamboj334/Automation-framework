package com.framework.webdriver.mobile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.net.UrlChecker;

import com.framework.jsonConfigurtor.ConfigFactory;
import com.framework.logger.LoggerUtil;
import com.framework.setupEntities.MobDriverConfig;
import com.sun.jna.Platform;

public class MobileDriverProvider {
	private static MobileDriverProvider driverProvider = new MobileDriverProvider();
	static AppiumServerWorker appiumServerWorker;
	MobDriverConfig mobConfig;

	public static MobileDriverProvider instance() {
		return driverProvider;
	}

	public void startServer() {
		mobConfig = ConfigFactory.getInstance().getMobDriverConfig();
		this.killAlreadyRunningPort();
		appiumServerWorker = new AppiumServerWorker();
		appiumServerWorker.start();
	}

	public void stopServer() {
		appiumServerWorker.stopServer();
	}

	public URL getAppiumUrl() {
		URL url = null;
		try {
			url = new URL(this.url());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}

	public String url() {
		return "http://" + mobConfig.getIp() + ":" + mobConfig.getPort() + "/wd/hub";
	}

	protected static boolean waitUntilAppiumIsRunning(long timeout, String SERVER_URL) {
		URL status = null;
		System.out.println("Waitng to start");
		try {
			status = new URL(SERVER_URL + "/sessions");
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		try {
			new UrlChecker().waitUntilAvailable(timeout, TimeUnit.MINUTES, status);
			return true;
		} catch (UrlChecker.TimeoutException e) {
			e.printStackTrace();
			return false;
		}
	}

	private void killAlreadyRunningPort() {
		ProcessBuilder processBuilder = null;
		if (Platform.isWindows()) {
			processBuilder = new ProcessBuilder("cmd.exe", "/C", "netstat -a -o -n | findstr " + mobConfig.getPort());
		} else if (Platform.isMac()) {
			processBuilder = new ProcessBuilder("/bin/bash", "-c", "lsof -t -i :" + mobConfig.getPort());
		}
		Process process = null;
		try {
			process = processBuilder.start();
			BufferedReader fileInputStream = new BufferedReader(new InputStreamReader(process.getInputStream()));
			StringBuilder builder = new StringBuilder();
			String cm = "";
			while ((cm = fileInputStream.readLine()) != null) {
				builder.append(cm + "\n");
			}
			if (!builder.toString().isEmpty()) {
				String portArray[] = builder.toString().split("\n");
				String processToKill = null;
				for (int i = 0; i < portArray.length; i++) {
					if (Platform.isWindows()) {
						processToKill = portArray[i].trim().replaceAll("\\s+", "~").split("~")[4];
						processBuilder = new ProcessBuilder("cmd.exe", "/C", "taskkill /F /PID " + processToKill);
					} else if (Platform.isMac()) {
						processToKill = portArray[i];
						processBuilder = new ProcessBuilder("/bin/bash", "-c", "kill " + processToKill);
					}
					LoggerUtil.logInfo(mobConfig.getPort() + " using processID " + processToKill
							+ " already running:::: killing process ID " + processToKill);
					process = processBuilder.start();
					LoggerUtil
							.logInfo(mobConfig.getPort() + " using processID " + processToKill + " ::has been killed");

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
