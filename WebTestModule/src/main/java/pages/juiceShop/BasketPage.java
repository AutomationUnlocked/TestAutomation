package pages.juiceShop;

import logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class BasketPage extends LoginPage {


    @FindBy(css = "button#checkoutButton")
    private WebElement buttonCheckout;

    @FindBy(css = "div#price")
    private WebElement textTotalPrice;

    private WebElement buttonDecreaseQuantity(String productName) {
        return find(By.xpath("(//mat-cell[contains(text(),'" + productName + "')]/..//button)[1]"));
    }

    private WebElement buttonIncreaseQuantity(String productName) {
        return find(By.xpath("(//mat-cell[contains(text(),'" + productName + "')]/..//button)[2]"));
    }

    private WebElement buttonDeleteProduct(String productName) {
        return find(By.xpath("(//mat-cell[contains(text(),'" + productName + "')]/..//button)[3]"));
    }

    protected void increaseQuantity(String productName, int increaseBy) {
        Log.info("increase quantity");
        for (int i = 0; i < increaseBy; i++) {
            clickOn(buttonIncreaseQuantity(productName));
            sleep(2);
        }
    }

    protected void deleteProduct(String productName) {
        Log.info("Delete product");
        clickOn(buttonDeleteProduct(productName));
        sleep(2);
    }

    protected double getTotalPrice() {
        String text = textTotalPrice.getText().replaceAll("[^\\d.]", "");
        double val = Double.parseDouble(text);
        System.out.println("Val is " + val);
        return val;

    }

    protected void checkout() {
        Log.info("Checkout");
        clickOn(buttonCheckout);
    }


}
