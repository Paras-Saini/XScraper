package demo;

import org.checkerframework.checker.units.qual.A;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases {
    ChromeDriver driver;

    /*
     * TODO: Write your tests here with testng @Test annotation.
     * Follow `testCase01` `testCase02`... format or what is provided in
     * instructions
     */
    @Test
    public void testCase01() throws InterruptedException {
        boolean status = false;

        // Navigate to the target page
        Wrappers.urL(driver, "https://www.scrapethissite.com/pages/");

        // Select "Hockey Teams" from the available options
        Wrappers.selectOpt(driver, "Hockey Teams");
        List<HashMap<String, Object>> hockeyData = new ArrayList<>();
        int pageCount = 4;

        for (int i = 1; i <= pageCount; i++) {
            // Getting the name, year and percentage of team, which has percentage less than
            // 0.4
            // Add the data to the list
            hockeyData.addAll(Wrappers.pageHockey(driver, "0.4", "name", "year", "pct"));

            // Navigate to the next page based on current page number
            if (i == 1) {
                status = Wrappers.clkPage(driver, i + 1);
            } else {
                status = Wrappers.clkPage(driver, i + 2);
            }
            Assert.assertTrue(status, "Navigating to next page failed");
        }

        // Converting map to a JSON payload as string and Writing JSON on a file
        status = ToJson.toJson(hockeyData, "hockey-team-data");
        Assert.assertTrue(status, "Test case 01 failed");
    }

    @Test
    public void testCase02() throws InterruptedException {
        boolean status = false;
        // Navigate to the target page
        Wrappers.urL(driver, "https://www.scrapethissite.com/pages/");

        // Select "Oscar Winning Films" from the available options
        Wrappers.selectOpt(driver, "Oscar Winning Films");
        List<HashMap<String, Object>> topMovies = new ArrayList<>();

        List<WebElement> eleYear = Wrappers.yearLink(driver, "//a[@class='year-link']");

        for (WebElement ele : eleYear) {
            int year = Integer.parseInt(ele.getText());

            // Click on the year link to load its data
            status = Wrappers.clickOpt(ele);
            Assert.assertTrue(status, "Clicking on year link failed");

            // Wait for the page to load
            Thread.sleep(3000);
            // Get top 5 movies of each year and store in list about title, nominations and
            // awards
            // Add the data to the list
            topMovies.addAll(Wrappers.pageMovYear(driver, year, "film-title", "film-nominations",
                    "film-awards"));
        }

        // Converting map to a JSON payload as string and Writing JSON on a file
        status = ToJson.toJson(topMovies, "oscar-winner-data");
        Assert.assertTrue(status, "Test case 02 failed");
    }

    /*
     * Do not change the provided methods unless necessary, they will help in
     * automation and assessment
     */
    @BeforeTest
    public void startBrowser() {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
    }

    @AfterTest
    public void endTest() {
        driver.close();
        driver.quit();

    }
}