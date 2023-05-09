package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/*
Define the configuration of the test cases to run
 */
public class TestConfig {
	private final static int DEFAULT_TIMEOUT_MAX_WAIT_TIME_SECS = 10;
	private final static int DEFAULT_MOBILE_MODE_X = 600;
	private final static int DEFAULT_MOBILE_MODE_Y = 400;
	private final static String PROPS_FILE_URL = "./src/resources/props.properties";
	private Properties locators;
	private boolean mobileModeEnabled;
	private boolean headlessModeEnabled;

	// no arguments constructor, reading testing mode (desktop or mobile0 from prop
	// file;
	public TestConfig() {
		try {
			// load properties file
			locators = new Properties();
			InputStream input = new FileInputStream(PROPS_FILE_URL);
			// load the properties file
			locators.load(input);

			this.mobileModeEnabled = (getData("test.mobile.mode.enabled").toLowerCase().trim().equals("true")) ? true
					: false;
			this.headlessModeEnabled = (getData("selenium.chrome.driver.headless.mode").toLowerCase().trim()
					.equals("true")) ? true : false;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	// constructor to specify if the mobile mode is enabled or not (desktop mode)
	public TestConfig(boolean mobileModeEnabled) {
		try {
			locators = new Properties();
			InputStream input = new FileInputStream(PROPS_FILE_URL);

			// load the properties file
			locators.load(input);
			this.mobileModeEnabled = mobileModeEnabled;

			// headless mode from property file
			this.headlessModeEnabled = (getData("selenium.chrome.driver.headless.mode").toLowerCase().trim()
					.equals("true")) ? true : false;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	// get a property by its key
	public String getData(String propId) {
		return locators.getProperty(propId);
	}

	// get a property by its key from key properties file
	// give the basePropId = page.propertyname;
	// return a By element to be used in Find property By, using the info inside the
	// property that defines it, according to this naming convention of definining
	// properties:
	// page.propertyname.<testMode>.<typeOfLocator>
	// <testMode> = ( mobile | desktop)
	// <typeOfLocator> = ( id | xpath | name | css )
	// or throws an exception, if the property cannot be found;

	public By getLocator(String basePropId) throws Exception {
		if (this.isMobileModeEnabled())
			basePropId = basePropId + "." + "mobile";
		else
			basePropId = basePropId + "." + "desktop";
		// try to guess the locator:
		// 1. xpath
		if (propExist(basePropId + "." + "xpath"))
			return By.xpath(getData(basePropId + "." + "xpath"));

		// 2. id
		if (propExist(basePropId + "." + "id"))
			return By.id(getData(basePropId + "." + "id"));

		// 3. name
		if (propExist(basePropId + "." + "name"))
			return By.name(getData(basePropId + "." + "name"));

		// 4. css
		if (propExist(basePropId + "." + "css"))
			return By.cssSelector(getData(basePropId + "." + "css"));

		throw new Exception("Cannot find a property for " + basePropId);
	}

	// returns true if a property with the input value exists in the properties
	// file, false otherwise
	private boolean propExist(String propId) {
		String res = this.getData(propId);
		if (res != null && !res.equalsIgnoreCase(""))
			return true;
		else
			return false;
	}

	// returns a Chrome driver
	public WebDriver getChromeDriver() {
		// set driver properties
		System.setProperty("webdriver.chrome.driver", this.getData("selenium.chrome.driver.executable.path"));
		System.setProperty("webdriver.chrome.silentOutput", "true");

		WebDriver driver;

		// if running headless mode
		if (headlessModeEnabled) {
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--no-sandbox");
			chromeOptions.addArguments("--headless");
			chromeOptions.addArguments("disable-gpu");
			// chromeOptions.addArguments("window-size=1400,2100"); // Linux should be
			// activate
			driver = new ChromeDriver(chromeOptions);

		} else {
			driver = new ChromeDriver();
		}

		if (!mobileModeEnabled)
			driver.manage().window().maximize();
		else
			driver.manage().window().setSize(new Dimension(getXforMobileMode(), getYforMobileMode()));
		return driver;
	}

	// get a webdriver headless mode
	public WebDriver getChromeDriverHeadless() {
		System.setProperty("webdriver.chrome.driver", this.getData("selenium.chrome.driver.executable.path"));
		System.setProperty("webdriver.chrome.silentOutput", "true");
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--no-sandbox");
		chromeOptions.addArguments("--headless");
		chromeOptions.addArguments("disable-gpu");
		// chromeOptions.addArguments("window-size=1400,2100"); // Linux should be
		// activate
		WebDriver driver = new ChromeDriver(chromeOptions);
		return driver;
	}

	// get configured timeout (max time to wait for a page) from props file;
	public int getPageWaitTimeout() {
		try {
			return Integer.parseInt(this.getData("page.wait.timeout.seconds"));
		} catch (Exception e) {
			return DEFAULT_TIMEOUT_MAX_WAIT_TIME_SECS;
		}
	}

	// get mobile mode info
	public boolean isMobileModeEnabled() {
		return this.mobileModeEnabled;
	}

	// get headless mode info
	public boolean isHeadlessModeEnabled() {
		return this.headlessModeEnabled;
	}

	// get the x size to be used for mobile mode (max before breaking point)
	public int getXforMobileMode() {
		try {
			return Integer.parseInt(this.getData("mobile.mode.x"));
		} catch (Exception e) {
			return DEFAULT_MOBILE_MODE_X;
		}
	}

	// get the y size to be used for mobile mode (max before breaking point)
	public int getYforMobileMode() {
		try {
			return Integer.parseInt(this.getData("mobile.mode.y"));
		} catch (Exception e) {
			return DEFAULT_MOBILE_MODE_Y;
		}
	}

	// print configurations info
	public String getConfigInfo() {
		String info = "";
		info = info + "*** Test Configuration information *******************************" + "\n";
		info = info + "* - Mobile mode enabled:   " + this.isMobileModeEnabled() + "\n";
		info = info + "* - Headless mode enabled:     " + this.isHeadlessModeEnabled() + "\n";
		info = info + "* - Max waiting time for a page (secs):    " + this.getPageWaitTimeout() + "\n";
		// hard-coded for the moment we only test against Chrome
		info = info + "* - Browser and Driver:    Chrome and ChromeDriver" + "\n";
		info = info + "* - Base url:  " + this.getData("homepage.url") + "\n";
		info = info + "******************************************************************" + "\n";
		return info;
	}

	// quick test
	public static void main(String[] args) throws Exception {

	}
}
