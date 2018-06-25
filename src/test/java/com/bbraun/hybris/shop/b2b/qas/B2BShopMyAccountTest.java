package com.bbraun.hybris.shop.b2b.qas;

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

public class B2BShopMyAccountTest {

    @Rule
    public RunOnStageRule rule = new RunOnStageRule();

    @Test
    @RunOnStage(stages = "QAS")
    public void testOpenMyAccountPageWithSavedCarts() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .execute(B2BActions::loginQAS) //

                    .assertUrl("https://qas-shop.bbraun.com/bob") //
                    .doClick(By.linkText("20005585"))
                    .doWaitUntil(urlContains("https://qas-shop.bbraun.com"))

                    .doClick(By.linkText("Mein Konto"))
                    .doWaitUntil(ExpectedConditions.urlContains("/my-account/saved-carts"))
                    .assertUrl("https://qas-shop.bbraun.com/my-account/saved-carts")
                    .assertElementExists(By.className("accountNav"))
                    .assertTextDisplayedOnPage("Bestellvorlagen")
                    .assertTextDisplayedOnPage("Neue Bestellvorlage anlegen")
            ;
        });
    }
}
