package com.saucedemo.utilities;

import com.opencsv.exceptions.CsvException;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/* This class create CsvHelper Object who read csv File row by row and save the data into Object array ,with 2 elements each (username, password) */
public class CsvHelper {

    public static Object[][] readCsvFile(String fileName) throws IOException, CsvException {
        /* Create new CSVReader object who read specific file */
        CSVReader csvReader = new CSVReader(new FileReader(fileName));
        /* Create new array list to store all data from the file */
        List<String[]> csvData = csvReader.readAll();
        /* Save every row from file in 2 values in each object */
        Object[][] result = new Object[csvData.size()][2];
        /* Read all results from the csvData list and return the result */
        for (int i = 0; i < csvData.size(); i++) {
            result[i] = csvData.get(i);
        }
        return result;

    }

}
