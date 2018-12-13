package com.bbraun.hybris.shop.b2b.prd;

import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.bbraun.bbmtest.conf.RunOnStage;
import com.bbraun.bbmtest.conf.RunOnStageRule;
import com.bbraun.bbmtest.ui.UiTest;
import com.bbraun.hybris.shop.b2b.B2BActions;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@RunOnStage(stages = "PRD")
@Epic("B2B E-Shop Tests")
@Feature("Login Tests")
public class B2BShopLoginTest {

    @Rule
    public RunOnStageRule rule = new RunOnStageRule();

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Description("Test Description: Test of Login and Logout via IDP success.")
    @Story("Login with correct credentials using IDP")
    public void testBobUserLoginAndLogoutViaIdpSuccess() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .execute(B2BActions::loginPRD) //

                    .assertUrl("https://shop.bbraun.com/bob")
                    .doClick(By.linkText("Logout"))
                    .doWaitUntil(ExpectedConditions.urlContains("https://idp.bbraun.com"))
                    .assertUrl("https://idp.bbraun.com/idp/SSO.saml2")
            ;
        });
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test Description: Test BOB user switch")
    @Story("Login and switch BOB user without logout")
    public void testUserSwitch() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .execute(B2BActions::loginPRD) //

                    .assertUrl("https://shop.bbraun.com/bob") //
                    .doClick(By.linkText("0020158045"))
                    .doWaitUntil(ExpectedConditions.urlContains("https://shop.bbraun.com"))
                    .assertTextDisplayedOnPage("0020158045")

                    .doClick(By.linkText("Kundenwechsel"))
                    .doWaitUntil(ExpectedConditions.urlContains("/bob"))
                    .assertUrl("https://shop.bbraun.com/bob")
                    .doClick(By.linkText("0020371505"))
                    .doWaitUntil(ExpectedConditions.urlContains("https://shop.bbraun.com"))
                    .assertTextDisplayedOnPage("0020371505")
            ;
        });
    }

}
