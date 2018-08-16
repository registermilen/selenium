package com.bbraun.hybris.shop.b2b.qas;

import com.bbraun.bbmtest.conf.RunOnStage;
import com.bbraun.bbmtest.conf.RunOnStageRule;
import com.bbraun.bbmtest.ui.UiTest;
import com.bbraun.hybris.shop.b2b.B2BActions;
import io.qameta.allure.*;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.hamcrest.core.StringContains.containsString;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

@RunOnStage(stages = "QAS")
@Epic("B2B E-Shop Tests")
@Feature("Cart Tests")
public class B2BShopCartTest {

    @Rule
    public RunOnStageRule rule = new RunOnStageRule();

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test Description: Test access shopping cart")
    @Story("Open shopping cart page")
    public void testAccessToShoppingCart() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .execute(B2BActions::loginQAS) //

                    .assertUrl("https://qas-shop.bbraun.com/bob") //
                    .doClick(By.linkText("0020005585"))
                    .doWaitUntil(visibilityOfElementLocated(By.className("miniCart")))

                    .doClick(By.className("miniCart"))
                    .doWaitUntil(ExpectedConditions.urlContains("/cart"))

                    .assertUrl(containsString("/cart"))
                    .assertElementExists(By.className("uploadForm")) // upload area
                    .assertElementExists(By.id("instantAddToCartButton")) // add to cart button
            ;
        });
    }


    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test Description: Test access shopping cart and add an article to the cart")
    @Story("Open shopping cart page and add an article to the cart")
    public void testAddArticleToShoppingCart() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .execute(B2BActions::loginQAS) //

                    .assertUrl("https://qas-shop.bbraun.com/bob") //
                    .doClick(By.linkText("0020005585"))
                    .doWaitUntil(visibilityOfElementLocated(By.className("miniCart")))
                    .doClick(By.className("miniCart"))

                    .doWaitUntil(ExpectedConditions.urlContains("/cart"))
                    .assertUrl(containsString("/cart"))
                    .doOpenUrl("https://qas-shop.bbraun.com/cart/remove") // clear cart
                    //.assertElementNotExists(By.className("cartItem")) // cart item row

                    .doType(By.name("productCodePost"), "5391010")
                    .doClick(By.className("code"))
                    .doClick(By.id("instantAddToCartButton"))
                    .assertElementExists(By.className("cartItem")) // cart item row
                    .doClick(By.linkText("Warenkorb entfernen"))
            ;
        });
    }
}
