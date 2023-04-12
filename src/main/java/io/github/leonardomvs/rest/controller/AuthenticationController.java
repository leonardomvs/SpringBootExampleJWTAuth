package io.github.leonardomvs.rest.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.leonardomvs.domain.entity.User;
import io.github.leonardomvs.rest.dto.CredentialsDTO;
import io.github.leonardomvs.rest.dto.TokenDTO;
import io.github.leonardomvs.security.jwt.JwtService;
import io.github.leonardomvs.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
	
	@Autowired
	JwtService jwtService;
	
	@Autowired
    UserServiceImpl userService;

	@PostMapping
    public TokenDTO authenticate(@RequestBody @Valid CredentialsDTO credentialsDTO){
            
    	User user = User.builder()
                .email(credentialsDTO.getEmail())
                .password(credentialsDTO.getPassword()).build();
        
        UserDetails authenticatedUser = userService.authenticate(user);
        

        String token = jwtService.generateToken(user);
        
        return new TokenDTO(authenticatedUser.getUsername(), token);
		        
    }
	
}
