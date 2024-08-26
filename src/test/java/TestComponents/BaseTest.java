package TestComponents;

import PageObject.LandingPage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class BaseTest {
   public WebDriver driver;
   public  LandingPage page;
    public WebDriver intializeDriver() throws IOException {

        //Setting Global Properties

        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/src/main/java/Resources/GlobalData.properties");
        properties.load(fis);

        //This code decide from where you want your browser data
        //System.getProperty(""); take variable input from terminal as well

      String browser=  System.getProperty("browser")!=null ? System.getProperty("browser"): properties.getProperty("browser");


        //Logic to Select Browser
   //     String browser = properties.getProperty("browser");
        if (browser.contains("Chrome")) {  //To Run in Headless Mode
            ChromeOptions options = new ChromeOptions();
            if (browser.contains("headless")) {
                options.addArguments("headless");
            }
            driver = new ChromeDriver(options);
            driver.manage().window().setSize(new Dimension(1400,900));

                   }

        else if (browser.equalsIgnoreCase("firefox"))
        {
             driver = new FirefoxDriver();
                   }
        else if (browser.equalsIgnoreCase("edge"))
        {
         driver = new EdgeDriver();
                 }

        else if (browser.equalsIgnoreCase("safari"))
        {
             driver = new SafariDriver();

        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        return driver;



    }

    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {

       TakesScreenshot ts= (TakesScreenshot)driver;
        File screenShot= ts.getScreenshotAs(OutputType.FILE);
        String dest=System.getProperty("user.dir")+"/reports/"+testCaseName+".png";
        File file=new File(dest);
        FileUtils.copyFile(file,screenShot);
        return dest;
    }



//Converting Json to Hash Map
    public List<HashMap<String, String>> getJsonDataToMap(String FilePath) throws IOException {
        //This is inside the common.io dependency
        //UTF 8 is Standard to Convert JSON file to String
        String jsonContent=    FileUtils.readFileToString(new File(System.getProperty("user.dir")+FilePath),
                StandardCharsets.UTF_8);

        //To Convert String into HashMap Jackson DataBind Dependency is needed
        ObjectMapper mapper = new ObjectMapper();
        List<HashMap<String,String>> data=  mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>() {
        });
        return data;


    }

    @BeforeMethod (alwaysRun = true) //This method send driver information to the page object classes
                                    //This is used to avoid while running for specific groups
    public  LandingPage launchApplication() throws IOException {
        driver=intializeDriver();
        page = new LandingPage(driver);
        page.goTo();
        return page;

    }

    @AfterMethod (alwaysRun = true)   //This is used to avoid while running for specific groups
    public void tearDown()
    {
        driver.quit();
    }


}
