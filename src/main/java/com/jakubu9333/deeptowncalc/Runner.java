package com.jakubu9333.deeptowncalc;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Jakub Uhlarik
 */
public class Runner {
    private static final String RESOURCES_FILE = "src/main/resources/resources.csv";
    private static final String RECIPES_FILE = "src/main/resources/recipes.csv";
    private static final String ADV_RECIPES_FILE = "src/main/resources/AdvancedRecipes.csv";
    private static int howMany = 0;

    public static void main(String[] args) throws IOException {

        Map<Resource, Recipe> recipeMap = ResourceDataReader.initializeRecipeMap(new File(RESOURCES_FILE));
        Resource dummyResource = new Resource("0",CraftingMethod.RAW,0);
        recipeMap.put(dummyResource,null);
        RecipeReader.addRecipesFromFile(new File(RECIPES_FILE), recipeMap);
        RecipeReader.addRecipesFromFile(new File(ADV_RECIPES_FILE), recipeMap);
        findFakeRecipes(recipeMap);
        findMissingRecipes(recipeMap);

    }

    private static void findFakeRecipes(Map<Resource, Recipe> recipeMap) {
        howMany=0;
        recipeMap.forEach((key, value) -> fakeRecipes(value, recipeMap));
        System.out.println(howMany + " fake recipes");
    }

    private static void fakeRecipes(Recipe recipe, Map<Resource, Recipe> recipeMap) {
        if (recipe == null) {
            return;
        }
        for (Resource resource : recipe.getInput().keySet()) {
            if (recipeMap.getOrDefault(resource, null) == null) {
                if (recipeMap.keySet().stream().filter(p -> p.equals(resource)).collect(Collectors.toList()).size() < 1) {
                    System.out.println(recipe + " " + resource);
                    howMany+=1;
                }


            }
        }
    }

    private static void findMissingRecipes(Map<Resource, Recipe> recipeMap) {
        howMany=0;
        recipeMap.forEach(Runner::missingRecipes);
        System.out.println(howMany + " missing recipes");
    }

    private static void missingRecipes(Resource resource, Recipe recipe) {
        if (recipe == null && resource.getHowCrafted() != CraftingMethod.RAW) {
            System.out.println(resource);
            howMany += 1;
        }

    }


}
