package PageObject;

import AbstractComponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage extends AbstractComponent {

    WebDriver driver;
    public LandingPage(WebDriver driver)
    {
        super(driver);
        //Initializing Class Instance Driver Variable with the Local Variable
        this.driver = driver;
        PageFactory.initElements(driver,this);  //This method is responsible for converting @findBy format into Normal Format

    }
//     driver.findElement(By.id("userEmail")).sendKeys("AbhayVerma@yopmail.com");
//        driver.findElement(By.id("userPassword")).sendKeys("Test@123");
//        driver.findElement(By.id("login")).click();

    //PageFactory Pattern

    @FindBy(id="userEmail")
    WebElement userEmail;

    @FindBy(id="userPassword")
    WebElement userPassword;
    @FindBy(id="login")
    WebElement submit;

    @FindBy(css = "[class*='flyInOut']")
    WebElement errorMessage;

    public ProdutCatalouge loginApplication(String email, String password)
    {
        userEmail.sendKeys(email);
        userPassword.sendKeys(password);
        submit.click();
        return  new ProdutCatalouge(driver);

    }

    public void goTo()
    {
        driver.get("https://rahulshettyacademy.com/client");
    }
     public String getErrorMessage()
     {
         WaitForWebElement(errorMessage);
         return errorMessage.getText();
     }



}

