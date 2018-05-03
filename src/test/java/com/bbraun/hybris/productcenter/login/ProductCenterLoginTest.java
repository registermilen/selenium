package com.bbraun.hybris.productcenter.login;

import com.bbraun.bbmtest.conf.RunOnStage;
import com.bbraun.bbmtest.conf.RunOnStageRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;

import com.bbraun.bbmtest.ui.UiTest;

@Ignore
public class ProductCenterLoginTest {

	@Rule
	public RunOnStageRule rule = new RunOnStageRule();

	@Test
	public void testMRLogoForPRID1011DE() {
		UiTest.go(builder -> {
			builder.doStartBrowser() //
					.doOpenUrl("https://qas-products.bbraun.com/hybrislogin") //
					.doType(By.id("j_username"), "stuestxl") //
					.doType(By.id("j_password"), "stuestxl") //
					.doSubmitForm(By.id("loginForm")) //
					.assertUrl("https://qas-products.bbraun.com/") //
					.doOpenUrl("https://qas-products.bbraun.com/p/PRID00001011") //
					.assertPageSectionScreenshotEquals(By.className("marketing-releases"), "MR.PNG", 0) //
			;
		});
	}

	@Test
	@RunOnStage(stages = "QAS")
	public void testRenderLoginPageSuccess() {
		UiTest.go(builder -> {
				builder.doStartBrowser() //
				// .doMaximizeWindow() //
				.doOpenUrl("https://qas-products.bbraun.com/hybrislogin") //
				.assertTextDisplayedOnPage(
						"Melden Sie sich bitte an und erleben Sie die digitale Produktwelt der B. Braun Gruppe") // ;
				.assertPageSectionScreenshotEquals(By.className("headerContent"), "PageHeader.PNG", 0) //
				.assertPageSectionScreenshotEquals(By.className("userLogin"), "UserLoginBox.PNG", 0) //
            ;
		});
	}

	@Test
	@RunOnStage(stages = "QAS")
	public void testLoginPagePerformLoginFail() {
        UiTest.go(builder -> {
                builder.doStartBrowser() //
				.doOpenUrl("https://qas-products.bbraun.com/hybrislogin") //
				.doType(By.id("j_username"), "peter.schnitzel@hackers.com") //
				.doType(By.id("j_password"), "wrongPassword") //
				.doSubmitForm(By.id("loginForm")) //

				.assertUrl("https://qas-products.bbraun.com/login?error=true")
				.assertTextDisplayedOnPage("Ihr Benutzername oder Kennwort ist falsch.") //
                ;
        });
	}

	@Test
	@RunOnStage(stages = "QAS")
	public void testLoginPagePerformLoginXLUserSuccess() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
				.doOpenUrl("https://qas-products.bbraun.com/hybrislogin") //
				.doType(By.id("j_username"), "stuestxl") //
				.doType(By.id("j_password"), "stuestxl") //
				.doSubmitForm(By.id("loginForm")) //
				.assertUrl("https://qas-products.bbraun.com/") //
            ;
        });
	}

}
