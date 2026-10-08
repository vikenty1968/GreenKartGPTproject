import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.latest.domsnapshot.model.StringIndex;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

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
    public boolean searchWindowByPartURL(Set<String> windows, String partURL){
        Iterator<String >itr=windows.iterator();
        while (itr.hasNext()){
            String win= itr.next();
            driver.switchTo().window(win);
            if(driver.getCurrentUrl().contains(partURL)){
                System.out.println(driver.getCurrentUrl());
                            return  true;
            }
        }return false;
    }
}
