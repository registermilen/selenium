package com.bbraun.hybris.shop.b2b.qas;

import com.bbraun.bbmtest.conf.RunOnStage;
import com.bbraun.bbmtest.conf.RunOnStageRule;
import com.bbraun.bbmtest.conf.TestProperty;
import com.bbraun.bbmtest.conf.TestPropertyRule;
import com.bbraun.bbmtest.ui.UiTest;
import com.bbraun.hybris.shop.b2b.B2BActions;
import io.qameta.allure.*;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

@RunOnStage(stages = "QAS")
@Epic("B2B E-Shop Tests")
@Feature("Login Tests")
public class B2BShopLoginTest {

    @Rule
    public RunOnStageRule rule = new RunOnStageRule();

    @ClassRule
	public static TestPropertyRule testPropertiesRule = new TestPropertyRule();
    
    @TestProperty("hybris.shop.b2b.vn.b2bunit")
    private static String b2bUnit1;
    
    @TestProperty("hybris.shop.b2b.german.b2bunit")
    private static String b2bUnit2;
    
    @Test
    @Severity(SeverityLevel.BLOCKER)
    @Description("Test Description: Test of Login and Logout via IDP success.")
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
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test Description: Test BOB user switch")
    @Story("Login and switch BOB user without logout")
    public void testUserSwitch() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .execute(B2BActions::loginQAS) //

                    .assertUrl("https://qas-shop.bbraun.com/bob") //
                    .doClick(By.linkText(b2bUnit1))
                    .doWaitUntil(ExpectedConditions.urlContains("https://qas-shop.bbraun.com"))
                    .assertTextDisplayedOnPage(b2bUnit1)

                    .doClick(By.linkText("Switch User"))
                    .doWaitUntil(ExpectedConditions.urlContains("/bob"))
                    .assertUrl("https://qas-shop.bbraun.com/bob")
                    .doClick(By.linkText(b2bUnit2))
                    .doWaitUntil(ExpectedConditions.urlContains("https://qas-shop.bbraun.com"))
                    .assertTextDisplayedOnPage(b2bUnit2)
            ;
        });
    }
}
