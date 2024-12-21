package pages.wiki;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
//import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import seleniumCore.CorePage;

public class HomePage extends CorePage {

    // Define locators for Android and iOS
    @FindBy(xpath = "//android.widget.FrameLayout[@content-desc=\"My lists\"]")
//    @iOSFindBy(accessibility = "")
    private WebElement buttonMyLists;

    @FindBy(xpath = "//android.widget.FrameLayout[@content-desc='History']")
//    @iOSFindBy(accessibility = "")
    private WebElement buttonHistory;

    @FindBy(xpath = "//android.widget.FrameLayout[@content-desc='Nearby']")
//    @iOSFindBy(accessibility = "")
    private WebElement buttonNearBy;

    @FindBy(xpath = "//android.widget.FrameLayout[@content-desc='Explore']")
//    @iOSFindBy(accessibility = "")
    private WebElement buttonExplore;


    @FindBy(xpath = "//android.view.View[@resource-id=\"org.wikipedia.alpha:id/fragment_feed_header\"]")
    private WebElement buttonSearch;

    @FindBy(xpath = "//android.widget.AutoCompleteTextView[@resource-id=\"org.wikipedia.alpha:id/search_src_text\"]")
    private WebElement inputSearch;

    @FindBy(xpath = "//android.widget.ImageView[@content-desc=\"Clear query\"]")
    private WebElement buttonCloseSearch;

    public void openMyLists() {
        clickOn(buttonMyLists);
        sleep(3);
    }

    public void openHistory() {
        clickOn(buttonHistory);
        sleep(3);
    }

    public void openNearBy() {
        clickOn(buttonNearBy);
        sleep(3);
    }

    public void openExplore() {
        clickOn(buttonExplore);
        sleep(3);
    }
}

