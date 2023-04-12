package io.github.leonardomvs.rest.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class CredentialsDTO {

	@Email(message = "{field.email.invalid}")
	@NotEmpty(message = "{field.email.mandatory}")
	private String email;
		
	@NotEmpty(message = "{field.password.mandatory}")
	private String password;
	
}
