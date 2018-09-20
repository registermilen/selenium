package com.bbraun.hybris.shop.b2b;

import com.bbraun.bbmtest.ui.BBMUiTestBuilder;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class B2BActions {

    /**
     * Performs a login via IDP to B2B e-Shop on QAS system
     *
     * @param builder
     */
    @Step("Login to QAS Shop via IDP")
    public static void loginQAS(BBMUiTestBuilder<BBMUiTestBuilder<?>> builder) {
        builder.doOpenUrl("https://qas-shop.bbraun.com")
                .doWaitUntil(ExpectedConditions.urlContains("idp-dev.bbraun.com"))
                .assertUrl("https://idp-dev.bbraun.com/idp/SSO.saml2") //
                .doType(By.id("username"), "shopdemo@bbraun.com") //
                .doType(By.id("password"), "demo") //
                .doSubmitForm(By.className("button-primary"));
    }

    /**
     * Performs a login via Hybris login form to B2B e-Shop on PRD system
     *
     * @param builder
     */
    @Step("Login to Shop via Hybris Login form")
    public static void loginPRD(BBMUiTestBuilder<BBMUiTestBuilder<?>> builder) {
        builder.doOpenUrl("https://shop.bbraun.com")
                .doWaitUntil(ExpectedConditions.urlContains("/login"))
                .assertUrl("https://shop.bbraun.com/login") //
                .doType(By.id("j_username"), "shoptest@bbraun.com") //
                .doType(By.id("j_password"), "Tech2TestIT") //
                .doSubmitForm(By.className("positive"));
    }
}
