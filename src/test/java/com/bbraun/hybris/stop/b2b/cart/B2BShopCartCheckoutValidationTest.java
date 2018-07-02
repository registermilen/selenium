package com.bbraun.hybris.stop.b2b.cart;

import static org.hamcrest.core.StringContains.containsString;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.bbraun.bbmtest.conf.TestProperty;
import com.bbraun.bbmtest.conf.TestPropertyRule;
import com.bbraun.bbmtest.ui.UiTest;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("B2B E-Shop Tests")
@Feature("Checkout Tests")
public class B2BShopCartCheckoutValidationTest {
	
	@ClassRule
	public static TestPropertyRule testPropertiesRule = new TestPropertyRule();
	
	@TestProperty("hybris.shop.b2b.host")
	private static String host;
	
	@TestProperty("hybris.shop.b2b.german.user.username")
	private static String username;
	
	@TestProperty("hybris.shop.b2b.german.user.password")
	private static String password;
	
	@TestProperty("hybris.shop.b2b.initial.url")
	private static String initialURL;
	
	@TestProperty("hybris.shop.b2b.german.product.with.minimal.quantity")
	private static String productWithMinimalQuantity;
	
	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("Test Description: Try to checkout invalid quantity for a "
			+ "product with minimum valid quantity, correct it and proceed to checkout.")
	@Story("PCAG-3815 Error Handling in Cart")
	public void testProductCheckoutWithInvalidThenValidQuantity() {
		UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .doOpenUrl(initialURL)
                    .doWaitUntil(ExpectedConditions.urlContains("/login"))
                    .assertUrl(host+"/login") //
                    .doType(By.id("j_username"), username) //
                    .doType(By.id("j_password"), password) //
                    .doSubmitForm(By.className("positive"))
                    .assertUrl(initialURL) //
                    
                    .doWaitUntil(visibilityOfElementLocated(By.className("miniCart")))
                    .doClick(By.className("miniCart"))
                    .doWaitUntil(ExpectedConditions.urlContains("/cart"))

                    .assertUrl(containsString("/cart"))
                    .doOpenUrl(host+"/cart/remove") // clear cart

                    .doWaitUntil(visibilityOfElementLocated(By.name("productCodePost")))
                    .doType(By.name("productCodePost"), productWithMinimalQuantity)
                    .doClick(By.className("code"))
                    .doClick(By.id("instantAddToCartButton"))
                    .assertElementExists(By.className("cartItem")) // cart item row

                    .doClick(By.id("checkoutButtonTop"))
                    
                    .assertUrl(containsString("/cart"))//make sure we stay on the cart
                    .assertTextDisplayedOnPage("Fehler im Bestellvorgang") //cart level error message
                    
                    .assertTextDisplayedOnPage("Bitte die Mindestbestellmenge beachten: 10 ST") //item level error message
                    .assertTextDisplayedOnPage("Preis kann nicht ermittelt werden") //item level error message
                    
                    .doType(By.name("quantity"), 200)
                    .doClick(By.className("_shopitems-item__update"))
                    
                    .assertTextNOTDisplayedOnPage("Fehler im Bestellvorgang") //all messages should be cleared
                    .assertTextNOTDisplayedOnPage("Bitte die Mindestbestellmenge beachten: 10 ST") 
                    .assertTextNOTDisplayedOnPage("Preis kann nicht ermittelt werden") 
                    
                    .doClick(By.id("checkoutButtonTop"))
                    
                    .doWaitUntil(ExpectedConditions.urlContains("/checkout"))
                    // There is no real order executed here !!!
                    .doOpenUrl(host+"/cart/remove") // clear cart
//                    .doOpenUrl("https://shop.bbraun.com/cart/remove") // clear cart
            ;
        });
	}
}
