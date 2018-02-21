package ir.company.view.rest;

import ir.company.model.service.UserService;
import ir.company.view.dto.UserDto;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Mohammad-Hossein Jamali
 */
@Path("/user")
public class UserRestService {
    
    @EJB
    private UserService userService;
    
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDto> userList(){
        return userService.findAllUsers();
    }
    
}
