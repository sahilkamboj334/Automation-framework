package com.framework.jsonConfigurtor;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Platform;

import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.framework.constUtill.Platforms;
import com.framework.executor.TestConfigInfoProvider;
import com.framework.setupEntities.MobDriverConfig;
import com.framework.setupEntities.TestSuiteSetup;
import com.framework.utill.JsonReader;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ConfigFactory implements ConfigFactoryHelper {
	private static ConfigFactory instance = new ConfigFactory();
	public static HashMap<String, String> testSuiteSetup = new HashMap<String, String>();
	public static HashMap<String, String> xpathMap = new HashMap<>();
	public static HashMap<String, String> idMap = new HashMap<>();
	public static HashMap<String, String> byName = new HashMap<>();
	public static HashMap<String, String> byClassName = new HashMap<>();
	public static HashMap<String, String> byAccesibilityID = new HashMap<>();
	private ObjectMapper objectMapper;

	public TestSuiteSetup getTestSuiteConfig() {
		String path = TEST_SUITE_SETUP_PATH;
		TestSuiteSetup testSuiteSetup = null;
		objectMapper = new ObjectMapper();
		try {
			testSuiteSetup = objectMapper.readValue(new File(path), TestSuiteSetup.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return testSuiteSetup;
	}

	public MobDriverConfig getMobDriverConfig() {
		MobDriverConfig config = null;
		objectMapper = new ObjectMapper();
		String path = null;
		if (this.getTestSuiteConfig().getPlatform().equals(Platforms.MOBILE.toString()))
			path = "setupconfig/modules/" + this.getTestSuiteConfig().getModuleIID() + "/mobile/MobDriverConfig.json";
		else
			throw new IllegalArgumentException("Platform is not mobile!!!Please set appropriate platform!!");
		try {
			HashMap<String, Object> configMap = objectMapper.readValue(new File(path),
					new TypeReference<Map<String, Object>>() {
					});
			if (this.getTestSuiteConfig().getDriverIID().equals(Platforms.ANDROID.toString())) {
				config = objectMapper.readValue(new Gson().toJson(configMap.get("ANDROID")).toString(),
						MobDriverConfig.class);
			} else {
				config = objectMapper.readValue(new Gson().toJson(configMap.get("IOS")).toString(),
						MobDriverConfig.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return config;
	}

	public static ConfigFactory getInstance() {
		return instance;
	}

	public void fillLoactorMaps() throws Exception {
		String jsonFile = null;
		if (this.getTestSuiteConfig().getPlatform().equals("WEB")) {
			jsonFile = "setupconfig/modules/" + this.getTestSuiteConfig().getModuleIID() + "/web/UIElement.json";
		} else if (this.getTestSuiteConfig().getPlatform().equals("MOBILE")
				&& this.getTestSuiteConfig().getDriverIID().equals("ANDROID")) {
			jsonFile = "setupconfig/modules/" + this.getTestSuiteConfig().getModuleIID()
					+ "/mobile/android/UIElement.json";
		} else if (this.getTestSuiteConfig().getPlatform().equals("MOBILE")
				&& this.getTestSuiteConfig().getDriverIID().equals("IOS")) {
			jsonFile = "setupconfig/modules/" + this.getTestSuiteConfig().getModuleIID() + "/mobile/ios/UIElement.json";
		} else
			throw new IllegalArgumentException("Platfrom is undefined!!");
		objectMapper = new ObjectMapper();
		HashMap<String, Object> mainMap = JsonReader.getMapFromNestedJson(jsonFile);
		for (Map.Entry<String, Object> map : mainMap.entrySet()) {
			if (map.getKey().equals(BY_XPATH_DIC)) {
				xpathMap = objectMapper.readValue(map.getValue().toString(), new TypeReference<Map<String, String>>() {
				});
			} else if (map.getKey().equals(BY_ID_DIC)) {
				idMap = objectMapper.readValue(map.getValue().toString(), new TypeReference<Map<String, String>>() {
				});
			} else if (map.getKey().equals(BY_NAME_DIC)) {
				byName = objectMapper.readValue(map.getValue().toString(), new TypeReference<Map<String, String>>() {
				});
			} else if (map.getKey().equals(BY_ACCSID_DIC)) {
				byAccesibilityID = objectMapper.readValue(map.getValue().toString(),
						new TypeReference<Map<String, String>>() {
						});
			} else if (map.getKey().equals(BY_CLASS_NAME_DIC)) {
				byClassName = objectMapper.readValue(map.getValue().toString(),
						new TypeReference<Map<String, String>>() {
						});
			}
		}
	}

	public static String getElementLocator(String elementIID) throws Exception {
		if (elementIID.isEmpty()) {
			throw new Exception("Element iid is nulll!!");
		}
		if (xpathMap.containsKey(elementIID)) {
			return xpathMap.get(elementIID);
		} else if (idMap.containsKey(elementIID)) {
			return idMap.get(elementIID);
		} else if (byAccesibilityID.containsKey(elementIID)) {
			return byAccesibilityID.get(elementIID);
		} else if (byClassName.containsKey(elementIID)) {
			return byClassName.get(elementIID);
		} else if (byName.containsKey(elementIID)) {
			return byName.get(elementIID);
		}
		return null;
	}
}
