package pages.shop;

import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import config.TestConfig;
import pages.BasePage;
import utilities.Utilities;
/*
    import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
 */

/*
POM (Page Object Model) of the OtherPage

 */
public class OtherPage extends BasePage {
	// define UI object as WebElements
	private WebElement backButton;
	private WebElement videoTitleBox;
	private WebElement nextVideoTitle;
	private WebElement watchNextBox;

	// TODO add remaining needed UI elements

	// constructor: connect the UI elements to the WebElements via locators (read
	// fro prop file).
	// It can be invoked from any page, as it will load the homepage url as first,
	// to bootstrap;
	public OtherPage(WebDriver driver, TestConfig config) throws Exception {
		super(driver, config);
		// load homepage;
		// TODO: improve this -- this is to fix a random failing due probably of some
		// dynamic content not loaded - add a proper wait();
		Utilities.pauseExecution(3000);
		String videoPageUrlPart = config.getData("videopage.url.part");

		// make sure to be in the video page (10 secs)
		WebDriverWait wait = new WebDriverWait(driver, config.getPageWaitTimeout());
		wait.until(ExpectedConditions.urlContains(videoPageUrlPart));

		// make sure the page content is loaded properly
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='YouTube video player']")));
		wait.until(ExpectedConditions.presenceOfElementLocated((config.getLocator("videopage.video.box"))));

		driver.switchTo().defaultContent();

		this.backButton = driver.findElement(config.getLocator("videopage.back.button"));
		// assert back button text is correct
		assertThat(backButton.getText().equalsIgnoreCase(config.getData("videopage.back.button.label"))).isTrue()
				.withFailMessage("Unexpected back button label'");

		this.videoTitleBox = driver.findElement(config.getLocator("videopage.video.title.box"));
		// assert that video title box is not empty (a title is shown)
		assertThat(!videoTitleBox.getText().equals("")).isTrue().withFailMessage("Empty video title");

		this.nextVideoTitle = driver.findElement(config.getLocator("videopage.next.video.title"));
		// assert that next video title box is not empty (a title is shown)
		assertThat(!nextVideoTitle.getText().equals("")).isTrue().withFailMessage("Empty next video title");

		this.watchNextBox = driver.findElement(config.getLocator("videopage.watch.next.box"));
		// assert watch next label text is correct
		assertThat(watchNextBox.getText().equalsIgnoreCase(config.getData("videopage.watch.next.label"))).isTrue()
				.withFailMessage("Unexpected next video label'");

	}

	// click on the back button, expect to go back on the homepage

	public HomePage clickBackButton() throws Exception {
		backButton.click();
		return new HomePage(driver, config);

	}

	// click on the next video button, expect to go onto another VideoPage

	public OtherPage clickNextVideoButton() throws Exception {
		String oldUrl = driver.getCurrentUrl();
		watchNextBox.click();
		OtherPage nextVideoVideoPage = new OtherPage(driver, config);
		// check that the new url is different than the previous one;
		// TODO: verify that this assertions always makes sense, or disable it;
		String newUrl = driver.getCurrentUrl();
		assertThat(oldUrl.equals(newUrl)).isFalse()
				.withFailMessage("It seems that the new video is the same as the old one (same url)");
		return nextVideoVideoPage;
	}

	/*
	 * Return the current video title
	 */
	public String getCurrentVideoTitle() {
		return videoTitleBox.getText();
	}

	/*
	 * Return the next video title
	 */
	public String getNextVideoTitle() {
		return nextVideoTitle.getText();
	}

	// close the driver
	public void close() {
		driver.close();
	}

	// TODO: build the remaining API here to interact with the page UI elements;

}
