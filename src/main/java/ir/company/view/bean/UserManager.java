package ir.company.view.bean;

import ir.company.view.dto.UserDto;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Mohammad-Hossein Jamali
 */
@Named
@RequestScoped
public class UserManager implements Serializable {
    
    private UserDto user;

    @PostConstruct
    private void init(){
        user = new UserDto();
    }
    
    public void saveUserInfo(){
        System.out.println(user.getName());
    }
    
    public void login(){
        
    }
    
    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }        
    
}
