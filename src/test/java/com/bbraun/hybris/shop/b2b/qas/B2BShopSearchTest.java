package com.bbraun.hybris.shop.b2b.qas;

import com.bbraun.bbmtest.conf.RunOnStage;
import com.bbraun.bbmtest.conf.RunOnStageRule;
import com.bbraun.bbmtest.ui.UiTest;
import com.bbraun.hybris.shop.b2b.B2BActions;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;

import static org.hamcrest.core.StringContains.containsString;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

@RunOnStage(stages = "QAS")
public class B2BShopSearchTest {

    @Rule
    public RunOnStageRule rule = new RunOnStageRule();

    @Test
    public void testPerformSearchFromHomepageWithSearchResults() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .execute(B2BActions::loginQAS) //

                    .assertUrl(containsString("/bob")) //
                    .doClick(By.linkText("20005585"))
                    .doWaitUntil(visibilityOfElementLocated(By.id("search")))

                    .doType(By.id("search"), "Introcan")
                    .doClick(By.className("siteSearchSubmit "))
                    .doWaitUntil(urlContains("/search/"))
                    .assertUrl(containsString("/search/?text=Introcan"))
                    .assertElementExists(By.className("searchResultList"))
                    .assertTitle(startsWith("Suchen Introcan"))
            ;
        });
    }

    @Test
    public void testPerformSearchFromHomepageWithEmptySearchResult() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .execute(B2BActions::loginQAS) //

                    .assertUrl(containsString("/bob")) //
                    .doClick(By.linkText("20005585"))
                    .doWaitUntil(visibilityOfElementLocated(By.id("search")))

                    .doType(By.id("search"), "Blablabla")
                    .doClick(By.className("siteSearchSubmit "))
                    .doWaitUntil(urlContains("/search/"))
                    .assertUrl(containsString("/search/?text=Blablabla"))
                    //.assertElementNotExists(By.className("searchResultList"))
                    .assertTitle(containsString("Blablabla"))
                    .assertTextDisplayedOnPage("Keine Suchergebnisse gefunden")
            ;
        });
    }

    @Test
    public void testPerformSearchAndAddItemToCartFromSearchResult() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .execute(B2BActions::loginQAS) //

                    .assertUrl(containsString("/bob")) //
                    .doClick(By.linkText("20005585"))
                    .doWaitUntil(visibilityOfElementLocated(By.id("search")))

                    .doType(By.id("search"), "Introcan")
                    .doClick(By.className("siteSearchSubmit "))
                    .doWaitUntil(urlContains("/search/"))
                    .doClick(By.partialLinkText("Artikel"))
                    .doWaitUntil(urlContains("pt=ARTICLE"))
                    .doClick(By.id("addToCartButton"))
            ;
        });
    }
}