import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PracticePage extends BasePage{
    public PracticePage(WebDriver givenDriver) {
        super(givenDriver);
    }
    private By coursesFrame = By.id("courses-iframe");
    private By courseBtn = By.cssSelector("a.view-all-courses-btn");
    private  By pageHeading = By.cssSelector("Practice Page");

    public void switchToCoursesFrame(){
      switchToIframe(coursesFrame);
    }
    public String getAllCoursesBtnText(){
        return wait.until(ExpectedConditions.elementToBeClickable(courseBtn)).getText();
    }
    public String getPageHeaderText(){
        return wait.until(ExpectedConditions.elementToBeClickable(pageHeading)).getText();
    }
}
