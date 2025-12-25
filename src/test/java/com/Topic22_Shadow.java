package com;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.utils.BasicTest;

public class Topic22_Shadow extends BasicTest {

       @Test
    public void TC_01_Home_Shop() {
        driver.get("https://shop.polymer-project.org/");

        // 1. Tìm Shadow Host cấp 1
        WebElement shopAppShadowHost = driver.findElement(By.cssSelector("shop-app"));
        // 2. Lấy Shadow Root cấp 1
        SearchContext shopAppShadowRoot = shopAppShadowHost.getShadowRoot();

        // 3. Tìm Shadow Host cấp 2 (nằm trong Shadow Root 1)
        WebElement shopHomeShadowHost = shopAppShadowRoot.findElement(By.cssSelector("iron-pages>shop-home"));
        // 4. Lấy Shadow Root cấp 2
        SearchContext shopHomeShadowRoot = shopHomeShadowHost.getShadowRoot();

        // 5. Thao tác với element bên trong Shadow Root 2
        // (Trong ảnh bạn có 3 dòng click với các selector khác nhau, mình liệt kê cả 3 như trong ảnh)
        
        // Cách 1:
        shopHomeShadowRoot.findElement(By.cssSelector("a[href*='mens_outerwear']~shop-button")).click();
        
        // Cách 2:
        shopHomeShadowRoot.findElement(By.cssSelector("shop-button>a[aria-label=\"Men's Outerwear Shop Now\"]")).click();
        
        // Cách 3:
        shopHomeShadowRoot.findElement(By.cssSelector("shop-button>a[href*='mens_outerwear']")).click();

    }
    @Test
    public void TC_02_Nested() {
        driver.get("https://automationfc.github.io/shadow-dom/");

        // Driver đang thao tác vs DOM bên ngoài chưa có vào Shadow DOM
        Assert.assertEquals(driver.findElement(By.xpath("//a")).getText(), "scroll.html");
        Assert.assertEquals(driver.findElements(By.xpath("//a")).size(), 1);

        // Switch qua Shadow DOM 1
        WebElement firstShadowHost = driver.findElement(By.cssSelector("div#shadow_host"));
        SearchContext firstShadowRoot = firstShadowHost.getShadowRoot();

        // Đang thao tác vs Shadow DOM đầu tiên
        Assert.assertEquals(firstShadowRoot.findElement(By.cssSelector("span#shadow_content>span")).getText(), "some text");
        Assert.assertEquals(firstShadowRoot.findElements(By.cssSelector("a")).size(), 1);
        Assert.assertEquals(firstShadowRoot.findElement(By.cssSelector("a")).getText(), "nested scroll.html");

        // Switch qua Shadow DOM 2 (Nested)
        WebElement secondShadowHost = firstShadowRoot.findElement(By.cssSelector("div#nested_shadow_host"));
        SearchContext secondShadowRoot = secondShadowHost.getShadowRoot();

        // Đang thao tác vs Shadow DOM thứ 2
        // (Bạn có thể thêm code verify text bên trong shadow thứ 2 ở đây nếu cần)
        Assert.assertEquals(secondShadowRoot.findElement(By.cssSelector("div#nested_shadow_content>div")).getText(), "nested text");

    }
    @Test
    public void TC_03_Books_PWA() {
        driver.get("https://books-pwakit.appspot.com/");
        //sleepInSecond(4);

        // 1. Tìm Host & Root
        WebElement bookAppShadowHost = driver.findElement(By.cssSelector("book-app"));
        SearchContext bookShadowRoot = bookAppShadowHost.getShadowRoot();

        // 2. Nhập từ khóa
        bookShadowRoot.findElement(By.cssSelector("book-input-decorator>input#input")).sendKeys("Harry Potter");

        // 3. Click nút search (nằm trong shadow con)
        WebElement bookDecoratorHost = bookShadowRoot.findElement(By.cssSelector("book-input-decorator"));
        SearchContext bookDecoratorShadowRoot = bookDecoratorHost.getShadowRoot();
        
        bookDecoratorShadowRoot.findElement(By.cssSelector("div.icon")).click();
        //sleepInSecond(5);

        // 4. Lấy danh sách sách
        // Lưu ý: Cần tìm lại element cha để tránh StaleElementReferenceException do DOM đã update sau khi search
        bookAppShadowHost = driver.findElement(By.cssSelector("book-app"));
        bookShadowRoot = bookAppShadowHost.getShadowRoot();

        WebElement bookExplorerShadowHost = bookShadowRoot.findElement(By.cssSelector("book-explore"));
        SearchContext bookExplorerShadowRoot = bookExplorerShadowHost.getShadowRoot();

        List<WebElement> listBookItems = bookExplorerShadowRoot.findElements(By.cssSelector("section>ul.books>li>book-item"));

        // 5. Duyệt list và in tên sách (Mỗi item lại là một shadow host)
        for (WebElement bookItem : listBookItems) {
            SearchContext bookItemShadowRoot = bookItem.getShadowRoot();
            System.out.println(bookItemShadowRoot.findElement(By.cssSelector("div.title-container>h2.title")).getText());
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