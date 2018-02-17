package ir.company.model.service;

import ir.company.model.entity.User;
import ir.company.view.dto.UserDto;
import java.io.Serializable;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jamali
 */
@Stateless
@LocalBean
public class UserService implements Serializable {
    
    @PersistenceContext(unitName = "sample")
    private EntityManager em;
    
    public void createUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setUsername(userDto.getUsername());
        em.persist(user);
    }
}
