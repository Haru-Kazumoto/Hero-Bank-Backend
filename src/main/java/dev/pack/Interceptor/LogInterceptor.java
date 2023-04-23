package dev.pack.Interceptor;

import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
@WebFilter(urlPatterns = "/*")
public class LogInterceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = time.format(formatter);

        logger.info("Incoming request [{} - {} - {}]: status {}",
                request.getMethod(), //Get the request method
                request.getRequestURI(), //Get the URI request method
                formattedTime, //Formatting time minute-hour-second
                response.getStatus()); //Get the app status response

        return true;
    }
}
