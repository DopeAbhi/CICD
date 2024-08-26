package Test;

import PageObject.*;
import TestComponents.BaseTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class SubmitOrderTest extends BaseTest {
    ;
@Test(dataProvider="getData",groups = {"Purchase"})
    public void submitOrder(HashMap<String, String> input) throws IOException {


        //  WebDriverManager.chromedriver().setup();  //We don't need this from selenium 4  //This downloads the driver for chrome while running

        //Browser Invoke and Login into the Application is in the Base test

        ProdutCatalouge produtCatalouge = page.loginApplication(input.get("email"), input.get("password")); //here is page object is getting access from parent class

        //Adding Item in Cart

//        List<WebElement> products = produtCatalouge.getProducts();
         produtCatalouge.addProductToCart(input.get("productName"));
        //   produtCatalouge.addProductToCart(productName2);


        //Cart Page Handling

    CartPage cartPage = produtCatalouge.cartNavigation(); //Method is Declared inside the AbstractComponent
        Boolean match = cartPage.verifyCartItem(input.get("productName"));
        Assert.assertTrue(match);


        //Payment Page Handling
        PaymentPage paymentPage = cartPage.cartCheckout();
        paymentPage.selectCountry("India");

        //Confirmation Page
        ConfirmationPage confirmationPage = paymentPage.orderPlace();

        String confirmMessage = confirmationPage.getConfirmationMessage();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase("Thankyou for the order."));

        //Browser Close is also defined inside the BaseTest Class
    }


    @Test(dependsOnMethods = {"submitOrder"}, dataProvider = "getData")
    public void OrderHistoryTest(HashMap<String, String> input)
    {

        ProdutCatalouge produtCatalouge = page.loginApplication(input.get("email"), input.get("password"));
       OrderPage orderPage= produtCatalouge.ordersNavigation();
      Boolean orderStatus= orderPage.getOrders(input.get("productName"));
        Assert.assertTrue(orderStatus);

    }
    @DataProvider
    public Object[][] getData() throws IOException {

        //Hash Map Method to Send Data from Data Provider
//        HashMap<String,String> map=new HashMap<String, String>();
//        map.put("email", "AbhayVerma@yopmail.com");
//        map.put("password", "Test@123");
//        map.put("productName","ZARA COAT 3");
//
//        HashMap<String,String> map1=new HashMap<String, String>();
//        map1.put("email", "AbhayVerma1@yopmail.com");
//        map1.put("password", "Test@123");
//        map1.put("productName","ADIDAS ORIGINAL");
     //   return new Object[][] {{map},{map1}};

       //Json to HashMap method
        List <HashMap<String,String>>data= getJsonDataToMap("/src/test/java/Data/PurchaseOrder.json");

        return new Object[][] {{data.get(0)},{data.get(1)}};

// Normal Method to Send Data from Data Provider
//        return new Object[][] {{"AbhayVerma1@yopmail.com\",\"Test@123\",\"ADIDAS ORIGINAL"},{"AbhayVerma1@yopmail.com","Test@123","ADIDAS ORIGINAL"}};

    }


}
