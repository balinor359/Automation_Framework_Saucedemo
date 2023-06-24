package com.saucedemo.tests.menu;

import com.saucedemo.pom.HomePage;
import com.saucedemo.pom.LoginPage;
import com.saucedemo.pom.ProductPage;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import com.saucedemo.utilities.UserBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC8_4_2_Test_menu_link_reset_app_state_product_page extends TestUtilities {
    @Test
    public void testMenuResetLinkFromProductPage() {
        MyFileWriter.writeToLog("TC8.4.1: Test menu link reset app state product page");

        LoginPage loginPage = new LoginPage(TestUtilities.driver);
        loginPage.loginPageValidator();
        HomePage homePage = loginPage.testUserLogin(UserBuilder.fullUsersList.get(0));
        homePage.homepageValidator();

        homePage.selectProduct("Sauce Labs Fleece Jacket");

        ProductPage productPage = homePage.openProductPage();
        productPage.productPageValidator();

        //todo Грешно ли е че викам метода от homepage? Трябвав ли да го има същия метод и в productPage? или така е по-добрия вариант?
        homePage.addItemToTheCart("fleece-jacket");

        Assert.assertEquals(homePage.getItemsInTheCart(),1, HomePage.CART_BADGE_WRONG_AMOUNT);

        homePage.menuLinksValidator();
        homePage.clickMenuResetButton();

        Assert.assertEquals(homePage.getItemsInTheCart(),0, HomePage.CART_BADGE_WRONG_AMOUNT);

        homePage.addToCartButtonValidator("fleece-jacket");
        //todo Логически теста е верен, проблема е в сайта. Така ли трябва да се остави теста, да не успява да намери елемент?
        //как трябва да се обработи? Достатъчно ли е съобщението което извеждам с try/catch? - но така теста минава!


    }
}
