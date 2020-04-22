import java.io.*;
import java.util.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class chrome_testing {
    private static final String IMG_URL = "https://pic4.zhimg.com/80/v2-71025dfd39488a4aaed9feb11eb9650b_hd.jpg";
    private static final String TEST_URL = "https://graph.baidu.com/pcpage/index?tpl_from=pc";
    private static final String FILE = "./search_configuration";
    public static void main(String[] args) {
        String visit = "";
        // creating java io and reading in visit result location
        try (FileReader reader = new FileReader(FILE);
             BufferedReader buffer = new BufferedReader(reader)){
            String line;

            while ((line = buffer.readLine()) != null) {
                visit = line;
            }
        }catch (IOException e) {
            e.printStackTrace();
            System.out.println("Intelij is using different directory");
        }

        System.out.println(visit);

        int loc = 1;

        // creating webdriver
        WebDriver driver = new ChromeDriver();
        // opening url
        driver.get(TEST_URL);
        driver.manage().window().maximize();
        System.out.println(driver.getTitle());
        driver.navigate().refresh();

        // searching elements
        WebElement textBox = driver.findElement(By.className("graph-search-left-input"));
        WebElement searchButton = driver.findElement(By.className("graph-search-center"));

        // performing actions
        textBox.sendKeys(IMG_URL);
        searchButton.click();

        // waiting for new page loading
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // result page
        //WebElement results = driver.findElement(By.className("graph-same-list-item"));

        List<WebElement> list = driver.findElements(By.className("graph-same-list-item"));

        System.out.println(list.isEmpty());
        list.get(loc).click();

        // waiting for new page loading
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // capturing final webpage
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get((1)));
        ((JavascriptExecutor) driver).executeScript("scrollTo(0,4000)");
        // waiting for new page loading
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // taking screenshot
        TakesScreenshot screenshot = (TakesScreenshot) driver;

        // writing to jpg file
        File temp = null;
        File finalFile = null;

        temp = screenshot.getScreenshotAs(OutputType.FILE);
        finalFile = new File("./" + driver.getTitle() + ".jpg");
        try {
            FileUtils.copyFile(temp,finalFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        driver.quit();
    }

}
