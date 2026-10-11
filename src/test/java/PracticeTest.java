import org.testng.Assert;
import org.testng.annotations.Test;

public class PracticeTest extends BaseTest{
    @Test
    public void getIframe(){

        getDriver().get("https://rahulshettyacademy.com/AutomationPractice/");
        PracticePage practicePage=new PracticePage(getDriver());
        practicePage.switchToCoursesFrame();
        String text=practicePage.getAllCoursesBtnText();
        Assert.assertEquals(text,"VIEW ALL COURSES");
        getDriver().switchTo().defaultContent();
        String header =practicePage.getPageHeaderText();
        Assert.assertTrue(header.contains("Practice Page"));
    }
}
