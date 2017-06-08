package ru.igorek.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.igorek.core.Main;

/**
 *
 * @author Игорек
 */
public class ConfigurationUtil { 
     private static final String DEFAULT_CONFIG_PATH = "/etc/configurationProperties.properties"; 
     private static final Properties configuration = new Properties(); 
     private static String pathEnv; 
     /** 
      * Hides default constructor 
      */ 
     private ConfigurationUtil() { 
         pathEnv = DEFAULT_CONFIG_PATH; 
     } 
      
     public ConfigurationUtil(String pathTofile){ 
         this.pathEnv = pathTofile; 
     } 
      
     private static Properties getConfiguration() throws IOException { 
         if(pathEnv==null){ 
             pathEnv = DEFAULT_CONFIG_PATH; 
         } 
         if(configuration.isEmpty()){ 
             loadConfiguration();
         } 
         return configuration; 
     } 
 

     /** 
      * Loads configuration from <code>DEFAULT_CONFIG_PATH</code> 
      * @throws IOException In case of the configuration file read failure 
      */ 
     private static void loadConfiguration() throws IOException{ 
         InputStream in = Main.class.getResourceAsStream(DEFAULT_CONFIG_PATH);
         try { 
             configuration.load(in); 
         } catch (IOException ex) { 
             throw new IOException(ex); 
         } finally{ 
             in.close(); 
         } 
     } 
     /** 
      * Gets configuration entry value 
      * @param key Entry key 
      * @return Entry value by key 
      * @throws IOException In case of the configuration file read failure 
      */ 
     public static String getConfigurationEntry(String key){ 
         try { 
             return getConfiguration().getProperty(key);
         } catch (IOException ex) {
             Logger.getLogger(ConfigurationUtil.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
     } 
 } 
