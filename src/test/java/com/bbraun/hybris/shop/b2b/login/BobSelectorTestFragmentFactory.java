package com.bbraun.hybris.shop.b2b.login;

import org.openqa.selenium.By;

import com.bbraun.bbmtest.conf.BBMTestConfiguration;
import com.bbraun.bbmtest.ui.BBMUiTestBuilder;
import com.bbraun.bbmtest.ui.UiTestFragment;

public class BobSelectorTestFragmentFactory {

	private BobSelectorTestFragmentFactory() {

	}

	public static UiTestFragment getBoBSelectorBasedOnStage(String b2bUnit) {
		switch (BBMTestConfiguration.get().getStage()) {
			case LOCAL :
				return new UiTestFragment() {
					
					@Override
					public void runWithTestBuilder(BBMUiTestBuilder<BBMUiTestBuilder<?>> builder) {
							builder.assertUrl("https://localhost:9002/bob")
							.doClick(By.linkText(b2bUnit));
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
				return new UiTestFragment() {
					
					@Override
					public void runWithTestBuilder(BBMUiTestBuilder<BBMUiTestBuilder<?>> builder) {
						builder.assertUrl("https://shop.bbraun.com/bob")
						.doClick(By.linkText(b2bUnit));
					}
				};
			default :
				throw new RuntimeException("Unexpected stage "+BBMTestConfiguration.get().getStage());
		}
		
	}
}
