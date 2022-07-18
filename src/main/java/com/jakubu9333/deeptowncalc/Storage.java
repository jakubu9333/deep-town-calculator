package com.jakubu9333.deeptowncalc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    private static File dictionary;
    private final Map<Resource,Integer> storage=new HashMap<>();
    private final String name;
    private static Map<String,Resource> resourceMap;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public Storage(){
        this("def",false);
    }
    public Storage(String name,boolean load){
        this.name=name;
        if (load){
            this.load();
        }
    }

    public void load(){
        File file=new File(dictionary,name+".json");
        if (!file.exists()){
            return;
        }
        try (Reader reader = Files.newBufferedReader(file.toPath())){

            // convert JSON file to map
            Map<?, ?> map = gson.fromJson(reader, Map.class);
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                storage.put(resourceMap.get(entry.getKey()), ((int)(double) entry.getValue()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void save() throws IOException {
        File SaveFile = new File(dictionary,name+".json");
        if (!SaveFile.exists()){
            boolean created = SaveFile.createNewFile();
            if (!created){
                throw new IOException("cant create file");
            }
        }
        try ( OutputStream fs = new FileOutputStream(SaveFile)){
            writeJson(fs);
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

    public static void setDictionary(File dictionary){
        Storage.dictionary=dictionary;
    }
}
