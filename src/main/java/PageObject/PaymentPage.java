package PageObject;

import AbstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PaymentPage extends AbstractComponent {
    WebDriver driver;
    public PaymentPage(WebDriver driver) {
        super(driver);
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }


   //  driver.findElement(By.cssSelector("[class='totalRow'] button")).click();


    @FindBy(xpath = "//input[@placeholder='Select Country']")
    WebElement autoSuggestiveDropdown;

    @FindBy(xpath = "(//button[contains(@class,'ta-item')]) [2]")
    WebElement optionSelection;

    @FindBy(css = ".action__submit")
    WebElement completePayment;

    By result=By.cssSelector(".ta-results");



    public void selectCountry(String countryName)
    {

        Actions a = new Actions(driver);
        a.sendKeys( autoSuggestiveDropdown,countryName).build().perform();
        WaitForLocator(result);
        optionSelection.click();
    }

    public ConfirmationPage orderPlace()
    {
        completePayment.click();
        return new ConfirmationPage(driver);
    }



}
