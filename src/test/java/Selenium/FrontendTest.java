package Selenium;

/**
 * Created by Adam on 2016-08-25.
 */

import com.thoughtworks.selenium.SeleneseTestBase;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FrontendTest extends SeleneseTestBase {


   @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.firefox.bin", "E:\\Windows7\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
        WebDriver driver = new FirefoxDriver();
        String baseUrl = "http://localhost:8016/";
        selenium = new WebDriverBackedSelenium(driver, baseUrl);
    }

    @Test
    public void shouldPerformLoginToAdminPageAndLogout() {
        loginAs("ADMINISTRATOR", "administration");
        selenium.open("/admin");
        selenium.waitForPageToLoad("3000");
        assertTrue(selenium.isTextPresent("ADMIN | Logout"));
        logout();
        assertTrue(selenium.isTextPresent("You have been logged out successfully."));
    }

    @Test
    public void shouldPerformLoginToWelcomePageAndLogout() {
        loginAs("username", "password");
        selenium.open("/welcome");
        selenium.waitForPageToLoad("3000");
        assertTrue(selenium.isTextPresent("Welcome username | Logout"));
        logout();
        assertTrue(selenium.isTextPresent("You have been logged out successfully."));
    }

    @Test
    public void shouldRejectLoggingWithEmptyForm() {
        loginAs("", "");
        assertTrue(selenium.isTextPresent("Username and password are required"));
    }

    @Test
    public void shouldRemindThatUsernameIsRequired() {
        loginAs("", "password");
        assertTrue(selenium.isTextPresent("Username is required"));
    }

    @Test
    public void shouldRemindThatPasswordIsRequired() {
        loginAs("username", "");
        assertTrue(selenium.isTextPresent("Password is required"));
    }

    @Test
    public void shouldHaveCheckboxSelected() {
        selenium.open("/login");
        selenium.waitForPageToLoad("3000");
        assertTrue(selenium.isTextPresent("Log in"));
        selenium.click("name=remember-me");
        assertTrue(selenium.isChecked("name=remember-me"));
    }

    @Test
    public void shouldPreventUnauthenticatedUserFromAccessingWelcomePage() {
        selenium.open("/welcome");
        assertTrue(selenium.isTextPresent("Log in"));
        assertTrue(selenium.getTitle().contains("Log in"));
    }

    @Test
    public void shouldPreventUnauthenticatedUserFromAccessingAdminPage() {
        selenium.open("/admin");
        assertTrue(selenium.isTextPresent("Log in"));
        assertTrue(selenium.getTitle().contains("Log in"));
    }

    @Test
    public void shouldPreventUserFromAccessingAdminPage() {
        loginAs("username", "password");
        selenium.open("/admin");
        assertTrue(selenium.isTextPresent("Access is denied"));
    }

    @After
    public void tearDown() throws Exception {
        selenium.stop();
    }

    private void loginAs(String username, String password) {
        selenium.open("/login");
        selenium.waitForPageToLoad("3000");
        selenium.type("username", username);
        selenium.type("password", password);
        selenium.click("name=login");
    }

    private void logout() {
        selenium.click("link=Logout");
    }

}
