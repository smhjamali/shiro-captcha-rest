package ir.company.view.bean;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.mgt.SecurityManager;

/**
 *
 * @author jamali
 */
@Named
@ApplicationScoped
public class ApplicationManager {

    @Inject
    private SecurityManager securityManager;

    @Inject
    private PasswordMatcher passwordMatcher;
    
    @Inject       
    private PasswordService passwordService;

    @PostConstruct
    private void init() {
        SecurityUtils.setSecurityManager(securityManager);
    }

    public PasswordMatcher getPasswordMatcher() {
        return passwordMatcher;
    }

    public void setPasswordMatcher(PasswordMatcher passwordMatcher) {
        this.passwordMatcher = passwordMatcher;
    }

    public SecurityManager getSecurityManager() {
        return securityManager;
    }

    public void setSecurityManager(SecurityManager securityManager) {
        this.securityManager = securityManager;
    }    

    public PasswordService getPasswordService() {
        return passwordService;
    }

    public void setPasswordService(PasswordService passwordService) {
        this.passwordService = passwordService;
    }
       
}
