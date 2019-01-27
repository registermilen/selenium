package ui.test.demo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.bonigarcia.wdm.BrowserManager;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.PhantomJsDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story; 

@Feature("Create leave request")
public class LeaveRequestTest {

	private WebDriver driver;

	/**
	 * default dimensions for selenium browser size
	 */
	private static final int DEFAULT_HEIGHT = 860;
	private static final int DEFAULT_WIDTH = 1500;

	private static final Logger logger = LoggerFactory.getLogger(LeaveRequestTest.class);

	@Test
	@Severity(SeverityLevel.CRITICAL)
	@Description("Test Description: Make sure address is selectable on cart level.")
	@Story("Introduce address selection component on cart position")
	@Issue("PCAG-4707")
	public void testCreateLeave() {
		
		Calendar dateOfMonday = Calendar.getInstance();
		Calendar dateOfFriday = Calendar.getInstance();
		Calendar today = Calendar.getInstance();
		int nextYear = today.get(Calendar.YEAR)+1;
        int diff = 2 - dateOfMonday.get(Calendar.DAY_OF_WEEK);
        if (diff <= 0) {
            diff += 7;
        }
        dateOfMonday.add(Calendar.DATE, diff);
        dateOfFriday.add(Calendar.DATE, diff+4);
	    SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
	    String fromDate= dateFormatter.format(dateOfMonday.getTime());
	    String toDate= dateFormatter.format(dateOfFriday.getTime());

		System.out.println(fromDate);
		System.out.println(toDate);
		   
		initLocalBrowser(TestBrowser.CHROME);
		doStartBrowser();
		doOpenUrl("https://test.easyleave.de/");
		doLoginAs("denitsa@westernacher.com","denitsa","https://test.easyleave.de/");
		doCreateLeave(fromDate,toDate);
		//To test that the user get additional leave days after new year.
		//doCreateLeave("12/01/"+today.get(Calendar.YEAR),"03/01/"+nextYear);
		doDeleteLeave();
		doLogout();
	}

	/**
	 * Starts the browser. MUST be the initial action of each test!
	 *
	 * @return this builder instance
	 */
	@Step("Start browser.")
	public void doStartBrowser() {
		driver.manage().window().setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = cap.getBrowserName().toLowerCase();
		logger.info("Start browser '{}'.", browserName);
	}

	@Step("Open browser URL '{0}'.")
	public void doOpenUrl(String url) {
		logger.info("Type browser URL '{}' ", url);
		driver.get(url);
		captureScreenshot();
	}


	@Step("Login as'{0}'.")
	public void doLoginAs(String usr,String pass, String url) {		
		assertTrue(driver.findElement(By.className("small-text")).getText().equals("Login page"));
		driver.findElement(By.id("emailAddress")).clear();
		driver.findElement(By.id("emailAddress")).sendKeys(usr);
		driver.findElement(By.id("mat-input-1")).clear();
		driver.findElement(By.name("password")).sendKeys(pass);
		driver.findElement(By.id("logButton")).click();
		assertFalse(driver.getCurrentUrl().equals(url));		
		logger.info("Login as '{}' ", usr, pass);
		captureScreenshot();
	}
	

	@Step("Create leave from '{0}' to '{1}'.")
	public void doCreateLeave(String fromDay,String toDay) {
		String remainingDays, requestedDays, offDays;
		int remainingDaysNum,requestedDaysNum, offDaysNum;
		
		assertTrue("User page is loaded.",driver.findElement(By.className("small-text")).getText().equals("User page"));
		remainingDays=driver.findElement(By.xpath("//div[text()=\" Remaining:  \"]/*")).getText();
		driver.findElement(By.xpath("//button[text()=\"Add\"]")).click();
		driver.findElement(By.id("fromDate")).sendKeys(Keys.chord(Keys.CONTROL, "a")+fromDay);
		driver.findElement(By.id("toDate")).sendKeys(Keys.chord(Keys.CONTROL, "a")+toDay);
		//The clink on the first date field refreshes the value of Days Off. Possible bug.
		driver.findElement(By.id("fromDate")).click();
		requestedDays=driver.findElement(By.xpath("//*[@role=\"dialog\"]/ng-component/mat-dialog-content/form/div[2]")).getText();
		offDays=driver.findElement(By.xpath("//*[@role=\"dialog\"]/ng-component/mat-dialog-content/form/div[3]")).getText();
		requestedDaysNum=Integer.parseInt(requestedDays.substring(6));
		offDaysNum=Integer.parseInt(offDays.substring(10));
		//assertTrue("Requested days are correct.",requestedDaysNum==5);
		assertTrue("Off days are correct.",offDaysNum>0);
		captureScreenshot();
		
		System.out.println("Some days:"+requestedDays+","+offDays+","+remainingDays);
		driver.findElement(By.xpath("(//button[text()=\"Add\"])[2]")).click();
	
		//assertTrue("Confirmation message is displayed",driver.findElement(By.className("alert")).isDisplayed());
		assertTrue("Remaining days are not updated.",driver.findElement(By.xpath("//div[text()=\" Remaining:  \"]/*")).getText().equals(remainingDays));
		assertTrue("Requested days are correct.",driver.findElement(By.xpath("//app-leaves/table/tbody/tr[2]/td[3]")).getText().equals(requestedDays.substring(6)));
		assertTrue("Off days are correct.",driver.findElement(By.xpath("//app-leaves/table/tbody/tr[2]/td[4]")).getText().equals(offDays.substring(10)));
		assertTrue("Status of the leave is Draft.", driver.findElement(By.xpath("//app-leaves/table/tbody/tr[2]/td[5]")).getText().contains("Draft"));
		
		logger.info("Create leave '{}' ", fromDay, toDay);
		captureScreenshot();
		
	}
	@Step("Delete Leave.")
	public void doDeleteLeave() {
		if (driver.findElement(By.xpath("//app-leaves/table/tbody/tr[2]/td[5]")).isDisplayed()) {
			String leaveStatus,remainingDays,requestedDays;
			int remainingDaysNum,requestedDaysNum;
			leaveStatus=driver.findElement(By.xpath("//app-leaves/table/tbody/tr[2]/td[5]")).getText().trim();
			remainingDays=driver.findElement(By.xpath("//div[text()=\" Remaining:  \"]/*")).getText();
			requestedDays=driver.findElement(By.xpath("//app-leaves/table/tbody/tr[2]/td[4]")).getText();
			remainingDaysNum=Integer.parseInt(remainingDays);
			requestedDaysNum=Integer.parseInt(requestedDays);
			
			driver.findElement(By.xpath("//button[text()=\"Delete\"]")).click();
			assertTrue("The deleted leave is the proper one.", driver.findElement(By.xpath("//ng-component/mat-dialog-content")).getText().
					contains(driver.findElement(By.xpath("//app-leaves/table/tbody/tr[2]/td[1]")).getText()));
			assertTrue("The deleted leave is the proper one.", driver.findElement(By.xpath("//ng-component/mat-dialog-content")).getText().
					contains(driver.findElement(By.xpath("//app-leaves/table/tbody/tr[2]/td[2]")).getText()));
			assertTrue("The deleted leave is the proper one.", driver.findElement(By.xpath("//ng-component/mat-dialog-content")).getText().
					contains(leaveStatus));
			driver.findElement(By.xpath("//*[@aria-label=\"Close dialog\"][text()=\"Yes\"]")).click();
			assertTrue("Confirmation message is displayed",driver.findElement(By.className("alert")).isDisplayed());
			if (leaveStatus.equalsIgnoreCase("Accepted")) {
				System.out.println(driver.findElement(By.xpath("//div[text()=\" Remaining:  \"]/*")).getText());
				assertTrue("Remaining days are updated accordingly.",Integer.parseInt(driver.findElement(By.xpath("//div[text()=\" Remaining:  \"]/*")).getText())==(remainingDaysNum+requestedDaysNum));
			}
			logger.info("Delete Leave.");
		}
		else logger.info("No Leave to be deleted.");
	}
	
	
	@Step("Logout.")
	public void doLogout() {		
		driver.findElement(By.id("accountMenu")).click();
		driver.findElement(By.linkText("Logout")).click();
		assertTrue(driver.findElement(By.className("small-text")).getText().equals("Login page"));
		logger.info("Logot");
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
//            	capability = DesiredCapabilities.phantomjs();
//				try {
//					driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capability);
//				} catch (MalformedURLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//                driver = new ChromeDriver();
//                break;
            	ChromeOptions options = new ChromeOptions();
            	options.addArguments("--headless", "window-size=1024,768", "--no-sandbox");
            	BrowserManager chromeBrowserManager = ChromeDriverManager.getInstance().forceCache();
            	chromeBrowserManager.setup();
                driver = new ChromeDriver(options);
                break;
            default:
                logger.warn("No browser settings found in test configuration!");
                break;
        }
    }
}
