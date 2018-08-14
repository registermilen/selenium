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

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

@RunOnStage(stages = "PRD")
@Epic("B2B E-Shop Tests")
@Feature("Product Detail Page Tests")
public class B2BShopProductDetailPageTest {

	 @Rule public RunOnStageRule rule = new RunOnStageRule();

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Access product detail page and assert BOM component exists")
    @Story("Access product detail page and check BOM component")
	 public void testBOMTable() {
		 UiTest.go(builder -> {
	            builder.doStartBrowser() //
	                    .doMaximizeWindow() //
	                    .execute(B2BActions::loginPRD) //
	                    
	                    .assertUrl("https://shop.bbraun.com/bob") //
	                    .doClick(By.linkText("0020158045"))
						.doWaitUntil(visibilityOfElementLocated(By.className("miniCart")))
						.assertUrl("https://shop.bbraun.com/")

	                    .doOpenUrl("https://shop.bbraun.com/p/PRID00003923")
	                    .doWaitUntil(ExpectedConditions.urlContains("https://shop.bbraun.com/p/PRID00003923"))
	                    .assertTextDisplayedOnPage("Introcan®")

						.doScrollToElement(By.id("billOfMaterialItemsARTICLES"))
						.assertElementExists(By.id("billOfMaterialItemsARTICLES"))
	                    .assertElementExists(By.xpath("//*[@id=\"billOfMaterialItemsARTICLES\"]/tbody/tr[2]"))
	            ;
	        });
	 }
}
