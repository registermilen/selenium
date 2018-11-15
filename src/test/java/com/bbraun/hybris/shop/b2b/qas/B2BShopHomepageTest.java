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

@RunOnStage(stages = "QAS")
@Epic("B2B E-Shop Tests")
@Feature("Homepage Tests")
public class B2BShopHomepageTest {

    @Rule
    public RunOnStageRule rule = new RunOnStageRule();

    @ClassRule
	public static TestPropertyRule testPropertiesRule = new TestPropertyRule();
    
    @TestProperty("hybris.shop.b2b.german.b2bunit")
    private static String b2bUnit;
    
    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Test Description: Access B2B Shop homepage")
    @Story("Access B2B Shop homepage")
    public void testAccessHomepageWithFavouritesTeaser() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .execute(B2BActions::loginQAS) //

                    .assertUrl("https://qas-shop.bbraun.com/bob") //
                    .doClick(By.linkText(b2bUnit))
                    .doWaitUntil(ExpectedConditions.urlContains("https://qas-shop.bbraun.com"))
                    .assertTextDisplayedOnPage("Meine Favoriten")
            ;
        });
    }

}
