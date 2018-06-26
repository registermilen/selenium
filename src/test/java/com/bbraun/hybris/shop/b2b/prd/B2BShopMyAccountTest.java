package com.bbraun.hybris.shop.b2b.prd;

import com.bbraun.bbmtest.conf.RunOnStage;
import com.bbraun.bbmtest.conf.RunOnStageRule;
import com.bbraun.bbmtest.ui.UiTest;
import com.bbraun.hybris.shop.b2b.B2BActions;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;

@RunOnStage(stages = "PRD")
public class B2BShopMyAccountTest {

    @Rule
    public RunOnStageRule rule = new RunOnStageRule();

    @Test
    public void testOpenMyAccountPageWithOrderHistory() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .execute(B2BActions::loginPRD) //

                    .assertUrl("https://shop.bbraun.com/bob") //
                    .doClick(By.linkText("20158045"))
                    .doWaitUntil(urlContains("https://shop.bbraun.com"))

                    .doClick(By.linkText("Ihr Konto"))
                    .doWaitUntil(ExpectedConditions.urlContains("/my-account/orders"))
                    .assertUrl("https://shop.bbraun.com/my-account/orders")
                    .assertElementExists(By.className("accountNav"))
                    .assertTextDisplayedOnPage("Bestellhistorie")
                    .assertElementExists(By.id("submitFilter")) // "Filter anwenden"
                    .assertTitle(startsWith("Bestellhistorie"))
            ;
        });
    }
}
