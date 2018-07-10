package com.bbraun.hybris.shop.b2b.qas;

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

@RunOnStage(stages = "QAS")
@Epic("B2B E-Shop Tests")
@Feature("Checkout Tests")
public class B2BShopCheckoutTest {

    @Rule
    public RunOnStageRule rule = new RunOnStageRule();

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test Description: Test access to shopping cart and complete the whole checkout process.")
    @Story("Process checkout and place order for CH customer.")
    public void testPassCartToCheckoutAndPlaceOrderForCHCustomer() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .execute(B2BActions::loginQAS) //

                    .assertUrl("https://qas-shop.bbraun.com/bob") //
                    .doClick(By.linkText("20262030"))
                    .doWaitUntil(visibilityOfElementLocated(By.className("miniCart")))
                    .doClick(By.className("miniCart"))
                    .doWaitUntil(ExpectedConditions.urlContains("/cart"))

                    .assertUrl(containsString("/cart"))
                    .doOpenUrl("https://qas-shop.bbraun.com/cart/remove") // clear cart

                    .doType(By.className("instantAddToCartQty"), "100") // currently means 1001
                    .doType(By.name("productCodePost"), "4097076")
                    .doClick(By.className("code"))
                    .doClick(By.id("instantAddToCartButton"))
                    .assertElementExists(By.className("cartItem")) // cart item row
                    .doClick(By.id("checkoutButtonTop"))
                    .doWaitUntil(urlContains("/checkout/multi/common-information/add"))
                    .assertTextDisplayedOnPage("Bestellübersicht")
                    .assertTextDisplayedOnPage("Gesamtnettopreis")
                    .assertTextDisplayedOnPage("329,33")
                    .assertTextDisplayedOnPage("Mindermengenzuschlag")
                    .assertTextDisplayedOnPage("30,00")
                    .assertTextDisplayedOnPage("Mehrwertsteuer")
                    .assertTextDisplayedOnPage("27,67")
                    .assertTextDisplayedOnPage("Rechnungsbetrag")
                    .assertTextDisplayedOnPage("387,00")

                    .doClick(By.className("force-right")) // Weiter
                    .doWaitUntil(urlContains("/checkout/multi/delivery-method/choose"))
                    .assertTextDisplayedOnPage("Liefervereinbarung")

                    .doClick(By.className("force-right")) // Weiter
                    .doWaitUntil(urlContains("/checkout/multi/summary/view"))
                    .assertTextDisplayedOnPage("Abschließende Prüfung")

                    .doClick(By.id("termsCheck1")) // AGB akzeptieren bei CH
                    .doClick(By.className("force-right")) //Kostenpflichtig bestellen
                    .doWaitUntil(urlContains("/checkout/documentsConfirmation/"))
                    .assertTextDisplayedOnPage("Vielen Dank für Ihre Bestellung")
            ;
        });
    }


}
