package com.bbraun.hybris.shop.b2b.prd;

import com.bbraun.bbmtest.conf.RunOnStage;
import com.bbraun.bbmtest.conf.RunOnStageRule;
import com.bbraun.bbmtest.ui.UiTest;
import com.bbraun.hybris.shop.b2b.B2BActions;
import io.qameta.allure.*;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

@RunOnStage(stages = "PRD")
@Epic("B2B E-Shop Tests")
@Feature("Static Pages Tests")
public class B2BShopStaticPageTest {

    @Rule
    public RunOnStageRule rule = new RunOnStageRule();

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Access terms of use page")
    @Story("Access terms of use page")
    public void testOpenTermsOfUsePage() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .execute(B2BActions::loginPRD) //

                    .assertUrl("https://shop.bbraun.com/bob") //
                    .doClick(By.linkText("0020158045"))
                    .doWaitUntil(ExpectedConditions.urlContains("https://shop.bbraun.com"))
                    .assertElementExists(By.linkText("Nutzungsbedingungen"))
                    .doClick(By.linkText("Nutzungsbedingungen"))
                    .doWaitUntil(ExpectedConditions.urlContains("/termsOfUse"))
                    .assertUrl("https://shop.bbraun.com/info-pages/termsOfUse")
                    .assertTextDisplayedOnPage("Nutzungsbedingung")
            ;
        });
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Access privacy policy page")
    @Story("Access privacy policy page")
    public void testOpenPrivacyPolicyPage() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .execute(B2BActions::loginPRD) //

                    .assertUrl("https://shop.bbraun.com/bob") //
                    .doClick(By.linkText("0020158045"))
                    .doWaitUntil(ExpectedConditions.urlContains("https://shop.bbraun.com"))
                    .assertElementExists(By.linkText("Datenschutz"))
                    .doClick(By.linkText("Datenschutz"))
                    .doWaitUntil(ExpectedConditions.urlContains("/privacyPolicy"))
                    .assertUrl("https://shop.bbraun.com/info-pages/privacyPolicy")
                    .assertTextDisplayedOnPage("Datenschutz")
            ;
        });
    }
}
