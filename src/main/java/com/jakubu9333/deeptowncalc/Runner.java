package com.jakubu9333.deeptowncalc;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Jakub Uhlarik
 */
public class Runner {
    private static final String RESOURCES_FILE = "src/main/resources/resources.csv";
    private static final String RECIPES_FILE = "src/main/resources/recipes.csv";
    private static final String ADV_RECIPES_FILE = "src/main/resources/AdvancedRecipes.csv";
    private static final String STORAGE_DICTIONARY = "src/main/resources/Storages";
    private static int howMany = 0;

    private static Map<String, Resource> resourceMap;
    private static Map<Resource,Recipe> recipeMap;

    public static void main(String[] args) throws IOException {
        initResourceMap();
        initRecipeMap();
        Storage.setResourceMap(Runner.resourceMap);
        findFakeRecipes(recipeMap,resourceMap);
        findMissingRecipes(recipeMap,resourceMap);
        Storage.setDictionary(new File(STORAGE_DICTIONARY));
        Storage storageO = new Storage();
        storageO.addToStorage("Water",10000);
        storageO.addToStorage("Water",110);
        storageO.addToStorage("Copper",110);
        storageO.writeJson(System.out);

        Storage xd = new Storage("storage",true);
        xd.addToStorage("Oil",1000);
        xd.save();
        int a =1;

    }
    private static void initResourceMap() throws IOException {
        resourceMap = ResourceDataReader.initializeResourceMap(new File(RESOURCES_FILE));
        Resource dummyResource = new Resource("0",CraftingMethod.RAW,0);
        resourceMap.put("0",dummyResource);
    }
    private static void initRecipeMap() throws IOException {
        recipeMap=new HashMap<>();
        RecipeReader.addRecipesFromFile(new File(RECIPES_FILE), recipeMap,resourceMap);
        RecipeReader.addRecipesFromFile(new File(ADV_RECIPES_FILE), recipeMap,resourceMap);
    }


    private static void findFakeRecipes(Map<Resource, Recipe> recipeMap,Map<String,Resource> resourceMap) {
        howMany=0;
        recipeMap.forEach((key, value) -> fakeRecipes(value, recipeMap,resourceMap));
        System.out.println(howMany + " fake recipes");
    }

    private static void fakeRecipes(Recipe recipe, Map<Resource, Recipe> recipeMap, Map<String,Resource> resourceMap) {
        if (recipe == null) {
            return;
        }
        for (Resource resource : recipe.getInput().keySet()) {
            if (resourceMap.get(recipe.getName())==null){
                System.out.println(recipe+" "+resource);
                howMany+=1;
            }
        }
    }

    private static void findMissingRecipes(Map<Resource, Recipe> recipeMap,Map<String,Resource> resourceMap) {
        howMany=0;
        resourceMap.forEach((key,value)->missingRecipes(value,recipeMap));
        System.out.println(howMany + " missing recipes");
    }

    private static void missingRecipes(Resource resource, Map<Resource, Recipe> recipeMap) {
        if (resource.getHowCrafted() != CraftingMethod.RAW && recipeMap.get(resource) == null ) {
            System.out.println(resource);
            howMany += 1;
        }

    }

}
