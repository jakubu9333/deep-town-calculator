package com.jakubu9333.deeptowncalc;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author Jakub Uhlarik
 */
public class CsvReader {
    private static final String DELIMETER = ";";

    public static String readLine(BufferedReader br) throws IOException {
        return br.readLine();
    }

    public static void readHeader(BufferedReader br) throws IOException {
        readLine(br);
    }

    public static String[] splitLine(String line){
        return line.split(DELIMETER);
    }
}
