package com;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import com.utils.BasicTest;

public class Topic25_Wait_1 extends BasicTest {

       @Test
    public void TC_01_Visible() {

        driver.get("https://live.techpanda.org/index.php/customer/account/login/");

        driver.findElement(By.cssSelector("button#send2")).click();

        // Điều kiện 1 - Element hiển thị ở trên UI và có trong DOM/ cây HTML
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#advice-required-entry-email")));
    }

    @Test
    public void TC_02_Invisible_In_HTML() {
        driver.get("https://live.techpanda.org/index.php/customer/account/login/");

        driver.findElement(By.cssSelector("button#send2")).click();

        driver.findElement(By.cssSelector("input#email")).sendKeys("dam@gmail.com");
        driver.findElement(By.cssSelector("button#send2")).click();

        // Điều kiện 2 - Element ko hiển thị trên UI và có trong DOM/ HTML
        // An expectation for checking that an element is either invisible

        // Điều kiện 3 - Element ko hiển thị trên UI và ko có trong DOM
        // not present on the DOM
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#advice-required-entry-email")));
    }
    @Test
    public void TC_03_Invisible_Not_In_HTML() {
        driver.get("https://live.techpanda.org/index.php/customer/account/login/");


        driver.findElement(By.cssSelector("input#email")).sendKeys("dam@gmail.com");
        driver.findElement(By.cssSelector("button#send2")).click();

       

        // Điều kiện 3 - Element ko hiển thị trên UI và ko có trong DOM
        // not present on the DOM
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#advice-required-entry-email")));
    }
    @Test
    public void TC_03_Presence() {
        driver.get("https://live.techpanda.org/index.php/customer/account/login/");
        
        driver.findElement(By.cssSelector("button#send2")).click();

        // Điều kiện 1 - Element hiển thị ở trên UI và có trong DOM/ cây HTML
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div#advice-required-entry-email")));

        driver.findElement(By.cssSelector("input#email")).sendKeys("dam@gmail.com");
        driver.findElement(By.cssSelector("button#send2")).click();

        // Điều kiện 2 - Element ko hiển thị trên UI và có trong DOM/ HTML
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div#advice-required-entry-email")));
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