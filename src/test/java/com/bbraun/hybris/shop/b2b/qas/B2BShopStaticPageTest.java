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

@RunOnStage(stages = "QAS")
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
                    .execute(B2BActions::loginQAS) //

                    .assertUrl("https://qas-shop.bbraun.com/bob") //
                    .doClick(By.linkText("0020005585"))
                    .doWaitUntil(ExpectedConditions.urlContains("https://qas-shop.bbraun.com"))
                    .assertElementExists(By.linkText("Nutzungsbedingungen"))
                    .doClick(By.linkText("Nutzungsbedingungen"))
                    .assertUrl("https://qas-shop.bbraun.com/info-pages/termsOfUse")
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
                    .execute(B2BActions::loginQAS) //

                    .assertUrl("https://qas-shop.bbraun.com/bob") //
                    .doClick(By.linkText("0020005585"))
                    .doWaitUntil(ExpectedConditions.urlContains("https://qas-shop.bbraun.com"))
                    .assertElementExists(By.linkText("Datenschutz"))
                    .doClick(By.linkText("Datenschutz"))
                    .assertUrl("https://qas-shop.bbraun.com/info-pages/privacyPolicy")
                    .assertTextDisplayedOnPage("Datenschutz")
            ;
        });
    }
}
