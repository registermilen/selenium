package com.bbraun.hybris.shop.b2b;

import com.bbraun.bbmtest.conf.RunOnStage;
import com.bbraun.bbmtest.conf.RunOnStageRule;
import com.bbraun.bbmtest.ui.UiTest;
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
                    .doOpenUrl("https://qas-shop.bbraun.com")
                    .doWaitUntil(ExpectedConditions.urlContains("idp-dev.bbraun.com"))
                    .assertUrl("https://idp-dev.bbraun.com/idp/SSO.saml2") //
                    .doType(By.id("username"), "shopdemo@bbraun.com") //
                    .doType(By.id("password"), "demo") //
                    .doSubmitForm(By.className("button-primary")) //

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
                    .doOpenUrl("https://qas-shop.bbraun.com")
                    .doWaitUntil(ExpectedConditions.urlContains("idp-dev.bbraun.com"))
                    .assertUrl("https://idp-dev.bbraun.com/idp/SSO.saml2") //
                    .doType(By.id("username"), "shopdemo@bbraun.com") //
                    .doType(By.id("password"), "demo") //
                    .doSubmitForm(By.className("button-primary")) //

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
