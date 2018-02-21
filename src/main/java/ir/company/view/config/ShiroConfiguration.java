package ir.company.view.config;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.jndi.JndiObjectFactory;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.filter.authz.SslFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.filter.session.NoSessionCreationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;

/**
 *
 * @author Mohammad-Hossein Jamali
 */
public class ShiroConfiguration {
    
    private FilterChainResolver filterChainResolver = null;
    private DefaultWebSecurityManager securityManager = null;
    private DefaultWebSessionManager sessionManager = null;
    private RedisManager redisManager = null;
    private RedisCacheManager redisCacheManager = null;
    private ExecutorServiceSessionValidationScheduler sessionValidatorScheduler = null;
    private DefaultPasswordService passwordService = null;
    private PasswordMatcher passwordMatcher = null;
    
    private ShiroConfiguration() {
    }

    @Produces    
    public ExecutorServiceSessionValidationScheduler getExecutorServiceSessionValidationScheduler(){
        if(sessionValidatorScheduler == null) {
            sessionValidatorScheduler = new ExecutorServiceSessionValidationScheduler();
            sessionValidatorScheduler.setInterval(3600000);            
        }
        return sessionValidatorScheduler;
    }
    
    @Produces
    public RedisCacheManager getRedisCacheManager(){
        if(redisCacheManager == null){
            redisCacheManager = new RedisCacheManager();
            redisCacheManager.setKeyPrefix(ApplicationConfiguration.REDIS_CACHE_PREFIX);
            redisCacheManager.setRedisManager(getRedisManager());            
        }        
        return redisCacheManager;
    }
    
    @Produces
    public RedisManager getRedisManager(){
        if(redisManager == null) {
            redisManager = new RedisManager();
            redisManager.setHost(ApplicationConfiguration.REDIS_SERVER_ADDRESS);
            redisManager.setPort(ApplicationConfiguration.REDIS_SERVER_PORT);
            redisManager.setExpire(600);
            redisManager.setTimeout(0);
            
            RedisSessionDAO redisSessionDao = new RedisSessionDAO();
            redisSessionDao.setKeyPrefix(ApplicationConfiguration.REDIS_KEY_PREFIX);
            redisSessionDao.setRedisManager(redisManager);
            getSessionManager().setSessionDAO(redisSessionDao);
            
        }
        return redisManager;
    }
    
    @Produces
    public DefaultWebSessionManager getSessionManager(){
        if(sessionManager == null) {
            sessionManager = new DefaultWebSessionManager();
            sessionManager.setGlobalSessionTimeout(1800000);                                    
            sessionManager.setSessionValidationScheduler(getExecutorServiceSessionValidationScheduler());
            sessionManager.setSessionValidationSchedulerEnabled(Boolean.TRUE);
            sessionManager.setDeleteInvalidSessions(Boolean.TRUE);
            if(sessionManager.getSessionListeners() == null) {
                sessionManager.setSessionListeners(new ArrayList<>());
            }
            List<SessionListener> sessionListeners = new ArrayList<>();
            sessionListeners.add(new ShiroSessionListener());
            sessionManager.setSessionListeners(sessionListeners);
        }
        return sessionManager;
    }
    
    @Produces
    public DefaultPasswordService getPasswordService(){
        if(passwordService == null){
            DefaultHashService hashService = new DefaultHashService();
            hashService.setHashIterations(200000);
            hashService.setHashAlgorithmName("SHA-512");
            hashService.setGeneratePublicSalt(Boolean.TRUE);
            Sha512Hash sha512 = new Sha512Hash();
            sha512.setIterations(200000);
            sha512.setSalt(new SecureRandomNumberGenerator().nextBytes());
            hashService.setPrivateSalt(sha512);
            passwordService = new DefaultPasswordService();
            passwordService.setHashService(hashService);        
        }
        return passwordService;
    }
    
    @Produces
    public PasswordMatcher getPasswordMatcher(){
        if(passwordMatcher == null){
            passwordMatcher = new PasswordMatcher();
            passwordMatcher.setPasswordService(getPasswordService());            
        }
        return passwordMatcher;
    }
    
    @Produces
    public WebSecurityManager getSecurityManager() {
        if(securityManager == null) {           
            JndiObjectFactory jndiObjectFactory = new JndiObjectFactory();
            jndiObjectFactory.setRequiredType(javax.sql.DataSource.class);
            jndiObjectFactory.setResourceName(ApplicationConfiguration.JNDI_RESOURCE);        
            JdbcRealm realm = new JdbcRealm();
            realm.setPermissionsLookupEnabled(Boolean.FALSE);
            realm.setDataSource((DataSource) jndiObjectFactory.getInstance());
            realm.setAuthenticationQuery("SELECT password FROM tbl_user WHERE username = ?");
            realm.setCredentialsMatcher(getPasswordMatcher());
            securityManager = new DefaultWebSecurityManager(realm);
            CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();            
            rememberMeManager.setCipherKey(Base64.decode("kPH+bIxk5D2deZiIxcaaaA=="));
            securityManager.setRememberMeManager(rememberMeManager);
            securityManager.setSessionManager(getSessionManager());
            securityManager.setCacheManager(getRedisCacheManager());    
            
        }
        return securityManager;
    }

    @Produces
    public FilterChainResolver getFilterChainResolver() {        
        if (filterChainResolver == null) {
            
            FormAuthenticationFilter authc = new FormAuthenticationFilter();
            AnonymousFilter anon = new AnonymousFilter();
            UserFilter user = new UserFilter();
            ShiroFilter shiro = new ShiroFilter();            
            LogoutFilter logout = new LogoutFilter();
            SslFilter ssl = new SslFilter();
            NoSessionCreationFilter noSessionCreation = new NoSessionCreationFilter();
            
            authc.setLoginUrl(WebPages.LOGIN_URL);
            authc.setSuccessUrl(WebPages.SUCCESS_URL);            
            user.setLoginUrl(WebPages.LOGIN_URL);
            logout.setRedirectUrl(WebPages.LOGIN_URL);
            
            FilterChainManager fcMan = new DefaultFilterChainManager();
            
            fcMan.addFilter("noSessionCreation", noSessionCreation);
            fcMan.addFilter("ssl", ssl);
            fcMan.addFilter("anon", anon);
            fcMan.addFilter("authc", authc);            
            fcMan.addFilter("user", user);
            fcMan.addFilter("shiro", shiro);
            fcMan.addFilter("logout", logout);                        
            
            fcMan.createChain("/faces/guest/**", "anon");
            fcMan.createChain("/css/**", "anon");
            fcMan.createChain("/js/**", "anon");
            fcMan.createChain("/captcha", "anon");
            
            fcMan.createChain("/faces/user/**", "authc");                        
            fcMan.createChain("/rs/login", "ssl[8443], anon");
            fcMan.createChain("/rs/**", "ssl[8443], anon");
            fcMan.createChain("/logout", "logout");                        
            //This will cause the session be destroyed and the user will be redirected to the URL specified in “redirectUrl” of logoutFilter.
            
            PathMatchingFilterChainResolver resolver = new PathMatchingFilterChainResolver();
            resolver.setFilterChainManager(fcMan);
            filterChainResolver = resolver;
        }
        return filterChainResolver;        
    }    
    
}
