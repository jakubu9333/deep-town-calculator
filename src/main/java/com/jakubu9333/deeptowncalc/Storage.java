package com.jakubu9333.deeptowncalc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Jakub Uhlarik
 */
public class Storage {

    private final Map<Resource,Integer> storage=new HashMap<>();
    private static Map<String,Resource> resourceMap;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public Storage(){
    }
    public Storage(File file){
        try (Reader reader = Files.newBufferedReader(file.toPath());){

            // convert JSON file to map
            Map<?, ?> map = gson.fromJson(reader, Map.class);
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                storage.put(resourceMap.get(entry.getKey()), ((int)(double) entry.getValue()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeJson(OutputStream os) {

        PrintStream ps = new PrintStream(os);
        String jsonOutput = gson.toJson(storage);
        ps.print(jsonOutput);


    }

    public void addToStorage(String name,int amount){
        Resource resource = resourceMap.get(name);
        if (resource==null){
            return;
        }
        Integer resourcesNow = storage.get(resource);
        if (resourcesNow!=null){
            amount+=resourcesNow;
        }
        storage.put(resource,amount);
    }

    public static void setResourceMap(Map<String, Resource> resourceMap) {
        Storage.resourceMap = resourceMap;
    }
}
