package com.jakubu9333.deeptowncalc;

import java.util.Objects;

/**
 * @author Jakub Uhlarik
 */
public class Resource {
    private String name;
    private int cost;
    private CraftingMethod howCrafted;

    @Override
    public String toString() {
        return name;
    }
    public String getName(){
        return name;
    }

    public Resource(String name, CraftingMethod howCrafted, int cost){
        this.cost=cost;
        this.name=name.toLowerCase();
        this.howCrafted=howCrafted;
    }

    public CraftingMethod getHowCrafted() {
        return howCrafted;
    }

    public Resource(String name){
       this(name,CraftingMethod.UNKNOWN,0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return Objects.equals(name.toLowerCase(), resource.name.toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase());
    }
}
