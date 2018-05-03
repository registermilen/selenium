package com.bbraun.hybris.shop.b2b;

import com.bbraun.bbmtest.conf.RunOnStage;
import com.bbraun.bbmtest.conf.RunOnStageRule;
import com.bbraun.bbmtest.ui.UiTest;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;

import static org.hamcrest.core.StringContains.containsString;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class B2BShopFavoritesTest {

    @Rule
    public RunOnStageRule rule = new RunOnStageRule();

    @Test
    @RunOnStage(stages = "QAS")
    public void testAddPridAsFavoriteFromSearch() {
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
                    .doWaitUntil(visibilityOfElementLocated(By.id("search")))

                    .doType(By.id("search"), "PRID00003923")
                    .doClick(By.className("siteSearchSubmit "))
                    .doWaitUntil(urlContains("/search/"))
                    .doClick(By.id("direct_favouriteWishlist"))

                    .doOpenUrl("https://qas-shop.bbraun.com/favourite/Prid")
                    .doWaitUntil(urlContains("favourite/Prid"))
                    .assertElementExists(By.className("favouritesVanish1"))

                    .doOpenUrl("https://qas-shop.bbraun.com/p/PRID00003923")
                    .doClick(By.id("direct_favouriteWishlist")) // remove favorite


            ;
        });
    }

    @Test
    @RunOnStage(stages = "QAS")
    public void testAddPridAsFavoriteFromProductDetailPage() {
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
                    .doWaitUntil(visibilityOfElementLocated(By.id("search")))

                    .doOpenUrl("https://qas-shop.bbraun.com/p/PRID00003923")
                    .doWaitUntil(urlContains("p/PRID00003923"))
                    .doClick(By.id("direct_favouriteWishlist")) // set as favorite

                    .doOpenUrl("https://qas-shop.bbraun.com/favourite/Prid")
                    .doWaitUntil(urlContains("favourite/Prid"))
                    .assertElementExists(By.className("favouritesVanish1"))

                    .doGoBack()
                    .doWaitUntil(urlContains("p/PRID00003923"))
                    .doClick(By.id("direct_favouriteWishlist")) // remove from favorites
            ;
        });
    }

    @Test
    @RunOnStage(stages = "QAS")
    public void testAddPridAsFavoriteFromProductDetailPageAndPassItToCart() {
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
                    .doWaitUntil(visibilityOfElementLocated(By.id("search")))

                    .doOpenUrl("https://qas-shop.bbraun.com/p/000000000004251300")
                    .doWaitUntil(urlContains("p/000000000004251300"))
                    .doClick(By.id("direct_favouriteWishlist")) // set as favorite

                    .doOpenUrl("https://qas-shop.bbraun.com//favourite/Article")
                    .doWaitUntil(urlContains("/favourite/Article"))
                    .assertElementExists(By.className("favouritesVanish1"))
                    .doClick(By.id("addToCartButton"))
                    .assertElementExists(By.id("addToCartLayer"))

                    .doGoBack()
                    .doWaitUntil(urlContains("p/000000000004251300"))
                    .doClick(By.id("direct_favouriteWishlist")) // remove from favorites
            ;
        });
    }

}
