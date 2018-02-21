package ir.company.view.rest;

import com.github.cage.Cage;
import com.github.cage.YCage;
import ir.company.model.service.UserService;
import ir.company.view.dto.UserDto;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

/**
 *
 * @author Mohammad-Hossein Jamali
 */
@Path("/user")
public class UserRestService {
    
    private static final Cage cage = new YCage();
    
    @EJB
    private UserService userService;
    
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDto> userList(){
        return userService.findAllUsers();
    }

    @GET
    @Path("/captcha")
    @Produces(MediaType.APPLICATION_JSON)    
    public String[] getCaptchaValue(){
        Session session = SecurityUtils.getSubject().getSession(Boolean.TRUE);
        String token = cage.getTokenGenerator().next();
        session.setAttribute("captchaToken", token);        
        String captchaTokenUsed = String.valueOf(session.getAttribute("captchaTokenUsed"));
        String captchaToken = String.valueOf(session.getAttribute("captchaToken"));
        return new String[]{"Captcha Token: ", captchaToken, "Used: ", captchaTokenUsed};                
    }

    @GET
    @Path("/captcha/{captcha}")
    @Produces(MediaType.APPLICATION_JSON)    
    public Boolean checkUserValue(@PathParam("captcha") String captcha) {
        Session session = SecurityUtils.getSubject().getSession(Boolean.FALSE);
        String captchaToken = String.valueOf(session.getAttribute("captchaToken"));
        return captcha.equals(captchaToken);
    }
    
}
