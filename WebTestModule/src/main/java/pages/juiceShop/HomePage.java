package pages.juiceShop;

import logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import seleniumCore.CorePage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomePage extends CorePage {

    @FindBy(css = "button.close-dialog")
    private WebElement buttonCloseWelcomeDialog;

    @FindBy(css = ".cc-btn")
    private WebElement buttonAcceptCookies;

    @FindBy(css = "#mat-select-0")
    private WebElement dropDownItemsPerPage;

    @FindBy(css = "span.mat-option-text")
    private List<WebElement> optionsItemsPerPage;

    @FindBy(css = "div.mat-paginator-range-label")
    private WebElement textPaginatorRange;

    @FindBy(css = "div.product")
    private List<WebElement> listItems;

    @FindBy(css = "img.mat-card-image")
    private List<WebElement> listItemImages;

    @FindBy(css = "app-product-details")
    private WebElement cardProductDetail;

    @FindBy(css = "mat-panel-title>span:nth-child(2)")
    private WebElement textNumberOfReviews;

    @FindBy(css = "app-product-details h1")
    private WebElement textItemDetailName;

    @FindBy(css = "app-product-details img")
    private WebElement imageItemDetail;


    @FindBy(css = "button.close-dialog")
    private WebElement buttonCloseItemDetailDialogue;

    @FindBy(css = "button#navbarAccount")
    private WebElement buttonAccount;

    @FindBy(css = "button#navbarLoginButton")
    private WebElement buttonLogin;

    @FindBy(css = "button[routerlink='/basket']")
    private WebElement buttonYourBasket;


    @FindBy(css = "button[routerlink='/basket']>span>span.fa-layers-counter")
    private WebElement textCartNotification;

    @FindBy(css = "button.mat-menu-item>span")
    private List<WebElement> optionsAccountMenuItems;

    @FindBy(css = "simple-snack-bar>span")
    private WebElement textProductAddedToCart;

    private WebElement getButtonBasketForProduct(String product) {
        return find(By.xpath("//div[contains(text(),'" + product + "')]/../../..//button"));
    }


    protected void navigateToJuiceShop() {
        Log.info("Navigating to Juice Shop");
        openURL("https://juice-shop.herokuapp.com/");
    }

    protected void navigateToLoginPage() {
        Log.info("Navigating to Login Page");
        clickOn(buttonAccount);
        clickOn(buttonLogin);
    }

    protected void closeWelcomeDialogue() {
        Log.info("closing the welcome dialogues");
        waitTillVisible(buttonCloseWelcomeDialog);
        clickOn(buttonCloseWelcomeDialog);
        clickOn(buttonAcceptCookies);
    }

    protected void selectMaxItemsPerPage() {
        Log.info("Selecting the maximum items per page");
        jsScrollToElement(dropDownItemsPerPage);
        clickOn(dropDownItemsPerPage);
        List<Integer> numbers = new ArrayList<>();
        for (WebElement element : optionsItemsPerPage) {
            numbers.add(Integer.parseInt(element.getText().trim()));
        }
        int max = Collections.max(numbers);
        for (WebElement element : optionsItemsPerPage) {
            if (Integer.parseInt(element.getText().trim()) == max)
                clickOn(element);
        }
    }

    private int getNumberOfItems() {
        String range = textPaginatorRange.getText().trim().replace(" ", "").split("of")[1];
        return Integer.parseInt(range);
    }

    protected void selectFirstItem() {
        clickOn(listItems.get(0));
    }

    protected void verifyAllItemsDisplayed() {
        Log.info("verifying all the items are visible");
        int numberOfItemsOnThePage = listItems.size();
        Assert.assertEquals(numberOfItemsOnThePage, getNumberOfItems(), "The number of items on the page range is not equal to the number of items on the page");
        for (WebElement item : listItems) {
            Assert.assertTrue(item.isDisplayed(), "The Item is not displayed");
        }
    }

    protected void verifyProductDetails(String productName) {
        Log.info("Verifying Product Details");
        Assert.assertTrue(cardProductDetail.isDisplayed(), "The product details card is not displayed");
        Assert.assertTrue(textItemDetailName.getText().contains(productName), "The product name is not as expected.");
        Assert.assertTrue(imageItemDetail.isDisplayed(), "The Item image is not displayed.");
    }

    protected void expandReview() {
        Log.info("Expanding Review");
        if (getNumberOfReviews() > 0) {
            clickOn(textNumberOfReviews);
        }
    }

    protected void closeProductDetails() {
        Log.info("Closing  product details dialogue ");
        waitTillVisible(buttonCloseItemDetailDialogue);
        clickOn(buttonCloseItemDetailDialogue);
    }

    private int getNumberOfReviews() {
        sleep(2);
        String no = textNumberOfReviews.getText().replaceAll("[^0-9]", "");
        return Integer.parseInt(no);
    }

    public void verifyLoggedInUser(String email) {
        clickOn(buttonAccount);
        sleep(5);
        waitTillVisible(optionsAccountMenuItems.get(0));
        Assert.assertEquals(optionsAccountMenuItems.get(0).getText().trim(), email, "The logged in user email address is wrong.");

    }

    protected void addProductsToBasket(String... products) {
        Log.info("Add Products to basket");
        for (String product : products) {
            clickOn(getButtonBasketForProduct(product));
            sleep(2);
            waitTillVisible(textProductAddedToCart);
            Assert.assertTrue(textProductAddedToCart.getText().contains(product), "The notification is not displayed for product added to the cart");
        }

    }

    protected void navigateToCart() {
        Log.info("Navigate to Basket Page");
        jsScrollToElement(buttonYourBasket);
        clickOn(buttonYourBasket);
        sleep(5);
    }

    protected void verifyCartNotification(int noOfItems) {
        Assert.assertEquals(Integer.parseInt(textCartNotification.getText()), noOfItems, "The cart does not show number of items added");
    }


}
