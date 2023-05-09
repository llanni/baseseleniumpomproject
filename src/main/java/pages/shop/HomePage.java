package pages.shop;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import config.TestConfig;
import pages.BasePage;
import utilities.Utilities;

/*
POM (Page Object Model) of the HomePage

 */
public class HomePage extends BasePage {
	// define UI object as WebElements
	private WebElement topicSelector;

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

	// get number of displayed videos
	public int getNumberOfDisplayedVideos() throws Exception {
		return driver.findElements(config.getLocator("homepage.videolist.all.items")).size();
	}

	// click on a video, given the index, return the VideoPage
	public OtherPage clickOnVideo(int idx) throws Exception {
		int noVideos = getNumberOfDisplayedVideos();
		if (idx < 0 || idx >= noVideos)
			throw new Exception(
					"The input video index: " + idx + " must be between 0 and number of videos: " + noVideos);
		WebElement video = driver.findElements(config.getLocator("homepage.videolist.all.items")).get(idx);
		/*
		 * Actions actions = new Actions(driver); actions.moveToElement(video);
		 * actions.perform(); video.click();
		 */
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("var evt = document.createEvent('MouseEvents');"
				+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
				+ "arguments[0].dispatchEvent(evt);", video);
		return new OtherPage(driver, config);
	}

	// click on a video containg a text (text normalised) -- if many, return the
	// first;
	public OtherPage clickOnVideo(String text) throws Exception {
		text = text.toLowerCase().trim();
		List<WebElement> videos = driver.findElements(config.getLocator("homepage.videolist.all.items"));
		for (int i = 0; i < videos.size(); i++) {
			WebElement video = videos.get(i);
			String videoTitle = video.getText().toLowerCase().trim();
			if (videoTitle.contains(text)) {
				/*
				 * Actions actions = new Actions(driver); actions.moveToElement(video);
				 * actions.perform(); video.click();
				 */
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("var evt = document.createEvent('MouseEvents');"
						+ "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
						+ "arguments[0].dispatchEvent(evt);", video);

				return new OtherPage(driver, config);
			}
		}
		throw new Exception("Couldn't find a video containing text: " + text);
	}

	// return the index a video containg a text (text normalised) -- if many, return
	// the first;
	public int getIdxOfVideoWithText(String text) throws Exception {
		text = text.toLowerCase().trim();
		List<WebElement> videos = driver.findElements(config.getLocator("homepage.videolist.all.items"));
		for (int i = 0; i < videos.size(); i++) {
			WebElement video = videos.get(i);
			String videoTitle = video.getText().toLowerCase().trim();
			if (videoTitle.contains(text)) {
				video.click();
				return i;
			}
		}
		throw new Exception("Couldn't find a video containing text: " + text);
	}

	// return the title of a video, given its index
	public String getVideoTitle(int idx) throws Exception {
		int noVideos = getNumberOfDisplayedVideos();
		if (idx < 0 || idx >= noVideos)
			throw new Exception(
					"The input video index: " + idx + " must be between 0 and number of videos: " + noVideos);
		WebElement video = driver.findElements(config.getLocator("homepage.videolist.all.items")).get(idx);
		String title = video.getText();
		if (title.equalsIgnoreCase("")) {// maybe lazy loading? move via js to the video and wait some msecs;
			System.out.println("Title for video with idx: " + idx + " seems empty, trying again to grab it...");
			Utilities.pauseExecution(300);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("var evt = document.createEvent('MouseEvents');"
					+ "evt.initMouseEvent('move',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
					+ "arguments[0].dispatchEvent(evt);", video);
			title = video.getText();
		}
		if (title.equalsIgnoreCase(""))
			throw new Exception("Cannot grab video title from homepage for video at index: " + idx);
		return title;
	}

	// return the list of the topic filters as String array
	public String[] getTopicFilters() throws Exception {
		String[] topicsArray = null;
		topicSelector = driver.findElement(config.getLocator("homepage.topic.selector"));
		if (this.config.isMobileModeEnabled()) {
			// mobile
			Select topicSelectorSelect = new Select(topicSelector);
			topicsArray = new String[topicSelectorSelect.getOptions().size() - 1];
			// starting from 1 as the 0 is the AllTopics
			for (int i = 0; i < topicSelectorSelect.getOptions().size() - 1; i++) {
				topicsArray[i] = topicSelectorSelect.getOptions().get(i + 1).getText();
			}
		} else {
			// desktop
			topicSelector.click();
			List<WebElement> topics = topicSelector.findElements(By.xpath("./../li"));
			topicsArray = new String[topics.size()];
			for (int i = 0; i < topics.size(); i++)
				topicsArray[i] = topics.get(i).getText();
		}
		return topicsArray;
	}

	// return the list of the guide filters as String array

	// close the driver
	public void close() {
		driver.close();
	}

	// TODO: build the remaining API here to interact with the page UI elements;

}
