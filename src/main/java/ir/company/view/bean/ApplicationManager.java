package ir.company.view.bean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;

/**
 *
 * @author Mohammad-Hossein Jamali
 */
@Named
@ApplicationScoped
public class ApplicationManager {

    @Inject
    private WebSecurityManager securityManager;

    @Inject
    private PasswordMatcher passwordMatcher;
    
    @Inject       
    private PasswordService passwordService;
        
    private RedissonClient redissonClient;
    
    @PostConstruct
    private void init() {
        SecurityUtils.setSecurityManager(securityManager);
        
        //Config config = new Config();
        //config.setTransportMode(TransportMode.EPOLL);        
        //config.useClusterServers().addNodeAddress(ApplicationConfiguration.REDIS_SERVER_ADDRESS_FULL);        
        redissonClient = Redisson.create(); 
    }

    @PreDestroy
    private void destroy(){
        if(redissonClient != null) {
            redissonClient.shutdown();
        }        
    }
    
    public PasswordMatcher getPasswordMatcher() {
        return passwordMatcher;
    }

    public void setPasswordMatcher(PasswordMatcher passwordMatcher) {
        this.passwordMatcher = passwordMatcher;
    }

    public WebSecurityManager getSecurityManager() {
        return securityManager;
    }

    public void setSecurityManager(WebSecurityManager securityManager) {
        this.securityManager = securityManager;
    }   

    public PasswordService getPasswordService() {
        return passwordService;
    }

    public void setPasswordService(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    public RedissonClient getRedissonClient() {
        return redissonClient;
    }
    
}
