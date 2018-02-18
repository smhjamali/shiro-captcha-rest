package ir.company.view.config;

import ir.company.view.util.RedisUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;

/**
 *
 * @author mohammad
 */
public class ShiroSessionListener extends SessionListenerAdapter {  
    
    @Override
    public void onExpiration(Session session) {
        super.onExpiration(session);
        if(session.getAttribute("uid") != null) {
            RedisUtils.removeSessionId(String.valueOf(session.getAttribute("uid")), session.getId().toString());
        }        
    }

    @Override
    public void onStop(Session session) {
        super.onStop(session); 
        if (session.getAttribute("uid") != null) {
            RedisUtils.removeSessionId(String.valueOf(session.getAttribute("uid")), session.getId().toString());
        }        
    }

    @Override
    public void onStart(Session session) {
        super.onStart(session);
    }                
    
}
