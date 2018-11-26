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
                .doType(By.id("username"), "shoptest@bbraun.com") //
                .doType(By.id("password"), "Passw0rd1") //
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
                .doWaitUntil(ExpectedConditions.urlContains("idp.bbraun.com"))
                .assertUrl("https://idp.bbraun.com/idp/SSO.saml2") //
                .doType(By.id("username"), "shoptest@bbraun.com") //
                .doType(By.id("password"), "u!%Jb7z2") //
                .doSubmitForm(By.className("button-primary"));
    }
}
