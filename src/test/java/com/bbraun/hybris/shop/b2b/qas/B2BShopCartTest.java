package com.bbraun.hybris.shop.b2b.qas;

import com.bbraun.bbmtest.conf.RunOnStage;
import com.bbraun.bbmtest.conf.RunOnStageRule;
import com.bbraun.bbmtest.conf.TestProperty;
import com.bbraun.bbmtest.conf.TestPropertyRule;
import com.bbraun.bbmtest.ui.UiTest;
import com.bbraun.hybris.shop.b2b.B2BActions;
import io.qameta.allure.*;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.hamcrest.core.StringContains.containsString;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

@RunOnStage(stages = "QAS")
@Epic("B2B E-Shop Tests")
@Feature("Cart Tests")
public class B2BShopCartTest {

    @Rule
    public RunOnStageRule rule = new RunOnStageRule();

    @ClassRule
	public static TestPropertyRule testPropertiesRule = new TestPropertyRule();
    
    @TestProperty("hybris.shop.b2b.german.b2bunit")
    private static String b2bUnit;
    
    @TestProperty("hybris.shop.b2b.german.product.with.minimal.quantity")
	private static String product;
    
    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test Description: Test access shopping cart")
    @Story("Open shopping cart page")
    public void testAccessToShoppingCart() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .execute(B2BActions::loginQAS) //

                    .assertUrl("https://qas-shop.bbraun.com/bob") //
                    .doClick(By.linkText(b2bUnit))
                    .doWaitUntil(visibilityOfElementLocated(By.className("miniCart")))

                    .doClick(By.className("miniCart"))
                    .doWaitUntil(ExpectedConditions.urlContains("/cart"))

                    .assertUrl(containsString("/cart"))
                    .assertElementExists(By.className("uploadForm")) // upload area
                    .assertElementExists(By.id("instantAddToCartButton")) // add to cart button
            ;
        });
    }


    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test Description: Test access shopping cart and add an article to the cart")
    @Story("Open shopping cart page and add an article to the cart")
    public void testAddArticleToShoppingCart() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .execute(B2BActions::loginQAS) //

                    .assertUrl("https://qas-shop.bbraun.com/bob") //
                    .doClick(By.linkText(b2bUnit))
                    .doWaitUntil(visibilityOfElementLocated(By.className("miniCart")))
                    .doClick(By.className("miniCart"))

                    .doWaitUntil(ExpectedConditions.urlContains("/cart"))
                    .assertUrl(containsString("/cart"))
                    .doOpenUrl("https://qas-shop.bbraun.com/cart/remove") // clear cart
                    //.assertElementNotExists(By.className("cartItem")) // cart item row

                    .doType(By.name("productCodePost"), product)
                    .doSubmitForm(By.id("instantAddToCartForm"))
                    .assertElementExists(By.className("cartItem")) // cart item row
                    .doClick(By.linkText("Warenkorb entfernen"))
            ;
        });
    }
}
