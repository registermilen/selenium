package com.bbraun.hybris.shop.b2b.prd;

import com.bbraun.bbmtest.conf.RunOnStage;
import com.bbraun.bbmtest.conf.RunOnStageRule;
import com.bbraun.bbmtest.ui.UiTest;
import com.bbraun.hybris.shop.b2b.B2BActions;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class B2BShopLoginTest {

    @Rule
    public RunOnStageRule rule = new RunOnStageRule();

    @Test
    @RunOnStage(stages = "PRD")
    public void testBobUserLoginAndLogoutViaIdpSuccess() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .execute(B2BActions::loginPRD) //

                    .assertUrl("https://shop.bbraun.com/bob")
                    .doClick(By.linkText("Logout"))
                    .doWaitUntil(ExpectedConditions.urlContains("hybrislogin"))
                    .assertUrl("https://shop.bbraun.com/hybrislogin")
            ;
        });
    }

    @Ignore // Not in place on PRD yet
    @RunOnStage(stages = "PRD")
    public void testUserSwitch() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .execute(B2BActions::loginPRD) //

                    .assertUrl("https://shop.bbraun.com/bob") //
                    .doClick(By.linkText("20158045"))
                    .doWaitUntil(ExpectedConditions.urlContains("https://shop.bbraun.com"))
                    .assertTextDisplayedOnPage("20158045")

                    .doClick(By.linkText("Switch User"))
                    .assertUrl("https://shop.bbraun.com/bob")
                    .doClick(By.linkText("20371505"))
                    .doWaitUntil(ExpectedConditions.urlContains("https://shop.bbraun.com"))
                    .assertTextDisplayedOnPage("20371505")
            ;
        });
    }

}
