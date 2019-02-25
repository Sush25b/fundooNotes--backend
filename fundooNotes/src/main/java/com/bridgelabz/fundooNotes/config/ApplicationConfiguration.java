
package com.bridgelabz.fundooNotes.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfiguration 
{
	@Bean
	public ModelMapper getMapper()
	{
		ModelMapper modelMapper = new ModelMapper();
	    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	    
	    return modelMapper;
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
}