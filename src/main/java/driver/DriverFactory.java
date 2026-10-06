package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {
    public WebDriver setUpDriver(String browser, String execution) throws MalformedURLException {
        if (execution.equalsIgnoreCase("local")) {
            return createLocalDriver(browser);
        }
        if (execution.equalsIgnoreCase("remote")) {

            return createRemoteDriver(browser);
        }
        throw new IllegalArgumentException("Unsupported execution " + execution);

    }
   private WebDriver createLocalDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-notifications");
                options.addArguments("--start-maximized");
                options.addArguments("--lang=en-US");
                return new ChromeDriver(options);
            case "firefox":
                return new FirefoxDriver();
            default:
                throw new IllegalArgumentException("Unsupported browser " + browser);
        }
    }
private WebDriver createRemoteDriver(String browser) throws MalformedURLException {
    String gridUrlValue = System.getProperty(
            "gridUrl",
            "http://localhost:4444"
    );

    URL gridUrl = new URL(gridUrlValue);
    switch (browser.toLowerCase()){
        case "chrome":
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-notifications");
            options.addArguments("--start-maximized");
            options.addArguments("--lang=en-US");
            return new RemoteWebDriver(gridUrl,options);
        case  "firefox":
            FirefoxOptions firefoxOptions=new FirefoxOptions();
            firefoxOptions.addPreference(
                    "intl.accept_languages", "en-US"
            );
            firefoxOptions.addPreference(
                    "permissions.default.desktop-notification", 2);
            return new RemoteWebDriver(gridUrl,firefoxOptions);
        case "edge":
            EdgeOptions edgeOptions = new EdgeOptions();
            edgeOptions.addArguments("--lang=en-US");
            edgeOptions.addArguments("--disable-notifications");

               return      new RemoteWebDriver(gridUrl, edgeOptions);


        default:throw new IllegalArgumentException("Unable to find browser: "+ browser);
    }


}

}
