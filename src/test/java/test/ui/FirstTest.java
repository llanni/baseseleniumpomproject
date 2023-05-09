package test.ui;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import pages.shop.LoginPage;
import test.BaseTest;
import utilities.Utilities;

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
		System.out.println("1. Navigate to the homepage");
		LoginPage loginPage = new LoginPage(driver, config);

		// 2. Perform login with right username and password
		String username = "standard_user";
		String password = "secret_sauce";
		loginPage.doLogin(username, password);

		// 3. Check the url contains inventory
		System.out.println("3. Check the url contains inventory");
		assertThat(driver.getCurrentUrl().contains("inventory")).isTrue().withFailMessage(
				"Expected to be in the inventory page and it is not, the current url is: " + driver.getCurrentUrl());

		// 4. Insert email and submit
		// String registeredEmail = config.getData("login.testcase.email");
		// String theName = config.getData("login.testcase.name");
		// System.out.println("4. Insert already registered email: " + registeredEmail +
		// " named " + theName
		// + " and click Submit to login");
		// registerLoginPopupPage.insertEmail(registeredEmail);
		// registerLoginPopupPage.clickSubmit();
		// wait.until(ExpectedConditions.invisibilityOf(registerLoginPopupPage.getSubmitButton()));

		// 5. ...

		// 6. Finished
		Utilities.pauseExecution(2000);
		System.out.println("6. End");

	}
}
