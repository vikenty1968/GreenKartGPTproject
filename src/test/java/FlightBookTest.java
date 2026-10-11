import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class FlightBookTest extends BaseTest {


    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, MMM dd yyyy", Locale.ENGLISH);
    DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH);

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
    @Test
    public void chosePaymentCurrency(){
        FlightBookPage fbp = getFlightBookPage();
       String currency= fbp.selectCurrency("USD","AED");
        Assert.assertEquals(currency,"AED");
        System.out.println("Currency=" +currency);
    }
    @Test
    public void canSelectAndDeselectStudentDiscount(){
        FlightBookPage fbp=getFlightBookPage();
        fbp.setStudentDiscount(true);
        Assert.assertTrue(fbp.isStudentDiscountSelected());
        fbp.setStudentDiscount(false);
        Assert.assertFalse(fbp.isStudentDiscountSelected());
    }




    @Test(dataProvider = "roundTripDates",groups = "regressionFlight")
    public void selectsRoundTripDates(LocalDate departureDate, LocalDate returnDate) {
        FlightBookPage fbp = getFlightBookPage();
        fbp.selectRoundTrip();

        // preparation cities need to get the calendar
        fbp.clickDepartureField();
        fbp.selectDepartureCity("KNU");
        fbp.clickArrivalField();
        fbp.selectArrivalCity("BOM");
        //  fbp.clickNextMonth();
        fbp.moveToDisplayedMonth(departureDate.format(monthFormatter));
        Assert.assertEquals(fbp.getLeftCalendarTitle(), departureDate.format(monthFormatter));

        fbp.selectDepartureDayInDisplayedMonth(departureDate.getMonthValue() - 1, departureDate.getDayOfMonth());
        Assert.assertEquals(fbp.getSelectedDepartureDate(), departureDate.format(formatter));
        fbp.invokeReturnCalendar();
        fbp.moveToDisplayedMonth(returnDate.format(monthFormatter));
        Assert.assertEquals(fbp.getLeftCalendarTitle(), returnDate.format(monthFormatter));

        fbp.selectReturnDate(returnDate.getMonthValue() - 1, returnDate.getDayOfMonth());
        Assert.assertEquals(fbp.getSelectedReturnDate(), returnDate.format(formatter));
    }

    @DataProvider(name = "roundTripDates")
    public Object[][] roundTripDates() {
        return new Object[][]{

                {
                        LocalDate.of(2019, 6, 1),
                        LocalDate.of(2019, 12, 31)
                },
                {
                        LocalDate.of(2019, 12, 31),
                        LocalDate.of(2020, 1, 1)
                },
                {
                        LocalDate.of(2019, 5, 5),
                        LocalDate.of(2019, 5, 31)
                },
                {
                    LocalDate.of(2021, 3, 15),
                        LocalDate.of(2021, 5, 3)

                }


        };
    }
}
