package pages.shop;

import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
import config.TestConfig;
import pages.BasePage;

/*
POM (Page Object Model) of the HomePage
WIP
 */
public class HomePage extends BasePage {
	// define UI object as WebElements
	// private WebElement topicSelector;

	// TODO add remaining needed UI elements

	// constructor: connect the UI elements to the WebElements via locators (read
	// from prop file).
	// It can be invoked from any page, as it will load the homepage url as first,
	// to bootstrap;
	public HomePage(WebDriver driver, TestConfig config) throws Exception {
		super(driver, config);
		// load homepage;
		// TODO add proper locator bindings (WebElement <- findElementBy())
	}

	// get the page title
	public String getPageTitle() {
		return driver.getTitle();
	}

	// close the driver
	public void close() {
		driver.close();
	}

}
