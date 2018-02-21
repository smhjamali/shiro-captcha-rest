package ir.company.view.config;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author jamali
 */
@ApplicationPath(value = "/rs")
public class ApplicationConfiguration extends Application {
    
    public static String REDIS_SERVER_ADDRESS = "127.0.0.1";    
    public static int REDIS_SERVER_PORT = 6379;
    public static String REDIS_SERVER_ADDRESS_FULL = "redis://" + REDIS_SERVER_ADDRESS + ":" + REDIS_SERVER_PORT;
    public static String REDIS_SERVER_ADDRESS_FULL_SSL = "rediss://" + REDIS_SERVER_ADDRESS + ":" + REDIS_SERVER_PORT;
    public static String REDIS_KEY_PREFIX = "shiro:session:";
    public static String REDIS_CACHE_PREFIX = "shiro:cache:";
    public static String REDIS_USER_SESSIONS_PREFIX = "application:users:";
    public static String JNDI_RESOURCE = "java:/jdbc/TestDs";
    
}
