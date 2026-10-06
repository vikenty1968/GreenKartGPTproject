import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CatalogPage extends BasePage{
    public CatalogPage(WebDriver givenDriver){
        super(givenDriver);

    }
    private By searchField = By.cssSelector("input[type='search']");
    private  By productCard = By.cssSelector(".products h4");
    private By incrementField =By.cssSelector(".stepper-input a.increment");
    private By priceField = By.cssSelector(".product-price");
    private By quantityField =By.cssSelector("input.quantity");
    private  By addToCart = By.cssSelector(".product-action>button");
    private By headerItems =By.xpath("//td[normalize-space()='Items']/following-sibling::td[2]");
    private By headerPrice = By.xpath("//td[normalize-space()='Price']/following-sibling::td[2]");
    private By cartPreview =By.cssSelector("a.cart-icon");
    private By noResult =By.cssSelector(".products .no-results h2");
    public List<String> searchProductInCatalog(String query){
        WebElement search = wait.until(ExpectedConditions
                .visibilityOfElementLocated(searchField));
        WebElement oldProduct = driver.findElement(productCard);
        search.sendKeys(query);
        wait.until(ExpectedConditions.stalenessOf(oldProduct));
        List<WebElement> result = driver.findElements(productCard);
        List<String> resultItems = new ArrayList<>();

        for (WebElement item : result) {
            String product = item.getText();
           resultItems.add(product);
        }
        return resultItems;
    }
    public WebElement findProductName(String fullName){
        WebElement productCard = driver.findElement(By.xpath("//h4[normalize-space()='"+fullName+"']/.."));
        return productCard;
    }
    public void enterSearchQuery(String query) {
        WebElement search = wait.until(
                ExpectedConditions.visibilityOfElementLocated(searchField));
        search.sendKeys(query);
    }


    public void  incrementQuantity(String fullName){
      WebElement plus=  findProductName(fullName).findElement(incrementField);
      plus.click();

    }
    public int getProductPrice(String fullName){
        WebElement itemPrice =  findProductName(fullName).findElement(priceField);
        int price = Integer.parseInt(itemPrice.getText());
        return  price;
    }
    public int getProductQuantity(String fullName){
        WebElement quantity =  findProductName(fullName).findElement(quantityField);
        int number = Integer.parseInt(quantity.getDomProperty("value"));
        return  number;
    }
    public void addToCart(String fullName){
        WebElement addToCartBtn = findProductName(fullName).findElement(addToCart);
        addToCartBtn.click();
    }
    public void waitForHeaderPrice(int expectedPrice){
        wait.until(ExpectedConditions
                .textToBePresentInElementLocated(
                       headerPrice,
                        String.valueOf(expectedPrice)));
    }
    public int getHeaderPrice(){
        WebElement cartPriceElement = driver
                .findElement(headerPrice);
        int cartPrice = Integer.parseInt(cartPriceElement.getText());
        return cartPrice;
    }
    public int getItemsNumberInHeader(){
        WebElement itemsField = driver.
                findElement(headerItems);
        int itemsNumber = Integer.parseInt(itemsField.getText());
        return itemsNumber;
    }
    public CartPreview cartPreview(){
        WebElement cart = driver.findElement(cartPreview);
        cart.click();
        return new CartPreview(driver);
    }
    public String getNoResultMessage(){
        WebElement infoMessage=wait.until(ExpectedConditions.visibilityOfElementLocated(noResult));
        return infoMessage.getText();
    }
    public void clearSearch(){
        WebElement search = wait.until(ExpectedConditions
                .visibilityOfElementLocated(searchField));
        search.click();
        search.sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.BACK_SPACE);
    }
    public List<String> getDisplayedProductNames(){
        List<String>productsAfterClear = new ArrayList<>();
        wait.until(ExpectedConditions.visibilityOfElementLocated(productCard));//wait for the first element
        List<WebElement>products =driver.findElements(productCard);
        for (WebElement item:products){
            productsAfterClear.add(item.getText());
        }
        return  productsAfterClear;
    }
    public void setProductQuantity(String fullName,String value){
       WebElement product= findProductName(fullName);
        WebElement quantityInput= product.findElement(By.cssSelector("input[type='number']"));
        quantityInput.click();
        quantityInput.clear();
        quantityInput.sendKeys(value);
    }

}
