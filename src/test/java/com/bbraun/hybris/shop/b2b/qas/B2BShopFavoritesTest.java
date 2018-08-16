package com.bbraun.hybris.shop.b2b.qas;

import com.bbraun.bbmtest.conf.RunOnStage;
import com.bbraun.bbmtest.conf.RunOnStageRule;
import com.bbraun.bbmtest.ui.UiTest;
import com.bbraun.hybris.shop.b2b.B2BActions;
import io.qameta.allure.*;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;

import static org.hamcrest.core.StringContains.containsString;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

@RunOnStage(stages = "QAS")
@Epic("B2B E-Shop Tests")
@Feature("Favorite Functionality Tests")
public class B2BShopFavoritesTest {

    @Rule
    public RunOnStageRule rule = new RunOnStageRule();

    @Test
    @Severity(SeverityLevel.MINOR)
    @Description("Test Description: Add a product to favourites list from search.")
    @Story("Add favourite PRID from search result")
    public void testAddPridAsFavoriteFromSearch() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .execute(B2BActions::loginQAS) //

                    .assertUrl(containsString("/bob")) //
                    .doClick(By.linkText("0020005585"))
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
    @Severity(SeverityLevel.MINOR)
    @Description("Test Description: Add a product to favourites list from product detail page.")
    @Story("Add favourite PRID from product detail page")
    public void testAddPridAsFavoriteFromProductDetailPage() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .execute(B2BActions::loginQAS)

                    .assertUrl(containsString("/bob")) //
                    .doClick(By.linkText("0020005585"))
                    .doWaitUntil(visibilityOfElementLocated(By.id("search")))

                    .doOpenUrl("https://qas-shop.bbraun.com/p/PRID00003923")
                    .doWaitUntil(urlContains("p/PRID00003923"))
                    .doClick(By.id("direct_favouriteWishlist")) // set as favorite

                    .doOpenUrl("https://qas-shop.bbraun.com/favourite/Prid")
                    .doWaitUntil(urlContains("favourite/Prid"))
                    .assertElementExists(By.className("favouritesVanish1"))

                    .doClickBrowserBackButton()
                    .doWaitUntil(urlContains("p/PRID00003923"))
                    .doClick(By.id("direct_favouriteWishlist")) // remove from favorites
            ;
        });
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @Description("Test Description: Add a product to favourites list from product detail page. Afterwards the favourites list is " +
            "accessed and the article is added to the cart")
    @Story("Add favourite PRID from product detail page and pass it to the cart")
    public void testAddPridAsFavoriteFromProductDetailPageAndPassItToCart() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .execute(B2BActions::loginQAS) //

                    .assertUrl(containsString("/bob")) //
                    .doClick(By.linkText("0020005585"))
                    .doWaitUntil(visibilityOfElementLocated(By.id("search")))

                    .doOpenUrl("https://qas-shop.bbraun.com/p/000000000004251300")
                    .doWaitUntil(urlContains("p/000000000004251300"))
                    .doClick(By.id("direct_favouriteWishlist")) // set as favorite

                    .doOpenUrl("https://qas-shop.bbraun.com//favourite/Article")
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
