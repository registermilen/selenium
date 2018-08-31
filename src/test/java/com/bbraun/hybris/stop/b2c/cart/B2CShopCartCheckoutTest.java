package com.bbraun.hybris.stop.b2c.cart;

import com.bbraun.bbmtest.conf.RunOnStage;
import com.bbraun.bbmtest.conf.RunOnStageRule;
import com.bbraun.bbmtest.conf.TestProperty;
import com.bbraun.bbmtest.conf.TestPropertyRule;
import com.bbraun.bbmtest.ui.UiTest;
import io.qameta.allure.*;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.hamcrest.core.StringContains.containsString;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

@Epic("B2C E-Shop Tests")
@Feature("Checkout Tests")
@RunOnStage(stages = {"QAS" /*, "PRD" */})
public class B2CShopCartCheckoutTest {
	
	@Rule
    public RunOnStageRule rule = new RunOnStageRule();
	
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
                    .doType(By.id("deliveryEMail"), "mail@example.com")
                    .doType(By.id("deliveryPhone"), "0030 351242 2145")

                    .doClick(By.className("float-right")) 
                    .doType(By.id("deliveryPostalCode"), "456789")
                    
                    .doClick(By.className("float-right"))
                    .doWaitUntil(urlContains("/multi/delivery-method/choose"))
                    .doClick(By.className("float-right"))
                    .doWaitUntil(urlContains("/checkout/multi/summary/view"))
                    
            		;
        });
	}


	@Test
	@Severity(SeverityLevel.NORMAL)
	@Description("Test Description: Check that INC number is rendered on page.")
	@Issue("PCAG-4827")
	@Story("INC Number Rendering")
	public void testRenderINCNumeberOnPage() {
		UiTest.go(builder -> {
			builder.doStartBrowser() //
					.doMaximizeWindow() //
					.doOpenUrl(initialURL) // Initial url contains login information
					.assertUrl(containsString("/cart"))
					.assertTextDisplayedOnPage("INC_201808")
			;
		});
	}
}
