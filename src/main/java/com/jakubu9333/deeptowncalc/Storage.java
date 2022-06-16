package com.jakubu9333.deeptowncalc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Map;


/**
 * @author Jakub Uhlarik
 */
public class Storage {

    private Map<Resource,Integer> storage;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public Storage(){

    }

    public void writeJson(OutputStream os) {

        PrintStream ps = new PrintStream(os);
        String jsonOutput = gson.toJson(storage);
        ps.print(jsonOutput);


    }

    public void addToStorage(Map<String,Resource> resourceMap,String name,int amount){
        Resource resource = resourceMap.get(name);
        if (resource==null){
            return;
        }
        storage.put(resource,amount);
    }
}
