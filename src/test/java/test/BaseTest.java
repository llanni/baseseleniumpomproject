package test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import config.TestConfig;

public class BaseTest {
	// base test containing setup, constructor and teardown methods to be handled
	// by all test cases;
	// all test cases must extend the BaseTest

	public WebDriver driver;
	public TestConfig config;
	public WebDriverWait wait;

	// constructor inporting driver and config
	public BaseTest(WebDriver driver, TestConfig config) {
		this.driver = driver;
		this.config = config;
		this.wait = new WebDriverWait(this.driver, this.config.getPageWaitTimeout());

		// print config info
		// System.out.println(config.getConfigInfo());
	}

	// constructor creating a new config (desktop mode) and generated driver
	public BaseTest() {
		this.config = new TestConfig();
		this.driver = config.getChromeDriver();
		this.wait = new WebDriverWait(this.driver, this.config.getPageWaitTimeout());

		// print config info
		// System.out.println(config.getConfigInfo());
	}

	// constructor creating a new config (specifying the mode) and generatic driver
	public BaseTest(boolean mobileModeEnabled) {
		this.config = new TestConfig(mobileModeEnabled);
		this.driver = config.getChromeDriver();
		this.wait = new WebDriverWait(this.driver, this.config.getPageWaitTimeout());

		// print config info
		// System.out.println(config.getConfigInfo());
	}

	@BeforeSuite
	// insert things to do before starting any test
	public void setUp() {
		try {
			// TODO insert code to run at setup
		} catch (Exception e) {
			// TODO handle any exception
		}
	}

	@AfterSuite
	// insert things to do after closing all tests
	public void tearDown() {
		// close any driver instance, if needed
		if (driver != null) {
			try {
				driver.close();
			} catch (Exception e) {
			}
			try {
				driver.quit();
			} catch (Exception e) {
			}
		}
	}

	// get the driver
	public WebDriver getDriver() {
		return driver;
	}

	// get the config
	public TestConfig getConfig() {
		return config;
	}

}
