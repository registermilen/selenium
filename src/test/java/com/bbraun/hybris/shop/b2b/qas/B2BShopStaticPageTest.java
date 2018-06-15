package com.bbraun.hybris.shop.b2b.qas;

import com.bbraun.bbmtest.conf.RunOnStage;
import com.bbraun.bbmtest.conf.RunOnStageRule;
import com.bbraun.bbmtest.ui.UiTest;
import com.bbraun.hybris.shop.b2b.B2BActions;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class B2BShopStaticPageTest {

    @Rule
    public RunOnStageRule rule = new RunOnStageRule();

    @Test
    @RunOnStage(stages = "QAS")
    public void testOpenTermsOfUsePage() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .execute(B2BActions::loginQAS) //

                    .assertUrl("https://qas-shop.bbraun.com/bob") //
                    .doClick(By.linkText("20005585"))
                    .doWaitUntil(ExpectedConditions.urlContains("https://qas-shop.bbraun.com"))
                    .assertElementExists(By.linkText("Nutzungsbedingungen"))
                    .doClick(By.linkText("Nutzungsbedingungen"))
                    .assertUrl("https://qas-shop.bbraun.com/info-pages/termsOfUse")
                    .assertTextDisplayedOnPage("Nutzungsbedingung")
            ;
        });
    }

    @Test
    @RunOnStage(stages = "QAS")
    public void testOpenPrivacyPolicyPage() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .execute(B2BActions::loginQAS) //

                    .assertUrl("https://qas-shop.bbraun.com/bob") //
                    .doClick(By.linkText("20005585"))
                    .doWaitUntil(ExpectedConditions.urlContains("https://qas-shop.bbraun.com"))
                    .assertElementExists(By.linkText("Datenschutz"))
                    .doClick(By.linkText("Datenschutz"))
                    .assertUrl("https://qas-shop.bbraun.com/info-pages/privacyPolicy")
                    .assertTextDisplayedOnPage("Datenschutz")
            ;
        });
    }
}
