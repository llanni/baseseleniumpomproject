package test.ui;

import pages.shop.LoginPage;
import test.BaseTest;
import utilities.Utilities;

import org.testng.Assert;
import org.testng.annotations.*;

public class SecondTest extends BaseTest {
	@Test(groups = { "e2e.ui" })
	/*
	 * TC: testLoginUser - login a user and verify the user is correctly logged in
	 */
	public void testXxx() throws Exception {
		// 1. Navigate to Dreaming Spanish homepage
		System.out.println("C1. Navigate to the homepage");
		LoginPage loginPage = new LoginPage(driver, config);

		// 2. Perform login with a correct username and password
		String username = "standard_user";
		String password = "secret_sauce";
		loginPage.doLogin(username, password);

		// 3. Verify the user has correctly logged in
		System.out.println("C2. Verify the user has correctly logged in");
		Assert.assertEquals(driver.getCurrentUrl().contains("inventory"), true);
		// assertThat(driver.getCurrentUrl().contains("inventory")).isTrue().withFailMessage(
		// "Expected to be in the inventory page and it is not, the current url is: " +
		// driver.getCurrentUrl());

		// 4. Finished
		Utilities.pauseExecution(1000);
		System.out.println("C3. Test 3 End");

	}
}
