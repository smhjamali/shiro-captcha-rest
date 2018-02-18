package ir.company.view.util;

import ir.company.view.config.ApplicationConfiguration;
import java.util.List;
import redis.clients.jedis.Jedis;

/**
 *
 * @author mohammad
 */
public class RedisUtils {
    
    public static void removeSessionId(String username, String sessionId){
        Jedis jedis = new Jedis(ApplicationConfiguration.REDIS_SERVER_ADDRESS);
        List<String> ids = jedis.lrange(username, 0, -1);        
        ids.remove(sessionId);
        jedis.del(username);
        for(String id : ids) {
            jedis.lpush(username, id);
        }
    }
    
}
