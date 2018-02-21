package ir.company.view.bean;

import ir.company.view.dto.UserSessionDto;
import ir.company.view.util.RedisUtils;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 *
 * @author Mohammad-Hossein Jamali
 */
@Named
@SessionScoped
public class UserSessionManager implements Serializable {    
    
    private List<UserSessionDto> userSessions;
    
    @PostConstruct
    private void init(){
        refreshUserSessionList();
    }
    
    public void refreshUserSessionList() {
        Subject subject = SecurityUtils.getSubject();
        String username = String.valueOf(subject.getSession().getAttribute("uid"));
        this.userSessions = new RedisUtils().getSessionsByUsername(username);
    }    
    
    public String removeUserSession(String sessionId){
        Session session = SecurityUtils.getSubject().getSession();
        new RedisUtils().invalidateSession(String.valueOf(session.getAttribute("uid")), sessionId);
        if (sessionId.equals(SecurityUtils.getSubject().getSession().getId().toString())) {
            return "login";
        } else {
            return null;
        }        
    }
    
    public List<UserSessionDto> getUserSessions() {
        return userSessions;
    }

    public void setUserSessions(List<UserSessionDto> userSessions) {
        this.userSessions = userSessions;
    }    
    
}
