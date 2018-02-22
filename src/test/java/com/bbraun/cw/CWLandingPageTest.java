package com.bbraun.cw;

import com.bbraun.bbmtest.ui.UiTest;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;

import com.bbraun.bbmtest.ui.BBMUiTestBuilder;


public class CWLandingPageTest {

	@Test
	public void testSearchProductCW() {
		UiTest.go(builder -> {
			builder //
					.doStartBrowser() //
					.doOpenUrl("https://www.bbraun.de/de.html") //
					.doType(By.name("q"), "Vasofix")
					.doSubmitForm(By.className("_search")) //
					.assertTextDisplayedOnPage("26 Suchergebnisse") //
			;
		});

	}
	
}
