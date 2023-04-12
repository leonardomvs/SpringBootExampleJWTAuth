package io.github.leonardomvs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.leonardomvs.domain.entity.User;
import io.github.leonardomvs.domain.repository.UserRepository;
import io.github.leonardomvs.exception.InvalidPasswordException;

@Service
public class UserServiceImpl implements UserDetailsService {

	@Autowired
    PasswordEncoder encoder;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	UserRepository userRepository;
			
	public UserDetails authenticate(User user){
        
		UserDetails userDetails = loadUserByUsername(user.getEmail());
        
        boolean passwordsMatch = encoder.matches(user.getPassword(), userDetails.getPassword());
        
        if(passwordsMatch){ return userDetails; }
        
        throw new InvalidPasswordException(messageSource);
        
    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		String msgNotFound = messageSource.getMessage("user_not_found", null, null);
		
		User user = userRepository.findByEmailIgnoreCase(username)
    			.orElseThrow(() -> new UsernameNotFoundException(msgNotFound));

        String[] roles = user.isAdmin() ?
                new String[] { "ADMIN", "USER" } : new String[] { "USER" };

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(roles)
                .build();
        
	}
	
}
