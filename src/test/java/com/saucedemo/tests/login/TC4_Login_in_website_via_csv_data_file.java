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

public class TC4_Login_in_website_via_csv_data_file extends TestUtilities {

    //Load CSV File
    @DataProvider(name = "csvDataFile")
    public static Object[][] readUsersFromCsvFile() throws IOException, CsvException {
        return CsvHelper.readCsvFile("saucedemo-files\\TestData.csv");
    }

    //todo  IN THE ENT COMMENTS!
    //The test uses the DataProvider, initializes the web driver using the LoginPage object,
    // then calls the login method, then the validation method from the Homepage object
    @Test(dataProvider = "csvDataFile")
    public void loginWithCsvFileDataInput(String username, String password) {
        MyFileWriter.writeToLog("TC4: Login in website via csv data file");
        LoginPage loginPage = new LoginPage(TestUtilities.driver);
        loginPage.loginPageValidator();
        HomePage homePage = loginPage.login(username, password);
        homePage.homepageValidator();
    }
}
