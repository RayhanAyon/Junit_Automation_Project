import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WebformAutomation {

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
    @DisplayName("Check if the WebForm is successfully submitted or not")
    public void regis() throws InterruptedException {

        //vsit url
        driver.get("https://www.digitalunite.com/practice-webform-learners");

        //allow cookies
        driver.findElement(By.id("onetrust-accept-btn-handler")).click();

        //select txt field using css class
        List<WebElement> inpFiled = driver.findElements(By.className("form-control"));

        //user full name using faker
        inpFiled.get(0).sendKeys(faker.name().fullName());

        //user phone number
        inpFiled.get(1).sendKeys("01334583854");

        //date select
        inpFiled.get(2).sendKeys("04/16/2025");

        //user mail using faker
        inpFiled.get(3).sendKeys(faker.internet().emailAddress());

        //scroll page
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");

        //about yourself using faker
        inpFiled.get(4).sendKeys(faker.lorem().paragraph());

        //upload photo
        driver.findElement(By.id("edit-uploadocument-upload")).sendKeys("D:\\JAVA Code\\JUnit_Automation_Project\\src\\test\\resources\\pic.jpg");

        //wait for uploading
        Thread.sleep(3000);

        //check terms and co
        List<WebElement> checkbtn = driver.findElements(By.className("form-required"));
        checkbtn.get(1).click();

        //click submuit btn
        driver.findElement(By.id("edit-submit")).click();

        //check for submission
        String expText = "Thank you for your submission!";
        String actText = driver.findElement(By.id("block-pagetitle-2")).getText();
        Assertions.assertTrue(actText.equals(expText));
    }

    @AfterAll
    public void close() {
        driver.quit();
    }
}

