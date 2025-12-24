package com;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.utils.BasicTest;

public class Topic20_WindowTab extends BasicTest {

    @Test
    public void TC_01_Github() {
        driver.get("https://automationfc.github.io/basic-form/index.html");

        // Lấy ra ID của tab hiện tại mà driver đang đứng ở đó
        String githubWindowID = driver.getWindowHandle();

        driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();
        sleepInSecond(3);

        // Switch sang tab Google (Tab khác Github)
        switchToWindowByID(githubWindowID);

        driver.findElement(By.cssSelector("textarea[name='q']")).sendKeys("Automation Testing");
        driver.findElement(By.cssSelector("textarea[name='q']")).sendKeys(Keys.ENTER);
        sleepInSecond(3);

        // Lấy ID của tab hiện tại (Google) để switch ngược lại
        String googleWindowID = driver.getWindowHandle();

        // Switch về lại tab Github
        switchToWindowByID(googleWindowID);
        sleepInSecond(3);
        // --- PART 2: SWITCH BY TITLE (FACEBOOK) ---
        driver.findElement(By.xpath("//a[text()='FACEBOOK']")).click();
        sleepInSecond(3);

        // Switch qua Facebook dựa vào Title
        switchWindowByTitle("Facebook");
        sleepInSecond(2);

        driver.findElement(By.cssSelector("input#email")).sendKeys("dam@gmail.com");
        driver.findElement(By.cssSelector("input#pass")).sendKeys("dam@gmail.com");

        // Switch về lại trang Github (Title là "Selenium WebDriver")
        switchWindowByTitle("Selenium WebDriver");
        sleepInSecond(2);

        closeAllWindowWithoutParent(githubWindowID);
    }
    @Test
    public void TC_02_TechPanda() {
        driver.get("https://live.techpanda.org/");

        driver.findElement(By.xpath("//a[text()='Mobile']")).click();
        sleepInSecond(2);

        // Add IPhone to Compare
        driver.findElement(By.xpath("//a[text()='IPhone']/parent::h2/following-sibling::div[@class='actions']//a[text()='Add to Compare']")).click();
        sleepInSecond(2);

        // Add Samsung Galaxy to Compare
        driver.findElement(By.xpath("//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div[@class='actions']//a[text()='Add to Compare']")).click();
        sleepInSecond(2);

        // Click Compare Button
        driver.findElement(By.xpath("//button[@title='Compare']")).click();
        sleepInSecond(2);

        // Switch qua cửa sổ Compare popup
        switchWindowByTitle("Products Comparison List");

        // Click vào Close button (trong HTML của trang popup)
        driver.findElement(By.xpath("//button[@title='Close Window']")).click();
        sleepInSecond(2);

        // Switch lại về trang Mobile
        switchWindowByTitle("Mobile");

        // Search check
        driver.findElement(By.xpath("//a[text()='Clear All']")).click();
        sleepInSecond(2);

        driver.switchTo().alert().accept();
        sleepInSecond(2);
    }
    @Test
    public void TC_04_Harvard() {
        driver.get("https://courses.dce.harvard.edu/");

        String homePageWindowID = driver.getWindowHandle();

        driver.findElement(By.cssSelector("a[data-action='login']")).click();
        sleepInSecond(8); // Chờ popup login load

        // Switch qua trang login portal để verify
        switchToWindowByID(homePageWindowID);
        //switchWindowByTitle("Harvard Division of Continuing Education Login Portal");
        Assert.assertTrue(driver.findElement(By.xpath("//h1[text()='DCE Login Portal']")).isDisplayed());

        // Đóng tab login, quay về trang chính
        closeAllWindowWithoutParent(homePageWindowID);

        // Đảm bảo driver đang ở trang chính (Dựa vào Title)
        switchWindowByTitle("DCE Course Search");

        Assert.assertTrue(driver.findElement(By.cssSelector("div.activescreen")).isDisplayed());

        // Thao tác tìm kiếm
        driver.findElement(By.cssSelector("button[class*='button--cancel']")).click();
        sleepInSecond(2);

        driver.findElement(By.cssSelector("input#crit-keyword")).sendKeys("Data Science: An Artificial Ecosystem");
        
        // Sử dụng Select Class để thao tác với Dropdown
        new Select(driver.findElement(By.cssSelector("select#crit-srcdb"))).selectByVisibleText("Harvard Summer School 2025");
        new Select(driver.findElement(By.cssSelector("select#crit-summer_school"))).selectByVisibleText("Harvard College");
        new Select(driver.findElement(By.cssSelector("select#crit-session"))).selectByVisibleText("Full Term");

        driver.findElement(By.cssSelector("button#search-button")).click();
        sleepInSecond(3);

        // Verify kết quả tìm kiếm
        Assert.assertEquals(driver.findElement(By.cssSelector("span.result__headline>span.result__title")).getText(), "Data Science: An Artificial Ecosystem");
    }
    @Test
    public void TC_05_Selenium_4x() {
        // Trang User vào đăng kí tài khoản/ mua hàng/ thanh toán
        driver.get("https://demo.nopcommerce.com/");

        // Qua trang Admin để kích hoạt tài khoản/ verify đơn hàng/ ship hàng/..
        // Tự driver sẽ switch qua luôn ko cần dùng hàm switch ID nữa
        driver.switchTo().newWindow(WindowType.TAB).get("https://admin-demo.nopcommerce.com/");
        sleepInSecond(3);

        switchWindowByTitle("Home page title"); // Lưu ý: Title thực tế có thể khác tùy vào trang web
        sleepInSecond(3);
    }
    private void switchToWindowByID(String windowID) {
        Set<String> allWindowIDs = driver.getWindowHandles();

        for (String id : allWindowIDs) {

            // Nếu cửa sổ nào khác vs ID cho trước
            if (!id.equals(windowID)) {

                // Switch vào cửa sổ đó
                driver.switchTo().window(id);
                break;
            }
        }
    }

    private void sleepInSecond(long timeInSecond) {
        try {
            Thread.sleep(timeInSecond * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Switch window khi có từ 2 cửa sổ/tab trở lên (Dựa vào Title trang)
    // Cover luôn cả cách làm của switchID
    private void switchWindowByTitle(String expectedPageTitle) {
        Set<String> allWindowIDs = driver.getWindowHandles();
        for (String id : allWindowIDs) {
            try {
                driver.switchTo().window(id);
            } catch (NoSuchWindowException e) {
                // If the window is no longer available, try the next one
                continue;
            }
            sleepInSecond(1);
            try {
                if (driver.getTitle().contains(expectedPageTitle)) {
                    break;
                }
            } catch (NoSuchWindowException e) {
                // If the window got closed after switching, continue searching
                continue;
            }
        }
    }
    // Đóng tất cả các tab ngoại trừ tab cha (Parent Window)
    private void closeAllWindowWithoutParent(String windowID) {
        Set<String> allWindowIDs = driver.getWindowHandles();
        for (String id : allWindowIDs) {
            if (!id.equals(windowID)) {
                driver.switchTo().window(id);
                // Đóng Tab/Window của driver đang Active
                driver.close();
            }
        }
        // Switch lại về driver cha sau khi đóng các con
        driver.switchTo().window(windowID);
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