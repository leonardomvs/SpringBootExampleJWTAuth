package io.github.leonardomvs.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import io.github.leonardomvs.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
    private final UserServiceImpl userService;    

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
            						HttpServletResponse httpServletResponse,
            						FilterChain filterChain) throws ServletException, IOException {

        String authorization = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if(authorization != null && authorization.startsWith("Bearer")){
            
        	String token = authorization.split(" ")[1];
            boolean isValid = jwtService.checkIfTokenIsValid(token);

            if(isValid) {
                
            	String userEmail = jwtService.getUserEmailFromToken(token);
                UserDetails userDetails = userService.loadUserByUsername(userEmail);
                                
                UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                user.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                
                SecurityContextHolder.getContext().setAuthentication(user);
                
            }

        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }

}