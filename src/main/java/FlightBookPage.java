import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;

import static org.openqa.selenium.support.ui.ExpectedConditions.textToBe;

public class FlightBookPage extends BasePage{
    public FlightBookPage(WebDriver givenDriver) {
        super(givenDriver);
    }

    private By returnField = By.cssSelector("#Div1.picker-second");
   private By roundTrip=By.id("ctl00_mainContent_rbtnl_Trip_1");
   private By oneWay =By.id("ctl00_mainContent_rbtnl_Trip_0");
    private By departureField =
            By.id("ctl00_mainContent_ddl_originStation1_CTXT");
    private By arrivalField =By.cssSelector("input#ctl00_mainContent_ddl_destinationStation1_CTXT");
    private By arrivalCityField= By.cssSelector("#glsctl00_mainContent_ddl_destinationStation1_CTNR a[value='BOM']");
    private By departureDateText =
            By.cssSelector(".picker-first2 span#view_fulldate_id_1");
    private By arrivalFieldText =
            By.cssSelector("#Div1.picker-second span#view_fulldate_id_2");
    private By nextMonth = By.cssSelector(" .ui-datepicker-next");
    private By leftCalendarTitle =By.cssSelector(".ui-corner-left .ui-datepicker-title");

    public String getReturnDateOpacity(){
        String opacity = driver.findElement(returnField).getCssValue("opacity");
        return opacity;
    }

    public void selectRoundTrip(){
        wait.until(ExpectedConditions.elementToBeClickable(roundTrip)).click();

    }
    public void selectOneWay(){
        wait.until(ExpectedConditions.elementToBeClickable(oneWay)).click();
    }

    public void clickDepartureField(){
        wait.until(ExpectedConditions.elementToBeClickable(departureField)).click();
    }
    public void selectDepartureCity(String city){
        wait.until(ExpectedConditions.
                elementToBeClickable(By
                        .cssSelector("#glsctl00_mainContent_ddl_originStation1_CTNR a[value='"+city+"']"))).click();
    }
    public String getSelectedDepartureCity(){
        String chosenCity = driver.findElement(departureField).getDomProperty("value");
        return chosenCity;
    }
    public void clickArrivalField(){
        wait.until(ExpectedConditions.elementToBeClickable(arrivalField)).click();
    }
    public void selectArrivalCity(String city){
        wait.until(ExpectedConditions.
                elementToBeClickable(By
                        .cssSelector("#glsctl00_mainContent_ddl_destinationStation1_CTNR  a[value='"+city+"']"))).click();
    }
    public String getSelectedArrivalCity(){
        String chosenCity = driver.findElement(arrivalField).getDomProperty("value");
        return chosenCity;
    }

    public void selectDepartureDayInDisplayedMonth(int monthIndex,int day){
           wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("//td[@data-month='"+monthIndex+"']/a[normalize-space()='"+day+"']"))).click();
    }

    public String getSelectedDepartureDate() {
        return driver.findElement(departureDateText).getText();
    }
    public void invokeReturnCalendar(){
        wait.until(ExpectedConditions
                .elementToBeClickable(By.cssSelector(".picker-second button.ui-datepicker-trigger"))).click();
    }
    public void selectReturnDate(int monthIndex,int day) {
        By returnDate = By.xpath(
                "//td[@data-handler='selectDay' and @data-month='"+monthIndex+"']/a[text()='"+day+"']"
        );
        wait.until(ExpectedConditions.elementToBeClickable(returnDate)).click();
    }
    public String getSelectedReturnDate() {
        return driver.findElement(arrivalFieldText).getText();
    }
    public void clickNextMonth(){
        String previousTitle = getLeftCalendarTitle();
        wait.until(ExpectedConditions.elementToBeClickable(nextMonth)).click();
        wait.until(ExpectedConditions.not(textToBe(leftCalendarTitle,previousTitle)));
    }

    public String getLeftCalendarTitle() {
       return driver.findElement(leftCalendarTitle).getText();
    }
    public void moveToDisplayedMonth(String expectedMonthYear){
        int count =0;
            while(!getLeftCalendarTitle().equalsIgnoreCase(expectedMonthYear)){
                if(count>12){
                    throw new IllegalStateException("Month not found "+ expectedMonthYear);
                }
            clickNextMonth();
            count++;
        }
    }
}
