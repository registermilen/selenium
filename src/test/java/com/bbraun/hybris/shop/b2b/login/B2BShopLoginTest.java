package com.bbraun.hybris.shop.b2b.login;

import com.bbraun.bbmtest.conf.RunOnStage;
import com.bbraun.bbmtest.conf.RunOnStageRule;
import com.bbraun.bbmtest.ui.UiTest;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class B2BShopLoginTest {

	@Rule
	public RunOnStageRule rule = new RunOnStageRule();


	@Test
	@RunOnStage(stages = "QAS")
	public void testLoginIDPBOBUserSuccess() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
				.doOpenUrl("https://qas-shop.bbraun.com")
                    .doWaitUntil(ExpectedConditions.urlContains("idp-dev.bbraun.com"))
				.assertUrl("https://idp-dev.bbraun.com/idp/SSO.saml2") //
					.doType(By.id("username"), "shopdemo@bbraun.com") //
					.doType(By.id("password"), "demo") //
				.doSubmitForm(By.className("button-primary")) //
				.assertUrl("https://qas-shop.bbraun.com/bobs") //
            ;

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        });
	}

	@Test
	@RunOnStage(stages = "QAS")
	public void testOpenSavedCarts() {
		UiTest.go(builder -> {
			builder.doStartBrowser() //
					.doMaximizeWindow() //
					.doOpenUrl("https://qas-shop.bbraun.com")
					.doWaitUntil(ExpectedConditions.urlContains("idp-dev.bbraun.com"))
					.assertUrl("https://idp-dev.bbraun.com/idp/SSO.saml2") //
					.doType(By.id("username"), "shopdemo@bbraun.com") //
					.doType(By.id("password"), "demo") //
					.doSubmitForm(By.className("button-primary")) //

					.assertUrl("https://qas-shop.bbraun.com/bob") //
					.doClick(By.linkText("20005585"))
					.doWaitUntil(ExpectedConditions.urlContains("https://qas-shop.bbraun.com"))

					.doClick(By.linkText("Ihr Konto"))
					.assertUrl("https://qas-shop.bbraun.com/my-account/orders")

					.doClick(By.linkText("Bestellvorlagen"))
					.assertUrl("https://qas-shop.bbraun.com/my-account/saved-carts")
					.assertTextDisplayedOnPage("Gespeicherte Warenkörbe")
                    .assertElementPresent(By.linkText("Neue Bestellvorlage anlegen"))
			;

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}

}
