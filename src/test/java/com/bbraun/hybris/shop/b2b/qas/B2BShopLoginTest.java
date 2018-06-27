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
@Epic("E-Shop Tests")
@Feature("Login Tests")
public class B2BShopLoginTest {

    @Rule
    public RunOnStageRule rule = new RunOnStageRule();

    @Test
    @Description("Test Description: Test of Login and Logout via IDP success.")
    @Severity(SeverityLevel.BLOCKER)
    @Story("Login with correct credentials using IDP")
    public void testBobUserLoginAndLogoutViaIdpSuccess() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .execute(B2BActions::loginQAS) //
                    .assertUrl("https://qas-shop.bbraun.com/bob")
                    .doClick(By.linkText("Logout"))
                    .doWaitUntil(ExpectedConditions.urlContains("idp-dev.bbraun.com"))
                    .assertUrl("https://idp-dev.bbraun.com/idp/SSO.saml2")
            ;
        });
    }

    @Test
    @Description("Test Description: Test BOB user switch")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Login and switch BOB user without logout")
    public void testUserSwitch() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .execute(B2BActions::loginQAS) //

                    .assertUrl("https://qas-shop.bbraun.com/bob") //
                    .doClick(By.linkText("20344385"))
                    .doWaitUntil(ExpectedConditions.urlContains("https://qas-shop.bbraun.com"))
                    .assertTextDisplayedOnPage("20344385")

                    .doClick(By.linkText("Switch User"))
                    .assertUrl("https://qas-shop.bbraun.com/bob")
                    .doClick(By.linkText("20005585"))
                    .doWaitUntil(ExpectedConditions.urlContains("https://qas-shop.bbraun.com"))
                    .assertTextDisplayedOnPage("20005585")
            ;
        });
    }
}
