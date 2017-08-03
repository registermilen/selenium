package com.bbraun.articlecomparison;

import org.junit.Test;
import org.openqa.selenium.By;

import com.bbraun.bbmtest.ui.BBMUiTestBuilder;

public class ArticleComparisonLandingPageTest {


	@Test
	public void testCallLandingPage() {
		new BBMUiTestBuilder<>() //
		.doStartBrowser() //
		.doOpenUrl("http://bbmag112:8080/articlecomparison?codes=BH111R,BH121R,BH110R,BC324R") //
		.assertTextDisplayedOnPage("Arterienklemme") //
		.doCloseBrowser() //
		;
	}
	
}
