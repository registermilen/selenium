package com.bbraun.hybris.shop.b2b.login;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.bbraun.bbmtest.conf.BBMTestConfiguration;
import com.bbraun.bbmtest.ui.UiTestFragment;

public class LogoutTestFragmentFactory {
	
	private LogoutTestFragmentFactory() {
		
	}
	
	public static UiTestFragment getGlobalLogoutTestFragmentBasedOnStage() {
		UiTestFragment loginTestFragment = null;
		switch (BBMTestConfiguration.get().getStage()) {
			case LOCAL :
				loginTestFragment = builder -> builder.doClick(By.cssSelector("a[href='/logout?global=true']"))
	                    .doWaitUntil(ExpectedConditions.urlContains("hybrislogin"));
				break;
			case QAS :
				loginTestFragment = builder -> builder.doClick(By.cssSelector("a[href='/logout?global=true']"))
                	.doWaitUntil(ExpectedConditions.urlContains("idp-dev.bbraun.com"));
				break;
			case PRD :
				loginTestFragment = builder -> builder.doClick(By.cssSelector("a[href='/logout?global=true']"))
                	.doWaitUntil(ExpectedConditions.urlContains("hybrislogin"));
				break;
			default :
				throw new RuntimeException("Unexpected stage "+BBMTestConfiguration.get().getStage());
		}
		
		return loginTestFragment;
	}
}
