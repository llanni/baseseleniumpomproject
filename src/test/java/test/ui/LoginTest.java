package test.ui;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import pages.shop.LoginPage;
import test.BaseTest;
import utilities.Utilities;

public class LoginTest extends BaseTest {
	@Test
	/*
	 * TC: testLoginUser - login a user and verify the user is correctly logged in
	 */
	public void testLoginUser() throws Exception {
		// 1. Navigate to Dreaming Spanish homepage
		System.out.println("1. Navigate to the homepage");
		LoginPage loginPage = new LoginPage(driver, config);

		// 2. Perform login with a correct username and password
		String username = "standard_user";
		String password = "secret_sauce";
		loginPage.doLogin(username, password);

		// 3. Verify the user has correctly logged in
		System.out.println("3. Verify the user has correctly logged in");
		assertThat(driver.getCurrentUrl().contains("inventory")).isTrue().withFailMessage(
				"Expected to be in the inventory page and it is not, the current url is: " + driver.getCurrentUrl());

		// 4. Finished
		Utilities.pauseExecution(2000);
		System.out.println("4. End");

	}
}
