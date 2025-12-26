package com;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.utils.BasicTest;
import com.utils.Utils;

public class Topic24_Upload extends BasicTest {
    // Lấy đường dẫn gốc của project
    String projectPath = System.getProperty("user.dir");
    
    // Lưu ý: test files are stored under src/test/uploadFiles
    String uploadFileFolderPath = projectPath + "\\src\\test\\uploadFiles\\"; 
    
    String mountainFile = "Mountain.png";
    String riverFile = "River.png";
    String treeFile = "Tree.png";

    String mountainFilePath = uploadFileFolderPath + mountainFile;
    String riverFilePath = uploadFileFolderPath + riverFile;
    String treeFilePath = uploadFileFolderPath + treeFile;
       @Test
    public void TC_01_Single_File() {
        driver.get("https://blueimp.github.io/jQuery-File-Upload/");

        By uploadFileBy = By.cssSelector("input[type='file']");

        // 1. Load File lên (Chưa upload)
        driver.findElement(uploadFileBy).sendKeys(mountainFilePath);
        driver.findElement(uploadFileBy).sendKeys(riverFilePath);
        driver.findElement(uploadFileBy).sendKeys(treeFilePath);
        
        /// load toàn bộ thì sendKeys(file1, file2, file3) 
        /// driver.findElement(uploadFileBy).sendKeys(mountainFilePath + "\n" + riverFilePath + "\n" + treeFilePath);
        
        // 2. Verify file đã được load thành công vào hàng đợi
        Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + mountainFile + "']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + riverFile + "']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + treeFile + "']")).isDisplayed());

        // 3. Click nút "Start Upload" cho từng file
        List<WebElement> startUploadButtons = driver.findElements(By.cssSelector("table button.start"));
        for (WebElement startButton : startUploadButtons) {
            startButton.click();
            Utils.hardWait(1000); // Chờ 2 giây để file upload xong
        }

        // 4. Verify file đã được upload thành công (Có link download)
        Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']/a[text()='" + mountainFile + "']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']/a[text()='" + riverFile + "']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name']/a[text()='" + treeFile + "']")).isDisplayed());
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