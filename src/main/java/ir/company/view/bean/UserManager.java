package ir.company.view.bean;

import ir.company.model.service.UserService;
import ir.company.model.service.UserServiceException;
import ir.company.view.dto.UserDto;
import ir.company.view.dto.UserSessionDto;
import ir.company.view.util.FacesUtils;
import ir.company.view.util.OWASPUtils;
import ir.company.view.util.RedisUtils;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

/**
 *
 * @author Mohammad-Hossein Jamali
 */
@Named
@RequestScoped
public class UserManager implements Serializable {
    
    @Inject
    private ApplicationManager applicationManager; 
    
    @EJB
    private UserService userService;
    
    private UserDto user;
    private String password;    
    
    @PostConstruct
    private void init(){
        user = new UserDto();
    }
    
    public void saveUserInfo(){
        try {
            String encryptedPassword = applicationManager.getPasswordService().encryptPassword(this.password);
            user.setPassword(encryptedPassword);
            user.setPassword(encryptedPassword);
            userService.createUser(user);
            FacesUtils.addInfoMessage("new user added.");
        } catch (UserServiceException ex) {
            Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, ex.getMessage());
            FacesUtils.addErrorMessage(ex.getMessage());
        }
    }
    
    public String login(){
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        token.setRememberMe(true);
        Subject subject = SecurityUtils.getSubject();        
        try {
            
            OWASPUtils.login(subject, token, applicationManager.getRedissonClient());            
            return "home";
        } catch (UnknownAccountException uae) {
            FacesUtils.addErrorMessage(uae.getMessage());
        } catch (IncorrectCredentialsException ice) {
            FacesUtils.addErrorMessage(ice.getMessage());
        } catch (LockedAccountException lae) {
            FacesUtils.addErrorMessage(lae.getMessage());
        } catch (ExcessiveAttemptsException eae) {
            FacesUtils.addErrorMessage(eae.getMessage());
        }catch ( AuthenticationException ae ) {    
            FacesUtils.addErrorMessage(ae.getMessage());
        }        
        return null;
    }   
    
    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }        

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }   
        
}
