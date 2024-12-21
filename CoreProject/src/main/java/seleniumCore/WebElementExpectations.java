package seleniumCore;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.Arrays;
import java.util.List;

public class WebElementExpectations {

    private static final List<String> HTML_FORM_TAGS = Arrays.asList("input", "button", "select", "textarea", "link", "option");

    public WebElementExpectations() {
    }

    public static ExpectedCondition<Boolean> elementIsDisplayed(WebElement element) {
        return (new ExpectedCondition<Boolean>() {
            private WebElement element;

            public ExpectedCondition<Boolean> forElement(WebElement element) {
                this.element = element;
                return this;
            }

            public Boolean apply(WebDriver driver) {
                return this.element.isDisplayed();
            }

            public String toString() {
                return this.element.toString() + " to be displayed";
            }
        }).forElement(element);
    }

    public static ExpectedCondition<Boolean> elementIsPresent(WebElement element) {
        return (new ExpectedCondition<Boolean>() {
            private WebElement element;

            public ExpectedCondition<Boolean> forElement(WebElement element) {
                this.element = element;
                return this;
            }

            public Boolean apply(WebDriver driver) {
                return this.element.isDisplayed();
            }

            public String toString() {
                return this.element.toString() + " to be present";
            }
        }).forElement(element);
    }

    public static ExpectedCondition<Boolean> elementIsEnabled(WebElement element) {
        return (new ExpectedCondition<Boolean>() {
            private WebElement element;

            public ExpectedCondition<Boolean> forElement(WebElement element) {
                this.element = element;
                return this;
            }

            public Boolean apply(WebDriver driver) {
                WebElement resolvedElement = this.element;
                return resolvedElement != null && !WebElementExpectations.isDisabledField(this.element);
            }

            public String toString() {
                return this.element.toString() + " to be enabled";
            }
        }).forElement(element);
    }

    public static ExpectedCondition<Boolean> elementIsNotEnabled(WebElement element) {
        return (new ExpectedCondition<Boolean>() {
            private WebElement element;

            public ExpectedCondition<Boolean> forElement(WebElement element) {
                this.element = element;
                return this;
            }

            public Boolean apply(WebDriver driver) {
                return !this.element.isEnabled();
            }

            public String toString() {
                return this.element.toString() + " to not be enabled";
            }
        }).forElement(element);
    }

    public static ExpectedCondition<Boolean> elementIsClickable(WebElement element) {
        return (new ExpectedCondition<Boolean>() {
            private WebElement element;

            public ExpectedCondition<Boolean> forElement(WebElement element) {
                this.element = element;
                return this;
            }

            public Boolean apply(WebDriver driver) {
                WebElement resolvedElement = this.element;
                return resolvedElement != null && resolvedElement.isDisplayed() && resolvedElement.isEnabled();
            }

            public String toString() {
                return this.element.toString() + " to be clickable";
            }
        }).forElement(element);
    }

    public static ExpectedCondition<Boolean> elementIsNotDisplayed(WebElement element) {
        return (new ExpectedCondition<Boolean>() {
            private WebElement element;

            public ExpectedCondition<Boolean> forElement(WebElement element) {
                this.element = element;
                return this;
            }

            public Boolean apply(WebDriver driver) {
                return !this.element.isDisplayed();
            }

            public String toString() {
                return this.element.toString() + " to be not displayed";
            }
        }).forElement(element);
    }

    public static ExpectedCondition<Boolean> elementIsNotPresent(WebElement element) {
        return (new ExpectedCondition<Boolean>() {
            private WebElement element;

            public ExpectedCondition<Boolean> forElement(WebElement element) {
                this.element = element;
                return this;
            }

            public Boolean apply(WebDriver driver) {
                return !this.element.isDisplayed();
            }

            public String toString() {
                return this.element.toString() + " to be not present";
            }
        }).forElement(element);
    }

    private static boolean isDisabledField(WebElement element) {
        return isAFormElement(element) && !element.isEnabled();
    }

    private static boolean isAFormElement(WebElement element) {
        if (element != null && element.getTagName() != null) {
            String tag = element.getTagName().toLowerCase();
            return HTML_FORM_TAGS.contains(tag);
        } else {
            return false;
        }
    }
}
