import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Set;

public class FlightBookTest extends BaseTest {
    private FlightBookPage getFlightBookPage(){
        CatalogPage catalogPage = new CatalogPage(getDriver());
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        Set<String> allWindows = getDriver().getWindowHandles();
        catalogPage.clickFlightBooking();
        wait.until(ExpectedConditions.numberOfWindowsToBe(allWindows.size() + 1));
        allWindows = getDriver().getWindowHandles();
        catalogPage.searchWindowByPartURL(allWindows, "/dropdownsPractise");
        boolean found=getDriver().getCurrentUrl().contains("/dropdownsPractise");
        Assert.assertTrue(found,"Page not found");
        return new FlightBookPage(getDriver());
    }
    @Test(groups = "smokeFlight")
    public void checkReturnDayOpacity() {

//        CatalogPage catalogPage = new CatalogPage(getDriver());
//        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
//        Set<String> allWindows = getDriver().getWindowHandles();
//        catalogPage.clickFlightBooking();
//        wait.until(ExpectedConditions.numberOfWindowsToBe(allWindows.size() + 1));
//        allWindows = getDriver().getWindowHandles();
//        catalogPage.searchWindowByPartURL(allWindows, "/dropdownsPractise");
//        Assert.assertTrue(getDriver().getCurrentUrl().contains("/dropdownsPractise"));
//        FlightBookPage fbp = new FlightBookPage(getDriver());
        FlightBookPage fbp=getFlightBookPage();
        fbp.selectRoundTrip();
        Assert.assertEquals(fbp.getReturnDateOpacity(), "1");
        fbp.selectOneWay();
        Assert.assertEquals(fbp.getReturnDateOpacity(), "0.5");

    }
    @Test(groups = "smokeFlight")
    public void selectsDepartureAndArrivalCities()  {

        FlightBookPage fbp=getFlightBookPage();
          // chose departure
        fbp.clickDepartureField();
        fbp.selectDepartureCity("KNU");
        String chosenCity=fbp.getSelectedDepartureCity();
        Assert.assertEquals(chosenCity,"Kanpur (KNU)");
        //chose arrival city
        fbp.clickArrivalField();
        fbp.selectArrivalCity("BOM");
        String arrivalCity= fbp.getSelectedArrivalCity().trim();
        Assert.assertEquals(arrivalCity,"Mumbai (BOM)");


    }
    @Test(groups = "smokeFlight")
    public void selectsRoundTripDates() {
        FlightBookPage fbp = getFlightBookPage();
        fbp.selectRoundTrip();

        // preparation cities need to get the calendar
        fbp.clickDepartureField();
        fbp.selectDepartureCity("KNU");
        fbp.clickArrivalField();
        fbp.selectArrivalCity("BOM");

        fbp.selectDepartureDayInDisplayedMonth(4,16);
        Assert.assertEquals(
                fbp.getSelectedDepartureDate(),
                "Thu, May 16 2019"
        );

        fbp.invokeReturnCalendar();
        fbp.selectReturnDate();
        Assert.assertEquals(
                fbp.getSelectedReturnDate(),
                "Thu, May 30 2019"
        );
    }
}
