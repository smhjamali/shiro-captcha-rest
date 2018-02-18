/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.company.model.entity;

import ir.company.view.dto.UserDto;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author mohammad
 */
@Table(name = "tbl_user")
@Entity
@NamedQueries({
    @NamedQuery(
            name = "User.findByUsername",
            query = "SELECT u FROM User u WHERE u.username = :username"
    ),
    @NamedQuery(
            name = "User.findCountByUsername",
            query = "SELECT COUNT(u) FROM User u WHERE u.username = :username"
    )    
})
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    private String password;

    public User() {
    }    
    
    public UserDto toUserDto(){
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setName(name);
        userDto.setPassword(password);
        userDto.setUsername(username);
        return userDto;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }        
    
}
