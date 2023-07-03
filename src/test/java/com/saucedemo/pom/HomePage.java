package com.saucedemo.pom;

import com.saucedemo.objects.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import org.openqa.selenium.support.Color;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends TestUtilities {
    protected WebDriver driver;
    private static final String ADD_TO_CART_LOCATOR = "//button[@id='add-to-cart-%s']";
    private static final String ADD_TO_CART_LOCATOR_ALL = "//button[contains(@id,'add-to-cart-')]";
    private static final String ADD_TO_CART_TEXT = "Add to cart";
    private static final String ADD_TO_CART_MISSING_MESSAGE = "'Add to cart' button is not displayed";
    private static final String ADD_TO_CART_FONT_COLOR = "#132322";
    private static final String ADD_TO_CART_BORDER_COLOR = "#132322";
    private static final String REMOVE_FROM_CART_LOCATOR = "//button[@id='remove-%s']";
    private static final String REMOVE_FROM_CART_LOCATOR_ALL = "//button[contains(@id,'remove-')]";
    private static final String REMOVE_BUTTON_TEXT = "Remove";
    private static final String REMOVE_BUTTON_FONT_COLOR = "#e2231a";
    private static final String REMOVE_BUTTON_BORDER_COLOR = "#e2231a";
    private static final String REMOVE_BUTTON_MISSING_MESSAGE = "'Remove' button is not displayed";
    private static final String HOME_PAGE_URL = "https://www.saucedemo.com/inventory.html";
    private static final String HOME_PAGE = "Current page is Home page.";
    private static final String HOME_PAGE_ERROR = "Home page loading Failed.";
    private static final String PRODUCTS_LIST_MISSING_MESSAGE = "Products list is not displayed!";
    private static final String MENU_BUTTON_MISSING_MESSAGE = "Menu button is not displayed!";
    private static final String SHOPPING_CART_MISSING_MESSAGE = "Shopping cart is not displayed!";
    private static final String TWITTER_LINK_URL = "https://twitter.com";
    private static final String FACEBOOK_LINK_URL = "https://www.facebook.com/saucelabs";
    private static final String LINKEDIN_LINK_URL = "https://www.linkedin.com";
    private static final String DIFFERENT_TEXT = "The text is different!";
    private static final String DIFFERENT_CSS_VALUE = "The CSS value is different!";
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
    public static final String CART_BADGE_WRONG_AMOUNT = "Cart have different amount of products!";
    public static final String CODE_ERROR_ADD_TO_CART_BUTTON = "Code error: the 'Add to Cart' button you interact with does not exist!";
    public static final String CODE_ERROR_REMOVE_BUTTON = "Code error: the 'Remove' button you interact with does not exist!";
    public static final Object RETURN_NULL_OBJECT = null;

//    public static String selectedProductName = "";
//    public static String selectedProductPrice = "";
//    public static String selectedProductImageSrc = "";


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

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

//    public ProductPage loadProductPageElements() {
//        return new ProductPage(driver);
//    }

    public void addItemToTheCartSimpleClick(String productNameUrl) {
        addToCartButtonValidator(productNameUrl);

        String xpathOfClickedElement = String.format(ADD_TO_CART_LOCATOR, productNameUrl);
        WebElement addToCartButton = driver.findElement(By.xpath(xpathOfClickedElement));
        addToCartButton.click();

        removeButtonValidator(productNameUrl);
    }

    public void addItemToTheCartAndSaveValues(String productNameUrl) {
        addToCartButtonValidator(productNameUrl);

        String xpathOfClickedElement = String.format(ADD_TO_CART_LOCATOR, productNameUrl);
        WebElement addToCartButton = driver.findElement(By.xpath(xpathOfClickedElement));

//        List<WebElement> products = driver.findElements(By.xpath("//div[@class='inventory_item']"));

        /* go through all products */
        for (WebElement product : productListLocal) {

            WebElement productNameUrlButton = product.findElement(By.cssSelector("button.btn_inventory"));
            String productNameUrlLocal = productNameUrlButton.getAttribute("id");

            if (productNameUrlLocal.contains(productNameUrl)) {
                /* get child element - Name */
                WebElement childName = product.findElement(By.cssSelector("div[class='inventory_item_name']"));

                /* get child element - Price */
                WebElement productPrice = product.findElement(By.cssSelector("div[class='inventory_item_price']"));

                /* get child element - Image Src*/
                WebElement productImageSrc = product.findElement(By.cssSelector("img[class='inventory_item_img']"));

                /* Create new product with name, price and image src, and add it to the product list */
                Product newProduct = new Product(childName.getText(), productPrice.getText(), productImageSrc.getAttribute("src"));
                Product.productList.add(newProduct);

                addToCartButton.click();
                removeButtonValidator(productNameUrl);
            }
        }
    }

    public void removeItemFromTheCart(String productName) {
        removeButtonValidator(productName);

        String xpathOfClickedElement = String.format(REMOVE_FROM_CART_LOCATOR, productName);
        WebElement removeButton = driver.findElement(By.xpath(xpathOfClickedElement));
        removeButton.click();

        addToCartButtonValidator(productName);
    }

    public void addAllItemToTheCart() {

        /* go through all products */
        for (WebElement product : productListLocal) {

            /* get element - Name */
            WebElement itemName = product.findElement(By.cssSelector("div[class='inventory_item_name']"));

            /* get element - Price */
            WebElement itemPrice = product.findElement(By.cssSelector("div[class='inventory_item_price']"));

            /* get child element - Image Src*/
            WebElement itemImageSrc = product.findElement(By.cssSelector("img[class='inventory_item_img']"));

            /* get element - Add to Cart Button */
            WebElement ItemAddToCartBtn = product.findElement(By.cssSelector("button.btn_primary"));

            ItemAddToCartBtn.click();

            /* Create new product with name, price and image src, and add it to the product list */
            Product newProduct = new Product(itemName.getText(), itemPrice.getText(), itemImageSrc.getAttribute("src"));
            Product.productList.add(newProduct);


            try {
                /* get element - Remove Button */
                WebElement ItemRemoveBtn = product.findElement(By.cssSelector("button.btn_secondary"));
                String ItemRemoveBtnId = ItemRemoveBtn.getAttribute("Id");

                removeButtonValidatorWholeUrl(ItemRemoveBtnId);

            } catch (NoSuchElementException e) {
                System.out.println(CODE_ERROR_REMOVE_BUTTON);
                MyFileWriter.writeToLog(CODE_ERROR_REMOVE_BUTTON);

                Assert.fail(CODE_ERROR_REMOVE_BUTTON);
            }

        }

    }

    public void addToCartButtonValidator(String productNameUrl) {
        try {
            String xpathOfClickedElement = String.format(ADD_TO_CART_LOCATOR, productNameUrl);
            WebElement addToCartButton = driver.findElement(By.xpath(xpathOfClickedElement));

            Assert.assertTrue(addToCartButton.isDisplayed(), ADD_TO_CART_MISSING_MESSAGE);
            Assert.assertEquals(addToCartButton.getText(), ADD_TO_CART_TEXT, DIFFERENT_TEXT);

            String fontColor = addToCartButton.getCssValue("color");
            String fontColorHex = Color.fromString(fontColor).asHex();
            Assert.assertEquals(fontColorHex, ADD_TO_CART_FONT_COLOR, DIFFERENT_CSS_VALUE);

            String elementBorderColor = addToCartButton.getCssValue("border-color");
            String elementBorderColorHex = Color.fromString(elementBorderColor).asHex();
            Assert.assertEquals(elementBorderColorHex, ADD_TO_CART_BORDER_COLOR, DIFFERENT_CSS_VALUE);

        } catch (NoSuchElementException e) {
            System.out.println(CODE_ERROR_ADD_TO_CART_BUTTON);
            MyFileWriter.writeToLog(CODE_ERROR_ADD_TO_CART_BUTTON);

            Assert.fail(CODE_ERROR_ADD_TO_CART_BUTTON);
        }
    }

    public void removeButtonValidator(String productNameUrl) {
        try {
            String xpathOfClickedElement = String.format(REMOVE_FROM_CART_LOCATOR, productNameUrl);
            WebElement removeButton = driver.findElement(By.xpath(xpathOfClickedElement));

            Assert.assertTrue(removeButton.isDisplayed(), REMOVE_BUTTON_MISSING_MESSAGE);
            Assert.assertEquals(removeButton.getText(), REMOVE_BUTTON_TEXT, DIFFERENT_TEXT);

            String fontColor = removeButton.getCssValue("color");
            String fontColorHex = Color.fromString(fontColor).asHex();
            Assert.assertEquals(fontColorHex, REMOVE_BUTTON_FONT_COLOR, DIFFERENT_CSS_VALUE);

            String elementBorderColor = removeButton.getCssValue("border-color");
            String elementBorderColorHex = Color.fromString(elementBorderColor).asHex();
            Assert.assertEquals(elementBorderColorHex, REMOVE_BUTTON_BORDER_COLOR, DIFFERENT_CSS_VALUE);

        } catch (NoSuchElementException e) {
            System.out.println(CODE_ERROR_REMOVE_BUTTON);
            MyFileWriter.writeToLog(CODE_ERROR_REMOVE_BUTTON);

            Assert.fail(CODE_ERROR_REMOVE_BUTTON);
        }
    }
    public void removeButtonValidatorWholeUrl(String productNameUrl) {
        try {
            String xpathOfClickedElement = String.format("//button[@id='%s']", productNameUrl);
            WebElement removeButton = driver.findElement(By.xpath(xpathOfClickedElement));

            Assert.assertTrue(removeButton.isDisplayed(), REMOVE_BUTTON_MISSING_MESSAGE);
            Assert.assertEquals(removeButton.getText(), REMOVE_BUTTON_TEXT, DIFFERENT_TEXT);

            String fontColor = removeButton.getCssValue("color");
            String fontColorHex = Color.fromString(fontColor).asHex();
            Assert.assertEquals(fontColorHex, REMOVE_BUTTON_FONT_COLOR, DIFFERENT_CSS_VALUE);

            String elementBorderColor = removeButton.getCssValue("border-color");
            String elementBorderColorHex = Color.fromString(elementBorderColor).asHex();
            Assert.assertEquals(elementBorderColorHex, REMOVE_BUTTON_BORDER_COLOR, DIFFERENT_CSS_VALUE);

        } catch (NoSuchElementException e) {
            System.out.println(CODE_ERROR_REMOVE_BUTTON);
            MyFileWriter.writeToLog(CODE_ERROR_REMOVE_BUTTON);

            Assert.fail(CODE_ERROR_REMOVE_BUTTON);
        }
    }

    public int getItemsInTheCart() {
        if (shoppingCartLink.getText().isEmpty()) {
            return 0;
        } else {
            return Integer.parseInt(shoppingCartBadge.getText());
        }

    }

//    /* method who navigate to Cart page and validate product items by submitted item name*/
//    public CartPage addProductToTheCart(String productName) {
//        System.out.println("test method");
//        /* get all products */
//        List<WebElement> products = driver.findElements(By.cssSelector("div[class='inventory_item']"));
//
//        /* go through all products */
//        for (WebElement product : products) {
////            System.out.println("test for");
//
//            /* get child element - Name */
//            WebElement childName = driver.findElement(By.cssSelector("div[class='inventory_item_name']"));
//
////            /* get parent element - Name */
////            WebElement parent = childName.findElement(By.xpath(".."));
//
//            /* get child element - Price */
//            WebElement productPrice = driver.findElement(By.cssSelector("div[class='inventory_item_price']"));
//
//            /* get child element - Image Src*/
//            WebElement productImageSrc = driver.findElement(By.cssSelector("img[class='inventory_item_img']"));
//
//            /* Save values for Name/Price/Image src for comparison in other pages */
//            selectedProductName = childName.getText();
//            selectedProductPrice = productPrice.getText();
//            selectedProductImageSrc = productImageSrc.getAttribute("src");
//
//            System.out.println("test for selectedProductName " + selectedProductName);
//            System.out.println("test for selectedProductPrice " + selectedProductPrice);
//            System.out.println("test for selectedProductImageSrc " + selectedProductImageSrc);
//            System.out.println("test for productName " + productName);
//
//            /* locate submitted product and click his product name link */
//            if (selectedProductName.contains(productName)) {
//                addItemToTheCart(productName);
////                parent.click();
//                System.out.println("test if");
//                return new CartPage(driver);
//            }
//        }
//        //todo така ли е правилно да се затвори метода с null?
//        return null;
//    }

    /* method who navigate to product page and validate product items by submitted item name*/
    public ProductPage selectProduct(String productName) {
        /* get all products */
//        List<WebElement> products = driver.findElements(By.cssSelector("div[class='inventory_item']"));

        /* go through all products */
        for (WebElement product : productListLocal) {

            /* get child element - Name */
            WebElement productTitle = product.findElement(By.cssSelector("div[class='inventory_item_name']"));

            /* get parent element - Name */
            WebElement productTitleParent = productTitle.findElement(By.xpath(".."));

            /* get child element - Price */
            WebElement productPrice = product.findElement(By.cssSelector("div[class='inventory_item_price']"));

            /* get child element - Image Src*/
            WebElement productImageSrc = product.findElement(By.cssSelector("img[class='inventory_item_img']"));

            /* Save values for Name/Price/Image src for comparison in other pages */

            /* locate submitted product and click his product name link */
            if (productTitle.getText().contains(productName)) {

                /* Create new product with name, price and image src, and add it to the product list */
                Product newProduct = new Product(productTitle.getText(), productPrice.getText(), productImageSrc.getAttribute("src"));
                Product.productList.add(newProduct);

                productTitleParent.click();

                return new ProductPage(driver);
            }
        }

        return (ProductPage) RETURN_NULL_OBJECT;
    }

    public CartPage clickOnCartButton() {
        shoppingCartLink.click();
        return new CartPage(driver);
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

            //todo Ако assertite ги няма, теста ще минава, не трябва ли да се остави да гръмне грешката за липсващ елемент,
            // ако се обработи с try/catch теста пак ще мине, единствено ще се изведе в конзолата съобщение, по кой от двата начина трябва да се процедира?
//            Assert.assertTrue(productsList.isDisplayed(), PRODUCTS_LIST_MISSING_MESSAGE);
//            Assert.assertTrue(menuButton.isDisplayed(), MENU_BUTTON_MISSING_MESSAGE);
//            Assert.assertTrue(shoppingCart.isDisplayed(), SHOPPING_CART_MISSING_MESSAGE);

            //todo или така е по-правилно да се направи, за да се считат за неуспешни?
            Assert.assertEquals(driver.getCurrentUrl(), HOME_PAGE_URL, HOME_PAGE_ERROR);
        }
    }

    public void socialLinksValidator() {
        /* Verify all social links in footer are displayed */
        Assert.assertTrue(footerTwitterLink.isDisplayed(), TWITTER_LINK_MISSING_MESSAGE);
        Assert.assertTrue(footerFacebookLink.isDisplayed(), FACEBOOK_LINK_MISSING_MESSAGE);
        Assert.assertTrue(footerLinkedinLink.isDisplayed(), LINKEDIN_LINK_MISSING_MESSAGE);


        /* Following waits are needed for test execution on firefox and edge */
        /* Wait link to be clickable, then click it, and wait web-driver to load it in new tab*/
        waitClickable(driver, footerTwitterLink, 5);
        footerTwitterLink.click();
        simpleWait(600);

        /* Wait link to be clickable, then click it, and wait web-driver to load it in new tab*/
        waitClickable(driver, footerFacebookLink, 5);
        footerFacebookLink.click();
        simpleWait(600);

        /* Wait link to be clickable, then click it, and wait web-driver to load it in new tab*/
        waitClickable(driver, footerLinkedinLink, 5);
        footerLinkedinLink.click();
        simpleWait(600);

        /* Array list with tabs */
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());

        /* Switch to wanted tab, then validate correctness of the external link */
        driver.switchTo().window(tabs.get(3));
        validateTwitterLink();

        /* Switch to wanted tab, then validate correctness of the external link */
        driver.switchTo().window(tabs.get(2));
        validateFacebookLink();

        /* Switch to wanted tab, then validate correctness of the external link */
        driver.switchTo().window(tabs.get(1));
        validateLinkedInLink();

    }

    public void validateTwitterLink() {

        if (driver.getCurrentUrl().contains(TWITTER_LINK_URL)) {
            System.out.println(REDIRECT_IS_SUCCESSFUL);
            MyFileWriter.writeToLog(REDIRECT_IS_SUCCESSFUL);
        } else {
            System.out.println(REDIRECT_FAILED);
            MyFileWriter.writeToLog(REDIRECT_FAILED);
            Assert.assertEquals(driver.getCurrentUrl(), TWITTER_LINK_URL, REDIRECT_FAILED);
        }
    }

    public void validateFacebookLink() {

        if (driver.getCurrentUrl().equals(FACEBOOK_LINK_URL)) {
            System.out.println(REDIRECT_IS_SUCCESSFUL);
            MyFileWriter.writeToLog(REDIRECT_IS_SUCCESSFUL);
        } else {
            System.out.println(REDIRECT_FAILED);
            MyFileWriter.writeToLog(REDIRECT_FAILED);
            Assert.assertEquals(driver.getCurrentUrl(), FACEBOOK_LINK_URL, REDIRECT_FAILED);
        }
    }

    public void validateLinkedInLink() {

        if (driver.getCurrentUrl().contains(LINKEDIN_LINK_URL)) {
            System.out.println(REDIRECT_IS_SUCCESSFUL);
            MyFileWriter.writeToLog(REDIRECT_IS_SUCCESSFUL);
        } else {
            System.out.println(REDIRECT_FAILED);
            MyFileWriter.writeToLog(REDIRECT_FAILED);
            Assert.assertEquals(driver.getCurrentUrl(), LINKEDIN_LINK_URL, REDIRECT_FAILED);
        }
    }

    /* method who validates existence and correctness of menu items */
    public void menuLinksValidator() {
        /* Click on hamburger menu */
        menuButton.click();

        /* Following code:
        - wait element to e clickable,
        - check if he is displayed,
        - check actual with expected link text */
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

    /* click method for Menu item - "Logout" */
    public void clickMenuLogoutButton() {
        menuLogoutButton.click();
    }

    /* click method for Menu item - "Reset App State" */
    public void clickMenuResetButton() {
        menuResetButton.click();
    }


}
