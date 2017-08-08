package com.bbraun.cw;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;

import com.bbraun.bbmtest.ui.BBMUiTestBuilder;

@Ignore
public class CWLandingPageTest {

	@Test
	public void testSearchProductCW() {
		new BBMUiTestBuilder<>() //
		.doStartBrowser() //
		.doOpenUrl("https://www.bbraun.de/de.html") //
		.doType(By.name("q"), "Vasofix")
		.doSubmitForm(By.className("_search")) //
		.assertTextDisplayedOnPage("25 Suchergebnisse") //
		
		.doCloseBrowser() //
		;
	}
	
}
