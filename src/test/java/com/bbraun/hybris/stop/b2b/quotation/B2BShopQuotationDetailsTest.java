package com.bbraun.hybris.stop.b2b.quotation;

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
import com.bbraun.hybris.shop.b2b.login.BobSelectorTestFragmentFactory;
import com.bbraun.hybris.shop.b2b.login.LoginTestFragmentFactory;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("B2B E-Shop Tests")
@Feature("Quotation Tests")
@RunOnStage(stages = "QAS")
public class B2BShopQuotationDetailsTest {
	
	@ClassRule
	public static TestPropertyRule testPropertiesRule = new TestPropertyRule();
	
	@TestProperty("hybris.shop.b2b.host")
	private static String host;
	
	@TestProperty("hybris.shop.b2b.vn.user.username")
	private static String username;
	
	@TestProperty("hybris.shop.b2b.vn.user.password")
	private static String password;
	
	@TestProperty("hybris.shop.b2b.initial.url")
	private static String initialURL;
	
	@TestProperty("hybris.shop.b2b.vn.b2bunit")
	private static String b2bUnit;
	
	@TestProperty("hybris.shop.b2b.vn.product")
	private static String product;
	
	@TestProperty("hybris.shop.b2b.vn.delivery.address.label")
	private static String deliveryAddressLabel;
	
	@TestProperty("hybris.shop.b2b.vn.delivery.method.label")
	private static String deliveryMethodLabel;
	
	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("Test Description: Try to checkout invalid quantity for a "
			+ "product with minimum valid quantity, correct it and proceed to checkout.")
	@Story("Create Order based on confirmed PCR")
	@Issue("PCAG-4451")
	public void testProductCheckoutWithInvalidThenValidQuantity() {
		UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .execute(LoginTestFragmentFactory.getLoginTestFragmentBasedOnStage(initialURL, host, username, password)) //
                    .execute(BobSelectorTestFragmentFactory.getBoBSelectorBasedOnStage(b2bUnit))
                    
                    .doWaitUntil(visibilityOfElementLocated(By.className("miniCart")))
                    .doClick(By.className("miniCart"))
                    .doWaitUntil(ExpectedConditions.urlContains("/cart"))

                    .assertUrl(containsString("/cart"))
                    .doOpenUrl(host+"/cart/remove") // clear cart

                    .doWaitUntil(visibilityOfElementLocated(By.name("productCodePost")))
                    .doType(By.name("productCodePost"), product)
                    .doClick(By.className("code"))
                    .doClick(By.id("instantAddToCartButton"))
                    .assertElementExists(By.className("cartItem")) // cart item row

                    .doClick(By.id("checkoutPcrButtonTop"))
                    
                    .assertUrl(containsString("/checkout/multi/common-information/add"))
                    
                    .doType(By.id("purchaseOrderNumber"), "testRefNumber")
                    .doType(By.id("cartPriceChangeRequest"), "1111")
                    .doType(By.id("cartText"), "some text")

                    .doClick(By.className("force-right")) 
                    .doWaitUntil(urlContains("/checkout/multi/delivery-method/choose"))
                    
                    .assertTextDisplayedOnPage("1,111") // VND / PC"
                    
                    .doClick(By.className("force-right")) 
                    .doWaitUntil(urlContains("/checkout/multi/summary/view"))
                    
                    .doClick(By.className("force-right")) //place quotation

                    //redirected to quotation details
                    
                    .doWaitUntil(urlContains("/my-account/quotation/"))
                    .assertTextDisplayedOnPage("Quotation Details")
                    .assertTextDisplayedOnPage("Your Reference: testRefNumber")
                    .assertTextDisplayedOnPage("some text")
                    .assertTextDisplayedOnPage(deliveryAddressLabel)
                    .assertTextDisplayedOnPage(deliveryMethodLabel) 
            ;
        });
	}
}
