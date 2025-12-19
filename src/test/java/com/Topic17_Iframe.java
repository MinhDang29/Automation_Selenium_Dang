package com;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.utils.BasicTest;

public class Topic17_Iframe extends BasicTest {

   @Test
    public void TC_02_FormSite() {
        driver.get("https://www.formsite.com/templates/education/campus-safety-survey/");

        // Click vào hình để load form
        driver.findElement(By.cssSelector("div#imageTemplateContainer>img")).click();

        // Verify form đã load lên (Dùng XPath kết hợp check class và text)
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='details__form-preview-wrapper' " +
                "and contains(string(),'Interactive form loaded. Try filling out below.')]")).isDisplayed());

        // QUAN TRỌNG: Switch vào iframe
        driver.switchTo().frame(driver.findElement(By.cssSelector("div#formTemplateContainer>iframe")));

        // Thao tác với các element bên trong Iframe
        new Select(driver.findElement(By.cssSelector("select#RESULT_RadioButton-2"))).selectByVisibleText("Senior");
        new Select(driver.findElement(By.cssSelector("select#RESULT_RadioButton-3"))).selectByVisibleText("South Dorm");
        driver.findElement(By.xpath("//label[text()='Female']")).click();

        // QUAN TRỌNG: Switch quay lại trang chính (Thoát khỏi iframe)
        driver.switchTo().defaultContent();

        // Click nút "Get this form" ở bên ngoài iframe
        driver.findElement(By.xpath("//a[@title='Get this form']")).click();
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