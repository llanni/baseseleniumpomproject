package test.ui;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import pages.shop.HomePage;
import test.BaseTest;

public class FirstTest extends BaseTest {
	@Test
	/*
	 * TC: add steps 0. Prerequisite: to have in the pom.xml property
	 * login.testcase.email an email that was already successfully registered to the
	 * website and the related name login.testcase.name 1. Navigate to 2. Click th
	 * 3. Click to Switch to 4. Insert the ... and click submit to Login; 5. Verify
	 * you are logged in; 6. End;
	 */
	public void testLoginUser() throws Exception {
		// 1. Navigate to Dreaming Spanish homepage
		System.out.println("1. Navigate to Dreaming Spanish homepage");
		HomePage homePage = new HomePage(driver, config);

		// 1.1 Verify loaded page title is
		System.out.println("1.1 Verify title");
		String expectedTitle = "Dreaming Spanish - Home";
		String actualTitle = homePage.getPageTitle();
		assertThat(actualTitle.equals(expectedTitle)).isTrue().withFailMessage(
				"Actual and Expected Title ar different! Actual: " + actualTitle + ", Expected: " + expectedTitle);

		// 2. On the right top corner of the page, click on “Login”
		System.out.println("2. Click the Register link ");
		// RegisterLoginPopupPage registerLoginPopupPage =
		// homePage.clickRegisterButton();
		// assertThat(registerLoginPopupPage.isUIinRegisterPanel()).isTrue().withFailMessage("Expected
		// to be in the Registration panel, while it is not");

		// 3. Switch to Login Tab
		System.out.println("3. Switch to Login Tab ");
		// registerLoginPopupPage.clickToLoginPanel();
		// Utilities.pauseExecution(200);
		// assertThat(registerLoginPopupPage.isUIinLoginPanel()).isTrue().withFailMessage("Expected
		// to be in the Login panel, while it is not");

		// 4. Insert email and submit
		String registeredEmail = config.getData("login.testcase.email");
		String theName = config.getData("login.testcase.name");
		System.out.println("4. Insert already registered email: " + registeredEmail + " named " + theName
				+ " and click Submit to login");
		// registerLoginPopupPage.insertEmail(registeredEmail);
		// registerLoginPopupPage.clickSubmit();
		// wait.until(ExpectedConditions.invisibilityOf(registerLoginPopupPage.getSubmitButton()));

		// 5. ...

		// 6. Finished
		System.out.println("6. End");

	}
}
