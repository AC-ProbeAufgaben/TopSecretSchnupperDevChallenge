package com.amiconsult.topsecretschnupperdevchallenge.util;

import com.amiconsult.topsecretschnupperdevchallenge.security.MyUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            final String authHeader = httpServletRequest.getHeader("Authorization");

            String username = null;
            String jwt = null;

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                jwt = authHeader.substring(7);
                username = jwtUtil.extractUsername(jwt);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
                if (jwtUtil.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        } catch (ExpiredJwtException ex) {


            String requestURL = httpServletRequest.getRequestURL().toString();

            System.out.println("<><><> HTTP REQ HEADERS<><><>");
            Enumeration<String> httpRequest= httpServletRequest.getHeaderNames();

            for (Enumeration<?> e = httpRequest; e.hasMoreElements();) {
                String nextHeaderName = (String) e.nextElement();
                String headerValue = httpServletRequest.getHeader(nextHeaderName);
                System.out.println(nextHeaderName + headerValue);
            }

            System.out.println("<><><> REQUEST URL <><><>");
            System.out.println(requestURL);
            System.out.println("<><><> EXPIRED JWT EXCEPTION <><><>");

            System.out.println(ex);

        } catch (Exception ex) {
            System.out.println("<><><> EXCEPTION <><><>");
            System.out.println(ex);
        }
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
}
