package system.io.config;

import system.io.filesystem.FileSystem;
import system.io.filesystem.SystemPaths;
import system.tools.skills.Console;
import system.tools.utils.StringUtils;

import java.io.*;
import java.util.*;

public class Config {

    private static File getConfigFolder() {
        return SystemPaths.CONFIG.getFileName();
    }

    private static TreeMap<ConfigCategories, List<ConfigEntries>> getConfigCategoryEntries() {

        TreeMap<ConfigCategories, List<ConfigEntries>> configCategoryEntries = new TreeMap<>();

        for (ConfigEntries entries : ConfigEntries.values()) {
            ConfigCategories category = entries.getCategory();
            if (configCategoryEntries.containsKey(category)) {
                List<ConfigEntries> entriesList = configCategoryEntries.get(category);
                entriesList.add(entries);
            } else {
                List<ConfigEntries> entriesList = new ArrayList<>();
                entriesList.add(entries);
                configCategoryEntries.put(category, entriesList);
            }
        }

        return configCategoryEntries;

    }

    private static void createConfigFiles() throws IOException {

        TreeMap<ConfigCategories, List<ConfigEntries>> configCategoryEntries = Config.getConfigCategoryEntries();

        configCategoryEntries.forEach((category, entries) -> {
            try {
                File configFile = new File(Config.getConfigFolder() + "/" + category.getName().toLowerCase() + ".conf");

                if (!configFile.exists()) {
                    if(!configFile.createNewFile()){
                        throw new IOException("file cannot be created");
                    }
                    Properties properties = new Properties();
                    entries.forEach(entry -> properties.setProperty(entry.getKeyName(), entry.getDefaultValue()));

                    OutputStream outputStream = new FileOutputStream(configFile.getPath());
                    properties.store(outputStream, StringUtils.EMPTY);
                }

            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });


    }

    public static void loadConfigEntries() {

        try {
            Console.print("Loading config entries...");

            Config.createConfigFiles();
            TreeMap<ConfigCategories, List<ConfigEntries>> configCategoryEntries = Config.getConfigCategoryEntries();

            configCategoryEntries.forEach((category, entries) -> {
                try {
                    File configFile = new File(Config.getConfigFolder() + "/" + category.getName().toLowerCase() + ".conf");

                    if (!configFile.exists()) {
                        throw new IOException("config file not exists");
                    }

                    if (!configFile.canRead()) {
                        throw new IOException("missing permission to readout config file");
                    }

                    InputStream inputStream = new FileInputStream(configFile.getPath());
                    Properties fileProperties = new Properties();
                    fileProperties.load(inputStream);

                    for (ConfigEntries entry : entries) {
                        String propertyValue = fileProperties.getProperty(entry.getKeyName());
                        if (propertyValue != null) {
                            entry.setDefaultValue(propertyValue);
                        } else {
                            Map<String, String> propertyStorage = new HashMap<>();
                            fileProperties.forEach((key, value) -> {
                                if (ConfigEntries.getConfigByName(key.toString()) == null) {
                                    return;
                                }
                                propertyStorage.put(key.toString(), value.toString());
                            });

                            FileSystem.removeFileContent(configFile);
                            Properties recreatedProperty = new Properties();
                            propertyStorage.forEach(recreatedProperty::setProperty);

                            List<ConfigEntries> configEntries = configCategoryEntries.get(entry.getCategory());
                            for (ConfigEntries singleEntry : configEntries) {
                                String keyName = singleEntry.getKeyName();
                                if (recreatedProperty.getProperty(keyName) == null) {
                                    recreatedProperty.setProperty(keyName, singleEntry.getDefaultValue());
                                }
                            }

                            OutputStream outputStream = new FileOutputStream(configFile.getPath());
                            recreatedProperty.store(outputStream, StringUtils.EMPTY);
                        }
                    }

                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            });

        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

}
