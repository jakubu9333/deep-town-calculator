package com.jakubu9333.deeptowncalc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Jakub Uhlarik
 */
public class MainLoop {
    private boolean run = true;
    private final Map<String, Resource> resourceMap;
    private final Map<Resource,Recipe> recipeMap;

    public MainLoop(Map<String, Resource> resourceMap, Map<Resource, Recipe> recipeMap) {
        this.resourceMap=resourceMap;
        this.recipeMap=recipeMap;
    }


    public void run() throws IOException {
        Storage mainStorage = new Storage("main",true);

        Resource crafted = null;
        while (crafted==null){
            System.out.println("What do you need to craft? ");
            String line = readLineFromStdIn();
            if (line.equals("list")){
                System.out.println(resourceMap.keySet());
            }
            crafted = resourceMap.get((line).toLowerCase());

            if (crafted==null){
                System.out.println("Illegal request");
            }
            else if (noncraftable(crafted)){
                System.out.println("cant be crafted");
                crafted=null;
            }
        }

        System.out.println("How many?");
        int amount = Integer.parseInt(readLineFromStdIn());
        System.out.println("Need");
        Map<Resource,Double> neededMap = new HashMap<>();
        List<OutputText> output = new ArrayList<>();
        needed(crafted,amount,neededMap,output,0);
        output.forEach(System.out::println);

        mainStorage.save();

    }

    private boolean needed(Resource resource, double amount,Map<Resource,Double> neededMap,List<OutputText> output,int depth){
        if (noncraftable(resource)){
            return false;
        }

        Recipe recipe=recipeMap.get(resource);
        for (Resource childResource:recipe.getInput().keySet()){
            double childAmount= recipe.getInput().get(childResource)*amount;
            neededMap.put(childResource,childAmount);
            output.add(new OutputText(depth,childResource,childAmount));
            needed(childResource,childAmount,neededMap,output,depth+1);
        }
        return true;
    }
    private boolean noncraftable(Resource resource){
        Recipe recipe =recipeMap.get(resource);
        return  (resource.getHowCrafted()==CraftingMethod.RAW)|| recipe ==null||
                (recipe.getInput().containsKey(new Resource("0")));

    }
    public static String readLineFromStdIn() {
        var scanner =  new Scanner(System.in);
        return scanner.nextLine();
    }
}
