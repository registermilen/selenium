package com.bbraun.hybris.productcenter.login;

import org.junit.Test;
import org.openqa.selenium.By;

import com.bbraun.bbmtest.ui.BBMUiTestBuilder;

public class ProductCenterLoginTest {

	@Test
	public void testRenderLoginPageSuccess() {
		new BBMUiTestBuilder<>() //
				.doStartBrowser() //
				.doOpenUrl("https://qas-products.bbraun.com/hybrislogin") //
				.assertTextDisplayedOnPage(
						"Melden Sie sich bitte an und erleben Sie die digitale Produktwelt der B. Braun Gruppe") // ;
				.assertPageSectionScreenshotEquals(By.className("headerContent"), "PageHeader.PNG", 1) //
				.assertPageSectionScreenshotEquals(By.className("userLogin"), "UserLoginBox.PNG", 1) //
		;
	}

	@Test
	public void testLoginPagePerformLoginFail() {
		new BBMUiTestBuilder<>() //
				.doStartBrowser() //
				.doOpenUrl("https://qas-products.bbraun.com/hybrislogin") //
				.doType(By.id("j_username"), "peter.schnitzel@hackers.com") //
				.doType(By.id("j_password"), "wrongPassword") //
				.doClick(By.className("positive")) //

				.assertUrl("https://qas-products.bbraun.com/login?error=true")
				.assertTextDisplayedOnPage("Ihr Benutzername oder Kennwort ist falsch.") //
		;
	}

	@Test
	public void testLoginPagePerformLoginXLUserSuccess() {
		new BBMUiTestBuilder<>() //
		.doStartBrowser() //
		.doOpenUrl("https://qas-products.bbraun.com/hybrislogin") //
		.doType(By.id("j_username"), "stuestxl") //
		.doType(By.id("j_password"), "stuestxl") //
		.doClick(By.className("positive")) //
		
		.assertUrl("https://qas-products.bbraun.com/")
		;
	}

}
