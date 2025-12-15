package com;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.utils.BasicTest;

public class Topic13_Alert extends BasicTest {

       @Test
    public void TC_01_Accept_Alert() {
    driver.get("https://automationfc.github.io/basic-form/index.html");

    driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();

    // Chờ cho Alert được presence
    // Presence: Có xuất hiện trong HTML (ko bắt buộc phải hiển thị trên UI)
    new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.alertIsPresent());

    // Thao tác vs Alert
    Alert alert = driver.switchTo().alert();

    Assert.assertEquals(alert.getText(), "I am a JS Alert");

    // Accept Alert
    alert.accept();

    Assert.assertEquals(driver.findElement(By.cssSelector("p#result")).getText(), "You clicked an alert successfully");
    }
        @Test
    public void TC_02_Confirm_Alert() {
        driver.get("https://automationfc.github.io/basic-form/index.html");

        driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();

        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.alertIsPresent());

        Alert alert = driver.switchTo().alert();

        Assert.assertEquals(alert.getText(), "I am a JS Confirm");

        // Dismiss (Bấm Cancel)
        alert.dismiss();

        Assert.assertEquals(driver.findElement(By.cssSelector("p#result")).getText(), "You clicked: Cancel");
    }
    @Test
    public void TC_03_Prompt_Alert() throws InterruptedException {
        driver.get("https://automationfc.github.io/basic-form/index.html");

        driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();

        // Chờ Alert xuất hiện
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.alertIsPresent());

        Alert alert = driver.switchTo().alert();

        // Kiểm tra text mặc định của Alert
        Assert.assertEquals(alert.getText(), "I am a JS prompt");

        String text = "Alert Prompt";

        // Gửi text vào Alert
        alert.sendKeys(text);
        
        // Chờ 3 giây để nhìn thấy kết quả (trong thực tế có thể bỏ dòng này)
        Thread.sleep(3000);

        // Bấm OK
        alert.accept();

        // Verify kết quả hiển thị dưới trang web
        Assert.assertEquals(driver.findElement(By.cssSelector("p#result")).getText(), "You entered: " + text);
    }
    @Test
    public void TC_06_Authentication_Alert() {
        driver.get("http://the-internet.herokuapp.com/");

        String url = driver.findElement(By.xpath("//a[text()='Basic Auth']")).getDomProperty("href");
        System.out.println(url);
        
        String username = "admin";
        String password = "admin";

        String[] urlArr = url.split("//");
        url = urlArr[0] + "//" + username + ":" + password + "@" + urlArr[1];

        driver.get(url);
        Assert.assertEquals(driver.findElement(By.cssSelector("div.example>p")).getText(), "Congratulations! You must have the proper credentials.");
    }
    public boolean isElementDisplayed(By by) {
        try {
            // Lưu ý: Đảm bảo biến 'wait' đã được khởi tạo trong class BasicTest
            return wait.until(ExpectedConditions.visibilityOfElementLocated(by)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}