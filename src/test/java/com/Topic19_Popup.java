package com;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.utils.BasicTest;

public class Topic19_Popup extends BasicTest {

      @Test
public void TC_01_In_HTML() throws InterruptedException {
    driver.get("https://www.kmplayer.com/home");

    // Thread.sleep(8000);

    By popup = By.cssSelector("div[class='pop-container']");

    // 1 - Nếu như Popup có hiển thị thì Close đi => Click vào FAQ link
    // 2 - Nếu không hiển thị thì click vào FAQ link

    if (driver.findElement(popup).isDisplayed()) {
        System.out.println("========== Popup hiển thị ==========");
        driver.findElement(By.cssSelector("div.close")).click();
        Thread.sleep(2000);
    }

    System.out.println("========== Popup không hiển thị ==========");
    // Đều mong đợi sẽ ko còn hiển thị trước khi click vào FAQ link
    Assert.assertFalse(driver.findElement(popup).isDisplayed());

    driver.findElement(By.xpath("//a[text()='FAQ']")).click();

    Assert.assertTrue(driver.findElement(By.xpath("//a[text()='KMPlayer FAQ']")).isDisplayed());
}
@Test
public void TC_02_Not_In_HTML() throws InterruptedException {
    driver.get("https://tienganhcomaiphuong.vn/");

    driver.findElement(By.xpath("//button[text()='Đăng nhập']")).click();
    Thread.sleep(2000);

    By popup = By.cssSelector("div[class*='custom-dialog-paper']");

    // Verify popup hiển thị
    Assert.assertTrue(driver.findElement(popup).isDisplayed());

    driver.findElement(By.cssSelector("input[placeholder='Tài khoản đăng nhập']")).sendKeys("automationfc");
    driver.findElement(By.cssSelector("input[placeholder='Mật khẩu']")).sendKeys("automationfc");
    driver.findElement(By.xpath("//form//button[text()='Đăng nhập']")).click();
    Thread.sleep(2000);

    Assert.assertEquals(driver.findElement(By.cssSelector("div#notistack-snackbar")).getText(), "Bạn đã nhập sai tài khoản hoặc mật khẩu!");

    driver.findElement(By.cssSelector("h2>button.close-btn")).click();
    Thread.sleep(2000);

    // Verify popup không hiển thị
    // isDisplayed() ko kiểm tra cho 1 element ko có trong HTML được
    // khi xử lí các element ko có trong HTML ta dùng size() thì xét implicit wait về số ngắn
    
    Assert.assertEquals(driver.findElements(popup).size(), 0);
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