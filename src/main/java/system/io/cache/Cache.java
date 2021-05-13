package system.io.cache;

import org.json.JSONObject;
import system.io.filesystem.FileSystem;
import system.io.filesystem.SystemPaths;

import system.tools.utils.JsonUtils;
import system.tools.utils.StringUtils;

import java.io.File;
import java.io.IOException;

import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;


public class Cache{

    private static final TreeMap<String, String> cacheValues;

    static{
        cacheValues = new TreeMap<>();
        Cache.loadCacheIndex();
    }

    public static void setItem(final String key, final String value){

        File cacheFile = new File(Cache.getCacheFolder() + "/cache.ic");

        try{

            JSONObject content = new JSONObject();
            content.put(key, value);

            if(Cache.cacheValues.containsKey(key)){
                Scanner scanner = new Scanner(cacheFile);
                StringBuilder buffer = new StringBuilder();

                String oldItem = StringUtils.EMPTY;
                while(scanner.hasNextLine()){
                    String nextLine = scanner.nextLine();
                    if(JsonUtils.validJson(nextLine)){
                        JSONObject lineJson = new JSONObject(nextLine);
                        if(lineJson.has(key)){
                            if(lineJson.get(key).equals(value)){
                                return;
                            }
                            oldItem = lineJson.toString();
                        }
                    }
                    buffer.append(nextLine).append(System.lineSeparator());
                }


                String fileContent = buffer.toString();
                scanner.close();

                String newItem = content.toString();
                fileContent = fileContent.replace(oldItem, newItem);

                FileSystem.setFileContent(cacheFile, fileContent);

            }else{
                FileSystem.appendFileContent(cacheFile, content.toString() + System.lineSeparator());
            }

            Cache.cacheValues.put(key, value);

        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public static void removeItem(final String key){

        File cacheFile = new File(Cache.getCacheFolder() + "/cache.ic");
        Cache.cacheValues.remove(key);
        try{
            Scanner scanner = new Scanner(cacheFile);
            StringBuilder buffer = new StringBuilder();

            while(scanner.hasNextLine()){
                String nextLine = scanner.nextLine();
                if(JsonUtils.validJson(nextLine)){
                    JSONObject lineJson = new JSONObject();
                    if(!lineJson.has(key)){
                        buffer.append(nextLine).append(System.lineSeparator());
                    }
                }
            }

            String fileContent = buffer.toString();
            scanner.close();

            FileSystem.setFileContent(cacheFile, fileContent);
        }catch(IOException exception){
            exception.printStackTrace();
        }
    }

    public static String getItem(final String key){
        return Cache.cacheValues.get(key);
    }

    public static void clearCache(){
        try{
            Cache.cacheValues.clear();
            File cacheFile = new File(Cache.getCacheFolder() + "/cache.ic");
            FileSystem.removeFileContent(cacheFile);
        }catch(IOException exception){
            exception.printStackTrace();
        }
    }

    public static int getCacheValueCount(){
        return Cache.cacheValues.size();
    }

    private static File getCacheFolder(){
        return SystemPaths.CACHE.getFileName();
    }

    private static void createCacheFile() throws IOException{
        File cacheFile = new File(Cache.getCacheFolder() + "/cache.ic");

        if(!cacheFile.exists()){
            if(!cacheFile.createNewFile()){
                throw new IOException("cache file cannot be created");
            }
        }
    }

    private static void loadCacheIndex(){

        File cacheFile = new File(Cache.getCacheFolder() + "/cache.ic");
        try{
            if(!cacheFile.canRead()){
                if(!cacheFile.exists()){
                    Cache.createCacheFile();
                }

                if(!cacheFile.canRead()){
                    throw new IOException("missing permission to read cache file #failure=0");
                }
            }

            String[] cacheFileContentArray = FileSystem.getFileContent(cacheFile);
            StringBuilder cacheFileContentNew = new StringBuilder();

            for(String singleLine : cacheFileContentArray){
                if(!JsonUtils.validJson(singleLine)){
                    continue;
                }

                cacheFileContentNew.append(singleLine).append("\n");
                JSONObject cacheObject = new JSONObject(singleLine);
                Set<String> keys = cacheObject.keySet();
                keys.forEach((key) -> Cache.cacheValues.put(key, cacheObject.get(key).toString()));
            }

            FileSystem.setFileContent(cacheFile, new String(cacheFileContentNew));
        }catch(IOException exception){
            exception.printStackTrace();
        }

    }

}
