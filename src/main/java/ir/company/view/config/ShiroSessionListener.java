package ir.company.view.config;

import ir.company.view.util.RedisUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;

/**
 *
 * @author Mohammad-Hossein Jamali
 */
public class ShiroSessionListener extends SessionListenerAdapter {                      
    
    @Override
    public void onExpiration(Session session) {
        super.onExpiration(session);
        if(session.getAttribute("uid") != null) {            
            new RedisUtils().removeSessionId(session);
        }        
    }

    @Override
    public void onStop(Session session) {
        super.onStop(session); 
        if (session.getAttribute("uid") != null) {            
            new RedisUtils().removeSessionId(session);
        }        
    }

    @Override
    public void onStart(Session session) {
        super.onStart(session);
    }                
    
}
