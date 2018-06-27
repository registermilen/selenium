package com.bbraun.hybris.shop.b2b.qas;

import com.bbraun.bbmtest.conf.RunOnStage;
import com.bbraun.bbmtest.conf.RunOnStageRule;
import com.bbraun.bbmtest.ui.UiTest;
import com.bbraun.hybris.shop.b2b.B2BActions;
import io.qameta.allure.Epic;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

@RunOnStage(stages = "QAS")
@Epic("E-Shop Tests")
public class B2BShopProductDetailPageTest {

	 @Rule public RunOnStageRule rule = new RunOnStageRule();
	 
	 @Test
	 public void testBOMTable() {
		 UiTest.go(builder -> {
	            builder.doStartBrowser() //
	                    .doMaximizeWindow() //
	                    .execute(B2BActions::loginQAS) //
	                    
	                    .assertUrl("https://qas-shop.bbraun.com/bob") //
	                    .doClick(By.linkText("20005585"))
	                    .doWaitUntil(ExpectedConditions.urlContains("https://qas-shop.bbraun.com"))
	                    .assertTextDisplayedOnPage("20005585")
	                    
	                    .doOpenUrl("https://qas-shop.bbraun.com/p/PRID00003923")
	                    .doWaitUntil(ExpectedConditions.urlContains("https://qas-shop.bbraun.com/p/PRID00003923"))
	                    .assertTextDisplayedOnPage("Introcan®")
	                    
	                    .assertElementExists(By.id("billOfMaterialItemsARTICLES"))
	            ;
	        });
	 }
}
