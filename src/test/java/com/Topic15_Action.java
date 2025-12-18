package com;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.utils.BasicTest;
import org.openqa.selenium.interactions.Actions;


public class Topic15_Action extends BasicTest {

       @Test
    public void TC_03_Double_Click() {
        driver.get("https://automationfc.github.io/basic-form/index.html");

        // Scroll tới phần tử (nếu cần)
        // Lưu ý: Trong hình bạn dùng biến 'jsExecutor', ở đây mình ép kiểu trực tiếp để bạn dễ copy
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", 
                driver.findElement(By.xpath("//button[text()='Double click me']")));
        
        // Pause 3 giây (Dùng Actions)
        action.pause(Duration.ofSeconds(3)).perform();

        // Thực hiện Double Click
        action.doubleClick(driver.findElement(By.xpath("//button[text()='Double click me']"))).perform();
        
        // Pause 2 giây để xem kết quả
        action.pause(Duration.ofSeconds(2)).perform();
        
        // Verify text kết quả
        Assert.assertEquals(driver.findElement(By.cssSelector("p#demo")).getText(), "Hello Automation Guys!");
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