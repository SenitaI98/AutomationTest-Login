package placelab.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverSetup {

    public static WebDriver getWebDriver(String browserName) {
        switch (browserName.toLowerCase()) {
            case "chrome":
                return getChromeDriver();
            case "firefox":
                return getFirefoxDriver();
            case "edge":
                return getEdgeDriver();
            default:
                throw new IllegalArgumentException("Match case not found for browser: "
                        + browserName);
        }
    }

    private static WebDriver getChromeDriver() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }

    private static WebDriver getFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver();
    }

    private static WebDriver getEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        return new EdgeDriver();
    }
}
