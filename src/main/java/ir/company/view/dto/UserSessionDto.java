package ir.company.view.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Mohammad-Hossein Jamali
 */
public class UserSessionDto implements Serializable {
    
    private String sessionId;
    private String browserName;
    private String ip;
    private Date loginDate;
    private String country;
    private String city;

    public UserSessionDto(String sessionId, String browserName, String ip, Date loginDate, String country, String city) {
        this.sessionId = sessionId;
        this.browserName = browserName;
        this.ip = ip;
        this.loginDate = loginDate;
        this.country = country;
        this.city = city;
    }

    public UserSessionDto() {
    }    
    
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
            
}
