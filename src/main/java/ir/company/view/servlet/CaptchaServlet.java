package ir.company.view.servlet;

import com.github.cage.Cage;
import com.github.cage.YCage;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

@WebServlet(urlPatterns = "/captcha")
public class CaptchaServlet extends HttpServlet {

    private static final long serialVersionUID = 1490947492185481844L;

    private static final Cage cage = new YCage();

    /**
     * Generates a captcha token and stores it in the session.
     *
     * @param session where to store the captcha.
     */
    private void generateToken(Session session) {
        String token = cage.getTokenGenerator().next();

        session.setAttribute("captchaToken", token);
        markTokenUsed(session, false);
    }

    /**
     * Used to retrieve previously stored captcha token from session.
     *
     * @param session where the token is possibly stored.
     * @return token or null if there was none
     */
    public static String getToken(Session session) {
        Object val = session.getAttribute("captchaToken");

        return val != null ? val.toString() : null;
    }

    /**
     * Marks token as used/unused for image generation.
     *
     * @param session where the token usage flag is possibly stored.
     * @param used false if the token is not yet used for image generation
     */
    protected static void markTokenUsed(Session session, boolean used) {
        session.setAttribute("captchaTokenUsed", used);
    }

    /**
     * Checks if the token was used/unused for image generation.
     *
     * @param session where the token usage flag is possibly stored.
     * @return true if the token was marked as unused in the session
     */
    protected static boolean isTokenUsed(Session session) {
        return !Boolean.FALSE.equals(session.getAttribute("captchaTokenUsed"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {
        
        Session session = SecurityUtils.getSubject().getSession(Boolean.FALSE);
        generateToken(session);
        String token = session != null ? getToken(session) : null;
        if (token == null || isTokenUsed(session)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Captcha not found.");
            return;
        }

        setResponseHeaders(resp);
        markTokenUsed(session, true);
        cage.draw(token, resp.getOutputStream());
    }

    /**
     * Helper method, disables HTTP caching.
     *
     * @param resp response object to be modified
     */
    protected void setResponseHeaders(HttpServletResponse resp) {
        resp.setContentType("image/" + cage.getFormat());
        resp.setHeader("Cache-Control", "no-cache, no-store");
        resp.setHeader("Pragma", "no-cache");
        long time = System.currentTimeMillis();
        resp.setDateHeader("Last-Modified", time);
        resp.setDateHeader("Date", time);
        resp.setDateHeader("Expires", time);
    }
}
