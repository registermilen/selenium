package com.bbraun.hybris.productcenter.login;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;

import com.bbraun.bbmtest.ui.BBMUiTestBuilder;
import com.bbraun.bbmtest.ui.UiTest;

public class ProductCenterLoginTest {

	// @Test
	public void testLambda() {
		UiTest.go(builder -> {
			builder //
					.doStartBrowser() //
					.doOpenUrl("http://jqueryui.com/resources/demos/droppable/default.html") //
					.doDragNDrop(By.id("draggable"), By.id("droppable")); //
		});
	}

	// @Test
	public void testDragNDrop() {
		new BBMUiTestBuilder<>() //
				.doStartBrowser() //
				// .doMaximizeWindow() //
				.doOpenUrl("http://jqueryui.com/resources/demos/droppable/default.html") //
				.doDragNDrop(By.id("draggable"), By.id("droppable")) //
				.doCloseBrowser() //
		;
	}

	// @Test
	public void testRenderLoginPageSuccess() {
		new BBMUiTestBuilder<>() //
				.doStartBrowser() //
				// .doMaximizeWindow() //
				.doOpenUrl("https://qas-products.bbraun.com/hybrislogin") //
				.assertTextDisplayedOnPage(
						"Melden Sie sich bitte an und erleben Sie die digitale Produktwelt der B. Braun Gruppe") // ;
				.assertPageSectionScreenshotEquals(By.className("headerContent"), "PageHeader.PNG", 10) //
				.assertPageSectionScreenshotEquals(By.className("userLogin"), "UserLoginBox.PNG", 10) //

				.doCloseBrowser() //
		;
	}

	@Test
	public void testLoginPagePerformLoginFail() {
		new BBMUiTestBuilder<>() //
				.doStartBrowser() //
				.doOpenUrl("https://qas-products.bbraun.com/hybrislogin") //
				.doType(By.id("j_username"), "peter.schnitzel@hackers.com") //
				.doType(By.id("j_password"), "wrongPassword") //
				.doSubmitForm(By.id("loginForm")) //

				.assertUrl("https://qas-products.bbraun.com/login?error=true")
				.assertTextDisplayedOnPage("Ihr Benutzername oder Kennwort ist falsch.") //

				.doCloseBrowser() //
		;
	}

	@Test
	public void testLoginPagePerformLoginXLUserSuccess() {
		new BBMUiTestBuilder<>() //
				.doStartBrowser() //
				.doOpenUrl("https://qas-products.bbraun.com/hybrislogin") //
				.doType(By.id("j_username"), "stuestxl") //
				.doType(By.id("j_password"), "stuestxl") //
				.doSubmitForm(By.id("loginForm")) //
				.assertUrl("https://qas-products.bbraun.com/") //
				.doCloseBrowser() //
		;
	}

}
