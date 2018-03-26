package com.bbraun.hybris.shop.b2b.login;

import com.bbraun.bbmtest.conf.RunOnStage;
import com.bbraun.bbmtest.conf.RunOnStageRule;
import com.bbraun.bbmtest.ui.UiTest;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;

@Ignore
public class B2BShopLoginTest {

	@Rule
	public RunOnStageRule rule = new RunOnStageRule();


	@Test
	@RunOnStage(stages = "QAS")
	public void testLoginIDPBOBUserSuccess() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
				.doOpenUrl("https://qas-shop.bbraun.com") //
				.assertUrl("https://idp-dev.bbraun.com/idp/SSO.saml2") //
				.doType(By.id("username"), "stefan.stuetzer@bbraun.com") //
				.doType(By.id("password"), "!") //
				.doSubmitForm(By.className("button-primary")) //
				.assertUrl("https://qas-shop.bbraun.com/bob") //
            ;
        });
	}

}
