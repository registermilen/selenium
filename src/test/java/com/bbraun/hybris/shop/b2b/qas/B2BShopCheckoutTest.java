package com.bbraun.hybris.shop.b2b.qas;

import com.bbraun.bbmtest.conf.RunOnStage;
import com.bbraun.bbmtest.conf.RunOnStageRule;
import com.bbraun.bbmtest.ui.UiTest;
import com.bbraun.hybris.shop.b2b.B2BActions;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;

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
                    .execute(B2BActions::loginQAS) //

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
                    .assertTextDisplayedOnPage("66,83")
                    .assertTextDisplayedOnPage("Mindermengenzuschlag")
                    .assertTextDisplayedOnPage("15,00")
                    .assertTextDisplayedOnPage("Umsatzsteuer")
                    .assertTextDisplayedOnPage("15,55")
                    .assertTextDisplayedOnPage("Rechnungsbetrag")
                    .assertTextDisplayedOnPage("97,38")

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
