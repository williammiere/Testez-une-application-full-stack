package com.openclassrooms.starterjwt.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.openclassrooms.starterjwt.security.services.UserDetailsServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AuthTokenFilterTest {

    @InjectMocks
    private AuthTokenFilter authTokenFilter; // AuthTokenFilter is the class we are testing

    @Mock
    private JwtUtils jwtUtils; // JwtUtils is a class used to handle JWTs

    @Mock
    private HttpServletRequest request; // HttpServletRequest is a Spring class used to handle HTTP requests

    @Mock
    private HttpServletResponse response; // HttpServletResponse is a Spring class used to handle HTTP responses

    @Mock
    private FilterChain filterChain; // FilterChain is a Spring class used to filter HTTP requests

    @Mock
    private UserDetails userDetails;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void testDoFilterInternal()  throws ServletException, IOException { // Should set the user authentication in the security context
        String mockUsername = "test";
        String mockJwt = "token";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + mockJwt);
        when(jwtUtils.validateJwtToken(mockJwt)).thenReturn(true);
        when(jwtUtils.getUserNameFromJwtToken(mockJwt)).thenReturn(mockUsername);
        when(userDetailsService.loadUserByUsername(mockUsername)).thenReturn(userDetails);

        authTokenFilter.doFilterInternal(request, response, filterChain);

        verify(jwtUtils).validateJwtToken(mockJwt);
        verify(jwtUtils).getUserNameFromJwtToken(mockJwt);
        verify(userDetailsService).loadUserByUsername(mockUsername);
        verify(filterChain).doFilter(request, response);

        UsernamePasswordAuthenticationToken authentication = // UsernamePasswordAuthenticationToken is a Spring class used to authenticate users
            (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        assertNotNull(authentication);
        assertEquals(userDetails, authentication.getPrincipal()); // Check that the user details are set in the authentication object
    }

    @Test
    void testDoFilterInternalNotOk() throws ServletException, IOException { // Should not set the user authentication in the security context

        String mockJwt = "invalidJWT";
        String headerAuth = "Bearer " + mockJwt;
        String mockUsername = "test";

        when(request.getHeader("Authorization")).thenReturn(headerAuth);
        when(jwtUtils.validateJwtToken(mockJwt)).thenThrow(new RuntimeException("Cannot set user authentication"));

        authTokenFilter.doFilterInternal(request, response, filterChain);

        verify(userDetailsService, never()).loadUserByUsername(mockUsername);
    }

    @Test
    void testDoFilterInternal_NoAuthorizationHeader() throws ServletException, IOException { // Should not set the user authentication in the security context

        when(request.getHeader("Authorization")).thenReturn(null);

        authTokenFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_InvalidAuthorizationHeader() throws ServletException, IOException { // Should not set the user authentication in the security context");

        when(request.getHeader("Authorization")).thenReturn("Basic dXNlcm5hbWU6cGFzc3dvcmQ=");

        authTokenFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }
}