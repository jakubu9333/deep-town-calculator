package com.jakubu9333.deeptowncalc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * @author Jakub Uhlarik
 */
public class RecipeReader{
    private static final int NAME_INDEX = 0;
    private static final int BUILDING_INDEX = 1;
    private static final int TIME_INDEX = 2;
    private static final int DEPTH_INDEX =4;
    private static final int INPUT_INDEX = 5;





    private static Recipe splitLineIntoRecipe(String line){
        if (line == null) {
            return null;
        }
        String[] splitLine = Reader.splitLine(line);
        CraftingBuilding building = switch (splitLine[BUILDING_INDEX]) {
            case "Smelting" -> CraftingBuilding.SMELTING;
            case "Chemistry" -> CraftingBuilding.CHEMISTRY;
            case "Greenhouse" -> CraftingBuilding.GREENHOUSE;
            case "Jewel Crafting" -> CraftingBuilding.JEWELER;
            case "Uranium Enrichment" -> CraftingBuilding.URANIUM;
            case "Crafting" -> CraftingBuilding.CRAFTING;
            case "Find/Buy" -> CraftingBuilding.FINDorBUY;
            case "Event"->CraftingBuilding.EVENT;
            case "Ground"->CraftingBuilding.GROUND;
            default -> CraftingBuilding.UNKNOWN;
        };
        return new Recipe(splitLine[NAME_INDEX],splitLine[TIME_INDEX],
                Integer.parseInt(splitLine[DEPTH_INDEX]),building,splitLine[INPUT_INDEX]);
    }


    public static void addRecipesFromFile(File file, Map<Resource,Recipe> resourceRecipeMap,Map<String,Resource> resourceMap) throws IOException {
        try (BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            Reader.readHeader(buf);
            boolean run = true;
            while (run) {
                Recipe newRecipe = splitLineIntoRecipe(Reader.readLine(buf));
                if (newRecipe == null) {
                    run = false;
                } else {
                    resourceRecipeMap.put(resourceMap.get(newRecipe.getName()),newRecipe);
                }
            }
        }

    }

}
