package com.jakubu9333.deeptowncalc;

import java.io.File;
import java.io.IOException;

/**
 * @author Jakub Uhlarik
 */
public class Runner {
    private static final String RESOURCES_FILE="src/main/resources/resources.csv";
    private static final String RECIPES_FILE= "src/main/resources/recipes.csv";
    public static void main(String[] args) throws IOException {
        var xd =ResourceDataReader.getAllResources(new File(RESOURCES_FILE));
        int a = 1;
    }

}
