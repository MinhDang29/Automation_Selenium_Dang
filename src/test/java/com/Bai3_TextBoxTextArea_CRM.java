package com;

import java.time.Duration;

import org.checkerframework.checker.units.qual.A;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.utils.BasicTest;
import com.utils.Utils;

public class Bai3_TextBoxTextArea_CRM extends BasicTest {

    // --- 1. KHAI BÁO LOCATOR ---
    By Username = By.xpath("//*[@name='username']");
    By Password = By.xpath("//*[@name='password']");
    By LoginBtn = By.xpath("//*[@type='submit']");
    By itemviewBy = By.xpath("//*[@class='oxd-loading-spinner']");
    By DashboardView = By.xpath("//h6[text()='Dashboard']");
    By PIMBtn = By.xpath("//span[text()='PIM']");
    By MyInfoBtn = By.xpath("//span[text()='My Info']");
    By PIMView = By.xpath("//h6[text()='PIM']");
    By FirstName = By.xpath("//input[@name='firstName']");
    By LastName = By.xpath("//input[@name='lastName']");
    String passwordValue = "Admin@123";
    String passportNumber = "18050641";
    String fillFirstname = "Nguyen";
    String fillLastName = "Dang";
    By firstNameLoc = By.xpath("//input[@name='firstName']");
    By lastNameLoc = By.xpath("//input[@name='lastName']");
    By empIdLoc = By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input");
    String comMents = "Some comments";
    By SaveBtn = By.xpath("//button[contains(.,' Save ')]");
    
    public String generateRandomEmail() {
        return "automation" + System.currentTimeMillis() + "@gmail.com";
    }
    // --- 3. CÁC TEST CASES ---
    @Test
    public void TC_01_Login() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        Utils.hardWait(10000);

        // Điền thông tin
        wait.until(ExpectedConditions.visibilityOfElementLocated(Username)).sendKeys("Admin");
        wait.until(ExpectedConditions.visibilityOfElementLocated(Password)).sendKeys("admin123");
        wait.until(ExpectedConditions.elementToBeClickable(LoginBtn)).click();
        // driver.findElement(Username).sendKeys("Admin");
        // driver.findElement(Password).sendKeys("admin123");
        // driver.findElement(LoginBtn).click();

        // Chờ loading biến mất
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions
                .invisibilityOfAllElements(driver.findElements(itemviewBy)));

        // Verify Dashboard
        Assert.assertTrue(isElementDisplayed(DashboardView));

        // Click PIM
        driver.findElement(PIMBtn).click();

        // Verify PIM loaded
        Assert.assertTrue(isElementDisplayed(PIMView));

        // click ADD
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()=' Add ']"))).click();

        // Điền thông tin
        wait.until(ExpectedConditions.visibilityOfElementLocated(FirstName)).sendKeys(fillFirstname);
        wait.until(ExpectedConditions.visibilityOfElementLocated(LastName)).sendKeys(fillLastName);

        // 1. Lấy giá trị Employee ID (được hệ thống tự sinh ra)
        // Lưu ý: getDomProperty("value") dùng cho Selenium 4, nếu bản cũ dùng
        // getAttribute("value")
        String employeeID = driver
                .findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input"))
                .getDomProperty("value");
        System.out.println("Employee ID: " + employeeID);
        

        // 2. Click vào nút "Create Login Details" (để hiện ra form nhập user/pass)
        //driver.findElement(By.xpath("//p[text()='Create Login Details']/following-sibling::div//span")).click();
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//p[text()='Create Login Details']/following-sibling::div//span"))).click();
        // Chờ 2 giây để form kịp hiện ra (cần throws InterruptedException ở tên hàm
        // hoặc dùng try-catch)
        Utils.hardWait(2000);

        // 3. Nhập Username (Dòng này trong ảnh bạn đang gõ dở)
        //driver.findElement(By.xpath("//label[text()='Username']/parent::div/following-sibling::div/input")).sendKeys("ten_dang_nhap_moi");
         String randomEmail = generateRandomEmail();
        System.out.println("Email đăng ký là: " + randomEmail);
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//label[text()='Username']/parent::div/following-sibling::div/input")))
                .sendKeys(randomEmail);
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//label[text()='Password']/parent::div/following-sibling::div/input")))
                .sendKeys(passwordValue);
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//label[text()='Confirm Password']/parent::div/following-sibling::div/input")))
                .sendKeys(passwordValue);

        // click save
        wait.until(ExpectedConditions.elementToBeClickable(SaveBtn)).click();
        // Verify lại thông tin nhân viên vừa thêm  
        Assert.assertTrue(isElementDisplayed(By.xpath("//p[text()='Success']"))); 
        // Personal Details page
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@name='firstName']")))
                .getDomProperty("value"), fillFirstname);
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@name='lastName']")))
                .getDomProperty("value"), fillLastName);
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input")))
                .getDomProperty("value"), employeeID);
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[text()='Immigration']"))).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("(//*[text()=' Add '])[1]"))).click();
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//label[text()='Number']/parent::div/following-sibling::div/input"))).sendKeys(passportNumber);
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//label[text()='Comments']/parent::div/following-sibling::div/textarea"))).sendKeys(comMents);
        wait.until(ExpectedConditions.elementToBeClickable(SaveBtn)).click();
        // Verify lại thông tin passport vừa thêm 
        Assert.assertTrue(isElementDisplayed(By.xpath("//p[text()='Success']"))); 
        Assert.assertTrue(isElementDisplayed(
                By.xpath("//div[@class='oxd-table-card']//div[contains(text(),'" + passportNumber + "')]")));     
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@class='oxd-userdropdown-tab']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[text()='Logout']"))).click();

        // login account vừa tạo
        wait.until(ExpectedConditions.visibilityOfElementLocated(Username)).sendKeys(randomEmail);
        wait.until(ExpectedConditions.visibilityOfElementLocated(Password)).sendKeys(passwordValue);
        wait.until(ExpectedConditions.elementToBeClickable(LoginBtn)).click();

        Assert.assertTrue(isElementDisplayed(DashboardView));

        wait.until(ExpectedConditions.visibilityOfElementLocated((MyInfoBtn))).click();

        // Personal Details page
        // 2. Chờ cho đến khi ô input First Name load xong dữ liệu (có chứa chữ "Nguyen")
        // Hàm này sẽ đợi cho value attribute có dữ liệu rồi mới đi tiếp
        wait.until(ExpectedConditions.textToBePresentInElementValue(firstNameLoc, fillFirstname));
        // 3. Sau khi data đã load xong thì mới Assert
        Assert.assertEquals(driver.findElement(firstNameLoc).getDomProperty("value"), fillFirstname);
        
        wait.until(ExpectedConditions.textToBePresentInElementValue(lastNameLoc, fillLastName));
        Assert.assertEquals(driver.findElement(lastNameLoc).getDomProperty("value"), fillLastName);
        // Verify Employee ID
        wait.until(ExpectedConditions.textToBePresentInElementValue(empIdLoc, employeeID));
        Assert.assertEquals(driver.findElement(empIdLoc).getDomProperty("value"), employeeID);

        // không cho phép sửa Employee ID
        Assert.assertFalse(wait.until(ExpectedConditions.visibilityOfElementLocated(
                empIdLoc)).isEnabled());
        // cho phép sửa
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@name='firstName']"))).isEnabled());   
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@name='lastName']"))).isEnabled());   

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[text()='Immigration']"))).click();
                
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