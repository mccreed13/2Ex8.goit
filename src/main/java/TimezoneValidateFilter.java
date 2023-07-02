import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(value = "/time")
public class TimezoneValidateFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        String utc = req.getParameter("timezone");
        if(utc == null){
            chain.doFilter(req,resp);
        }
        if(utc != null && utc.length()>=5){
            String rigthUtc = utc.substring(0,3);
            if(!rigthUtc.equals("UTC")){
                sendInvalidTimezone(resp);
            }
            try {
                utc = utc.replaceAll(" ", "");
                int timeZone = Integer.parseInt(utc.substring(3));
                if (timeZone > 14 || timeZone < -12) {
                    sendInvalidTimezone(resp);
                } else chain.doFilter(req, resp);
            }
            catch (Exception e){
                sendInvalidTimezone(resp);
            }
        }
        sendInvalidTimezone(resp);
    }
    private static void sendInvalidTimezone(HttpServletResponse resp) throws  IOException{
        resp.setContentType("text/html");
        resp.setStatus(400);
        resp.getWriter().write("Invalid timezone");
        resp.getWriter().close();
    }
}
