package com;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.utils.BasicTest;
import com.utils.Utils;

public class Bai4_DropDown_Costom extends BasicTest {
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

    @Test
    public void TC_01_Login() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        Utils.hardWait(10000);

        // Điền thông tin
        wait.until(ExpectedConditions.visibilityOfElementLocated(Username)).sendKeys("Admin");
        wait.until(ExpectedConditions.visibilityOfElementLocated(Password)).sendKeys("admin123");
        wait.until(ExpectedConditions.elementToBeClickable(LoginBtn)).click();

        // Chờ loading biến mất
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions
                .invisibilityOfAllElements(driver.findElements(itemviewBy)));

        // Verify Dashboard
        Assert.assertTrue(isElementDisplayed(DashboardView));

        // Click PIM
        driver.findElement(PIMBtn).click();

        // Verify PIM loaded
        Assert.assertTrue(isElementDisplayed(PIMView));
        // Chọn dropdown Job Title
        selectItemDropdown("Job Title", "//div[@role='listbox']//span", "QA Engineer");
        // chọn Employment Status
        selectItemDropdown("Employment Status", "//div[@role='listbox']//span", "Full-Time Contract");
        // chọn Sub Unit
        selectItemDropdown("Sub Unit", "//div[@role='listbox']//span", "Engineering");
        // chọn Include
        selectItemDropdown("Include", "//div[@role='listbox']//span", "Current Employees Only");
        /*
         * 1. click vào element dropdown để xổ danh sách ra
         * 2. chờ cho đến khi các options trong dropdown được load ra hết
         * 3. kiểm tra các item xem đâu cần chọn
         * 4. nếu tìm thấy thì click item
         */
        // verify đã chọn đúng
        Assert.assertEquals(driver.findElement(By.xpath(
                "//label[text()='Job Title']/parent::div/following-sibling::div//div[@class='oxd-select-text-input']"))
                .getText(), "QA Engineer");
        Assert.assertEquals(driver.findElement(By.xpath(
                "//label[text()='Employment Status']/parent::div/following-sibling::div//div[@class='oxd-select-text-input']"))
                .getText(), "Full-Time Contract");
        Assert.assertEquals(driver.findElement(By.xpath(
                "//label[text()='Sub Unit']/parent::div/following-sibling::div//div[@class='oxd-select-text-input']"))
                .getText(), "Engineering");
        Assert.assertEquals(driver.findElement(By.xpath(
                "//label[text()='Include']/parent::div/following-sibling::div//div[@class='oxd-select-text-input']"))
                .getText(), "Current Employees Only");
    }
    @Test
    public void TC_02_Login() {
        driver.get("https://jqueryui.com/resources/demos/selectmenu/default.html");
        selectItemSelectableDropdown("//span[@id='speed-button']", "//ul[@id='speed-menu']/li/div", "Fast");
        Assert.assertEquals(driver.findElement(By.xpath("//span[@id='speed-button']/span[@class='ui-selectmenu-text']"))
                .getText(), "Fast");

    }
    @Test
    public void TC_03_Login() {
        driver.get("https://react.semantic-ui.com/maximize/dropdown-example-search-selection/");
        selectItemEditableDropdown("//input[@class='search']", "//div[@class='visible menu transition']//span", "Albania");
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='divider text']"))
                .getText(), "Albania");

    }
    // hàm sendkey rồi mới dropdown
    public void selectItemEditableDropdown(String editableXpath, String childXpath, String itemText) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(editableXpath))).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(editableXpath))).sendKeys(itemText);

        // BƯỚC 2: Chờ tất cả các option HIỆN RA (Dùng visibility)
        // XPath của bạn đúng, nhưng mình rút gọn lại chút cho dễ nhìn (OrangeHRM dùng
        // role='listbox')
        List<WebElement> allElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath(childXpath)));

        // BƯỚC 3: Duyệt qua từng dòng để chọn Job mong muốn
        for (WebElement item : allElements) {
            String textItem = item.getText();
            System.out.println(textItem);
            // BƯỚC 4: Kiểm tra đúng job cần chọn không, nếu đúng thì click
            if (textItem.equals(itemText)) { // Thay tên Job bạn muốn vào đây
                item.click();
                break; // Chọn xong thì thoát vòng lặp ngay cho nhanh
            }
        }
    }
    // hàm rút gọn chùng cho các site
    public void selectItemSelectableDropdown(String parentXpath, String childXpath, String itemText) {
        // BƯỚC 1: Click vào mũi tên để mở Dropdown "Job Title" ra trước
        // (Nếu không click, list option sẽ không bao giờ xuất hiện để mà tìm)
        wait.until(ExpectedConditions.elementToBeClickable(
            //"//label[text()='" + dropdownBy + "']/parent::div/following-sibling::div//i"
                By.xpath(parentXpath))).click();

        // BƯỚC 2: Chờ tất cả các option HIỆN RA (Dùng visibility)
        // XPath của bạn đúng, nhưng mình rút gọn lại chút cho dễ nhìn (OrangeHRM dùng
        // role='listbox')
        List<WebElement> allElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath(childXpath)));

        // BƯỚC 3: Duyệt qua từng dòng để chọn Job mong muốn
        for (WebElement item : allElements) {
            String textItem = item.getText();
            System.out.println(textItem);
            // BƯỚC 4: Kiểm tra đúng job cần chọn không, nếu đúng thì click
            if (textItem.equals(itemText)) { // Thay tên Job bạn muốn vào đây
                item.click();
                break; // Chọn xong thì thoát vòng lặp ngay cho nhanh
            }
        }
    }
    // hàm rút gòn dùng 4 dropdown chung
    public void selectItemDropdown(String dropdownBy, String optionsBy, String itemText) {
        // BƯỚC 1: Click vào mũi tên để mở Dropdown "Job Title" ra trước
        // (Nếu không click, list option sẽ không bao giờ xuất hiện để mà tìm)
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[text()='" + dropdownBy + "']/parent::div/following-sibling::div//i"))).click();

        // BƯỚC 2: Chờ tất cả các option HIỆN RA (Dùng visibility)
        // XPath của bạn đúng, nhưng mình rút gọn lại chút cho dễ nhìn (OrangeHRM dùng
        // role='listbox')
        List<WebElement> allElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath(optionsBy)));

        // BƯỚC 3: Duyệt qua từng dòng để chọn Job mong muốn
        for (WebElement item : allElements) {
            String textItem = item.getText();
            System.out.println(textItem);
            // BƯỚC 4: Kiểm tra đúng job cần chọn không, nếu đúng thì click
            if (textItem.equals(itemText)) { // Thay tên Job bạn muốn vào đây
                item.click();
                break; // Chọn xong thì thoát vòng lặp ngay cho nhanh
            }
        }
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
