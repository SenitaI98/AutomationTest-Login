package placelab.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import placelab.utilities.WebDriverSetup;

public class LoginTests {
    private String host = System.getProperty("host");
    private String email = System.getProperty("email");
    private String password = System.getProperty("password");
    private WebDriver driver;
    private String homePageUrl = System.getProperty("homepage");
    private String expectedErrorMessage = "Invalid credentials!";
    private String user = System.getProperty("username");
    private String forgotPasswordUrl = System.getProperty("forgotYourPasswordPage");


    @Parameters({"browser"})
    /*@BeforeSuite(alwaysRun = true)   //Ovaj dio ukoliko koristimo samo 1 browser
    public void initDriver(String browser){
        driver= WebDriverSetup.getWebDriver(browser);
    }
    */
    @BeforeTest(alwaysRun = true, groups = {"Positive, Negative"}, description = "Verify that the user is able to open "
            + "PlaceLab App.")
    public void openApp(String browser) {
        driver = WebDriverSetup.getWebDriver(browser);
        //Go to PlaceLab demo app
        driver.navigate().to(host);
        //Validate that user is redirected to the right page
        Assert.assertEquals(driver.getCurrentUrl(), host);
        Assert.assertEquals(driver.getTitle(), "PlaceLab");

    }

    //Actual tests implementation
    @Test(priority = 4, groups = {"Positive"}, description = "Verify that user is able to login to homepage PlaceLab App, with valid credentials.", suiteName = "Smoke Test")
    public void testLoginValidCredentials() {
        //Go to PlaceLab demo app
        driver.navigate().to(host);
        //Validate that user is redirected to the right page
        Assert.assertEquals(driver.getCurrentUrl(), host);
        Assert.assertEquals(driver.getTitle(), "PlaceLab");
        //Enter valid email and password, click button submit
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.name("commit")).click();
        //Validate that user successfully logged in
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, homePageUrl);
        try {
            WebElement userName = driver.findElement(By.id("user-name"));
            assert userName.getText().contains(user);
        } catch (Exception e) {
            throw new IllegalArgumentException("Expected user is not logged in!");
        }
    }

    @Test(priority = 3, groups = {"Positive"}, description = "Verify that user is able to open Forgot your password link", suiteName = "Smoke Test")
    public void testLinkForgotYourPassword() {
        WebElement linkForgotPassword = driver.findElement(By.linkText("Forgot your password?"));
        linkForgotPassword.click();
        Assert.assertEquals(driver.getCurrentUrl(), forgotPasswordUrl);
    }

    @Test(priority = 2, groups = {"Negative"}, description = "Verify that user can't login with invalid email(username)")
    private void testLoginInvalidEmail() {
        //Enter invalid email and valid password and click button submit
        driver.findElement(By.id("email")).sendKeys("some.wrong@email.com");
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.name("commit")).click();

    }

    @Test(priority = 2, groups = {"Negative"}, description = "Verify that user can't login with invalid password")
    private void testLoginInvalidPassword() {
        //Enter valid email and invalid password and click button submit
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.id("password")).sendKeys("invalidPassword123");
        driver.findElement(By.name("commit")).click();

    }

    @Test(priority = 1, groups = {"Negative"}, description = "Verify that user can't login with empty email(username)")
    private void testLoginEmptyEmail() {
        //Enter password and click button submit
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.name("commit")).click();

    }

    @Test(priority = 1, groups = {"Negative"}, description = "Verify that user can't login with empty password")
    private void testLoginEmptyPassword() {
        //Enter email and click button submit
        driver.findElement(By.id("email")).sendKeys(email);
        driver.findElement(By.name("commit")).click();

    }

    @Test(priority = 1, groups = {"Negative"}, description = "Verify that user can't login with empty password and email(username) fields")
    private void testLoginEmptyPasswordAndEmail() {
        //Click button submit, without password and email
        driver.findElement(By.name("commit")).click();

    }

    @AfterTest(dependsOnGroups = {"Negative"}, description = "Verify that user is not logged in")
    public void failedLogin() {
        Assert.assertEquals(driver.getCurrentUrl(), host);
        String actualErrorMessage = driver.findElement(By.xpath("//div[@class = 'error-area']")).getText();
        Assert.assertEquals(expectedErrorMessage, actualErrorMessage, "User should not be able to login with invalid credentials");
    }

    @AfterSuite(alwaysRun = true)
    public void closeDriver() {
        driver.quit();
    }
}
