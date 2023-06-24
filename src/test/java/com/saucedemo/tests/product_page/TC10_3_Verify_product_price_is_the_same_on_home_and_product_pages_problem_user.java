package com.saucedemo.tests.product_page;

import com.saucedemo.pom.HomePage;
import com.saucedemo.pom.LoginPage;
import com.saucedemo.pom.ProductPage;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;
import com.saucedemo.utilities.UserBuilder;
import org.testng.annotations.Test;

public class TC10_3_Verify_product_price_is_the_same_on_home_and_product_pages_problem_user extends TestUtilities {

    @Test
    public void verifyProductPriceProblemUser() {
        MyFileWriter.writeToLog("TC10.3: Verify product price is the same on Home and Product pages (problem_user)");

        LoginPage loginPage = new LoginPage(TestUtilities.driver);
        loginPage.loginPageValidator();
        HomePage homePage = loginPage.testUserLogin(UserBuilder.fullUsersList.get(2));
        homePage.homepageValidator();
        homePage.selectProduct("Sauce Labs Bolt T-Shirt");
//        homePage.selectProduct("Sauce Labs Fleece Jacket");
        //todo в този случей какво се прави, след като има различен елемент и waitClicable спира целия тест?
        //така ли се оставя теста? завършен ли е ? той така или иначе ще гърми, или трябва да се вкара в POM файла елемента който по принцип не му е там мястото
        //в случея name="add-to-cart-item-not-found", и да се направи специално проверка за него за да го сравним с нормалния add to cart?

        ProductPage productPage = homePage.openProductPage();
        productPage.productPageValidator();
        homePage.menuLinksValidator();
        homePage.clickMenuInventoryButton();

        simpleWait(2000);           //todo Ако я няма тази пауза излиза error-a
        //org.openqa.selenium.remote.http.WebSocket$Listener onError WARNING: Connection reset
        homePage.homepageValidator();

    }
}
