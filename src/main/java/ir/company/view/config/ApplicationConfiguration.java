package ir.company.view.config;

/**
 *
 * @author jamali
 */
public class ApplicationConfiguration {
    
    public static String REDIS_SERVER_ADDRESS = "127.0.0.1";    
    public static int REDIS_SERVER_PORT = 6379;
    public static String REDIS_SERVER_ADDRESS_FULL = "redis://" + REDIS_SERVER_ADDRESS + ":" + REDIS_SERVER_PORT;
    public static String REDIS_SERVER_ADDRESS_FULL_SSL = "rediss://" + REDIS_SERVER_ADDRESS + ":" + REDIS_SERVER_PORT;
    public static String REDIS_KEY_PREFIX = "shiro:session:";
    public static String JNDI_RESOURCE = "java:/jdbc/TestDs";
    
}
