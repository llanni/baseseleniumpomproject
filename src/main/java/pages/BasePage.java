package pages;

import config.TestConfig;
import org.openqa.selenium.WebDriver;

public class BasePage {
    //class containing everything that all the pages have
    //it must be extended by any page in the POM
    public WebDriver driver;
    public TestConfig config;

    public BasePage(WebDriver driver, TestConfig config) {
        this.driver = driver;
        this.config = config;
    }
}
