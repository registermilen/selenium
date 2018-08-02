package com.bbraun.hybris.shop.b2b.login;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.bbraun.bbmtest.conf.BBMTestConfiguration;
import com.bbraun.bbmtest.ui.UiTestFragment;
import com.bbraun.hybris.shop.b2b.B2BActions;

public class LoginTestFragmentFactory {
	
	private LoginTestFragmentFactory() {
		
	}
	
	public static UiTestFragment getLoginTestFragmentBasedOnStage(String initialURL, String host, String username, String password) {
		UiTestFragment loginTestFragment = null;
		switch (BBMTestConfiguration.get().getStage()) {
			case LOCAL :
				loginTestFragment = builder -> builder.doOpenUrl(initialURL)
	                    .doWaitUntil(ExpectedConditions.urlContains("/login"))
	                    .assertUrl(host+"/login") //
	                    .doType(By.id("j_username"), username) //
	                    .doType(By.id("j_password"), password) //
	                    .doSubmitForm(By.className("positive"))
	                    .assertUrl(initialURL);
				break;
			case QAS :
				loginTestFragment = B2BActions::loginQAS;
				break;
			case PRD :
				loginTestFragment = B2BActions::loginPRD;
				break;
			default :
				throw new RuntimeException("Unexpected stage "+BBMTestConfiguration.get().getStage());
		}
		
		return loginTestFragment;
	}
}
