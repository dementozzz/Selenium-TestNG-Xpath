import java.math.BigDecimal;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class buyitem {
    
    WebDriver driver = new ChromeDriver(); // Set the browser = Chrome
    String url = "https://www.saucedemo.com"; 
    BigDecimal totalPriceItems;
    BigDecimal bd;

    @BeforeTest
    public void openWebsite(){
        // open Chrome browser and open the URL
        driver.get(url);
    }

    @Test(priority = 1)
    public void fillUsernameAndPassword() throws InterruptedException{
        //Fill the username & password inputfield
        driver.findElement(By.xpath("//input[@id='user-name']")).sendKeys("standard_user");
        Thread.sleep(1000); 
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("secret_sauce");
        Thread.sleep(1000); 
    }

    @Test(priority = 2)
    public void tryLogin(){
        //Navigate to Inventory page by click the login button
        driver.findElement(By.xpath("//input[@id='login-button']")).click();
    }

    

    @Test(priority = 3)
    public void addItemsToCart() throws InterruptedException{
        //Get all items by retrieve all of the elements
        List<WebElement> listItem = driver.findElements(By.xpath("//div[@class='inventory_item']"));  

        //Since the item contain more than one, we have to do something on every items
        for(int i = 0; i < listItem.size(); i++){

            //And I only want select first 3 items, no action required for the rest of the item after 3rd
            if(i < 3){

              WebElement btnelement = driver.findElements(By.xpath("//div[@class='pricebar']//child::button")).get(i);
              
              String btnTxt = btnelement.getText();
              
              if (btnTxt.equals("Add to cart")){ //If button text equal 'Add to cart', click the button. Otherwise no action is required
                btnelement.click();
              }
              Thread.sleep(1000);
              
            }    
          }

    }

    @Test(priority = 4)
    public void navigateToCartPage() throws InterruptedException{
        //Navigate to Cart Page by click the shopping cart icon
        driver.findElement(By.xpath("//a[@class='shopping_cart_link']")).click();
        Thread.sleep(1000);
    }

    @Test(priority = 5)
    public void navigateToCheckoutPage1() throws InterruptedException{

        boolean isItemPresent = driver.findElement(By.xpath("//div[@class='cart_item']")).isDisplayed(); //Return boolean status ('TRUE'/'FALSE') if there is an item in my cart (or not)
        List<WebElement> cartItemName;
        List<WebElement> cartItemPrice;

        if(isItemPresent){// If there's an item in my cart ('TRUE')
            
          totalPriceItems = new BigDecimal("0");

          //Get the elements for every items in my cart
          cartItemPrice = driver.findElements(By.xpath("//div[@class='cart_item']//child::div[@class='inventory_item_price']"));
          cartItemName = driver.findElements(By.xpath("//div[@class='cart_item']//child::div[@class='inventory_item_name']"));

          System.out.println("Qty item on my cart : " + cartItemName.size());

          //Since the item contain more than one, we have to do something on every items
          for(int i = 0; i < cartItemName.size(); i++){

            //Getting the item and price
            String getText = cartItemName.get(i).getText();
            String getPrice = cartItemPrice.get(i).getText().substring(1);

            System.out.println( "Name : " + getText + " | Price = " + getPrice);

            bd = new BigDecimal(getPrice); //Since the price are on 'String' type, we have to convert it into decimal, so that we can use it to sum the price
            totalPriceItems = totalPriceItems.add(bd); //Sum the price for every item in my cart
          }

          System.out.println("Total Item Price = " + totalPriceItems);

          //Navigate to Checkout: Information page
          driver.findElement(By.xpath("//div[@class='cart_footer']//child::button[2]")).click();
          Thread.sleep(1000);
                
        }else{// if my cart is empty ('FALSE')

          // leave message on console and terminate it
          System.out.println("CART IS EMPTY, END THE AUTOMATION");
          driver.close();
        }

    }

    @Test(priority = 6)
    public void fillInformationDelivery() throws InterruptedException{
      //input data for delivery
      driver.findElement(By.xpath("//input[@id='first-name']")).sendKeys("Joe");
      Thread.sleep(1000); 
      driver.findElement(By.xpath("//input[@id='last-name']")).sendKeys("Mandrake"); 
      Thread.sleep(1000); 
      driver.findElement(By.xpath("//input[@id='postal-code']")).sendKeys("60221");
      Thread.sleep(1000); 
    }

    @Test(priority = 7)
    public void navigateToCheckoutPage2() throws InterruptedException{
      //Navigate to Checkout: Summary page
      driver.findElement(By.xpath("//input[@id='continue']")).click();
      Thread.sleep(1000); 
    }

    @Test(priority = 8)
    public void beginCheckout(){
      //Getting tax and total price
      String getTaxString = driver.findElement(By.xpath("//div[@class='summary_tax_label']")).getText().substring(6);
      String getTotalPriceString = driver.findElement(By.xpath("//div[@class='summary_info_label summary_total_label']")).getText().substring(8);

      System.out.println("Tax = " + getTaxString);
      System.out.println("Total Price = " + getTotalPriceString);

      // Begin checkout item and Navigate to Checkout complete page
      driver.findElement(By.xpath("//button[@id='finish']")).click();
    }
}
