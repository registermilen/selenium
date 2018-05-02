package com.bbraun.hybris.shop.b2b;

import com.bbraun.bbmtest.conf.RunOnStage;
import com.bbraun.bbmtest.conf.RunOnStageRule;
import com.bbraun.bbmtest.ui.UiTest;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;

import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;

public class B2BShopMyAccountTest {

    @Rule
    public RunOnStageRule rule = new RunOnStageRule();

    @Test
    @RunOnStage(stages = "QAS")
    public void testOpenMyAccountPageWithOrderHistory() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //

                    .doOpenUrl("https://qas-shop.bbraun.com")
                    .doWaitUntil(urlContains("idp-dev.bbraun.com"))
                    .assertUrl("https://idp-dev.bbraun.com/idp/SSO.saml2") //
                    .doType(By.id("username"), "shopdemo@bbraun.com") //
                    .doType(By.id("password"), "demo") //
                    .doSubmitForm(By.className("button-primary")) //
                    .assertUrl("https://qas-shop.bbraun.com/bob") //

                    .doClick(By.linkText("20005585"))
                    .doWaitUntil(urlContains("https://qas-shop.bbraun.com"))

                    .doClick(By.linkText("Ihr Konto"))
                    .assertUrl("https://qas-shop.bbraun.com/my-account/orders")
                    .assertElementPresent(By.className("accountNav"))
                    .assertTextDisplayedOnPage("Bestellhistorie")
                    .assertElementPresent(By.id("submitFilter")) // "Filter anwenden"
                    .assertTitle(startsWith("Bestellhistorie"))
            ;
        });
    }
}
