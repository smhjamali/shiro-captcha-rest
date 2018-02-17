package ir.company.view.bean;

import ir.company.view.dto.UserDto;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Mohammad-Hossein Jamali
 */
@Named
@RequestScoped
public class UserManager implements Serializable {
    
    @Inject
    private ApplicationManager applicationManager;    
    
    private UserDto user;
    private String password;
    
    @PostConstruct
    private void init(){
        user = new UserDto();
    }
    
    public void saveUserInfo(){
        String encryptedPassword = applicationManager.getPasswordService().encryptPassword(user.getName());
        user.setPassword(encryptedPassword);
        System.out.println(user.getPassword());
    }
    
    public void login(){
        
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
    
}
