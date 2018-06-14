package com.bbraun.hybris.shop.b2b;

import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;

import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;

import com.bbraun.bbmtest.conf.RunOnStage;
import com.bbraun.bbmtest.conf.RunOnStageRule;
import com.bbraun.bbmtest.ui.UiTest;

public class B2BShopMyAccountTestRolling {

    @Rule
    public RunOnStageRule rule = new RunOnStageRule();

    @Test
    @RunOnStage(stages = "DEV")
    public void testOpenMyAccountPageWithOrderHistory() {
        UiTest.go(builder -> {
        	// @formatter:off
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .doOpenUrl("https://212.0.1.227:9002/?site=bbraunb2b")
                    .doOpenUrl("https://212.0.1.227:9002/hybrislogin")
                    .doType(By.id("j_username"), "test.user.vn@bbraun.com") //
					.doType(By.id("j_password"), "Abcd1234") //
					.doSubmitForm(By.id("loginForm"))
                    .doWaitUntil(urlContains("?site=bbraunb2b"))
                    .doClick(By.linkText("My Account"))
                    .doWaitUntil(urlContains("my-account/orders"))
                    .doClick(By.linkText("Quotation History"))
                    .doWaitUntil(urlContains("my-account/quotations"))
                    .doClick(By.linkText("Address Book"))
                    .doWaitUntil(urlContains("my-account/address-book"))
            ;
            
            while (true) {
            	builder.doClick(By.linkText("Quotation History"))
                .doWaitUntil(urlContains("my-account/quotations"))
                .doClick(By.linkText("Address Book"))
                .doWaitUntil(urlContains("my-account/address-book"));
			}
            // @formatter:on
        });
    }
}
