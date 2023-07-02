import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
    private LocalDateTime initTime;

    @Override
    public void init(){
        initTime = LocalDateTime.now();
    }
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=utf-8");
        String utc = req.getParameter("timezone");
        String time;
        if(utc != null) {
                Long hours = Long.parseLong(utc.substring(4));
                if (utc.contains("-")) {
                    time = LocalDateTime.now(ZoneId.of("UTC")).minusHours(hours)
                            .format(DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm:ss")) + " UTC-" + hours;
                } else {
                    time = LocalDateTime.now(ZoneId.of("UTC")).plusHours(hours)
                            .format(DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm:ss")) + " UTC+" + hours;
                }
            } else {
                time = LocalDateTime.now(ZoneId.of("UTC"))
                        .format(DateTimeFormatter.ofPattern("dd-MM-yyyy, HH:mm:ss")) + " UTC";
        }
            resp.getWriter().write(time);
            resp.getWriter().close();

    }
}
