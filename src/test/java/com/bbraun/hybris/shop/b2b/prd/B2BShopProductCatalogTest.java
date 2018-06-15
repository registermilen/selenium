package com.bbraun.hybris.shop.b2b.prd;

import com.bbraun.bbmtest.conf.RunOnStage;
import com.bbraun.bbmtest.conf.RunOnStageRule;
import com.bbraun.bbmtest.ui.UiTest;
import com.bbraun.hybris.shop.b2b.B2BActions;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class B2BShopProductCatalogTest {

    @Rule
    public RunOnStageRule rule = new RunOnStageRule();

    @Test
    @RunOnStage(stages = "PRD")
    public void testOpenProductCatalogPage() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .execute(B2BActions::loginPRD) //

                    .assertUrl("https://shop.bbraun.com/bob") //
                    .doClick(By.linkText("20158045"))
                    .doWaitUntil(ExpectedConditions.urlContains("https://shop.bbraun.com"))
                    .doClick(By.className("catalog"))
                    .doWaitUntil(ExpectedConditions.urlContains("bbraunRootCategory"))
                    .assertTitle("Category Page")
                    .assertElementExists(By.className("leftsidebar"))
                    .assertElementExists(By.className("subCategoryRefinementToggle"))
            ;
        });
    }
}
