import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ScrapTableData {
    WebDriver driver;

    @BeforeAll
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @Test
    public void scrapData() throws IOException {
        driver.get("https://dsebd.org/latest_share_price_scroll_by_value.php");
        List<WebElement> table=driver.findElements(By.className("table-responsive"));
        List<WebElement> allRows = table.get(0).findElements(By.tagName("tr"));

        FileWriter writer = new FileWriter("D:\\JAVA Code\\JUnit_Automation_Project\\src\\test\\resources\\dse_data.txt");

        for(WebElement row:allRows){
            List<WebElement> cells=row.findElements(By.tagName("td"));
            for(WebElement cell:cells){
                writer.write(cell.getText() + "\t");
            }
            writer.write("\n");
        }
        writer.flush();
        writer.close(); // Close file
    }

    @AfterAll
    public void close(){
        driver.quit();
    }

}
