package demo.wrappers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */

    public static void urL(WebDriver driver, String url) {
        driver.get(url);
    }

    public static boolean clkPage(WebDriver driver, int index) {
        boolean status = false;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        try {
            WebElement ele = wait
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//ul[@class='pagination']")));
            ele.findElement(By.xpath(".//li[" + index + "]/a")).click();
            status = true;
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error:" + e);
        }
        return status;
    }

    public static void selectOpt(WebDriver driver, String opt) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement ele = wait
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h3/a[contains(text(),'" + opt + "')]")));
        ele.click();
    }

    public static List<WebElement> winPer(WebDriver driver, String per) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table")));
        List<WebElement> elePer = driver
                .findElements(By.xpath("//tr[.//td[contains(@class,'pct') and text()<'" + per + "']]"));
        return elePer;
    }

    public static List<HashMap<String, Object>> pageHockey(WebDriver driver, String per, String nameH, String yearH,
            String prt) {
        List<HashMap<String, Object>> hockeyData = new ArrayList<>();

        try {
            List<WebElement> wPerL = winPer(driver, per);
            for (WebElement ele : wPerL) {
                long epoch = System.currentTimeMillis() / 1000;
                HashMap<String, Object> hockeyD = new HashMap<>();
                hockeyD.put("Epoch Time of Scrape: ", epoch);
                hockeyD.put("Team Name: ", ele.findElement(By.xpath(".//td[@class='" + nameH + "']")).getText());
                hockeyD.put("Year: ", ele.findElement(By.xpath(".//td[@class='" + yearH + "']")).getText());
                hockeyD.put("Win %: ", ele.findElement(By.xpath(".//td[contains(@class,'" + prt + "')]")).getText());
                hockeyData.add(hockeyD);
            }
        } catch (Exception e) {
            System.out.println("Error:" + e);
        }
        return hockeyData;
    }

    public static boolean clickOpt(WebElement ele) throws InterruptedException {
        boolean status = false;
        try {
            ele.click();
            status = true;
        } catch (Exception e) {
            System.out.println("Error:" + e);
        }
        return status;
    }

    public static List<WebElement> yearLink(WebDriver driver, String path) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        List<WebElement> eYear = new ArrayList<>();
        try {
            eYear = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(path)));
        } catch (Exception e) {
            System.out.println("Error:" + e);
        }
        return eYear;
    }

    public static List<HashMap<String, Object>> pageMovYear(WebDriver driver, int year, String title,
            String nomi,
            String award) {
        List<HashMap<String, Object>> movieData = new ArrayList<>();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tbody/tr")));
            for (int i = 1; i <= 5; i++) {
                long epoch = System.currentTimeMillis() / 1000;
                HashMap<String, Object> filmHM = new HashMap<>();
                WebElement elem = driver.findElement(By.xpath("//tr[@class='film'][" + i + "]"));
                filmHM.put("Epoch Time of Scrape:", epoch);
                filmHM.put("Year: ", year);
                filmHM.put("Title: ", elem.findElement(By.xpath(".//td[@class='" + title + "']")).getText());
                filmHM.put("Nomination: ",
                        Integer.parseInt(elem.findElement(By.xpath(".//td[@class='" + nomi + "']")).getText()));
                filmHM.put("Awards: ",
                        Integer.parseInt(elem.findElement(By.xpath(".//td[@class='" + award + "']")).getText()));
                boolean isWinner = false;
                if (i == 1) {
                    isWinner = true;
                }
                filmHM.put("Best Picture Winner: ", isWinner);
                movieData.add(filmHM);
            }
        } catch (Exception e) {
            System.out.println("Error:" + e);
        }
        return movieData;
    }

}
