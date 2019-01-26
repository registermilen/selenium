package com.bbraun.hybris.shop.b2b.cart;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.bonigarcia.wdm.BrowserManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.PhantomJsDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;

@Epic("B2B E-Shop Tests")
@Feature("Checkout Tests")
public class B2BShopCartAddressSelectionTest {

	private WebDriver driver;

	/**
	 * default dimensions for selenium browser size
	 */
	private static final int DEFAULT_HEIGHT = 860;
	private static final int DEFAULT_WIDTH = 1500;

	private static final Logger logger = LoggerFactory.getLogger(B2BShopCartAddressSelectionTest.class);

	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("Test Description: Make sure address is selectable on cart level.")
	@Story("Introduce address selection component on cart position")
	@Issue("PCAG-4707")
	public void testSelectAddressOnCartLevel() {
		initLocalBrowser(TestBrowser.PHANTOMJS);
		doStartBrowser();
		doOpenUrl("https://google.com");
	}

	/**
	 * Starts the browser. MUST be the initial action of each test!
	 *
	 * @return this builder instance
	 */
	@Step("Start browser")
	public void doStartBrowser() {
		driver.manage().window().setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = cap.getBrowserName().toLowerCase();

		logger.info("Start browser '{}'.", browserName);
	}

	@Step("Open browser URL '{0}'")
	public void doOpenUrl(String url) {
		logger.info("Type browser URL '{}' ", url);
		driver.get(url);
		captureScreenshot();
	}

	private void captureScreenshot() {
		ScreenshotHelper.capturePageScreenshotForReport(driver);
	}
	
	/**
     * Setup local Selenium Driver manager
     *
     * @param browser browser instance to use
     */
    private void initLocalBrowser(TestBrowser browser) {

        logger.info("Use local browser. Browser: {},", browser);
        DesiredCapabilities capability = null;
        
        switch (browser) {
            case PHANTOMJS:
                BrowserManager phantomBrowserManager = PhantomJsDriverManager.getInstance().forceCache();
                phantomBrowserManager.setup();
                driver = new PhantomJSDriver();
                break;
            case FIREFOX:
                BrowserManager firefoxBrowserManager = FirefoxDriverManager.getInstance().forceCache();
                firefoxBrowserManager.setup();
                driver = new FirefoxDriver();
                break;
            case CHROME:
            	capability = DesiredCapabilities.phantomjs();
			try {
				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capability);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
                driver = new ChromeDriver();
                break;
            default:
                logger.warn("No browser settings found in test configuration!");
                break;
        }
    }
}
