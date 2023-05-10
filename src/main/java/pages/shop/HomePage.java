package pages.shop;

import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import config.TestConfig;
import pages.BasePage;
import utilities.Utilities;

/*
POM (Page Object Model) of the HomePage

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
		String homepageUrl = config.getData("homepage.url");
		driver.get(homepageUrl);

		// make sure to be in the homepage (10 secs)
		WebDriverWait wait = new WebDriverWait(driver, config.getPageWaitTimeout());
		wait.until(ExpectedConditions.urlContains(homepageUrl));

		// make sure the page content is loaded properly
		// wait.until(ExpectedConditions.presenceOfElementLocated((config.getLocator("homepage.videoList"))));

		// TODO: improve this -- this is to fix a random failing due probably of some
		// dynamic content not loaded - add a proper wait();
		Utilities.pauseExecution(4000);

		// this.watchContactBanner =
		// driver.findElement(config.getLocator("homepage.watchContactBanner"));
		// verify the contact us is there - important element

		// this.contactUsSelector =
		// driver.findElement(config.getLocator("homepage.contactus"));
		// String contactUsEmail = config.getData("homepage.contactus.email");
		// assertThat(contactUsSelector.getAttribute("href").trim().toLowerCase().contains(contactUsEmail.toLowerCase()))
		// .isTrue().withFailMessage("The contact us email does not point to mailto: " +
		// contactUsEmail);
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
