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
public class B2BShopProductCatalogTest {

    @Rule
    public RunOnStageRule rule = new RunOnStageRule();

    @Test
    public void testOpenProductCatalogPage() {
        UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .execute(B2BActions::loginQAS) //

                    .assertUrl("https://qas-shop.bbraun.com/bob") //
                    .doClick(By.linkText("20005585"))
                    .doWaitUntil(ExpectedConditions.urlContains("https://qas-shop.bbraun.com"))
                    .doClick(By.className("catalog"))
                    .doWaitUntil(ExpectedConditions.urlContains("bbraunRootCategory"))
                    .assertTitle("Category Page")
                    .assertElementExists(By.className("leftsidebar"))
                    .assertElementExists(By.className("subCategoryRefinementToggle"))
            ;
        });
    }
}
