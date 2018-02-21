package ir.company.model.service;

import ir.company.model.entity.User;
import ir.company.view.dto.UserDto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
    
    public void createUser(UserDto userDto) throws UserServiceException {
        if(isUsernameExist(userDto.getUsername())){
            throw new UserServiceException("Username already exist");
        }
        User user = new User();
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setUsername(userDto.getUsername());
        em.persist(user);
    }
    
    public UserDto findByUsername(String username) {
        User user = (User) em.createNamedQuery("User.findByUsername")
                .setParameter("username", username)
                .getSingleResult();
        return user.toUserDto();
    }
    
    public Boolean isUsernameExist(String username) {
        Long count = (Long) em.createNamedQuery("User.findCountByUsername")
                .setParameter("username", username)
                .getSingleResult();        
        return count > 0L;
    }
    
    public List<UserDto> findAllUsers(){
        List<User> users = em.createNamedQuery("User.findAll")
                .getResultList();
        List<UserDto> result = new ArrayList<>(users.size());
        for(User user : users) {
            result.add(user.toUserDto());
        }
        return result;
    }
}
