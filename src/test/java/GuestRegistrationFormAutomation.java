import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GuestRegistrationFormAutomation {

    WebDriver driver;
    Faker faker;

    @BeforeAll
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        faker = new Faker();
    }

    @Test
    public void regis() {

        //visit url
        driver.get("https://demo.wpeverest.com/user-registration/guest-registration-form/");

        //user first name using faker
        driver.findElement(By.id("first_name")).sendKeys(faker.name().firstName());

        //user last name using faker
        driver.findElement(By.id("last_name")).sendKeys(faker.name().lastName());

        //user email using faker
        driver.findElement(By.id("user_email")).sendKeys((CharSequence) faker.internet().emailAddress());

        //select user gender
        List<WebElement> gender = driver.findElements(By.className("radio"));
        gender.get(0).click();

        //user password using faker
        driver.findElement(By.id("user_pass")).sendKeys(faker.internet().password());

        //user date of birth
        List<WebElement> dob = driver.findElements(By.className("ur-flatpickr-field"));
        dob.get(0).click();
        Select month = new Select(driver.findElement(By.className("flatpickr-monthDropdown-months")));
        month.selectByVisibleText("February");
        List<WebElement> dobYear = driver.findElements(By.className("numInput"));
        dobYear.get(0).click();
        dobYear.get(0).sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        dobYear.get(0).sendKeys("1999");
        driver.findElement(By.xpath("//span[@aria-label='February 14, 1999']")).click();

        //user country
        Select nationality = new Select(driver.findElement(By.id("country_1665629257")));
        nationality.selectByValue("BD");

        //user nationality
        driver.findElement(By.id("input_box_1665629217")).sendKeys("Bangladeshi");

        //user phone number using faker
        driver.findElement(By.xpath("//input[@id='phone_1665627880']")).sendKeys(faker.phoneNumber().phoneNumber());

        //scroll page
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1500)");

        //check terms and co
        driver.findElement(By.id("privacy_policy_1665633140")).click();

        //select submit btn
        driver.findElement(By.className("ur-submit-button")).click();

        //check successful registration
        String expectedMsg = "User successfully registered.";
        String actualMsg = driver.findElement(By.className("user-registration-message")).getText();
        Assertions.assertTrue(actualMsg.equals(expectedMsg));

    }

    @AfterAll
    public void close() {
        driver.quit();
    }


}
