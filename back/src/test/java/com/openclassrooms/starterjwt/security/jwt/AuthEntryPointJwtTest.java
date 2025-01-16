package com.openclassrooms.starterjwt.security.jwt;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.AuthenticationException;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class AuthEntryPointJwtTest {

    @Mock
    private HttpServletRequest request; // HttpServletRequest is a Spring class used to handle HTTP requests

    @Mock
    private HttpServletResponse response; // HttpServletResponse is a Spring class used to handle HTTP responses

    @Mock
    private AuthenticationException authException;  // AuthenticationException is a Spring Security class used to handle authentication exceptions

    @InjectMocks
    private AuthEntryPointJwt authEntryPointJwt; // AuthEntryPointJwt is the class we are testing

    @Test
    void testCommence() throws IOException, ServletException {

        when(authException.getMessage()).thenReturn("Unauthorized error"); // Set the message of the authentication exception
        when(request.getServletPath()).thenReturn("/test-path"); // Set the path of the request

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); // A ByteArrayOutputStream is a stream that writes data to a byte array
        ServletOutputStream servletOutputStream = new ServletOutputStream() { // A Servlet output stream is used to write data to the HTTP response
            @Override
            public void write(int b) throws IOException {
                byteArrayOutputStream.write(b);
            }

            @Override
            public boolean isReady() { // Returns true if the output stream is ready to be written
                return true;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) { // Sets the write listener
            }
        };
        when(response.getOutputStream()).thenReturn(servletOutputStream); // Set the output stream of the response

        authEntryPointJwt.commence(request, response, authException); // Call the method we are testing

        verify(response).setContentType("application/json"); // Verify that the response content type is set to JSON
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Verify that the response status is set to unauthorized

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> responseBody = mapper.readValue(byteArrayOutputStream.toString(), Map.class); // Read the response body as a map
        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, responseBody.get("status")); // Verify that the status is set to unauthorized
        assertEquals("Unauthorized", responseBody.get("error"));
        assertEquals("Unauthorized error", responseBody.get("message"));
        assertEquals("/test-path", responseBody.get("path"));
    }
}