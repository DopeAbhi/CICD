package PageObject;

import AbstractComponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage extends AbstractComponent {
    WebDriver driver;

    public CartPage(WebDriver driver) {
        super(driver);
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//div[@class='cartSection']/h3")
    List<WebElement> cartItem;


    @FindBy(css="[class='totalRow'] button")
    WebElement CheckoutButton;


    public Boolean verifyCartItem(String productName)
    {
        Boolean match=  cartItem.stream().anyMatch(product -> product.getText().equals(productName));
        return match;
    }

    public PaymentPage cartCheckout()
    {
        CheckoutButton.click();
        return new PaymentPage(driver);

    }


}
