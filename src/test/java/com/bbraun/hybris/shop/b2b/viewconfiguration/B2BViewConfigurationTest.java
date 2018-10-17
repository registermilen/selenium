package com.bbraun.hybris.shop.b2b.viewconfiguration;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;

import com.bbraun.bbmtest.conf.RunOnStage;
import com.bbraun.bbmtest.conf.RunOnStageRule;
import com.bbraun.bbmtest.conf.TestProperty;
import com.bbraun.bbmtest.conf.TestPropertyRule;
import com.bbraun.bbmtest.ui.UiTest;
import com.bbraun.hybris.shop.b2b.login.BobSelectorTestFragmentFactory;
import com.bbraun.hybris.shop.b2b.login.LoginTestFragmentFactory;
import com.bbraun.hybris.shop.b2b.login.LogoutTestFragmentFactory;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("B2B E-Shop Tests")
@Feature("View Configuration Tests")
@RunOnStage(stages = {"LOCAL", "QAS", "PRD"})
public class B2BViewConfigurationTest {
	
	@Rule
    public RunOnStageRule rule = new RunOnStageRule();
	
	@ClassRule
	public static TestPropertyRule testPropertiesRule = new TestPropertyRule();
	
	@TestProperty("hybris.shop.b2b.host")
	private static String host;
	
	@TestProperty("hybris.shop.b2b.swiss.user.username")
	private static String username;
	
	@TestProperty("hybris.shop.b2b.swiss.user.password")
	private static String password;
	
	@TestProperty("hybris.shop.b2b.initial.url")
	private static String initialURL;
	
	
	@TestProperty("hybris.shop.b2b.swiss.b2bunit")
	private static String b2bUnit;
	
	@Test
	@Severity(SeverityLevel.NORMAL)
	@Description("Test Description: Switch languages between logins and verify they are used on next login.")
	@Story("Preselect last used language for user")
	@Issue("PCAG-5079")
	public void testLastUsedLanguageIsUsed() {
		UiTest.go(builder -> {
            builder.doStartBrowser() //
                    .doMaximizeWindow() //
                    .execute(LoginTestFragmentFactory.getLoginTestFragmentBasedOnStage(initialURL, host, username, password)) //
                    
                    .execute(BobSelectorTestFragmentFactory.getBoBSelectorBasedOnStage(b2bUnit))
                    
                    .doWaitUntil(visibilityOfElementLocated(By.id("de_CH")))
                    .doClick(By.id("de_CH"))
                    .assertTextDisplayedOnPage("Meine Favoriten")
                    .execute(LogoutTestFragmentFactory.getGlobalLogoutTestFragmentBasedOnStage())
                    
                    //switch to French
                    .execute(LoginTestFragmentFactory.getLoginTestFragmentBasedOnStage(initialURL, host, username, password)) //
                    .execute(BobSelectorTestFragmentFactory.getBoBSelectorBasedOnStage(b2bUnit))
                    .doWaitUntil(visibilityOfElementLocated(By.id("fr_CH")))
                    .doClick(By.id("fr_CH"))
                    .assertTextDisplayedOnPage("Mes favoris")
                    .execute(LogoutTestFragmentFactory.getGlobalLogoutTestFragmentBasedOnStage())
                    
                    //new login, make sure French remains selected
                    .execute(LoginTestFragmentFactory.getLoginTestFragmentBasedOnStage(initialURL, host, username, password)) //
                    .execute(BobSelectorTestFragmentFactory.getBoBSelectorBasedOnStage(b2bUnit))
                    .assertTextDisplayedOnPage("Mes favoris")
                    
                    //reset to initial language
                    .doWaitUntil(visibilityOfElementLocated(By.id("de_CH")))
                    .doClick(By.id("de_CH"))
            ;
        });
	}
}
