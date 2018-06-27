package com.bbraun.hybris.shop.b2b.prd;

import com.bbraun.bbmtest.conf.RunOnStage;
import com.bbraun.bbmtest.conf.RunOnStageRule;
import com.bbraun.bbmtest.ui.UiTest;
import com.bbraun.hybris.shop.b2b.B2BActions;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;

import static org.hamcrest.core.StringContains.containsString;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

@RunOnStage(stages = "PRD")
@Epic("E-Shop Tests")
@Feature("Favorite Functionality Tests")
public class B2BShopFavoritesTest {

    @Rule
    public RunOnStageRule rule = new RunOnStageRule();

    @Test
    public void testAddPridAsFavoriteFromSearch() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .execute(B2BActions::loginPRD) //

                    .assertUrl(containsString("/bob")) //
                    .doClick(By.linkText("20158045"))
                    .doWaitUntil(visibilityOfElementLocated(By.id("search")))

                    .doType(By.id("search"), "PRID00003923")
                    .doClick(By.className("siteSearchSubmit "))
                    .doWaitUntil(urlContains("/search/"))
                    .doClick(By.id("direct_favouriteWishlist"))

                    .doOpenUrl("https://shop.bbraun.com/favourite/Prid")
                    .doWaitUntil(urlContains("favourite/Prid"))
                    .assertElementExists(By.className("favouritesVanish1"))

                    .doOpenUrl("https://shop.bbraun.com/p/PRID00003923")
                    .doClick(By.id("direct_favouriteWishlist")) // remove favorite


            ;
        });
    }

    @Test
    public void testAddPridAsFavoriteFromProductDetailPage() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .execute(B2BActions::loginPRD)

                    .assertUrl(containsString("/bob")) //
                    .doClick(By.linkText("20158045"))
                    .doWaitUntil(visibilityOfElementLocated(By.id("search")))

                    .doOpenUrl("https://shop.bbraun.com/p/PRID00003923")
                    .doWaitUntil(urlContains("p/PRID00003923"))
                    .doClick(By.id("direct_favouriteWishlist")) // set as favorite

                    .doOpenUrl("https://shop.bbraun.com/favourite/Prid")
                    .doWaitUntil(urlContains("favourite/Prid"))
                    .assertElementExists(By.className("favouritesVanish1"))

                    .doClickBrowserBackButton()
                    .doWaitUntil(urlContains("p/PRID00003923"))
                    .doClick(By.id("direct_favouriteWishlist")) // remove from favorites
            ;
        });
    }

    @Test
    public void testAddPridAsFavoriteFromProductDetailPageAndPassItToCart() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .execute(B2BActions::loginPRD) //

                    .assertUrl(containsString("/bob")) //
                    .doClick(By.linkText("20158045"))
                    .doWaitUntil(visibilityOfElementLocated(By.id("search")))

                    .doOpenUrl("https://shop.bbraun.com/p/000000000004251300")
                    .doWaitUntil(urlContains("p/000000000004251300"))
                    .doClick(By.id("direct_favouriteWishlist")) // set as favorite

                    .doOpenUrl("https://shop.bbraun.com//favourite/Article")
                    .doWaitUntil(urlContains("/favourite/Article"))
                    .assertElementExists(By.className("favouritesVanish1"))
                    .doClick(By.id("addToCartButton"))
                    .assertElementExists(By.id("addToCartLayer"))

                    .doClickBrowserBackButton()
                    .doWaitUntil(urlContains("p/000000000004251300"))
                    .doClick(By.id("direct_favouriteWishlist")) // remove from favorites
            ;
        });
    }

}
