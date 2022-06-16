package com.jakubu9333.deeptowncalc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jakub Uhlarik
 */
public class ResourceDataReader {
    private static final int NAME_INDEX = 0;
    private static final int CRAFTING_INDEX = 1;
    private static final int COST_INDEX = 2;
    private static final String DELIMETER = ";";

    private static String readLine(BufferedReader br) throws IOException {
        return br.readLine();
    }

    private static void readHeader(BufferedReader br) throws IOException {
        readLine(br);
    }

    private static Resource splitLineToResource(String line) {
        if (line == null) {
            return null;
        }
        String[] splitedLine = line.split(DELIMETER);
        CraftingMethod method = CraftingMethod.UNKNOWN;
        switch (splitedLine[CRAFTING_INDEX]) {
            case "Raw":
                method = CraftingMethod.RAW;
                break;
            case "Crafted":
                method = CraftingMethod.CRAFTED;
                break;
            case "Quest":
                method = CraftingMethod.QUEST;
                break;
            case "Organic":
                method = CraftingMethod.ORGANIC;
                break;
            case "Chemical":
                method = CraftingMethod.CHEMICAL;
                break;
            default:
                method = CraftingMethod.UNKNOWN;
        }
        return new Resource(splitedLine[NAME_INDEX], method, Integer.parseInt(splitedLine[COST_INDEX]));
    }


    public static List<Resource> getAllResources(File file) throws IOException {
        List<Resource> result = new ArrayList<>();
        try (BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            readHeader(buf);
            boolean run = true;
            while (run) {
                Resource newResource = splitLineToResource(readLine(buf));
                if (newResource == null) {
                    run = false;
                } else {
                    result.add(newResource);
                }
            }
        }
        return result;
    }


}
