package com.bres.siodme.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Adam on 2016-08-23.
 */
public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        if (! request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: "
                    + request.getMethod());
        }

        String username = super.obtainUsername(request);
        String password = super.obtainPassword(request);

        // username and password required
        if ((! StringUtils.hasText(username)) && (! StringUtils.hasText(password))) {
            throw new AuthenticationServiceException("Username and password are required");
        }

        // username required
        if (! StringUtils.hasText(username)) {
            throw new AuthenticationServiceException("Username is required");
        }

        //  password required
        if (! StringUtils.hasText(password)) {
            throw new AuthenticationServiceException("Password is required");
        }

        // validate password
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (! bCryptPasswordEncoder.matches(password, userDetails.getPassword())) {
            throw new AuthenticationServiceException("The password you have used is invalid");
        }

        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(username, password);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }



}
