import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class CountryPage extends BasePage{
    public CountryPage(WebDriver givenDriver) {
        super(givenDriver);
    }
    By selectCountryField=By.cssSelector("div>select");
    By checkBox=By.cssSelector("input[type='checkbox']");
    By proceedField =By.xpath("//button[text()='Proceed']");
    public void selectCountry(String country){
        WebElement selectField = wait
                .until(ExpectedConditions.elementToBeClickable(selectCountryField));
        Select select = new Select(selectField);
        select.selectByVisibleText(country);
    }
    public void checkLicenceAgreement(){
        WebElement checkbox = driver.findElement(checkBox);
        checkbox.click();
    }
    public void confirmTheOrder(){
        WebElement proceed = wait
                .until(ExpectedConditions.visibilityOfElementLocated(proceedField));
        proceed.click();
    }
    public String getSuccessText(){
        WebElement success = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.wrapperTwo>span")));
        return success.getText();
    }
}
