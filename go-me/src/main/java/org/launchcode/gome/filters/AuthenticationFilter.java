package org.launchcode.gome.filters;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by AnnaYoungyeun on 7/17/17.
 */
@SpringBootApplication
@Configuration
@WebFilter(filterName = "AuthenticationFilter", value = {"/*"})
public class AuthenticationFilter implements Filter{

    private ServletContext context;
    @Override
    public void init(FilterConfig config) throws ServletException {
        this.context = config.getServletContext();
        this.context.log("Initializing authentication filter.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        HttpSession session = req.getSession(false);
        Cookie[] cookies = req.getCookies();
        Cookie userCookie = null;

        //Is there a more efficient way to do this???
        //Find if there is a user cookie.
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user")) {
                    userCookie = cookie;  //find cookie associated with this user
                }
            }
        }

        //If there is a user cookie, set session to true.  Else, false.
        if (userCookie != null){
            session = req.getSession(true);
        } else if (userCookie == null) {
            session = req.getSession(false);
        }

        if(session == null && !uri.endsWith("login")){
            this.context.log("Unauthorized request");
            res.sendRedirect("/user/login");
        } else {
            this.context.log("Do filter THIS IS HAPPENING.");
            chain.doFilter(req, res);
        }

    }

    @Override
    public void destroy(){
        this.context.log("Destroying Authentication filter");


    }




}

//package org.launchcode.gome.filters;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
///**
// * Created by AnnaYoungyeun on 7/17/17.
// */
//@WebFilter(urlPatterns = {"/*"}, description = "AuthenticationFilter")
//public class AuthenticationFilter implements Filter{
//    private ServletContext context;
//
//    public void init(FilterConfig config) throws ServletException {
//        this.context = config.getServletContext();
//        this.context.log("Initializing authentication filter.");
//    }
//
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//        throws IOException, ServletException {
//
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse res = (HttpServletResponse) response;
//
//        String uri = req.getRequestURI();
//        this.context.log("Requested Resource::" +uri);
//        HttpSession session = req.getSession(false);
//
//        if(session == null && !uri.endsWith("login")){
//            this.context.log("Unauthorized request");
//
//            res.sendRedirect("login.html");
//        } else {
//            chain.doFilter(req, res);
//        }
//    }
//
//    public void destroy(){
//
//    }
//
//
//
//
//}
