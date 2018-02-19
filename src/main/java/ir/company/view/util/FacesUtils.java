package ir.company.view.util;

import ir.company.view.dto.UserSessionDto;
import java.util.Date;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jamali
 */
public class FacesUtils {
    
    public static void addInfoMessage(String message){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
    }

    public static void addErrorMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
    }
    
    public static ExternalContext getExternalContext(){
        return FacesContext.getCurrentInstance().getExternalContext();
    }
    
    public static UserSessionDto getCurrentUserSessionDto(){
        UserSessionDto userSessionDto = new UserSessionDto();
        ExternalContext ec = getExternalContext();
        Map<String, String> reqHeaderMap = ec.getRequestHeaderMap();        
        userSessionDto.setBrowserName(reqHeaderMap.get("User-Agent"));
        userSessionDto.setSessionId(ec.getSessionId(Boolean.FALSE));
        userSessionDto.setIp(getClientIPAddress((HttpServletRequest) ec.getRequest()));
        userSessionDto.setLoginDate(new Date());
        return userSessionDto;
    }
    
    public static String getClientIPAddress(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;        
    }
    
}
