package io.github.leonardomvs.exception;

import org.springframework.context.MessageSource;

public class InvalidPasswordException extends RuntimeException {

	private static final long serialVersionUID = 62601863244528839L;
	
	public InvalidPasswordException(MessageSource messageSource){
        super(messageSource.getMessage("invalid_password", null, null));
    }
	
}
