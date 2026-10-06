import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    public BasePage(WebDriver givenDriver){
        this.driver = givenDriver;
        this.wait=new WebDriverWait(this.driver, Duration.ofSeconds(10));
    }
    public boolean waitFoUrl(String endPoint){
        return  wait.until(ExpectedConditions.urlContains(endPoint));
    }
}
