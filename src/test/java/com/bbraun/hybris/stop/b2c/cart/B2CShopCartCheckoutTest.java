package com.bbraun.hybris.stop.b2c.cart;

import static org.hamcrest.core.StringContains.containsString;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.bbraun.bbmtest.conf.RunOnStage;
import com.bbraun.bbmtest.conf.TestProperty;
import com.bbraun.bbmtest.conf.TestPropertyRule;
import com.bbraun.bbmtest.ui.UiTest;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("B2B E-Shop Tests")
@Feature("Checkout Tests")
@RunOnStage(stages = "QAS")
public class B2CShopCartCheckoutTest {
	
	@ClassRule
	public static TestPropertyRule testPropertiesRule = new TestPropertyRule();
	
	@TestProperty("hybris.shop.b2c.host")
	private static String host;
	
	@TestProperty("hybris.shop.b2c.fr.user.username")
	private static String username;
	
	@TestProperty("hybris.shop.b2c.fr.user.password")
	private static String password;
	
	@TestProperty("hybris.shop.b2c.initial.url")
	private static String initialURL;
	
	@TestProperty("hybris.shop.b2b.german.product.with.minimal.quantity")
	private static String productWithMinimalQuantity;
	
	@TestProperty("hybris.shop.b2b.german.b2bunit")
	private static String b2bUnit;
	
	
	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("Test Description: Try to checkout a product via b2c shop.")
	@Issue("PCAG-4799")
	@Story("Checkout in B2C cart")
	public void testProductCheckoutWithInvalidThenValidQuantity() {
		UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .doOpenUrl(initialURL) // Initial url contains login information
                    .assertUrl(containsString("/cart"))
                    .doOpenUrl(host+"/cart/remove") // clear cart
                    
                    .doWaitUntil(visibilityOfElementLocated(By.className("addToCartButton")))
                    .doClick(By.id("addToCartButton"))
                    .doWaitUntil(ExpectedConditions.urlContains("/cart"))
            		.doClick(By.className("fa-plus"))
            		.doClick(By.className("fa-plus")) //add two more items
            		.doClick(By.id("checkoutButtonTop"))
            		
            		.assertUrl(containsString("/checkout/multi/common-information/add"))
            		.assertTextDisplayedOnPage("3") //item count
            		
                    .doClick(By.className("float-right")) 
                    .doWaitUntil(urlContains("/checkout/multi/delivery-address/add"))

                    .doType(By.id("deliveryFirstname"), "John")
                    .doType(By.id("deliveryLastname"), "Doe")
                    .doType(By.id("deliveryStreet"), "123 Main St")
                    .doType(By.id("deliveryPostalCode"), "456")
                    .doType(By.id("deliveryCity"), "Town")
                    .doType(By.id("billingEMail"), "mail@example.com")
                    
                    .doClick(By.className("float-right")) 
                    .doType(By.id("deliveryPostalCode"), "456789")
                    
                    .doClick(By.className("float-right"))
                    .doWaitUntil(urlContains("/multi/delivery-method/choose"))
                    .doClick(By.className("float-right"))
                    .doWaitUntil(urlContains("/checkout/multi/summary/view"))
                    
            		;
        });
	}
}
