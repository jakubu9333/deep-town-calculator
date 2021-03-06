package com.jakubu9333.deeptowncalc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jakub Uhlarik
 */
public class Recipe {
    private int seconds;
    private int depth;
    private CraftingBuilding craftingBuilding;
    private Map<Resource,Double> input;
    private String name;

    public Map<Resource, Double> getInput() {
        return Collections.unmodifiableMap(input);
    }

    private int calculateTime(String time){
        int result=0;
        String[] split=time.split(" ");
        for (int i = 0; i < split.length; i+=2) {
            String units=split[i+1];
            int number=Integer.parseInt(split[i]);
            if (units.equals("Minute")|| units.equals("Minutes")){
                result+=number*60;
            }else if (units.equals("Seconds") || units.equals("Second")){
                result+=number;
            }else if (units.equals("Hour")|| units.equals("Hours")){
                result+=3600*number;
            }
        }
        return result;
    }
    public Recipe(String name, String time, int depth, CraftingBuilding craftingBuilding, String input) {
        this.name = name.toLowerCase();
        this.input=new HashMap<>();
        this.seconds = calculateTime(time);
        this.depth = depth;
        this.craftingBuilding = craftingBuilding;
        String[] resources=input.split(",");
        for (String resource:resources) {
            String [] splitRes= resource.split(" x ");
            this.input.put(new Resource(splitRes[0]),Double.parseDouble(splitRes[1]));
        }
    }

    @Override
    public String toString(){
        return name;
    }

    public String getName() {
        return name;
    }


}
