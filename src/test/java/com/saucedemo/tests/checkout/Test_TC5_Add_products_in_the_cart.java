package com.saucedemo.tests.checkout;

import com.saucedemo.pom.CartPage;
import com.saucedemo.pom.HomePage;
import com.saucedemo.pom.LoginPage;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import com.saucedemo.utilities.UserBuilder;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class Test_TC5_Add_products_in_the_cart extends TestUtilities {
    @Test
    public void verifyProductsAddedToTheCart() {
        MyFileWriter.writeToLog("TC5: Add products in the cart");

        LoginPage loginPage = new LoginPage(TestUtilities.driver);
        loginPage.loginPageValidator();
        HomePage homePage = loginPage.testUserLogin(UserBuilder.fullUsersList.get(0));
        homePage.homepageValidator();

        SoftAssert softAssert = new SoftAssert();//todo for delete

//        homePage.addItemToTheCart("sauce-labs-backpack");
//        homePage.addItemToTheCart("test.allthethings()-t-shirt-(red)");
        homePage.addItemToTheCart("Sauce Labs Backpack");
        homePage.addItemToTheCart("Test.allTheThings() T-Shirt (Red)");

        softAssert.assertEquals(homePage.getItemsInTheCart(),2, HomePage.CART_BADGE_WRONG_AMOUNT);

        CartPage cartPage = homePage.clickOnCartButton();
        cartPage.cartPageValidator();
//        softAssert.assertEquals(homePage.getItemsInTheCart(),6, HomePage.CART_BADGE_WRONG_AMOUNT);

//        homePage.selectProduct("Sauce Labs Bolt T-Shirt");

//        homePage.selectProduct("Sauce Labs Fleece Jacket");
        //todo в този случей какво се прави, след като има различен елемент и waitClicable спира целия тест?
        //така ли се оставя теста? завършен ли е ? той така или иначе ще гърми, или трябва да се вкара в POM файла елемента който по принцип не му е там мястото
        //в случея name="add-to-cart-item-not-found", и да се направи специално проверка за него за да го сравним с нормалния add to cart?

//        ProductPage productPage = homePage.openProductPage();
//        productPage.productPageValidator();
//        homePage.menuLinksValidator();
//        homePage.clickMenuInventoryButton();

//        softAssert.assertAll();//todo for delete

        simpleWait(2000);           //todo Ако я няма тази пауза излиза error-a


    }
}
