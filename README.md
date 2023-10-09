# Selenium-TestNG-Xpath
Doing some scenario with fully using Xpath for finding elements. The scenario will be divided into parts below.

- Login
- Select 3 of 6 item to be added on shopping cart
- Check if there are item on shopping cart, if there is an item, then proceed into "Checkout: Delivery information" page
- Fill the First & Last name, ZIP code then proceed to "Checkout: Summary" page
- Checkout the item and expected to navigate to "Purchase Successful" page


## How to run the automation?

Simply click on green checkmark/play icon on the left.

![run_automation](https://github.com/dementozzz/Selenium-TestNG-Xpath/assets/20464988/fac9d8a4-d048-464c-a668-49169be963d3)

Or, you can run the command on the "TERMINAL" using the command below.

```
mvn test -Dtest=”{YourTestClassName}”
```



