package com.saucedemo.utilities;

import com.saucedemo.objects.TestUser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/* This class create object read data from TestDataUsers.csv */
public class UserBuilder extends TestUser {
    protected static ArrayList<String> emptyUsersList;

    public static ArrayList<TestUser> fullUsersList;

    public static ArrayList<TestUser> createUserList() {
        File file = new File("saucedemo-files\\TestDataUsers.csv");

        emptyUsersList = new ArrayList<>();
        fullUsersList = new ArrayList<>();

        /* Save the data into protected array */
        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                emptyUsersList.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File was not found. - " + e.getMessage());
        }

        /* Read all items from array and split data into username and password , then create new TestUser with every row of data
         * and save the data into public array */
        for (String user : emptyUsersList) {
            TestUser testUser;
            String[] userFields = user.split(",");
            testUser = new TestUser(userFields[0], userFields[1]);
            fullUsersList.add(testUser);
        }
        /* Return full user list*/
        return fullUsersList;
    }


}
