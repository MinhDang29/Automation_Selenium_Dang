package com;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.utils.BasicTest;
import com.utils.Utils;

public class Bai4_DropDown extends BasicTest {

       @Test
    public void TC_01_Login() {
        driver.get("https://www.facebook.com/r.php?entry_point=login");
        Utils.hardWait(10000);
        select  = new Select(driver.findElement(By.xpath("//select[@id='month']")));

        select.selectByIndex(3);
                Utils.hardWait(10000);

        select.selectByValue("9");
                Utils.hardWait(10000);

        select.selectByVisibleText("Dec");
        // kiểm  tra chọn thành công
        Assert.assertEquals(select.getFirstSelectedOption().getText(), "Dec");

        select.getOptions().size();
        //  kiểm tra có cho chộn nhiều hay không
        Assert.assertFalse(select.isMultiple());
        

     
                
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