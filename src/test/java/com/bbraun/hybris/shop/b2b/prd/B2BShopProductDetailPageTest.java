package com.bbraun.hybris.shop.b2b.prd;

import com.bbraun.bbmtest.conf.RunOnStage;
import com.bbraun.bbmtest.conf.RunOnStageRule;
import com.bbraun.bbmtest.ui.UiTest;
import com.bbraun.hybris.shop.b2b.B2BActions;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

@RunOnStage(stages = "PRD")
public class B2BShopProductDetailPageTest {

	 @Rule public RunOnStageRule rule = new RunOnStageRule();
	 
	 @Test
	 public void testBOMTable() {
		 UiTest.go(builder -> {
	            builder.doStartBrowser() //
	                    .doMaximizeWindow() //
	                    .execute(B2BActions::loginPRD) //
	                    
	                    .assertUrl("https://shop.bbraun.com/bob") //
	                    .doClick(By.linkText("20158045"))
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
