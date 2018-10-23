package com.bbraun.hybris.shop.b2b.cart;

import com.bbraun.bbmtest.conf.RunOnStage;
import com.bbraun.bbmtest.conf.RunOnStageRule;
import com.bbraun.bbmtest.ui.UiTest;
import com.bbraun.bbmtest.ui.WebDriverVisitor;
import com.bbraun.hybris.shop.b2b.B2BActions;
import io.qameta.allure.*;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

@RunOnStage(stages = "DEV")
@Epic("B2B E-Shop Tests")
@Feature("Checkout Tests")
public class B2BShopCheckoutTextLimitTest {

    private static final String TEXT_MORE_THAN_132_CHARACTERS = "0123456789012345678901234567890123456789012345678901234567890123456789"
    		+ "01234567890123456789012345678901234567890123456789012345678901234567890123456789";

    @Rule
    public RunOnStageRule rule = new RunOnStageRule();

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test Description: Test text limit in checkout steps.")
    @Story("Process checkout and place long texts that shall be limited to 132 symbols.")
    public void testPassCartToCheckoutAndPlaceLongText() {
        UiTest.go(builder -> {
			builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .execute(B2BActions::loginQAS) //

//                    .assertUrl("https://qas-shop.bbraun.com/bob") //
//                    .doClick(By.linkText("0020262030"))
                    .doWaitUntil(visibilityOfElementLocated(By.className("miniCart")))
                    .doClick(By.className("miniCart"))
                    .doWaitUntil(ExpectedConditions.urlContains("/cart"))

                    .assertUrl(containsString("/cart"))
                    .doOpenUrl("https://qas-shop.bbraun.com/cart/remove") // clear cart

                    .doType(By.name("productCodePost"), "4097076")
                    .doSubmitForm(By.id("instantAddToCartForm"))
                    .assertElementExists(By.className("cartItem")) // cart item row

                    .doSelectOptionFromSelectByIndex(By.id("uom"), 1)

                    .doClick(By.id("checkoutButtonTop"))
                    .doWaitUntil(urlContains("/checkout/multi/common-information/add"))
                    .doType(By.cssSelector("textarea[data-entrynumber='']"), "HEADER" + TEXT_MORE_THAN_132_CHARACTERS)
                    .doClick(By.className("positionToggle"))
                    .doType(By.cssSelector("textarea[data-entrynumber='10']"), "POSITION10" + TEXT_MORE_THAN_132_CHARACTERS)
                    
                    .doClick(By.className("force-right")) // Weiter
                    .doWaitUntil(urlContains("/checkout/multi/summary/view"))
                    .doClick(By.className("positionToggle"))
                    .executeWithWebDriver(new WebDriverVisitor() {
						
						@Override
						public void executeWithWebDriver(WebDriver driver) {
							assertThat(driver.findElement(By.id("cartText")).getText(), equalTo(("HEADER" + TEXT_MORE_THAN_132_CHARACTERS).substring(0, 132)));
							assertThat(driver.findElement(By.cssSelector("div[data-entrynumber='10']")).getText(), equalTo(("POSITION10" + TEXT_MORE_THAN_132_CHARACTERS).substring(0, 132)));
						}
					})
            ;
        });
    }


}
