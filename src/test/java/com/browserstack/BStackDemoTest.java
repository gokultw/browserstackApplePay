package com.browserstack;

import com.browserstack.SeleniumTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BStackDemoTest extends SeleniumTest {
    public void openShop(String url) throws InterruptedException {
        driver.get(url);
        pageload();
        waitForVisibility(By.xpath("//*[@title=\"BLANCHARD EQUIPMENT CO. INC.\"]"));
        Thread.sleep(5000);
        if (driver.findElements(By.xpath("//*[contains(.,'UNITED AG & TURF')]")).size() > 0) {
            driver.get(url);
            pageload();
            waitForVisibility(By.xpath("//*[@title=\"BLANCHARD EQUIPMENT CO. INC.\"]"));
            Thread.sleep(5000);
        }
    }
    @Test
    public void e2e1() throws Exception {
        openShop("https://shop-cert.deere.com/us?dealer-id=010443");
        waitForClickability(By.xpath("//*[@id=\"searchTriggerInput\"]"));
        waitForClickability(By.id("search-box"));
        waitForVisibility(By.id("search-box")).sendKeys("LG185");
        waitForClickability(By.cssSelector(".whiteIcon"));
        waitForClickability(By.xpath("//SECTION[contains(.,'LG185')]//*[@data-testid=\"addToCartButton\"]"));
        waitForClickability(By.xpath("//button[normalize-space()='View Cart']"));
        waitForClickability(By.xpath("//button[normalize-space()='Checkout']"));


        waitForVisibility(By.id("shipAddressFirstName")).sendKeys("Gokul");
        waitForVisibility(By.id("shipAddressLastname")).sendKeys("Kuppan");
        waitForVisibility(By.id("shipAddressLine1")).sendKeys("1560 Broadway Ste 1001");
        waitForVisibility(By.id("shipAddressTownCity")).sendKeys("New York");

        // Select state from the dropdown and highlight it
        WebElement dropdown = driver.findElement(By.id("addressregion"));
        highlightElement(dropdown); // Highlight dropdown
        new Select(dropdown).selectByVisibleText("New York");

        waitForVisibility(By.id("shipAddressPostccode")).sendKeys("10036");
        waitForVisibility(By.id("shipAddressPhone")).sendKeys("9786543210");
        waitForVisibility(By.id("email.guest")).sendKeys("kuppangokul@johndeere.com");

        waitForClickability(By.id("validateAddressCheckoutButton"));
        waitForClickability(By.id("onsubmitValidatedAddress"));
        waitForClickability(By.xpath("//input[contains(@value,'GROUND')]"));
        waitForClickability(By.id("shipAddressSubmit"));
        waitForClickability(By.xpath("//*[@id=\"paymentM_9_applepay\"]"));
        waitForClickability(By.xpath("//*[@id=\"savedPaymentsTerms2\"]"));
        waitForClickability(By.xpath(" //*[@id=\"apple-pay-button\"]"));
        Thread.sleep(30000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String command = "browserstack_executor:{\"action\":\"applePay\", \"arguments\": {\"confirmPayment\" : \"true\"}}";
        js.executeScript(command);
        driver.findElement(By.name("Passcode field")).sendKeys("123456");
    }
}
