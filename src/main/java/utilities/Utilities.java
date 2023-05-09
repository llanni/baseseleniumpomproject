package utilities;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Random;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/*
Class containing utilities methods, available for the whole project
 */
public class Utilities {

	// generate a random email, given a domain as input (e.g. domain:
	// leonardolanni.com)
	public static String generateRandomEmail(String domain) {
		String defaultDomain = domain;
		String randomEmail = "qa.test.automation." + System.currentTimeMillis() + "." + generateRandomPassword(6) + "@"
				+ defaultDomain;
		System.out.println("Built random email: " + randomEmail);
		return randomEmail;
	}

	// generated a random password, given an input length
	public static String generateRandomPassword(int length) {
		String randomPassword = generateRandomString(length);

		// add uppercase and lowercase
		randomPassword = randomPassword.substring(0, 1).toUpperCase()
				+ randomPassword.substring(1, randomPassword.length()).toLowerCase();

		// add symbol
		randomPassword = randomPassword + ".";

		// add number 2x
		randomPassword = randomPassword + (new Random().nextInt(9));
		randomPassword = randomPassword + (new Random().nextInt(9));

		return randomPassword;
	}

	// generate a random String, given an input length
	public static String generateRandomString(int len) {
		// final String AB =
		// "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		final String AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		SecureRandom rnd = new SecureRandom();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

	// pause the Thread for the given number of msecs
	public static void pauseExecution(long msec) {
		try {
			Thread.sleep(msec);
		} catch (InterruptedException e) {
			// do nothing;
		}

	}

	/*
	 * Extract activation link from email or return exception //TODO: make it more
	 * robust
	 */
	public static String extractActivationLink(String htmlEmailBody) throws Exception {
		Document doc = Jsoup.parse(htmlEmailBody);
		Element links = doc.select("a[href]").first();
		String activationLink = links.attr("href").toString();
		if (isUrl(activationLink))
			return activationLink;
		else
			throw new Exception("The email does not seem to contain an activation link - provided: " + activationLink);
	}

	/*
	 * Extract activation code and return it as a link //TODO: make it more robust
	 */
	public static String extractActivationCode(String htmlEmailBody) throws Exception {
		Document doc = Jsoup.parse(htmlEmailBody);
		Element links = doc.select("strong").first();
		try {
			String activationCode = links.text();
			activationCode = activationCode.replace(" ", "").trim();
			return activationCode;
		} catch (Exception e) {
			throw new Exception("Cannot extract activation code from the email");
		}
	}

	/*
	 * Validate an input string to be a url
	 */
	public static boolean isUrl(String url) {
		try {
			new URL(url).toURI();
			return true;
		} catch (URISyntaxException exception) {
			return false;
		} catch (MalformedURLException exception) {
			return false;
		}
	}

	/*
	 * Given the driver, check if the current page contains a text showing the input
	 * element
	 */
	public static boolean isUIshowingText(WebDriver driver, String text) {
		try {
			WebElement el = driver.findElement(By.xpath("//*[contains(text(),'" + text + "')]"));
			if (el.isDisplayed())
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	// check if a String is numeric
	public static boolean isNumeric(String strNum) {
		Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
		if (strNum == null) {
			return false;
		}
		return pattern.matcher(strNum).matches();
	}

	// calculate diff in days between two Calendar dates
	public static int daysBetween(Calendar day1, Calendar day2) {
		Calendar dayOne = (Calendar) day1.clone(), dayTwo = (Calendar) day2.clone();

		if (dayOne.get(Calendar.YEAR) == dayTwo.get(Calendar.YEAR)) {
			return Math.abs(dayOne.get(Calendar.DAY_OF_YEAR) - dayTwo.get(Calendar.DAY_OF_YEAR));
		} else {
			if (dayTwo.get(Calendar.YEAR) > dayOne.get(Calendar.YEAR)) {
				// swap them
				Calendar temp = dayOne;
				dayOne = dayTwo;
				dayTwo = temp;
			}
			int extraDays = 0;

			int dayOneOriginalYearDays = dayOne.get(Calendar.DAY_OF_YEAR);

			while (dayOne.get(Calendar.YEAR) > dayTwo.get(Calendar.YEAR)) {
				dayOne.add(Calendar.YEAR, -1);
				// getActualMaximum() important for leap years
				extraDays += dayOne.getActualMaximum(Calendar.DAY_OF_YEAR);
			}

			return extraDays - dayTwo.get(Calendar.DAY_OF_YEAR) + dayOneOriginalYearDays;
		}
	}

	/*
	 * Return random integer between input min and max
	 */
	public static int getRandomInteger(int min, int max) throws Exception {
		max--;
		if (max <= min)
			throw new Exception("max: " + max + " must be bigger than min: " + min);
		Random random = new Random();
		return random.nextInt(max + 1 - min) + min;
	}

	/*
	 * To quickly test utilities methods, if needed
	 */
	public static void main(String[] args) throws Exception {
	}
}
