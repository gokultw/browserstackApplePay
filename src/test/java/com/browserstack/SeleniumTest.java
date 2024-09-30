package com.browserstack;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class SeleniumTest {
    public WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
    }

    private static final Duration TIMEOUT = Duration.ofSeconds(60);

    public void highlightElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
        System.out.println("Element highlighted. " + element.toString());
    }

    public void clickJs(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }
    public void sendKeysJs(WebElement element,String value) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value='"+value+"';", element);
    }

    public void click(WebElement element) {
        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            clickJs(element);
        }
    }

    public WebElement waitForVisibility(By locator) {
        WebElement element = new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.visibilityOfElementLocated(locator));
        highlightElement(element); // Highlight the element
        return element;
    }

    public WebElement waitForClickability(By locator) {
        WebElement element = new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.elementToBeClickable(locator));
        highlightElement(element);
        click(element);
        return element;
    }

    public WebElement waitForClickability(By locator, boolean js) {
        WebElement element = new WebDriverWait(driver, TIMEOUT).until(ExpectedConditions.elementToBeClickable(locator));
        highlightElement(element);
        if (js)
            clickJs(element);
        else
            click(element);
        return element;
    }

    public void pageload() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }

}
