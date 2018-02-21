package ir.company.view.util;

import ir.company.view.config.ApplicationConfiguration;
import ir.company.view.dto.UserSessionDto;
import java.util.Iterator;
import java.util.List;
import org.apache.shiro.session.Session;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import redis.clients.jedis.Jedis;

/**
 *
 * @author mohammad
 */
public class RedisUtils {
    
    public void removeSessionId(Session session){                
        String username = String.valueOf(session.getAttribute("uid"));
        String sessionId = session.getId().toString();        
        removeSessionId(username, sessionId);
    }

    public void removeSessionId(String username, String sessionId) {
        RedissonClient redissonClient = createRedissonClient();
        List<UserSessionDto> list = redissonClient.getList(ApplicationConfiguration.REDIS_USER_SESSIONS_PREFIX + username);
        Iterator<UserSessionDto> userSessionDtoIterator = list.iterator();
        while (userSessionDtoIterator.hasNext()) {
            UserSessionDto userSessionDto = userSessionDtoIterator.next();
            if (userSessionDto.getSessionId().equals(sessionId)) {
                userSessionDtoIterator.remove();
                break;
            }
        }
    }
    
    public void invalidateSession(String username, String sessionId) {        
        //RedissonClient redissonClient = createRedissonClient();
        //Long count = redissonClient.getKeys().delete(ApplicationConfiguration.REDIS_KEY_PREFIX + sessionId);        
        
        Jedis jedis = new Jedis(ApplicationConfiguration.REDIS_SERVER_ADDRESS);
        Long count = jedis.del(ApplicationConfiguration.REDIS_KEY_PREFIX + sessionId);                
        if(count > 0) {
            removeSessionId(username, sessionId);
        }
    }
    
    public List<UserSessionDto> getSessionsByUsername(String username){
        RedissonClient redissonClient = createRedissonClient();
        List<UserSessionDto> result = redissonClient.getList(ApplicationConfiguration.REDIS_USER_SESSIONS_PREFIX + username);
        return result;
    }
    
    private RedissonClient createRedissonClient(){
        //Config config = new Config();
        //config.setTransportMode(TransportMode.EPOLL);        
        //config.useClusterServers().addNodeAddress(ApplicationConfiguration.REDIS_SERVER_ADDRESS_FULL);        
        RedissonClient redissonClient = Redisson.create();        
        return redissonClient;
    }
    
}
