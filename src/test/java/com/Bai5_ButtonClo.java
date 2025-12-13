package com;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.utils.BasicTest;
import com.utils.Utils;

public class Bai5_ButtonClo extends BasicTest {

       @Test
    public void TC_01_fahasa() {
        driver.get("https://www.fahasa.com/customer/account/login");
        Utils.hardWait(10000);
        By LoginBtn = By.xpath("(//button[@title='Đăng nhập'])[3]");

        Assert.assertFalse(driver.findElement(LoginBtn).isEnabled());
        Assert.assertEquals(Color.fromString(driver.findElement(LoginBtn).getCssValue("background-color")).asHex(), "#000000");
        
        driver.findElement(By.xpath("//input[@id='login_username']")).sendKeys("mdangdn29@gmail.com");
        driver.findElement(By.xpath("//input[@id='login_password']")).sendKeys("Admin@123");
        Utils.hardWait(5000);

        Assert.assertTrue(driver.findElement(LoginBtn).isEnabled());
        Assert.assertEquals(Color.fromString(driver.findElement(LoginBtn).getCssValue("background-color")).asHex().toUpperCase(), "#C92127");


     
                
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