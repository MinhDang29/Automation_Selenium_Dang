package com;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.utils.BasicTest;

public class Topic13_Action extends BasicTest {

       @Test
    public void TC_User_Actions() throws InterruptedException {
        driver.get("https://www.fahasa.com/");
        Thread.sleep(10000); // Chờ trang load xong (hoặc xử lý popup nếu có)

        // Khởi tạo Actions
        Actions actions = new Actions(driver);

        // Hover vào Icon Menu
        actions.moveToElement(driver.findElement(By.cssSelector("span.icon_menu"))).perform();
        actions.pause(Duration.ofSeconds(2)).perform();

        // Hover vào Sách Giáo Khoa 2025
        actions.moveToElement(driver.findElement(By.xpath("//span[text()='Sách Giáo Khoa 2025']"))).perform();
        actions.pause(Duration.ofSeconds(2)).perform();

        // Click vào Luyện Thi Môn Toán
        // Lưu ý: Dùng click của WebElement thường ổn định hơn click của Actions trong trường hợp này
        driver.findElement(By.xpath("//div[@class='fhs_column_stretch']//a[text()='Luyện Thi Môn Toán']")).click();

        // Verify
        Assert.assertTrue(driver.findElement(By.xpath("//ol[@class='breadcrumb']//strong[text()='Toán']")).isDisplayed());
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