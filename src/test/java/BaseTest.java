import config.ConfigReader;
import driver.DriverFactory;
import driver.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.net.MalformedURLException;

public class BaseTest {
  //  WebDriverWait wait;

    private ConfigReader configReader;
    private DriverFactory driverFactory;
    @BeforeClass(alwaysRun = true)
    public void prepareFramework() {
        driverFactory = new DriverFactory();
        configReader = new ConfigReader();
    }
    @BeforeMethod(alwaysRun = true)
    public void setUp() throws MalformedURLException {
       //create driver from driver factory
       WebDriver driver =driverFactory.setUpDriver(configReader.getProperty("browser"),configReader.getProperty("execution"));
        //send driver to the thread
        DriverManager.setDriver(driver);
        System.out.println(  "Thread: " + Thread.currentThread().getName()
                + ", driver: " + System.identityHashCode(driver));
       // driver.manage().window().maximize();
        DriverManager.getDriver().manage().window().maximize();
      //  wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//     //   driver.get(configReader.getProperty("baseUrl"));
        DriverManager.getDriver().get((configReader.getProperty("baseUrl")));
    }
    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        try{
        if(getDriver()!=null){
        getDriver().quit();}
        }finally {
            DriverManager.removeDriver();
        }
    }
protected WebDriver getDriver(){
        return DriverManager.getDriver();
}

}
