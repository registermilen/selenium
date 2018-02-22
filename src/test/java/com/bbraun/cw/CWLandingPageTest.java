package com.bbraun.cw;

import com.bbraun.bbmtest.ui.BBMUiTestBuilder;
import org.junit.Test;
import org.openqa.selenium.By;


public class CWLandingPageTest {

    @Test
    public void testSearchProductCW() {
        new BBMUiTestBuilder<>()
                .doStartBrowser() //
                .doOpenUrl("https://www.bbraun.de/de.html") //
                .doType(By.name("q"), "Vasofix")
                .doSubmitForm(By.className("_search")) //
                .assertTextDisplayedOnPage("26 Suchergebnisse") //
                .doCloseBrowser() //
        ;

    }

}
