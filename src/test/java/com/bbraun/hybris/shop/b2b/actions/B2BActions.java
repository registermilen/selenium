package com.bbraun.hybris.shop.b2b.actions;

import com.bbraun.bbmtest.ui.BBMUiTestBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class B2BActions {

    /**
     * Performs a login via IDP to B2B e-Shop on QAS system
     *
     * @param builder
     */
    public static void loginQAS(BBMUiTestBuilder<BBMUiTestBuilder<?>> builder) {
        builder.doOpenUrl("https://qas-shop.bbraun.com")
                .doWaitUntil(ExpectedConditions.urlContains("idp-dev.bbraun.com"))
                .assertUrl("https://idp-dev.bbraun.com/idp/SSO.saml2") //
                .doType(By.id("username"), "shopdemo@bbraun.com") //
                .doType(By.id("password"), "demo") //
                .doSubmitForm(By.className("button-primary"));
    }
}
