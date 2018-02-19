package ir.company.view.bean;

import ir.company.view.dto.UserSessionDto;
import ir.company.view.util.RedisUtils;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.apache.shiro.SecurityUtils;
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
        Subject subject = SecurityUtils.getSubject();
        this.userSessions = new RedisUtils().getUserSessions(subject.getSession());
    }
    
    public void refreshUserSessionList() {
        Subject subject = SecurityUtils.getSubject();
        this.userSessions = new RedisUtils().getUserSessions(subject.getSession());
    }    
    
    public List<UserSessionDto> getUserSessions() {
        return userSessions;
    }

    public void setUserSessions(List<UserSessionDto> userSessions) {
        this.userSessions = userSessions;
    }    
    
}
