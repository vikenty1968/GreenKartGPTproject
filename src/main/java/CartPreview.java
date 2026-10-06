import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CartPreview extends BasePage {

    public CartPreview(WebDriver givenDriver){
        super(givenDriver);

    }
    private By previewName=By.cssSelector(".cart-preview .product-name");
    private By previewQuantity=By.cssSelector(".cart-preview.active p.quantity");
    private By goToCart =By.cssSelector(".cart-preview.active button");
    public String productNameInPreview(){
        WebElement itemName = wait
                .until(ExpectedConditions.visibilityOfElementLocated(previewName));
        return itemName.getText();
    }
    public String productQuantityInPreview(){
        WebElement itemQuantity = wait
                .until(ExpectedConditions.visibilityOfElementLocated(previewQuantity));
       return  itemQuantity.getText();
    }
    public List<String> getProductsNames(){
      WebElement cartPreview=  wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.cart-preview")));
        List<WebElement>names =cartPreview.findElements(By.cssSelector(".product-name"));
        List<String>foundProducts = new ArrayList<>();
        for(WebElement item:names){
            foundProducts.add(item.getText());
        }
        return foundProducts;
    }

    public CartPage goToCart(){
        WebElement checkOutBtn = wait
                .until(ExpectedConditions.elementToBeClickable(goToCart));
        checkOutBtn.click();
        return new CartPage(driver);
    }
}
