package com.bbraun.hybris.shop.b2b.prd;

import com.bbraun.bbmtest.conf.RunOnStage;
import com.bbraun.bbmtest.conf.RunOnStageRule;
import com.bbraun.bbmtest.ui.UiTest;
import com.bbraun.hybris.shop.b2b.B2BActions;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;

import static org.hamcrest.core.StringContains.containsString;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class B2BShopCartTest {

    @Rule
    public RunOnStageRule rule = new RunOnStageRule();

    @Test
    @RunOnStage(stages = "PRD")
    public void testAccessToShoppingCart() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .execute(B2BActions::loginPRD) //

                    .assertUrl("https://shop.bbraun.com/bob") //
                    .doClick(By.linkText("20158045"))
                    .doWaitUntil(visibilityOfElementLocated(By.className("miniCart")))
                    .doClick(By.className("miniCart"))

                    .assertUrl(containsString("/cart"))
                    .assertElementExists(By.id("cartItems")) // items table
                    .assertElementExists(By.id("checkoutButtonTop")) // order button
            ;
        });
    }


    @Test
    @RunOnStage(stages = "PRD")
    public void testAddArticleToShoppingCart() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .execute(B2BActions::loginPRD) //

                    .assertUrl("https://shop.bbraun.com/bob") //
                    .doClick(By.linkText("20158045"))
                    .doWaitUntil(visibilityOfElementLocated(By.className("miniCart")))
                    .doClick(By.className("miniCart"))

                    .assertUrl(containsString("/cart"))
                    .doOpenUrl("https://shop.bbraun.com/cart/remove") // clear cart
                    .assertElementNotExists(By.className("cartItem")) // cart item row

                    .doType(By.name("productCodePost"), "5391010")
                    .doClick(By.id("instantAddToCartButton"))
                    .assertElementExists(By.className("cartItem")) // cart item row
                    .doClick(By.linkText("Warenkorb entfernen"))
            ;
        });
    }
}
