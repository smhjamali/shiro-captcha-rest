package ir.company.view.config;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;
import org.apache.shiro.web.env.DefaultWebEnvironment;
import org.apache.shiro.web.env.EnvironmentLoaderListener;
import org.apache.shiro.web.env.WebEnvironment;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.mgt.WebSecurityManager;

/**
 *
 * @author Mohammad-Hossein Jamali
 */
@WebListener
public class ShiroListener extends EnvironmentLoaderListener {
    
    @Inject 
    WebSecurityManager webSecurityManager;
    
    @Inject
    FilterChainResolver filterChainResolver;    
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setInitParameter(ENVIRONMENT_CLASS_PARAM, DefaultWebEnvironment.class.getName());
        super.contextInitialized(sce);
    }    
    
    @Override
    protected WebEnvironment createEnvironment(ServletContext sc){
        DefaultWebEnvironment webEnvironment = (DefaultWebEnvironment) super.createEnvironment(sc);    
        
        webEnvironment.setSecurityManager(webSecurityManager);
        webEnvironment.setFilterChainResolver(filterChainResolver);        
        
        return webEnvironment;
    }    
    
}
