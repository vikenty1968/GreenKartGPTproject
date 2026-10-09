import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Set;

public class FlightBookTest extends BaseTest {
    private LocalDate departureDate = LocalDate.of(2019, 5, 05);
    private LocalDate returnDate = LocalDate.of(2019, 5, 31);
    LocalDate departureInJune = LocalDate.of(2019, 6, 1);
    LocalDate returnInDecember = LocalDate.of(2019, 12, 31);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, MMM dd yyyy", Locale.ENGLISH);

    private FlightBookPage getFlightBookPage() {
        CatalogPage catalogPage = new CatalogPage(getDriver());
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        Set<String> allWindows = getDriver().getWindowHandles();
        catalogPage.clickFlightBooking();
        wait.until(ExpectedConditions.numberOfWindowsToBe(allWindows.size() + 1));
        allWindows = getDriver().getWindowHandles();
        catalogPage.searchWindowByPartURL(allWindows, "/dropdownsPractise");
        boolean found = getDriver().getCurrentUrl().contains("/dropdownsPractise");
        Assert.assertTrue(found, "Page not found");
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
        FlightBookPage fbp = getFlightBookPage();
        fbp.selectRoundTrip();
        Assert.assertEquals(fbp.getReturnDateOpacity(), "1");
        fbp.selectOneWay();
        Assert.assertEquals(fbp.getReturnDateOpacity(), "0.5");

    }

    @Test(groups = "smokeFlight")
    public void selectsDepartureAndArrivalCities() {

        FlightBookPage fbp = getFlightBookPage();
        // chose departure
        fbp.clickDepartureField();
        fbp.selectDepartureCity("KNU");
        String chosenCity = fbp.getSelectedDepartureCity();
        Assert.assertEquals(chosenCity, "Kanpur (KNU)");
        //chose arrival city
        fbp.clickArrivalField();
        fbp.selectArrivalCity("BOM");
        String arrivalCity = fbp.getSelectedArrivalCity().trim();
        Assert.assertEquals(arrivalCity, "Mumbai (BOM)");


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

        fbp.selectDepartureDayInDisplayedMonth(departureDate.getMonthValue() - 1, departureDate.getDayOfMonth());
        //create formatter for universal assert
        Assert.assertEquals(
                fbp.getSelectedDepartureDate(),
                departureDate.format(formatter));


        fbp.invokeReturnCalendar();
        fbp.selectReturnDate(returnDate.getMonthValue() - 1, returnDate.getDayOfMonth());
        Assert.assertEquals(
                fbp.getSelectedReturnDate(),
                returnDate.format(formatter)
        );
    }

    @Test(dataProvider = "roundTripDates")
    public void selectsRoundTripDatesInDifferentMonths() {
        FlightBookPage fbp = getFlightBookPage();
        fbp.selectRoundTrip();

        // preparation cities need to get the calendar
        fbp.clickDepartureField();
        fbp.selectDepartureCity("KNU");
        fbp.clickArrivalField();
        fbp.selectArrivalCity("BOM");
        fbp.clickNextMonth();
        Assert.assertEquals(fbp.getLeftCalendarTitle(), "June 2019");

        fbp.selectDepartureDayInDisplayedMonth(departureInJune.getMonthValue() - 1, departureInJune.getDayOfMonth());
        Assert.assertEquals(fbp.getSelectedDepartureDate(), departureInJune.format(formatter));
        fbp.invokeReturnCalendar();
        fbp.moveToDisplayedMonth("December 2019");
        Assert.assertEquals(fbp.getLeftCalendarTitle(), "December 2019");

        fbp.selectReturnDate(returnInDecember.getMonthValue() - 1, returnInDecember.getDayOfMonth());
        Assert.assertEquals(fbp.getSelectedReturnDate(), returnInDecember.format(formatter));
    }

    @DataProvider(name = "roundTripDates")
    public Object[][] roundTripDates() {
        return new Object[][]{

                {
                        LocalDate.of(2019, 6, 1),
                        LocalDate.of(2019, 12, 31)
                }, {
                LocalDate.of(2019, 12, 31),
                LocalDate.of(2020, 1, 1)
        }


        };
    }
}
