package com.bbraun.hybris.shop.b2b;

import com.bbraun.bbmtest.conf.RunOnStage;
import com.bbraun.bbmtest.conf.RunOnStageRule;
import com.bbraun.bbmtest.ui.UiTest;
import org.hamcrest.core.StringContains;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;

import static org.hamcrest.core.StringContains.containsString;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;

public class B2BShopSearchTest {

    @Rule
    public RunOnStageRule rule = new RunOnStageRule();

    @Test
    @RunOnStage(stages = "QAS")
    public void testPerformSearchFromHomepageWithSearchResults() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //

                    .doOpenUrl("https://qas-shop.bbraun.com")
                    .doWaitUntil(urlContains("idp-dev.bbraun.com"))
                    .assertUrl("https://idp-dev.bbraun.com/idp/SSO.saml2") //
                    .doType(By.id("username"), "shopdemo@bbraun.com") //
                    .doType(By.id("password"), "demo") //
                    .doSubmitForm(By.className("button-primary")) //
                    .assertUrl(containsString("/bob")) //

                    .doClick(By.linkText("20005585"))
                    .doWaitUntil(urlContains("https://qas-shop.bbraun.com"))

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
    @RunOnStage(stages = "QAS")
    public void testPerformSearchFromHomepageWithEmptySearchResult() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //

                    .doOpenUrl("https://qas-shop.bbraun.com")
                    .doWaitUntil(urlContains("idp-dev.bbraun.com"))
                    .assertUrl("https://idp-dev.bbraun.com/idp/SSO.saml2") //
                    .doType(By.id("username"), "shopdemo@bbraun.com") //
                    .doType(By.id("password"), "demo") //
                    .doSubmitForm(By.className("button-primary")) //
                    .assertUrl(containsString("/bob")) //

                    .doClick(By.linkText("20005585"))
                    .doWaitUntil(urlContains("https://qas-shop.bbraun.com"))

                    .doType(By.id("search"), "Blablabla")
                    .doClick(By.className("siteSearchSubmit "))
                    .doWaitUntil(urlContains("/search/"))
                    .assertUrl(containsString("/search/?text=Blablabla"))
                    .assertElementNotExists(By.className("searchResultList"))
                    .assertTitle(containsString("Blablabla"))
                    .assertTextDisplayedOnPage("Keine Suchergebnisse gefunden")
            ;
        });
    }
}