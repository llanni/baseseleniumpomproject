# 1. Abstract
This project (downloadable from git) is a template to generate UI E2E test cases against Web Applications (on the internet or on local) using Selenium and the Page Object Model (POM) design pattern.
It can be used as the starting point to create a test automation suite to test any web application (also covering the mobile part, if the application supports responsive design).
The project is delivered as a Maven project, any text editor can be used to edit it as well as any IDE like Eclipse or IntelliJ.
If using Eclipse, starting from a workspace, select import existing maven project and point it to the root folder of the project.
The test cases are meant to run on Chrome with Chrome Driver, both in desktop (full screen mode) and mobile mode (the window is smaller than the breaking point, to trigger Mobile responsive design), despite it is possible to modify/extend the code to support any existing browser.

The project is containing basic testing against the Saucelab testing eCommerce web app called Swag Labs, which is assumed to be installed and running on localhost at port 3000 (as it defaults).
Swag Labs can be downloaded from here:
https://github.com/saucelabs/sample-app-web
And installed locally following the instructions in the README.md file.

Or also the same web application is available online at:
https://www.saucedemo.com/

In this case please remember to update the `homepage.url` property in the pom.xml file accordingly.

# 2. Prerequirements
- Java 1.8.x
- Maven (configured properly with Java)
- Internet connection
- Google Chrome browser
- Selenium Chrome Driver (download here: `http://chromedriver.chromium.org/downloads` and unzip and store somewhere safe the executable)
- *IMPORTANT:* please provide the selenium chrome driver full path (from step 7) into the pom.xml file, at the property `selenium.chrome.driver.executable.path` (see the current property value for an example).

# 3. Installation
- Checkout/download the project from the git repository into a root folder

# 4. To run the test cases
- Open a Command Line
- Go where this project was checked out (root folder)
- Run 
```
"mvn clean verify"
```
The command will compile and build the project and run all the test cases.
The screen command line output will provide the test logs and information

# 5. Some information
- The `/README.md` is this current file
- The `/pom.xml` files contains the project information, Maven repository, all the dependencies (required external libraries) as well as the plugins and the properties.
The properties section contains all the locators as well as some configuration parameters.
- The test cases are in the packages: `/src/test/java/test/ui/`, all exstending a BaseTest in `/src/test/java/test`, that can contain common features. 
Each test cases is a sequence of interactions with the UI calling the API of the interacted pages via the corrispective POMs, and doing assertions on the content to match the expected one (via JUnit assertions)
More test cases can be created in the `/src/test/java/test/ui/`
- The resource folder contains the `props.properties` with all the project properties; the properties are dynamically read from the pom.xml file in the project root, so properties must be edited in the pom exclusively; it is possible to override from commandline any pom.mxl property by adding in the `mvn` command the following:
```
-D<propName>=<propValue>
```
- The `/testng.xml` files contains the configuration to decide which tests to run (using TestNG) and if running them in parallel
- The package `/src/main/java/config` contains a test configuration file to read the property file and handle the Chrome driver and both Mobile and Desktop mode
- The package `/src/main/java/pages/shop` contains the Page Object Model (POM) of the page(s) needed in test cases, all extending a BasePage inside `/src/main/java/pages/` that should contain common UI elements over all the pages;
- The package `/src/main/java/utilities` contains some utility methods that can be commonly used in all the Test Cases or POM Classes

A Test Case extends the BaseTest classes. The BaseTest connects a Test Case with a WebDriver and a TestConfig (configuration).
The WebDriver object is used to connect to a specific browser driver (e.g. Chrome Driver), the TestConfig is used as an object to specify parameters to customise the test execution, and it is the place where a new WebDriver for other browsers can be generated, specifying all the needed capabilities.

A Test Case interacts with the UI Pages by calling each page's POM.
Each POM Page contains a constructor that first visit the page and then binds each WebElement to the locator, read from the property (from the pom.xml).
If the constructor does not fail throwing an Exception, this means that the page is properly linked to the POM, and then the Test Case can use the methods in the POM to interact with the page (clicking elements, typing values into fields, reading values) and can do assertions via JUnit (or any other testing framework) to check that the actual content matches the expectation.
For a simplicity matter, there are no common Test Steps (a common sequence of steps that is used by different test cases), this can be created extending the project.
As well, there is at the moment no proper logger installed (like Log4j 2) and any information will be writte on the System.out, it is obviously possible to add any better logging mechanism.

# 6. Reporting
To generate a test report, the Surefie plugin is used, and it gan generate a report by using its standalone goal in the command line, from the root folder:
```
mvn surefire-report:report
```
A HTML report should be generated in `/target/site/surefire-report.html` and can be read using any browser (please remember to manually clean the folder if nededed before any new run).

# 7. Important notes
- Make sure to use latest Chrome Driver (at least 2.43.0) and Chrome (at least 70.0)
- The properties inside props.properties file are self explaining, the most important one is probably:
`test.mobile.mode.enabled=false`
which decide if the test is run in mobile (true) or dekstop (false) mode

