package com.jakubu9333.deeptowncalc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jakub Uhlarik
 */
public class ResourceDataReader {
    private static final int NAME_INDEX = 0;
    private static final int CRAFTING_INDEX = 1;
    private static final int COST_INDEX = 2;


    private static Resource splitLineToResource(String line) {
        if (line == null) {
            return null;
        }
        String[] splitedLine = Reader.splitLine(line);
        CraftingMethod method = switch (splitedLine[CRAFTING_INDEX]) {
            case "Raw" -> CraftingMethod.RAW;
            case "Crafted" -> CraftingMethod.CRAFTED;
            case "Quest" -> CraftingMethod.QUEST;
            case "Organic" -> CraftingMethod.ORGANIC;
            case "Chemical" -> CraftingMethod.CHEMICAL;
            default -> CraftingMethod.UNKNOWN;
        };
        return new Resource(splitedLine[NAME_INDEX], method, Integer.parseInt(splitedLine[COST_INDEX]));
    }


    public static Map<String,Resource> initializeResourceMap(File file) throws IOException {
        Map<String,Resource> result = new HashMap<>();
        try (BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            Reader.readHeader(buf);
            boolean run = true;
            while (run) {
                Resource newResource = splitLineToResource(Reader.readLine(buf));
                if (newResource == null) {
                    run = false;
                } else {
                    result.put(newResource.getName(),newResource);
                }
            }
        }
        return result;
    }


}
