package com.bbraun.hybris.shop.b2b;

import com.bbraun.bbmtest.conf.RunOnStage;
import com.bbraun.bbmtest.conf.RunOnStageRule;
import com.bbraun.bbmtest.ui.UiTest;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.hamcrest.core.StringContains.containsString;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class B2BShopCheckoutTest {

    @Rule
    public RunOnStageRule rule = new RunOnStageRule();

    @Test
    @RunOnStage(stages = "QAS")
    public void testPassCartToCheckout() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .doOpenUrl("https://qas-shop.bbraun.com")
                    .doWaitUntil(ExpectedConditions.urlContains("idp-dev.bbraun.com"))
                    .assertUrl("https://idp-dev.bbraun.com/idp/SSO.saml2") //
                    .doType(By.id("username"), "shopdemo@bbraun.com") //
                    .doType(By.id("password"), "demo") //
                    .doSubmitForm(By.className("button-primary")) //

                    .assertUrl("https://qas-shop.bbraun.com/bob") //
                    .doClick(By.linkText("20005585"))
                    .doWaitUntil(visibilityOfElementLocated(By.className("miniCart")))
                    .doClick(By.className("miniCart"))

                    .assertUrl(containsString("/cart"))
                    .doOpenUrl("https://qas-shop.bbraun.com/cart/remove") // clear cart

                    .doType(By.name("productCodePost"), "5391010")
                    .doClick(By.id("instantAddToCartButton"))
                    .assertElementExists(By.className("cartItem")) // cart item row

                    .doClick(By.id("checkoutButtonTop"))
                    .doWaitUntil(urlContains("/checkout/multi/common-information/add"))
                    .assertTextDisplayedOnPage("Bestellübersicht")
                    .assertTextDisplayedOnPage("Gesamtnettopreis")
                    .assertTextDisplayedOnPage("€ 66,83")
                    .assertTextDisplayedOnPage("Mindermengenzuschlag")
                    .assertTextDisplayedOnPage("€ 6,00")
                    .assertTextDisplayedOnPage("Umsatzsteuer")
                    .assertTextDisplayedOnPage("€ 13,84")
                    .assertTextDisplayedOnPage("Rechnungsbetrag")
                    .assertTextDisplayedOnPage("€ 86,67")

                    .doClick(By.className("force-right")) // Weiter
                    .doWaitUntil(urlContains("/checkout/multi/delivery-address/add"))
                    .assertTextDisplayedOnPage("Lieferadresse")

                    .doClick(By.className("force-right")) // Weiter
                    .doWaitUntil(urlContains("/checkout/multi/delivery-method/choose"))
                    .assertTextDisplayedOnPage("Liefervereinbarung")

                    .doClick(By.className("force-right")) // Weiter
                    .doWaitUntil(urlContains("/checkout/multi/summary/view"))
                    .assertTextDisplayedOnPage("Abschließende Prüfung")

                    .doClick(By.className("force-right")) //Kostenpflichtig bestellen
                    .doWaitUntil(urlContains("/checkout/documentsConfirmation/"))
                    .assertTextDisplayedOnPage("Vielen Dank für Ihre Bestellung")
            ;
        });
    }


}
