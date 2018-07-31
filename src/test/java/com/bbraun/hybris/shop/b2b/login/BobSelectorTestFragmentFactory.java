package com.bbraun.hybris.shop.b2b.login;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.bbraun.bbmtest.conf.BBMTestConfiguration;
import com.bbraun.bbmtest.ui.BBMUiTestBuilder;
import com.bbraun.bbmtest.ui.UiTestFragment;
import com.bbraun.hybris.shop.b2b.B2BActions;

public class BobSelectorTestFragmentFactory {
	private BobSelectorTestFragmentFactory() {
		
	}
	
	public static UiTestFragment getBoBSelectorBasedOnStage(String b2bUnit) {
		UiTestFragment loginTestFragment = null;
		switch (BBMTestConfiguration.get().getStage()) {
			case LOCAL :
				return new UiTestFragment() {
					
					@Override
					public void runWithTestBuilder(BBMUiTestBuilder<BBMUiTestBuilder<?>> builder) {
						
					}
				};
			case QAS :
				return new UiTestFragment() {
					
					@Override
					public void runWithTestBuilder(BBMUiTestBuilder<BBMUiTestBuilder<?>> builder) {
						builder.assertUrl("https://qas-shop.bbraun.com/bob")
						.doClick(By.linkText(b2bUnit));
					}
				};
			case PRD :
				loginTestFragment = B2BActions::loginPRD;
				break;
			default :
				throw new RuntimeException("Unexpected stage "+BBMTestConfiguration.get().getStage());
		}
		
		return loginTestFragment;
	} 
}
