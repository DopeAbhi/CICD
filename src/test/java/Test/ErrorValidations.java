package Test;

import PageObject.CartPage;
import PageObject.ConfirmationPage;
import PageObject.PaymentPage;
import PageObject.ProdutCatalouge;
import TestComponents.BaseTest;
import TestComponents.Retry;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class ErrorValidations extends BaseTest {

@Test(groups = "ErrorHandling", retryAnalyzer = Retry.class)// for rerun failed test cases
    public void submitOrder() throws IOException {

    //Browser Invoke and Login into the Application is in the Base test

    page.loginApplication("AbhayVerma@yopmail.com", "Test"); //here is page object is getting access from parent class
    Assert.assertEquals(page.getErrorMessage(),"Incorrect eemail or password.");

    //#toast-container
   // div[aria-label='Incorrect email or password.']

        //    .ng-tns-c4-11.ng-star-inserted.ng-trigger.ng-trigger-flyInOut.ngx-toastr.toast-error

   // System.out.println(driver.findElement(By.className("ng-trigger-flyInOut.ngx-toastr")).getText());

        //Browser Close is also defined inside the BaseTest Class
    }

    @Test
    public void addtoCartcheck()
    {
        String productName = "ADIDAS ORIGINAL";
        String productName2 = "ZARA COAT 3";
        //  WebDriverManager.chromedriver().setup();  //We don't need this from selenium 4  //This downloads the driver for chrome while running

        //Browser Invoke and Login into the Application is in the Base test

        ProdutCatalouge produtCatalouge = page.loginApplication("AbhayVerma@yopmail.com", "Test@123"); //here is page object is getting access from parent class

        //Adding Item in Cart

//        List<WebElement> products = produtCatalouge.getProducts();
        produtCatalouge.addProductToCart(productName);
        //   produtCatalouge.addProductToCart(productName2);


        //Cart Page Handling

        CartPage cartPage =   produtCatalouge.cartNavigation(); //Method is Declared inside the AbstractComponent
        Boolean match = cartPage.verifyCartItem(productName);
        Assert.assertTrue(match);
    }
}
