import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage extends BasePage{
    public CartPage(WebDriver givenDriver) {
        super(givenDriver);
    }
    By placeOrderBtn =By.xpath("//button[text()='Place Order']");
    private By productRow(String fullName){
        return By.xpath("//tr[td[normalize-space()='"+fullName+"']]");
    }
 public String getProductNameInOrder(String fullName) {
        WebElement nameField = wait
                .until(ExpectedConditions
                        .visibilityOfElementLocated(productRow(fullName)));
        return nameField.findElement(By.xpath("./td[2]")).getText();
    }
    public CountryPage placeOrder(){
        WebElement orderBtn = wait
                .until(ExpectedConditions.elementToBeClickable(placeOrderBtn));
        orderBtn.click();
        return new CountryPage(driver);
    }
}
