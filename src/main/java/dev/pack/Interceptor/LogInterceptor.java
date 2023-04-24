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

//@Component
//@WebFilter(urlPatterns = "/*")
public class LogInterceptor  {

    Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Override
//    public boolean preHandle(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            Object handler) throws Exception {
//
//        LocalTime time = LocalTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
//        String formattedTime = time.format(formatter);
//        String logLevel = "INFO"; // initialize the log level variable to "INFO" by default
//        int statusCode = response.getStatus(); //Get the app status response
//
//        if (statusCode >= 400 && statusCode < 500) {
//            logLevel = "WARN"; // set the log level to "WARN" if the status code is between 400 and 499
//        } else if (statusCode >= 500) {
//            logLevel = "ERROR"; // set the log level to "ERROR" if the status code is 500 or higher
//        }
//
//        // Use the appropriate logging method based on the log level
//        switch (logLevel) {
//            case "WARN" -> logger.warn("Incoming request [{} - {} - {}]: status {}",
//                    request.getMethod(),
//                    request.getRequestURI(),
//                    formattedTime,
//                    statusCode);
//            case "ERROR" -> logger.error("Incoming request [{} - {} - {}]: status {}",
//                    request.getMethod(),
//                    request.getRequestURI(),
//                    formattedTime,
//                    statusCode);
//            default -> logger.info("Incoming request [{} - {} - {}]: status {}",
//                    request.getMethod(),
//                    request.getRequestURI(),
//                    formattedTime,
//                    statusCode);
//        }
//        return true;
//    }
}
