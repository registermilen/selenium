package com.bbraun.hybris.shop.b2b.prd;

import com.bbraun.bbmtest.conf.RunOnStage;
import com.bbraun.bbmtest.conf.RunOnStageRule;
import com.bbraun.bbmtest.ui.UiTest;
import com.bbraun.hybris.shop.b2b.B2BActions;
import io.qameta.allure.*;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.hamcrest.core.StringContains.containsString;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

@RunOnStage(stages = "PRD")
@Epic("B2B E-Shop Tests")
@Feature("Checkout Tests")
public class B2BShopCheckoutTest {

    @Rule
    public RunOnStageRule rule = new RunOnStageRule();

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test Description: Test access to shopping cart and complete the whole checkout process.")
    @Story("Process checkout and place order.")
    public void testPassCartToCheckout() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .execute(B2BActions::loginPRD) //

                    .assertUrl("https://shop.bbraun.com/bob") //
                    .doClick(By.linkText("20158045"))
                    .doWaitUntil(visibilityOfElementLocated(By.className("miniCart")))
                    .doClick(By.className("miniCart"))
                    .doWaitUntil(ExpectedConditions.urlContains("/cart"))

                    .assertUrl(containsString("/cart"))
                    .doOpenUrl("https://shop.bbraun.com/cart/remove") // clear cart

                    .doType(By.name("productCodePost"), "5391010")
                    .doClick(By.className("code"))
                    .doClick(By.id("instantAddToCartButton"))
                    .assertElementExists(By.className("cartItem")) // cart item row

                    .doClick(By.id("checkoutButtonTop"))
                    .doWaitUntil(urlContains("/checkout/multi/common-information/add"))
                    .assertTextDisplayedOnPage("Bestellübersicht")
                    .assertTextDisplayedOnPage("Gesamtnettopreis")
                    .assertTextDisplayedOnPage("23,39")
                    .assertTextDisplayedOnPage("Mindermengenzuschlag")
                    .assertTextDisplayedOnPage("15,00")
                    .assertTextDisplayedOnPage("Umsatzsteuer")
                    .assertTextDisplayedOnPage("7,29")
                    .assertTextDisplayedOnPage("Rechnungsbetrag")
                    .assertTextDisplayedOnPage("45,68")

                    .doClick(By.className("force-right")) // Weiter
                    .doWaitUntil(urlContains("/checkout/multi/delivery-address/add"))
                    .assertTextDisplayedOnPage("Lieferadresse")

                    .doClick(By.className("force-right")) // Weiter
                    .doWaitUntil(urlContains("/checkout/multi/delivery-method/choose"))
                    .assertTextDisplayedOnPage("Liefervereinbarung")

                    .doClick(By.className("force-right")) // Weiter
                    .doWaitUntil(urlContains("/checkout/multi/summary/view"))
                    .assertTextDisplayedOnPage("Abschließende Prüfung")


                    // There is no real order executed here !!!

                    .doOpenUrl("https://shop.bbraun.com/cart/remove") // clear cart
            ;
        });
    }


}
