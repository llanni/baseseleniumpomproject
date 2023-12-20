package test.ui;

import pages.shop.LoginPage;
import test.BaseTest;
import utilities.Utilities;

import org.testng.Assert;
import org.testng.annotations.*;

public class LoginTest extends BaseTest {
	@Test(groups = { "e2e.ui" })
	/*
	 * TC: testLoginUser - login a user and verify the user is correctly logged in
	 */
	public void testLoginUser() throws Exception {
		// 1. Navigate to Dreaming Spanish homepage
		System.out.println("A1. Navigate to the homepage");
		LoginPage loginPage = new LoginPage(driver, config);

		// 2. Perform login with a correct username and password
		String username = "standard_user";
		String password = "secret_sauce";
		loginPage.doLogin(username, password);
		Utilities.pauseExecution(5000);

		// 3. Verify the user has correctly logged in
		System.out.println("A2. Verify the user has correctly logged in");
		Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
		// assertThat(driver.getCurrentUrl().contains("inventory")).isTrue().withFailMessage(
		// "Expected to be in the inventory page and it is not, the current url is: " +
		// driver.getCurrentUrl());

		// 4. Finished
		Utilities.pauseExecution(1000);
		System.out.println("A3. Test1 End");

	}
}
