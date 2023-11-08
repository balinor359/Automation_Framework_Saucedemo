package com.saucedemo.pom;

import com.saucedemo.objects.Product;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import org.openqa.selenium.support.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomePage extends TestUtilities {

    /* Declaring web-driver in protected variable */
    protected WebDriver driver;

    /* Declaring string variables for the current page */
    private static final String ADD_TO_CART_LOCATOR = "//button[@id='add-to-cart-%s']";
    private static final String REMOVE_FROM_CART_LOCATOR = "//button[@id='remove-%s']";
    private static final String HOME_PAGE_URL = "https://www.saucedemo.com/inventory.html";
    private static final String HOME_PAGE = "Current page is Home page.";
    private static final String HOME_PAGE_ERROR = "Home page loading Failed.";
    private static final String PRODUCTS_LIST_MISSING_MESSAGE = "Products list is not displayed!";
    private static final String MENU_BUTTON_MISSING_MESSAGE = "Menu button is not displayed!";
    private static final String SHOPPING_CART_MISSING_MESSAGE = "Shopping cart is not displayed!";
    private static final String TWITTER_LINK_URL = "https://twitter.com";
    private static final String FACEBOOK_LINK_URL = "https://www.facebook.com/saucelabs";
    private static final String LINKEDIN_LINK_URL = "https://www.linkedin.com";
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
    private static final String REDIRECT_IS_SUCCESSFUL = "Redirect is successful!";
    private static final String REDIRECT_FAILED = "Redirect failed!";
    private static final String LOGOUT_LINK_TEXT = "Logout";
    private static final String RESET_APP_STATE_LINK_TEXT = "Reset App State";
    public static final String CART_BADGE_WRONG_AMOUNT = "Cart have different amount of products!";
    private static final String CODE_ERROR_ADD_TO_CART_BUTTON = "Code error: the 'Add to Cart' button you interact with does not exist!";
    private static final Object RETURN_NULL_OBJECT = null;
    private static final String PRICES_ASC_ERROR_MESSAGE = "Prices are not sorted in ascending order!";
    private static final String PRICES_DESC_ERROR_MESSAGE = "Prices are not sorted in descending order!";
    private static final String SORT_BY_PRICE_LOW_TO_HIGH_TEXT = "lohi";
    private static final String SORT_BY_PRICE_HIGH_TO_LOW_TEXT = "hilo";
    public static ArrayList<Double> originalPriceList = new ArrayList<>();
    public static ArrayList<Double> productsAfterLoad = new ArrayList<>();

    /* Declaring page elements */
    @FindBy(className = "shopping_cart_link")
    private WebElement shoppingCartLink;
    @FindBy(className = "shopping_cart_badge")
    private WebElement shoppingCartBadge;
    @FindBy(xpath = "//div[@class='inventory_list']")
    private WebElement productsList;
    @FindBy(xpath = "//div[@class='inventory_item']")
    private List<WebElement> productListLocal;
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
    @FindBy(xpath = "//select[@class='product_sort_container']")
    private WebElement sortingDropdown;

    /* This is constructor for home page using PageFactory for web-elements */
    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /* Method who add product in the cart, by given productNameUrl, validates add-to-cart button, click it, then validate is remove button is shown */
    public void addItemToTheCartSimpleClick(String productNameUrl) {
        addToCartButtonValidator(productNameUrl);

        String xpathOfClickedElement = String.format(ADD_TO_CART_LOCATOR, productNameUrl);
        WebElement addToCartButton = driver.findElement(By.xpath(xpathOfClickedElement));
        addToCartButton.click();

        removeButtonValidator(productNameUrl);
    }


    /* Method who add product in the cart and save its data, by given productNameUrl */
    public void addItemToTheCartAndSaveValues(String productNameUrl) {
        addToCartButtonValidator(productNameUrl);

        String xpathOfClickedElement = String.format(ADD_TO_CART_LOCATOR, productNameUrl);
        WebElement addToCartButton = driver.findElement(By.xpath(xpathOfClickedElement));

        /* Go through all products */
        for (WebElement product : productListLocal) {

            /* Take product - action button, and take his whole id */
            WebElement productNameUrlButton = product.findElement(By.cssSelector("button.btn_inventory"));
            String productNameUrlLocal = productNameUrlButton.getAttribute("id");

            /* Check is id contains productNameUrl */
            if (productNameUrlLocal.contains(productNameUrl)) {
                /* Take element - Name */
                WebElement productName = product.findElement(By.className("inventory_item_name"));

                /* Take element - Price */
                WebElement productPrice = product.findElement(By.cssSelector("div[class='inventory_item_price']"));

                /* Take element - Image Src*/
                WebElement productImageSrc = product.findElement(By.cssSelector("img[class='inventory_item_img']"));

                /* Create new product with name, price and image src, and add it to the product list */
                Product newProduct = new Product(productName.getText(), productPrice.getText(), productImageSrc.getAttribute("src"));
                Product.productList.add(newProduct);

                /* Click add to cart button, then validate is remove button is shown */
                addToCartButton.click();
                removeButtonValidator(productNameUrl);
            }
        }
    }

    /* Method who remove product from the cart, by given productNameUrl, validates remove button, click it, then validate is add-to-cart button is shown */
    public void removeItemFromTheCart(String productNameUrl) {
        removeButtonValidator(productNameUrl);

        String xpathOfClickedElement = String.format(REMOVE_FROM_CART_LOCATOR, productNameUrl);
        WebElement removeButton = driver.findElement(By.xpath(xpathOfClickedElement));
        removeButton.click();

        addToCartButtonValidator(productNameUrl);
    }

    /* Method who add all products to the cart */
    public void addAllItemToTheCart() {

        /* Go through all products */
        for (WebElement product : productListLocal) {

            /* Take element - Name */
            WebElement itemName = product.findElement(By.className("inventory_item_name"));

            /* Take element - Price */
            WebElement itemPrice = product.findElement(By.cssSelector("div[class='inventory_item_price']"));

            /* Take child element - Image Src */
            WebElement itemImageSrc = product.findElement(By.cssSelector("img[class='inventory_item_img']"));

            /* Take element - Add to Cart Button */
            WebElement itemAddToCartBtn = product.findElement(By.cssSelector("button.btn_primary"));

            /* Click on add to cart button */
            itemAddToCartBtn.click();

            /* Create new product with name, price and image src, and add it to the product list */
            Product newProduct = new Product(itemName.getText(), itemPrice.getText(), itemImageSrc.getAttribute("src"));
            Product.productList.add(newProduct);


            try {
                /* Take element - Remove Button, and get his whole id attribute, then validate the remove button */
                WebElement ItemRemoveBtn = product.findElement(By.cssSelector("button.btn_secondary"));
                String ItemRemoveBtnId = ItemRemoveBtn.getAttribute("Id");

                removeButtonValidatorWholeUrl(ItemRemoveBtnId);

            } catch (NoSuchElementException e) {
                System.out.println(GenericMessages.CODE_ERROR_REMOVE_BUTTON);
                MyFileWriter.writeToLog(GenericMessages.CODE_ERROR_REMOVE_BUTTON);

                Assert.fail(GenericMessages.CODE_ERROR_REMOVE_BUTTON);
            }

        }

    }

    /* Method who validates add to cart button has correct text / font-color / border color */
    public void addToCartButtonValidator(String productNameUrl) {
        try {
            String xpathOfClickedElement = String.format(ADD_TO_CART_LOCATOR, productNameUrl);
            WebElement addToCartButton = driver.findElement(By.xpath(xpathOfClickedElement));

            Assert.assertTrue(addToCartButton.isDisplayed(), GenericMessages.ADD_TO_CART_MISSING_MESSAGE);
            Assert.assertEquals(addToCartButton.getText(), GenericMessages.ADD_TO_CART_TEXT, GenericMessages.DIFFERENT_TEXT);

            String fontColor = addToCartButton.getCssValue("color");
            String fontColorHex = Color.fromString(fontColor).asHex();
            Assert.assertEquals(fontColorHex, GenericMessages.ADD_TO_CART_FONT_COLOR, GenericMessages.DIFFERENT_CSS_VALUE);

            String elementBorderColor = addToCartButton.getCssValue("border-color");
            String elementBorderColorHex = Color.fromString(elementBorderColor).asHex();
            Assert.assertEquals(elementBorderColorHex, GenericMessages.ADD_TO_CART_BORDER_COLOR, GenericMessages.DIFFERENT_CSS_VALUE);

        } catch (NoSuchElementException e) {
            System.out.println(CODE_ERROR_ADD_TO_CART_BUTTON);
            MyFileWriter.writeToLog(CODE_ERROR_ADD_TO_CART_BUTTON);

            Assert.fail(CODE_ERROR_ADD_TO_CART_BUTTON);
        }
    }

    /* Method who validates remove button has correct text / font-color / border color, by given fragment of his productNameUrl */
    public void removeButtonValidator(String productNameUrl) {
        try {
            String xpathOfClickedElement = String.format(REMOVE_FROM_CART_LOCATOR, productNameUrl);
            WebElement removeButton = driver.findElement(By.xpath(xpathOfClickedElement));

            Assert.assertTrue(removeButton.isDisplayed(), GenericMessages.REMOVE_BUTTON_MISSING_MESSAGE);
            Assert.assertEquals(removeButton.getText(), GenericMessages.REMOVE_BUTTON_TEXT, GenericMessages.DIFFERENT_TEXT);

            String fontColor = removeButton.getCssValue("color");
            String fontColorHex = Color.fromString(fontColor).asHex();
            Assert.assertEquals(fontColorHex, GenericMessages.REMOVE_BUTTON_FONT_COLOR, GenericMessages.DIFFERENT_CSS_VALUE);

            String elementBorderColor = removeButton.getCssValue("border-color");
            String elementBorderColorHex = Color.fromString(elementBorderColor).asHex();
            Assert.assertEquals(elementBorderColorHex, GenericMessages.REMOVE_BUTTON_BORDER_COLOR, GenericMessages.DIFFERENT_CSS_VALUE);

        } catch (NoSuchElementException e) {
            System.out.println(GenericMessages.CODE_ERROR_REMOVE_BUTTON);
            MyFileWriter.writeToLog(GenericMessages.CODE_ERROR_REMOVE_BUTTON);

            Assert.fail(GenericMessages.CODE_ERROR_REMOVE_BUTTON);
        }
    }

    /* Method who validates remove button has correct text / font-color / border color, by his whole id attribute */
    public void removeButtonValidatorWholeUrl(String productNameUrl) {
        try {
            String xpathOfClickedElement = String.format("//button[@id='%s']", productNameUrl);
            WebElement removeButton = driver.findElement(By.xpath(xpathOfClickedElement));

            Assert.assertTrue(removeButton.isDisplayed(), GenericMessages.REMOVE_BUTTON_MISSING_MESSAGE);
            Assert.assertEquals(removeButton.getText(), GenericMessages.REMOVE_BUTTON_TEXT, GenericMessages.DIFFERENT_TEXT);

            String fontColor = removeButton.getCssValue("color");
            String fontColorHex = Color.fromString(fontColor).asHex();
            Assert.assertEquals(fontColorHex, GenericMessages.REMOVE_BUTTON_FONT_COLOR, GenericMessages.DIFFERENT_CSS_VALUE);

            String elementBorderColor = removeButton.getCssValue("border-color");
            String elementBorderColorHex = Color.fromString(elementBorderColor).asHex();
            Assert.assertEquals(elementBorderColorHex, GenericMessages.REMOVE_BUTTON_BORDER_COLOR, GenericMessages.DIFFERENT_CSS_VALUE);

        } catch (NoSuchElementException e) {
            System.out.println(GenericMessages.CODE_ERROR_REMOVE_BUTTON);
            MyFileWriter.writeToLog(GenericMessages.CODE_ERROR_REMOVE_BUTTON);

            Assert.fail(GenericMessages.CODE_ERROR_REMOVE_BUTTON);
        }
    }

    /* Method who check if cart is empty, and if it`s not return the amount of products in the cart */
    public int  getItemsInTheCart() {
        if (shoppingCartLink.getText().isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(shoppingCartBadge.getText());
        }
    }


    /* Method who navigate to product page and validate product items by submitted item name */
    public ProductPage selectProduct(String productName) {
        /* Go through all products */
        for (WebElement product : productListLocal) {

            /* Take child element - Name */
            WebElement productTitle = product.findElement(By.className("inventory_item_name"));

            /* Take element - Link */
            WebElement productTitleParent = product.findElement(By.tagName("a"));

            /* Take child element - Price */
            WebElement productPrice = product.findElement(By.cssSelector("div[class='inventory_item_price']"));

            /* Take child element - Image Src*/
            WebElement productImageSrc = product.findElement(By.cssSelector("img[class='inventory_item_img']"));

            /* Save values for Name/Price/Image src for comparison in other pages */

            /* Locate submitted product and click his product name link */
            if (productTitle.getText().contains(productName)) {

                /* Create new product with name, price and image src, and add it to the product list */
                Product newProduct = new Product(productTitle.getText(), productPrice.getText(), productImageSrc.getAttribute("src"));
                Product.productList.add(newProduct);

                /* Click on product name */
                productTitleParent.click();

                /* Pass the driver to ProductPage (POM) */
                return new ProductPage(driver);
            }
        }
        /* Return null driver if haven`t found the product */
        return (ProductPage) RETURN_NULL_OBJECT;
    }

    /* Click method for "Cart" button */
    public CartPage clickOnCartButton() {
        shoppingCartLink.click();
        /* Pass the driver to CartPage (POM) */
        return new CartPage(driver);
    }

    /* Method who validate Homepage is loaded */
    public void homepageValidator() {
        /* Check if current page is inventory.html */
        if (driver.getCurrentUrl().equals(HOME_PAGE_URL)) {
            System.out.println(HOME_PAGE);
            MyFileWriter.writeToLog(HOME_PAGE);

            Assert.assertTrue(productsList.isDisplayed(), PRODUCTS_LIST_MISSING_MESSAGE);
            Assert.assertTrue(menuButton.isDisplayed(), MENU_BUTTON_MISSING_MESSAGE);
            Assert.assertTrue(shoppingCart.isDisplayed(), SHOPPING_CART_MISSING_MESSAGE);

        } else {
            System.out.println(HOME_PAGE_ERROR);
            MyFileWriter.writeToLog(HOME_PAGE_ERROR);

            Assert.assertEquals(driver.getCurrentUrl(), HOME_PAGE_URL, HOME_PAGE_ERROR);
        }
    }

    /* Method who validates social footer links are displayed and working correctly */
    public void socialLinksValidator() {
        /* Verify all social links in footer are displayed */
        Assert.assertTrue(footerTwitterLink.isDisplayed(), TWITTER_LINK_MISSING_MESSAGE);
        Assert.assertTrue(footerFacebookLink.isDisplayed(), FACEBOOK_LINK_MISSING_MESSAGE);
        Assert.assertTrue(footerLinkedinLink.isDisplayed(), LINKEDIN_LINK_MISSING_MESSAGE);

        /* Array list with tabs(WindowHandles) */
        ArrayList<String> tabs = new ArrayList<String>();
        String parentWindow = driver.getWindowHandle();
        /* Add main tab handle */
        tabs.add(parentWindow);

        /* Print used browser*/
        System.out.println( "Used Browser: " + usedBrowser);

        /* Verify browser - Firefox loads browser tabs in a different order compared to Chrome and Edge */
        if (usedBrowser.equals("firefox")){

            /* Following waits are needed for test execution on firefox */
            /* Wait link to be clickable, then click it, and wait web-driver to load it in new tab */
            waitClickable(driver, footerTwitterLink, 5);
            footerTwitterLink.click();
            simpleWait(600);
            tabs.add(driver.getWindowHandles().toArray()[1].toString());

            /* Wait link to be clickable, then click it, and wait web-driver to load it in new tab */
            waitClickable(driver, footerFacebookLink, 5);
            footerFacebookLink.click();
            simpleWait(600);
            tabs.add(driver.getWindowHandles().toArray()[1].toString());

            /* Wait link to be clickable, then click it, and wait web-driver to load it in new tab */
            waitClickable(driver, footerLinkedinLink, 5);
            footerLinkedinLink.click();
            simpleWait(600);
            tabs.add(driver.getWindowHandles().toArray()[1].toString());

        } else {

            /* Following waits are needed for test execution on edge */
            /* Wait link to be clickable, then click it, and wait web-driver to load it in new tab */
            waitClickable(driver, footerTwitterLink, 5);
            footerTwitterLink.click();
            simpleWait(600);
            tabs.add(driver.getWindowHandles().toArray()[1].toString());

            /* Wait link to be clickable, then click it, and wait web-driver to load it in new tab */
            waitClickable(driver, footerFacebookLink, 5);
            footerFacebookLink.click();
            simpleWait(600);
            tabs.add(driver.getWindowHandles().toArray()[2].toString());

            /* Wait link to be clickable, then click it, and wait web-driver to load it in new tab */
            waitClickable(driver, footerLinkedinLink, 5);
            footerLinkedinLink.click();
            simpleWait(600);
            tabs.add(driver.getWindowHandles().toArray()[3].toString());

        }

        /* Switch to wanted tab, then validate correctness of the external link */
        driver.switchTo().window(tabs.get(1));
        validateTwitterLink();
        driver.close();

        /* Switch to wanted tab, then validate correctness of the external link */
        driver.switchTo().window(tabs.get(2));
        validateFacebookLink();
        driver.close();

        /* Switch to wanted tab, then validate correctness of the external link */
        driver.switchTo().window(tabs.get(3));
        validateLinkedInLink();
        driver.close();
    }

    /* Method who validate correctness of the social link and print right messages in console and log file */
    public void validateTwitterLink() {
        /* Wait web-driver to load it in new tab */
        simpleWait(600);
        if (driver.getCurrentUrl().contains(TWITTER_LINK_URL)) {
            System.out.println(REDIRECT_IS_SUCCESSFUL);
            MyFileWriter.writeToLog(REDIRECT_IS_SUCCESSFUL);
        } else {
            System.out.println(REDIRECT_FAILED);
            MyFileWriter.writeToLog(REDIRECT_FAILED);
            Assert.assertEquals(driver.getCurrentUrl(), TWITTER_LINK_URL, REDIRECT_FAILED);
        }


    }

    /* Method who validate correctness of the social link and print right messages in console and log file */
    public void validateFacebookLink() {
        /* Wait web-driver to load it in new tab */
        simpleWait(600);
        if (driver.getCurrentUrl().equals(FACEBOOK_LINK_URL)) {
            System.out.println(REDIRECT_IS_SUCCESSFUL);
            MyFileWriter.writeToLog(REDIRECT_IS_SUCCESSFUL);
        } else {
            System.out.println(REDIRECT_FAILED);
            MyFileWriter.writeToLog(REDIRECT_FAILED);
            Assert.assertEquals(driver.getCurrentUrl(), FACEBOOK_LINK_URL, REDIRECT_FAILED);
        }

    }

    /* Method who validate correctness of the social link and print right messages in console and log file */
    public void validateLinkedInLink() {
        /* Wait web-driver to load it in new tab */
        simpleWait(600);
        if (driver.getCurrentUrl().contains(LINKEDIN_LINK_URL)) {
            System.out.println(REDIRECT_IS_SUCCESSFUL);
            MyFileWriter.writeToLog(REDIRECT_IS_SUCCESSFUL);
        } else {
            System.out.println(REDIRECT_FAILED);
            MyFileWriter.writeToLog(REDIRECT_FAILED);
            Assert.assertEquals(driver.getCurrentUrl(), LINKEDIN_LINK_URL, REDIRECT_FAILED);
        }

    }

    /* Method who validates existence and correctness of menu items */
    public void menuLinksValidator() {
        /* Click on hamburger menu */
        menuButton.click();

        /* Following code:
        - wait element to e clickable,
        - check if he is displayed,
        - check actual with expected link text */
        waitClickable(driver, menuInventoryButton, 2);
        Assert.assertTrue(menuInventoryButton.isDisplayed(), ALL_ITEMS_LINK_MISSING_MESSAGE);
        Assert.assertEquals(menuInventoryButton.getText(), ALL_ITEMS_LINK_TEXT, GenericMessages.DIFFERENT_TEXT);

        waitClickable(driver, menuAboutButton, 2);
        Assert.assertTrue(menuAboutButton.isDisplayed(), ABOUT_LINK_MISSING_MESSAGE);
        Assert.assertEquals(menuAboutButton.getText(), ABOUT_LINK_TEXT, GenericMessages.DIFFERENT_TEXT);

        waitClickable(driver, menuLogoutButton, 2);
        Assert.assertTrue(menuLogoutButton.isDisplayed(), LOGOUT_LINK_MISSING_MESSAGE);
        Assert.assertEquals(menuLogoutButton.getText(), LOGOUT_LINK_TEXT, GenericMessages.DIFFERENT_TEXT);

        waitClickable(driver, menuResetButton, 2);
        Assert.assertTrue(menuResetButton.isDisplayed(), RESET_APP_STATE_LINK_MISSING_MESSAGE);
        Assert.assertEquals(menuResetButton.getText(), RESET_APP_STATE_LINK_TEXT, GenericMessages.DIFFERENT_TEXT);
    }


    /* Click method for Menu item - "All Items" */
    public void clickMenuInventoryButton() {
        menuInventoryButton.click();
    }

    /* Click and check method for Menu item - "About" */
    public void clickMenuAboutButton() {
        menuAboutButton.click();

        /* Check if URL is correct, print in console and logFile message: "Redirect is successful!" OR "Redirect failed!" */
        if (driver.getCurrentUrl().equals(ABOUT_LINK_URL)) {
            System.out.println(REDIRECT_IS_SUCCESSFUL);
            MyFileWriter.writeToLog(REDIRECT_IS_SUCCESSFUL);
        } else {
            System.out.println(REDIRECT_FAILED);
            MyFileWriter.writeToLog(REDIRECT_FAILED);
            Assert.assertEquals(driver.getCurrentUrl(), ABOUT_LINK_URL, REDIRECT_FAILED);
        }
    }

    /* Click method for Menu item - "Logout" */
    public void clickMenuLogoutButton() {
        menuLogoutButton.click();
    }

    /* Click method for Menu item - "Reset App State" */
    public void clickMenuResetButton() {
        menuResetButton.click();
    }

    /* This method extract only prices from WebElement without any other text */
    public static double extractPriceFromElement(WebElement element) {

        String elementText = element.getText();

        /* Matches one or more digits */
        Pattern pattern = Pattern.compile("\\d+\\.\\d+");
        Matcher matcher = pattern.matcher(elementText);

        if (matcher.find()) {
            String extractedPrice = matcher.group();
            return Double.parseDouble(extractedPrice);
        }

        return 0;
    }

    /* This method fill originalPriceList with product prices */
    public void getOriginalPriceList() {

        /* Go through all products */
        for (WebElement product : productListLocal) {

            /* Take element - Price */
            WebElement itemPrice = product.findElement(By.cssSelector("div[class='inventory_item_price']"));

            /* Extract only price from the WebElement, and add it to the list */
            originalPriceList.add(Double.valueOf(extractPriceFromElement(itemPrice)));

        }
    }

    /* This method fill productsAfterLoad list with product prices */
    public void getPriceListAfterLoad() {
        List<WebElement> productsAfterLoadList = driver.findElements(By.cssSelector("div[class='inventory_item']"));

        /* Go through all products */
        for (WebElement product : productsAfterLoadList) {

            /* Take element - Price */
            WebElement itemPrice = product.findElement(By.cssSelector("div[class='inventory_item_price']"));

            /* Extract only price from the WebElement, and add it to the list */
            productsAfterLoad.add(Double.valueOf(extractPriceFromElement(itemPrice)));

        }
    }

    /* This method clear productsAfterLoad list data */
    public void clearProductsAfterLoadList() {
        productsAfterLoad.clear();
    }

    /* This method select sorting product by price Low to High from dropdown */
    public void clickProductSortingByPriceLowHigh() {
        new Select(sortingDropdown).selectByValue(SORT_BY_PRICE_LOW_TO_HIGH_TEXT);

    }

    /* This method select sorting product by price High to Low from dropdown */
    public void clickProductSortingByPriceHighLow() {
        new Select(sortingDropdown).selectByValue(SORT_BY_PRICE_HIGH_TO_LOW_TEXT);
    }

    /* Method who validate sorting Low to High prices is correct */
    public void validateProductsPricesAreAsc() {
        boolean isAscending = true;
        for (int i = 0; i < productsAfterLoad.size() - 1; i++) {
            /* Check if current i is greater than next, if it is return false */
            if (productsAfterLoad.get(i) > productsAfterLoad.get(i + 1)) {
                isAscending = false;
                break;
            }
        }

        Assert.assertTrue(isAscending, PRICES_ASC_ERROR_MESSAGE);
    }

    /* Method who validate sorting High to Low prices is correct */
    public void validateProductsPricesAreDesc() {
        boolean isDescending = true;
        for (int i = 0; i < productsAfterLoad.size() - 1; i++) {
            /* Check if current i is greater than next, if it is return false */
            if (productsAfterLoad.get(i) < productsAfterLoad.get(i + 1)) {
                isDescending = false;
                break;
            }
        }

        Assert.assertTrue(isDescending, PRICES_DESC_ERROR_MESSAGE);
    }

    /* Method who take, compare and clear the price list after sorting */
    public void sortingValidator() {
        getOriginalPriceList();
        clickProductSortingByPriceLowHigh();
        getPriceListAfterLoad();
        validateProductsPricesAreAsc();

        clearProductsAfterLoadList();

        clickProductSortingByPriceHighLow();
        getPriceListAfterLoad();
        validateProductsPricesAreDesc();
    }

}
