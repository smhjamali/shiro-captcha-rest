package ir.company.view.util;

import ir.company.view.bean.ApplicationManager;
import ir.company.view.dto.UserSessionDto;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import org.apache.shiro.session.Session;
import org.redisson.Redisson;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;

/**
 *
 * @author mohammad
 */
public class RedisUtils {
    
    public void removeSessionId(Session session){                
        String username = String.valueOf(session.getAttribute("uid"));
        String sessionId = session.getId().toString();        
        RedissonClient redissonClient = createRedissonClient();
        List<UserSessionDto> list = redissonClient.getList(username);        
        Iterator<UserSessionDto> userSessionDtoIterator = list.iterator();
        while(userSessionDtoIterator.hasNext()) {
            UserSessionDto userSessionDto = userSessionDtoIterator.next();
            if(userSessionDto.getSessionId().equals(sessionId)){
                userSessionDtoIterator.remove();
                break;
            }
        }
    }
    
    public List<UserSessionDto> getUserSessions(Session session){        
        String username = String.valueOf(session.getAttribute("uid"));                
        RedissonClient redissonClient = createRedissonClient();
        List<UserSessionDto> result = redissonClient.getList(username);        
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
