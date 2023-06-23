package com.saucedemo.pom;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends TestUtilities {
    protected WebDriver driver;
    private static final String ADD_TO_CART_LOCATOR = "//button[@id='add-to-cart-sauce-labs-%s']";
    private static final String REMOVE_FROM_CART_LOCATOR = "//button[@id='remove-sauce-labs-%s']";
    private static final String HOME_PAGE_URL = "https://www.saucedemo.com/inventory.html";
    private static final String HOME_PAGE = "Current page is Home page.";
    private static final String HOME_PAGE_ERROR = "Home page loading Failed.";
    private static final String PRODUCTS_LIST_MISSING_MESSAGE = "Products list is not displayed!";
    private static final String MENU_BUTTON_MISSING_MESSAGE = "Menu button is not displayed!";
    private static final String SHOPPING_CART_MISSING_MESSAGE = "Shopping cart is not displayed!";
    private static final String TWITTER_LINK_URL = "https://twitter.com/saucelabs";
    private static final String FACEBOOK_LINK_URL = "https://www.facebook.com/saucelabs";
    private static final String LINKEDIN_LINK_URL = "https://www.linkedin.com";
    private static final String DIFFERENT_TEXT = "The text is different!";
    private static final String TWITTER_LINK_MISSING_MESSAGE = "Twitter link is not displayed!";
    private static final String FACEBOOK_LINK_MISSING_MESSAGE = "Facebook link is not displayed!";
    private static final String LINKEDIN_LINK_MISSING_MESSAGE = "LinkedIn link is not displayed!";
    private static final String ALL_ITEMS_LINK_MISSING_MESSAGE = "'All Items' link is not displayed!";
    private static final String ABOUT_LINK_MISSING_MESSAGE = "'About' link is not displayed!";
    private static final String LOGOUT_LINK_MISSING_MESSAGE = "'Logout' link is not displayed!";
    private static final String RESET_APP_STATE_LINK_MISSING_MESSAGE = "'Reset App State' link is not displayed!";
    private static final String ALL_ITEMS_LINK_TEXT = "All Items";
    private static final String ABOUT_LINK_TEXT = "About";
    private static final String ABOUT_LINK_URL = "https://saucelabs.com/";
    private static final String ABOUT_LINK_URL_DIFFERENT_MESSAGE = "The URL is different.";
    private static final String REDIRECT_IS_SUCCESSFUL = "Redirect is successful!";
    private static final String REDIRECT_FAILED = "Redirect failed!";
    private static final String LOGOUT_LINK_TEXT = "Logout";
    private static final String RESET_APP_STATE_LINK_TEXT = "Reset App State";
    public static String selectedProductName = "";
    public static String selectedProductPrice = "";
    public static String selectedProductImageSrc = "";
//    public static ArrayList<Product> productList;

    @FindBy(className = "shopping_cart_link")
    private WebElement shoppingCartLink;
    @FindBy(className = "shopping_cart_badge")
    private WebElement shoppingCartCounter;
    @FindBy(xpath = "//div[@class='inventory_list']")
    private WebElement productsList;
    @FindBy(xpath = "//div[@class='inventory_item']")
    private WebElement singleProduct;
    @FindBy(xpath = "//div[@class='inventory_item_label']//a")
    private WebElement singleProductName;
    @FindBy(xpath = "//div[@class='inventory_item_label']//a[contains(@id,'title_link')]")
    private WebElement singleProductNameLink;
    @FindBy(xpath = "//div[@id='shopping_cart_container']")
    private WebElement shoppingCart;
    @FindBy(xpath = "//a[contains(.,'Twitter')]")
    private WebElement footerTwitterLink;
    @FindBy(xpath = "//a[contains(text(),'Facebook')]")
    private WebElement footerFacebookLink;
    @FindBy(xpath = "//li[@class='social_linkedin']//a[contains(text(),'LinkedIn')]")
    private WebElement footerLinkedinLink;
    @FindBy(xpath = "//button[@id='react-burger-menu-btn']")
    private WebElement menuButton;
    @FindBy(xpath = "//button[@id='react-burger-cross-btn']")
    private WebElement menuCloseButton;
    @FindBy(xpath = "//a[@id='inventory_sidebar_link']")
    private WebElement menuInventoryButton;
    @FindBy(xpath = "//a[@id='about_sidebar_link']")
    private WebElement menuAboutButton;
    @FindBy(xpath = "//a[@id='logout_sidebar_link']")
    private WebElement menuLogoutButton;
    @FindBy(xpath = "//a[@id='reset_sidebar_link']")
    private WebElement menuResetButton;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public ProductPage openProductPage() {
        return new ProductPage(driver);
    }

    public void addItemToTheCart(String productName) {
        String xpathOfClickedElement = String.format(ADD_TO_CART_LOCATOR, productName);
        WebElement addToCartButton = driver.findElement(By.xpath(xpathOfClickedElement));
        addToCartButton.click();
    }

    /* method who navigate to product page and validate product items by submitted item name*/
    public ProductPage selectProduct(String productName) {
        /* get all products */
        List<WebElement> products = driver.findElements(By.cssSelector("div[class='inventory_item']"));

        /* go through all products */
        for (WebElement product : products) {
            /* get child element - Name */
            WebElement childName = product.findElement(By.cssSelector("div[class='inventory_item_name']"));

            /* get parent element - Name */
            WebElement parent = childName.findElement(By.xpath(".."));

            /* get child element - Price */
            WebElement productPrice = product.findElement(By.cssSelector("div[class='inventory_item_price']"));

            /* get child element - Image Src*/
            WebElement productImageSrc = product.findElement(By.cssSelector("img[class='inventory_item_img']"));

            /* Save values for Name/Price/Image src for comparison in other pages */
            selectedProductName = childName.getText();
            selectedProductPrice = productPrice.getText();
            selectedProductImageSrc = productImageSrc.getAttribute("src");

            /* locate submitted product and click his product name link */
            if (selectedProductName.contains(productName)) {
                parent.click();
                return new ProductPage(driver);
            }
        }

        return null;
    }

    public boolean removeItemFromTheCart(String productName) {
        String xpathOfClickedElement = String.format(REMOVE_FROM_CART_LOCATOR, productName);

        WebElement removeButton = driver.findElement(By.xpath(xpathOfClickedElement));

//        //todo Да се изведе в TestUtilities !!!!
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7));
//        removeButton = wait.until(ExpectedConditions.elementToBeClickable(By.id(xpathOfClickedElement)));
//        element.click();


        if (removeButton.getText().equals("Remove")) {
            removeButton.click();
            return true;
        } else {
            return false;
        }
    }

    public int getItemsInTheCart() {
        if (shoppingCartLink.getText().isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(shoppingCartCounter.getText());
        }

    }

    public void clickOnCartButton() {
        shoppingCartLink.click();
    }

    public void homepageValidator() {
        if (driver.getCurrentUrl().equals(HOME_PAGE_URL)) {
            System.out.println(HOME_PAGE);
            MyFileWriter.writeToLog(HOME_PAGE);

            Assert.assertTrue(productsList.isDisplayed(), PRODUCTS_LIST_MISSING_MESSAGE);
            Assert.assertTrue(menuButton.isDisplayed(), MENU_BUTTON_MISSING_MESSAGE);
            Assert.assertTrue(shoppingCart.isDisplayed(), SHOPPING_CART_MISSING_MESSAGE);

        } else {
            System.out.println(HOME_PAGE_ERROR);
            MyFileWriter.writeToLog(HOME_PAGE_ERROR);
        }
    }

    public void socialLinksValidator() {

        Assert.assertTrue(footerTwitterLink.isDisplayed(), TWITTER_LINK_MISSING_MESSAGE);
        Assert.assertTrue(footerFacebookLink.isDisplayed(), FACEBOOK_LINK_MISSING_MESSAGE);
        Assert.assertTrue(footerLinkedinLink.isDisplayed(), LINKEDIN_LINK_MISSING_MESSAGE);

        //Following waits are needed for test execution on firefox and edge
        waitClickable(driver, footerTwitterLink, 5);
        footerTwitterLink.click();
        simpleWait(600);
        waitClickable(driver, footerFacebookLink, 5);
        footerFacebookLink.click();
        simpleWait(600);
        waitClickable(driver, footerLinkedinLink, 5);
        footerLinkedinLink.click();
        simpleWait(600);

        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());

        driver.switchTo().window(tabs.get(3));
        Assert.assertEquals(driver.getCurrentUrl(), TWITTER_LINK_URL);

        driver.switchTo().window(tabs.get(2));
        Assert.assertEquals(driver.getCurrentUrl(), FACEBOOK_LINK_URL);

        driver.switchTo().window(tabs.get(1));
        Assert.assertTrue(driver.getCurrentUrl().contains(LINKEDIN_LINK_URL));

    }

    /* method who validates existence and correctness of menu items */
    public void menuLinksValidator() {
        menuButton.click();
        waitClickable(driver, menuInventoryButton, 2);
        Assert.assertTrue(menuInventoryButton.isDisplayed(), ALL_ITEMS_LINK_MISSING_MESSAGE);
        Assert.assertEquals(menuInventoryButton.getText(), ALL_ITEMS_LINK_TEXT, DIFFERENT_TEXT);

        waitClickable(driver, menuAboutButton, 2);
        Assert.assertTrue(menuAboutButton.isDisplayed(), ABOUT_LINK_MISSING_MESSAGE);
        Assert.assertEquals(menuAboutButton.getText(), ABOUT_LINK_TEXT, DIFFERENT_TEXT);

        waitClickable(driver, menuLogoutButton, 2);
        Assert.assertTrue(menuLogoutButton.isDisplayed(), LOGOUT_LINK_MISSING_MESSAGE);
        Assert.assertEquals(menuLogoutButton.getText(), LOGOUT_LINK_TEXT, DIFFERENT_TEXT);

        waitClickable(driver, menuResetButton, 2);
        Assert.assertTrue(menuResetButton.isDisplayed(), RESET_APP_STATE_LINK_MISSING_MESSAGE);
        Assert.assertEquals(menuResetButton.getText(), RESET_APP_STATE_LINK_TEXT, DIFFERENT_TEXT);
    }

    /* click method for Menu item - "All Items" */
    public void clickMenuInventoryButton() {
        menuInventoryButton.click();
    }

    /* click and check method for Menu item - "About" */
    public void clickMenuAboutButton() {
        menuAboutButton.click();
        Assert.assertEquals(driver.getCurrentUrl(), ABOUT_LINK_URL, ABOUT_LINK_URL_DIFFERENT_MESSAGE);

        if (driver.getCurrentUrl().equals(ABOUT_LINK_URL)) {
            System.out.println(REDIRECT_IS_SUCCESSFUL);
            MyFileWriter.writeToLog(REDIRECT_IS_SUCCESSFUL);
        } else {
            System.out.println(REDIRECT_FAILED);
            MyFileWriter.writeToLog(REDIRECT_FAILED);
        }
    }

    /* click method for Menu item - "Logout" */
    public void clickMenuLogoutButton() {
        menuLogoutButton.click();
    }

    /* click method for Menu item - "Reset App State" */
    public void clickMenuResetButton() {
        menuResetButton.click();
    }


}
