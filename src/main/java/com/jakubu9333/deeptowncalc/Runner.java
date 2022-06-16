package com.jakubu9333.deeptowncalc;

import java.io.File;
import java.io.IOException;

/**
 * @author Jakub Uhlarik
 */
public class Runner {
    private static final String RESOURCES_FILE="src/main/resources/resources.csv";
    private static final String RECIPES_FILE= "src/main/resources/recipes.csv";
    private static int howMany=0;
    public static void main(String[] args) throws IOException {

        var recipeMap =ResourceDataReader.initializeRecipeMap(new File(RESOURCES_FILE));
        RecipeReader.addRecipesFromFile(new File(RECIPES_FILE),recipeMap);
        recipeMap.forEach(Runner::missingRecipes);
        System.out.println(howMany+" missing recipes");
    }
    private static void missingRecipes(Resource resource, Recipe recipe){
        if (recipe==null && resource.getHowCrafted()!=CraftingMethod.RAW ){
            System.out.println(resource);
            howMany+=1;
        }

    }


}
