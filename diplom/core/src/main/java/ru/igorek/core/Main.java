package ru.igorek.core;


import java.io.IOException;
import org.apache.log4j.Logger;
import ru.igorek.core.utils.ConfigurationUtil;

/**
 *
 * @author Игорек
 */
public class Main{
    private static Logger log = Logger.getLogger(Main.class);
    
    public static void main(String[] args) throws IOException{
        log.debug(ConfigurationUtil.getConfigurationEntry("DB_URL"));
    }
}
