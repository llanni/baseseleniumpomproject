package pages.shop;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import config.TestConfig;
import pages.BasePage;

public class LoginPage extends BasePage {
	// define UI object as WebElements
	private WebElement usernameField;
	private WebElement passwordField;
	private WebElement submitButton;

	public LoginPage(WebDriver driver, TestConfig config) throws Exception {
		super(driver, config);
		String baseUrl = config.getData("homepage.url");
		driver.get(baseUrl);

		WebDriverWait wait = new WebDriverWait(driver, config.getPageWaitTimeout());
		wait.until(ExpectedConditions.urlContains(baseUrl));

		this.usernameField = driver.findElement(config.getLocator("loginpage.usernameField"));
		this.passwordField = driver.findElement(config.getLocator("loginpage.passwordField"));
		this.submitButton = driver.findElement(config.getLocator("loginpage.submitButton"));
	}

	// type the provided username in the related field
	public void insertUsername(String username) {
		usernameField.clear();
		usernameField.sendKeys(username);
	}

	// type the provided password in the related field
	public void insertPassword(String password) {
		passwordField.clear();
		passwordField.sendKeys(password);
	}

	// click login button
	public void clickLogin() {
		submitButton.click();
	}

	// do login
	public void doLogin(String username, String password) {
		this.insertUsername(username);
		this.insertPassword(password);
		this.clickLogin();
	}
}
