package com;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.utils.BasicTest;
import com.utils.Utils;

public class Topic12_Default_Checkbox_Radio extends BasicTest {

       @Test
    public void TC_01_fahasa() {
        driver.get("https://demos.telerik.com/kendo-ui/checkbox/index");

        By dualZoneCheckbox = By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::span/input");
        By leatherTrimCheckbox = By.xpath("//label[text()='Leather trim']/preceding-sibling::span/input");
        By towBarCheckbox = By.xpath("//label[text()='Towbar preparation']/preceding-sibling::span/input");

        // Click chọn
        if (!driver.findElement(dualZoneCheckbox).isSelected()) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.id("demo-runner")));
            driver.findElement(dualZoneCheckbox).click();
        }

        // Kiểm tra
        Assert.assertTrue(driver.findElement(dualZoneCheckbox).isSelected());

        // Disable + Selected
        Assert.assertFalse(driver.findElement(leatherTrimCheckbox).isEnabled());
        Assert.assertTrue(driver.findElement(leatherTrimCheckbox).isSelected());

        // Disable + De-selected
        Assert.assertFalse(driver.findElement(towBarCheckbox).isEnabled());
        Assert.assertFalse(driver.findElement(towBarCheckbox).isSelected());
    }
    @Test
public void TC_02() {
    driver.get("https://automationfc.github.io/multiple-fields/");

    List<WebElement> everhadCheckboxes = driver.findElements(By.xpath(
            "//label[contains(text(), 'Have you ever had')]/following-sibling::div//input[@type='checkbox']"));

    // Select all (Chọn hết)
    for (WebElement checkbox : everhadCheckboxes) {
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    // Verify all (Kiểm tra lại xem đã chọn hết chưa)
    for (WebElement checkbox : everhadCheckboxes) {
        Assert.assertTrue(checkbox.isSelected());
    }

    // Bo Select all (Chọn hết)
    for (WebElement checkbox : everhadCheckboxes) {
        if (checkbox.isSelected()) {
            checkbox.click();
        }
    }
    // Verify all (Kiểm tra lại xem đã bỏ chọn hết chưa)
    // Verify all (Kiểm tra lại xem đã chọn hết chưa)
    for (WebElement checkbox : everhadCheckboxes) {
        Assert.assertFalse(checkbox.isSelected());
    }
    //specific
    for (WebElement checkbox : everhadCheckboxes) {
        if (!checkbox.isSelected() && checkbox.getAttribute("value").equals("Cancer")) {
            checkbox.click();
            break;
        }
    }
}
       @Test
    public void TC_03_Js() {
        driver.get("https://login.ubuntu.com");

        By newUserRadio = By.xpath("//*[@id='id_new_user']");
        By acceptToCheckbox = By.xpath("//input[@id='id_accept_tos']");
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

        
            jsExecutor.executeScript("arguments[0].click();", driver.findElement(newUserRadio));
            //wait.until(ExpectedConditions.elementToBeSelected(driver.findElement(newUserRadio)));
            Assert.assertTrue(driver.findElement(newUserRadio).isSelected());
                        Utils.hardWait(2000);

            //((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", acceptToCheckbox);
            jsExecutor.executeScript("arguments[0].click();", driver.findElement(acceptToCheckbox));
            Assert.assertTrue(driver.findElement(acceptToCheckbox).isSelected());

        
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