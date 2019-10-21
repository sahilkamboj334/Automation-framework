package com.framework.methods;

public enum Keyword {

	SEND_KEYS("sendKeys"), CLICK_BUTTON("clickButton"), CLICK_DYN_BUTTON("clickDynXpathButton"), REFRESH_PAGE(
			"refreshPage"), NAVIGATE_BACK("navigateBack"), NAVIGATE_TO_URL("navigateToURL");
	private String methodName;
	private boolean isValid;

	private Keyword(String str) {
		methodName = str;
	}

	public String getMethodName() {
		return this.methodName;
	}

	public boolean isValid() {
		return this.isValid;
	}

}
