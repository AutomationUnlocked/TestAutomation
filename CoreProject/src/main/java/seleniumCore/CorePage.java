package seleniumCore;

import enums.CompareMode;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import logging.Log;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkNotNull;

public class CorePage extends CoreTest {

    @BeforeMethod(alwaysRun = true)
    public void beforeCorePage() {
        PageFactory.initElements(getDriver(), this);

    }

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sleepByMilliSeconds(int milli) {
        try {
            Thread.sleep(milli);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static void openURL(String url) {
        getDriver().navigate().to(url);
    }

    protected static boolean clickOn(WebElement element) {
        boolean isClickSuccessful = true;
        try {
            element.click();
        } catch (Exception e) {
            isClickSuccessful = false;
            e.printStackTrace();
            Assert.fail("Unable to click on element " + element + " due to " + e.getMessage());
        }
        return isClickSuccessful;

    }

    protected static void clickOnException(WebElement firstElement, WebElement secondWebElement) {
        try {
            firstElement.click();
        } catch (Exception e) {
            clickOn(secondWebElement);
        }
    }

    protected static void clickAndWaitFor(WebElement elementToBeClicked, WebElement elementToBeWaited) {
        elementToBeClicked.click();
        waitTillVisible(elementToBeWaited);
    }

    protected void clickOnInterceptedElement(WebElement element, int withInSeconds) {
        while (withInSeconds > 0) {
            if (clickOn(element)) {
                break;
            } else {
                logger.info("Element is still intercepted");
                sleep(1);
            }
            withInSeconds--;
        }


    }

    protected static void sendKeys(WebElement element, String text, Keys... keys) {
        element.clear();
        element.sendKeys(text);
        for (Keys key : keys) {
            element.sendKeys(key);
        }
    }

    protected static void sendKeysSlowly(WebElement element, String text, Keys... keys) {
        element.clear();
        slowTyping(element, text);
        for (Keys key : keys) {
            element.sendKeys(key);
        }
    }

    public static boolean HandleStaleElement(WebElement element) {

        boolean result = false;
        int attempts = 0;
        while (attempts < 2) {
            try {
                element.isDisplayed();
                result = true;
                break;
            } catch (StaleElementReferenceException ignored) {
            }
            attempts++;
        }
        return result;
    }

    public static void doubleClickOnElement(WebElement element) {

        Actions action = new Actions(getDriver());
        action.doubleClick(element);
    }

    public static void waitTillClickable(WebElement element, int seconds) {
        waitForClickable(seconds).until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitTillClickable(WebElement element) {
        waitForClickable(2).until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void handleAlert() {

        Alert alert = getDriver().switchTo().alert();
        alert.accept();
    }

    public static void jsClickOnElement(WebElement element) {

        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();",
                element);
    }

    public static String jsGetInnerText() {

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        return js
                .executeScript("return document.documentElement.innerText;")
                .toString();
    }

    public String jsGetTitle() {

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        return js.executeScript("return document.title;").toString();
    }

    public void jsCreatePopUp() {

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("alert('hello world');");
    }

    public void jsRefresh() {

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("history.go(0)");
    }

    public void jsScrollDown(int pixels) {

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("scroll(0, " + String.valueOf(pixels) + ");");
    }

    public void jsScrollUp(int pixels) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("scroll(0, -" + String.valueOf(pixels) + ");");

    }

    public void jsScrollToElement(WebElement element) {

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void jsScrollElementIntoMiddle(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                + "var elementTop = arguments[0].getBoundingClientRect().top;"
                + "window.scrollBy(0, elementTop-(viewPortHeight/2));";
        js.executeScript(scrollElementIntoMiddle, element);
    }


    public void jsClickOn(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        String scriptClick = "arguments[0].click();";
        js.executeScript(scriptClick, element);
    }

    public void scrollTillViewPort() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        String scrolltoView = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                + "window.scrollBy(0, (viewPortHeight));";
        sleep(1);
        js.executeScript(scrolltoView);

    }

    public void scrollToButtonOnApp() {
        getDriver().findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollToEnd(10);"));
    }


    public void scrollToTopOnApp() {
        getDriver().findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollToBeginning(10);"));
    }


    public void jsScrollTillBottom() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        trackAjaxCount();
        long hightBeforeScroll;
        long hightAfterScroll;
        do {
            hightBeforeScroll = (long) js.executeScript("return document.body.scrollHeight");
            jsScrollDown(200);
            sleep(2);
            hightAfterScroll = (long) js.executeScript("return document.body.scrollHeight");
        } while (hightBeforeScroll < hightAfterScroll);
    }

    public <T, R> R fluentWait(Function<T, R> function, T argument, int timeOutSeconds, int pollingTimeSeconds, String timeoutMessage, Class<? extends Throwable>... exceptions) {
        checkNotNull(function, "Function shouldn't be null");
        checkNotNull(argument, "Argument shouldn't be null");
        List<Class<? extends Throwable>> exceptionToIgnore = new ArrayList<>();
        if (exceptions != null) {
            exceptionToIgnore.addAll(Arrays.asList(exceptions));
        }
        // To avoid to use deprecated method, changed arguments
        return new FluentWait<>(argument)
                .withTimeout(Duration.of(timeOutSeconds, ChronoUnit.SECONDS))
                .pollingEvery(Duration.of(pollingTimeSeconds, ChronoUnit.SECONDS))
                .ignoreAll(exceptionToIgnore)
                .withMessage(timeoutMessage)
                .until(function);
    }

    public void trackAjaxCount() {
        JavascriptExecutor jes = (JavascriptExecutor) getDriver();
        String js = "(function(xhr) {\n" +
                "    xhr.ajaxcount = 0;\n" +
                "    var pt = xhr.prototype;\n" +
                "    var _send = pt.send;\n" +
                "    pt.send = function() {\n" +
                "        xhr.ajaxcount++;\n" +
                "        this.addEventListener('readystatechange', function(e) {\n" +
                "            if ( this.readyState == 4 ) {\n" +
                "                setTimeout(function() {\n" +
                "                    xhr.ajaxcount--;\n" +
                "                }, 1);\n" +
                "            }\n" +
                "        });\n" +
                "        _send.apply(this, arguments);\n" +
                "    }\n" +
                "})(XMLHttpRequest);";
        jes.executeScript(js);
    }

    public void mouseHoverAndClick(WebElement mainElement,
                                   WebElement subElement) {

        Actions action = new Actions(getDriver());
        action.moveToElement(mainElement).moveToElement(subElement).click()
                .build().perform();
    }

    public void mouseHoverAndClick(WebElement Element) {

        Actions action = new Actions(getDriver());
        action.moveToElement(Element).click().build().perform();
    }

    public static void mouseHover(WebElement Element) {

        Actions action = new Actions(getDriver());
        action.moveToElement(Element).build().perform();
    }

    public void rightClickAndSelectOption(WebElement element,
                                          int position) {

        Actions action = new Actions(getDriver());
        action.contextClick(element).build().perform();
        for (int i = 0; i < position; i++)
            action.sendKeys(Keys.ARROW_DOWN).build().perform();
        action.sendKeys(Keys.RETURN).perform();
    }

    public void selectByText(WebElement element, String text) {

        Select dd = new Select(element);
        dd.selectByVisibleText(text);
    }


    public void selectByIndex(WebElement element, int index) {

        Select dd = new Select(element);
        dd.selectByIndex(index);
    }

    public final void dragAndDrop(WebElement source, WebElement target) {

        Actions act = new Actions(getDriver());
        act.dragAndDrop(source, target).perform();
    }

    protected void uploadFileByAutoIT(String fileLocation) {
        String windowTitle = getWindowUploadName();
        if (verifyFileExists(CoreConstants.pathToAutoITChrome + fileLocation)) {
            String command = "cmd /c CALL" + " \"" + CoreConstants.pathToAutoITChrome + "\"" + " \"" + windowTitle + "\" \"Edit1\" \"Button1\" " + "\"" + fileLocation + "\"";
            try {
                Process proc = Runtime.getRuntime().exec(command);
                InputStream is = proc.getInputStream();
                int retCode = 0;
                while (retCode != -1) {
                    retCode = is.read();
                }
                Log.info("Successfully handled  Upload Window");
            } catch (Exception e) {
                Assert.fail("Exception happened while uploading a file using AUTOIT " + e.getMessage());
            }
        } else
            Assert.fail("The autoit file  was not found at the location : " + fileLocation);


    }

    private String getWindowUploadName() {
        String winTitle = "";
        String browser = ((RemoteWebDriver) getDriver()).getCapabilities().getBrowserName();
        if (browser.contains("chrome"))
            winTitle = "Open";
        else if (browser.contains("firefox"))
            winTitle = "File Upload";
        else if (browser.contains("edge"))
            winTitle = "Open";
        else
            Assert.fail("Supporting Chome, edge and firefox as of now., Please contact Taqueem");
        return winTitle;
    }


    protected String getDownloadDirectory() {
        String directory = System.getProperty("user.home");
        directory = directory + "\\Downloads\\";
        new File(directory);
        return directory;
    }

    protected boolean verifyFileDownloaded(String fileName) {
        int maxWaitTime = 0;
        boolean fileDownloaded = true;
        do {
            sleep(2);
            maxWaitTime++;
            if (maxWaitTime == 10) {
                fileDownloaded = false;
                break;
            }
        } while (!new File(getDownloadDirectory() + fileName).exists());
        return fileDownloaded;
    }

    protected boolean verifyFileExists(String absFilePath) {
        return !new File(absFilePath).exists();
    }

    protected boolean verifyFileExists(String directoryPath, String fileName) {
        boolean isFileDownloaded = false;
        if (fileName == null)
            Log.info("File name is not defined");
        else {
            File directory = new File(directoryPath);
            File[] files = directory.listFiles();
            for (File file : files) {
                if (file.getName().contains(fileName)) {
                    isFileDownloaded = true;
                    Log.info("File is downloaded successfully");
                }
            }
        }
        return isFileDownloaded;
    }

    protected static void deleteMatchingFiles(String directoryPath, String fileName) {
        if (fileName == null)
            Log.info("File name is not defined");
        else {
            File directory = new File(directoryPath);
            File[] files = directory.listFiles();
            for (File file : files) {
                if (file.getName().contains(fileName)) {
                    file.delete();
                    Log.info("Deleted an existing file " + file.getName());
                }

            }
        }
    }

    protected static boolean verifyElementIsPresent(WebElement element) {
        return element.isDisplayed();
    }

    protected static boolean verifyElementHasText(WebElement element, String expectedText) {
        logger.info("Actual Text :" + element.getText());
        logger.info("Expected Text :" + expectedText);
        return element.getText().contains(expectedText);
    }

    protected static boolean verifyElementHasValue(WebElement element, String expectedValue) {
        return verifyElementHasAttributeValue(element, "value", expectedValue);
    }

    protected static boolean verifyElementHasAttributeValue(WebElement element, String attribute, String expectedValue) {
        logger.info("Actual Attribute Value :" + element.getAttribute(attribute));
        logger.info("Expected Attribute Value :" + expectedValue);
        return element.getAttribute(attribute).contains(expectedValue);
    }

    protected static void waitTillElementNotDisplayed(WebElement element, int seconds) {
        WebDriverWait wait = new WebDriverWait(getDriver(), seconds);
        wait.until(ExpectedConditions.invisibilityOf(element));
    }


    private static FluentWait waitForCondition() {
        int seconds;
        try {
            seconds = Integer.parseInt(System.getProperty(CoreProperties.waitTimeOut)) / 1000;
        } catch (NumberFormatException e) {
            seconds = 30;
        }

        return (new FluentWait(getDriver(), Clock.systemDefaultZone(), Sleeper.SYSTEM_SLEEPER)).withTimeout(Duration.ofSeconds(seconds)).
                pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class, NoSuchFrameException.class);
    }

/*    public Wait<WebDriver> waitForCondition() {
        return (new FluentWait(this.driver, this.webdriverClock, this.sleeper)).withTimeout(this.waitForTimeoutInMilliseconds, TimeUnit.MILLISECONDS).pollingEvery(100L, TimeUnit.MILLISECONDS).ignoring(org.openqa.selenium.NoSuchElementException.class, NoSuchFrameException.class);
    }*/

    private static FluentWait waitForCondition(int seconds) {
        return (new FluentWait(getDriver(), Clock.systemDefaultZone(), Sleeper.SYSTEM_SLEEPER)).withTimeout(Duration.ofSeconds(seconds)).
                pollingEvery(Duration.ofSeconds(1)).ignoring(Exception.class);
    }

    private static FluentWait waitForClickable(int seconds) {
        return (new FluentWait(getDriver(), Clock.systemDefaultZone(), Sleeper.SYSTEM_SLEEPER)).withTimeout(Duration.ofSeconds(seconds)).
                pollingEvery(Duration.ofSeconds(1)).ignoring(ElementClickInterceptedException.class, NoSuchElementException.class);
    }

    private static FluentWait waitForEnable(int seconds) {
        return (new FluentWait(getDriver(), Clock.systemDefaultZone(), Sleeper.SYSTEM_SLEEPER)).withTimeout(Duration.ofSeconds(seconds)).
                pollingEvery(Duration.ofSeconds(1)).ignoring(InvalidElementStateException.class, StaleElementReferenceException.class);
    }


    protected boolean waitTillNotVisible(WebElement element) {
        boolean elementVisible;
        int timeToWait = 30;

        do {
            timeToWait = timeToWait - 2;
            elementVisible = isElementPresent(element);
            if (elementVisible) {
                sleep(2);
            } else {
                break;
            }
        }
        while (elementVisible && timeToWait > 0);

        return !elementVisible;
    }

    protected boolean waitTillNotVisible(WebElement element, int seconds) {
        boolean elementVisible;
        int timeToWait = seconds;

        do {
            timeToWait = timeToWait - 2;
            elementVisible = isElementPresent(element);
            if (elementVisible) {
                sleep(2);
            } else {
                break;
            }
        }
        while (elementVisible && timeToWait > 0);

        return !elementVisible;
    }

    protected static void waitTillVisible(WebElement element) {
        waitForCondition().until(ExpectedConditions.visibilityOf(element));
    }

    protected static void waitTillVisible(WebElement element, int timeOut) {
        waitForCondition(timeOut).until(ExpectedConditions.visibilityOf(element));
    }


    protected static void closeChildWindows() {
        String parent = getDriver().getWindowHandle();
        Set<String> pops = getDriver().getWindowHandles();
        for (String pop : pops) {
            if (!pop.contains(parent)) {
                getDriver().switchTo().window(pop);
                getDriver().close();
            }
        }
    }

    protected static void closeAllOpenWindows() {
        logger.info("closing all open windows");
        Set<String> pops = getDriver().getWindowHandles();
        for (String pop : pops) {
            getDriver().switchTo().window(pop);
            getDriver().close();
        }
    }

    protected static WebElement getElementByDynamicXpath(String xpath) {
        return getDriver().findElement(By.xpath(xpath));
    }

    public static void switchToAngularIFrame(WebElement frameAngular) {
        switchToDefaultContent();
        if (isElementPresent(frameAngular)) {
            waitTillVisible(frameAngular);
            getDriver().switchTo().frame(frameAngular);
            logger.info("Switched to frame: " + frameAngular + " successfully");
        }
    }

    protected static boolean isElementPresent(WebElement element) {
        String elementName = element.toString();
        boolean isElementPresent;
        if (elementName.contains("Proxy")) {
            return false;
        }
        try {
            waitForCondition(2).until(ExpectedConditions.visibilityOf(element));
            isElementPresent = true;
        } catch (Exception E) {
            isElementPresent = false;
        }
//        logger.info("returning " + isElementPresent + " from  isElementPresent for the element :  " + elementName);
        return isElementPresent;
    }

    protected static boolean isElementPresent(WebElement element, int seconds) {
        String elementName = element.toString();
        boolean isElementPresent;
        if (elementName.contains("Proxy")) {
            return false;
        }
        try {
            waitForCondition(seconds).until(ExpectedConditions.visibilityOf(element));
            isElementPresent = true;
        } catch (Exception E) {
            isElementPresent = false;
        }
//        logger.info("returning " + isElementPresent + " from  isElementPresent for the element :  " + elementName);
        return isElementPresent;
    }

    protected static boolean isElementClickable(WebElement element) {
        String elementName = element.toString();
        boolean isElementPresent;
        if (elementName.contains("Proxy")) {
            return false;
        }
        try {
            waitForCondition(2).until(ExpectedConditions.elementToBeClickable(element));
            isElementPresent = true;
        } catch (Exception E) {
            isElementPresent = false;
        }
//        logger.info("returning " + isElementPresent + " from  isElementPresent for the element :  " + elementName);
        return isElementPresent;
    }

    protected static boolean isElementPresent(By locator) {
//        logger.info("Looking for presence of element" + locator);
        boolean isElementPresent = false;
        try {
            waitForCondition(2).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
            isElementPresent = true;
        } catch (Exception E) {
        }
//        logger.info("returning " + isElementPresent + " from  isElementPresent for the element :  " + locator);
        return isElementPresent;
    }

    protected static boolean isElementEnabled(WebElement element, int withInSeconds) {
        boolean isElementEnabled = false;
        try {
            waitForEnable(withInSeconds).until(ExpectedConditions.elementToBeClickable(element));
            isElementEnabled = true;
        } catch (Exception E) {

        }
//        logger.info("returning " + isElementEnabled + " from  isElementEnabled for the element :  " + element);
        return isElementEnabled;
    }


    protected static void switchToDefaultContent() {
        getDriver().switchTo().defaultContent();
    }

    protected static WebElement find(By locator) {
        return getDriver().findElement(locator);
    }

    protected WebElement findOnException(String firstXpath, String secondXpath) {
        WebElement element = null;
        try {
            element = find(By.xpath(firstXpath));
        } catch (NoSuchElementException e) {
            logger.info("Element not found by " + firstXpath);
            element = find(By.xpath(secondXpath));
        } catch (Exception e) {
            Assert.fail("The element is not found");
        }
        return element;
    }

    protected static List<WebElement> findAll(By locator) {
        return getDriver().findElements(locator);
    }

    public static boolean isNullOrEmpty(String str) {
        if (str != null && !str.isEmpty())
            return false;
        return true;
    }

    protected void refreshPage() {

        getDriver().navigate().refresh();
    }

    public void switchToLatestWindow() {
        Set<String> winHandles = getDriver().getWindowHandles();
        for (String handle : winHandles) {
            getDriver().switchTo().window(handle);
        }
        logger.info("Switched to latest window");
    }

    public String getRandomUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    protected void switchToFrame(int i) {

        getDriver().switchTo().frame(i);
    }

    protected boolean isStringListSorted(List<String> list, CompareMode mode) {
        logger.info("Comparing the String list");
        boolean isSorted = true;
        Iterator<String> iter = list.iterator();
        String current, previous = iter.next();

        switch (mode) {
            case ASCENDING:
                while (iter.hasNext()) {
                    current = iter.next();
                    if (previous.compareTo(current) > 0) {
                        isSorted = false;
                        break;
                    }
                    previous = current;
                }
                break;
            case DESCENDING:
                while (iter.hasNext()) {
                    current = iter.next();
                    if ((previous.compareTo(current) < 0)) {
                        isSorted = false;
                        break;
                    }
                    previous = current;
                }
                break;
            default:
                Assert.fail("The Comparison method is not defined.");
        }
        return isSorted;
    }

    protected boolean isIntegerListSorted(List<Integer> list, CompareMode mode) {
        logger.info("Comparing the integer list");
        boolean isSorted = true;
        int size = list.size();
        switch (mode) {
            case ASCENDING:
                for (int i = 0; i < size - 1; i++) {
                    if (list.get(i) > list.get(i + 1))
                        isSorted = false;
                    break;
                }
                break;
            case DESCENDING:
                for (int i = 0; i < size - 1; i++) {
                    if (list.get(i) < list.get(i + 1))
                        isSorted = false;
                    break;
                }
                break;
            default:
                Assert.fail("The Comparison method is not defined.");
        }
        return isSorted;
    }

    protected boolean isDateListSorted(List<LocalDate> list, CompareMode mode) {
        logger.info("Comparing the integer list");
        boolean isSorted = true;
        int size = list.size();
        switch (mode) {
            case ASCENDING:
                for (int i = 0; i < size - 1; i++) {
                    if (list.get(i).compareTo(list.get(i + 1)) > 0)
                        isSorted = false;
                    break;
                }
                break;
            case DESCENDING:
                for (int i = 0; i < size - 1; i++) {
                    if (list.get(i).compareTo(list.get(i + 1)) < 0)
                        isSorted = false;
                    break;
                }
                break;
            default:
                Assert.fail("The Comparison method is not defined.");
        }
        return isSorted;
    }


    protected String removeAllSpecialCharacters(String str) {
        return str.replaceAll(" ", "").replaceAll("[^a-zA-Z0-9-]", "");
    }

    protected void acceptAlert() {
        try {
            getDriver().switchTo().alert().accept();
        } catch (NoAlertPresentException e) {
            logger.info("No alert is present");
        }
    }

    public void waitTillAttributeToContain(WebElement element, String attribute, String value) {
        int attempt = 0;

        while (!(attempt == 15)) {
            if (element.getAttribute(attribute).contains(value)) {
                logger.info(element + " having attribute " + attribute + " has the value " + value + "after attempts " + attempt);
                break;
            } else {
                attempt++;
                sleep(2);
            }
        }

    }


    public void waitTillNotHaveAttribute(WebElement element, String attribute) {
        int attempt = 0;

        while (!(attempt == 15)) {
            if (element.getAttribute(attribute) != null) {
                sleep(2);
                attempt++;
            } else break;

        }

    }


    protected static void slowTyping(WebElement inputElement, String value) {
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            String s = new StringBuilder().append(c).toString();
            inputElement.sendKeys(s);
            sleepByMilliSeconds(100);
        }
    }

    public void keyBoardActions(Keys keys) {
        Actions action = new Actions(getDriver());
        action.sendKeys(keys).build().perform();
    }


}