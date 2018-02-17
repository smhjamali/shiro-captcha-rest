package ir.company.view.config;

import javax.servlet.annotation.WebFilter;
import org.apache.shiro.web.servlet.ShiroFilter;

/**
 *
 * @author Mohammad-Hossein Jamali
 */
@WebFilter("/*")
public class ShiroFilterActivator extends ShiroFilter {
    private ShiroFilterActivator() {
    }    
}
