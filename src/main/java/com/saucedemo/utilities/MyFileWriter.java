package com.saucedemo.utilities;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/* This class create object who write messages to logFile.txt with wanted time format */
public class MyFileWriter {

    public static void writeToLog(String msg) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("log\\logFile.txt", true));
            LocalDateTime currentDateTime = LocalDateTime.now();
            String format = currentDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

            writer.append(format + " : ");
            writer.append(msg + "\n");
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Exception writing logs: " + e.getMessage());
        }
    }

}
