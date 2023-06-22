package com.saucedemo.utilities;

import com.saucedemo.objects.TestUser;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class UserBuilder extends TestUser {
    protected static ArrayList<String> emptyUsersList;

    public static ArrayList<TestUser> fullUsersList;

    public static ArrayList<TestUser> createUserList() {
        File file = new File("saucedemo-files\\TestDataUsers.csv");

        emptyUsersList = new ArrayList<>();
        fullUsersList = new ArrayList<>();

        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                emptyUsersList.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File was not found. - " + e.getMessage());
        }

        for (String user : emptyUsersList) {
            TestUser testUser;
            String[] userFields = user.split(",");
            testUser = new TestUser(userFields[0], userFields[1]);
            fullUsersList.add(testUser);
        }

        return fullUsersList;
    }


}
