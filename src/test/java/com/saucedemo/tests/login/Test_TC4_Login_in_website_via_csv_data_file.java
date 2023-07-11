package com.saucedemo.tests.login;

import com.opencsv.exceptions.CsvException;
import com.saucedemo.pom.HomePage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.saucedemo.pom.LoginPage;
import com.saucedemo.utilities.CsvHelper;
import com.saucedemo.utilities.MyFileWriter;
import com.saucedemo.utilities.TestUtilities;

import java.io.IOException;

public class Test_TC4_Login_in_website_via_csv_data_file extends TestUtilities {

    /* The test uses the generated users from CSV file, initializes the web driver using the LoginPage object, call login page validator,
     * then calls the login method provided users from CSV, after that validation method from the Homepage object to check if user successful longed in
     */

    /* Load CSV File */
    @DataProvider(name = "csvDataFile")
    public static Object[][] readUsersFromCsvFile() throws IOException, CsvException {
        return CsvHelper.readCsvFile("saucedemo-files\\TestData.csv");
    }

    /* Read form provided CSV file */
    @Test(dataProvider = "csvDataFile")
    public void loginWithCsvFileDataInput(String username, String password) {
        /* Print info data to log file */
        MyFileWriter.writeToLog("TC4: Login in website via csv data file");

        LoginPage loginPage = new LoginPage(TestUtilities.driver);
        loginPage.loginPageValidator();

        HomePage homePage = loginPage.login(username, password);
        homePage.homepageValidator();

    }
}
