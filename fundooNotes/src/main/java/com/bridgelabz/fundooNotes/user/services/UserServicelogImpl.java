package com.bridgelabz.fundooNotes.user.services;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundooNotes.user.dto.LoginDto;
import com.bridgelabz.fundooNotes.user.dto.UserDto;
import com.bridgelabz.fundooNotes.user.exception.UserException;
import com.bridgelabz.fundooNotes.user.model.User;
import com.bridgelabz.fundooNotes.user.repository.IUserRepository;

@Service
public class UserServicelogImpl 
{
	@Autowired
	IUserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ModelMapper modelMapper;

	public String onLogin(LoginDto loginDto) throws UserException 
	{
		//extract user details by using emailid 

		String emailId = loginDto.getEmailId();

		Optional<User> validUser = userRepository.findByEmailid(loginDto.getEmailId()); 

		boolean passwordStaus=passwordEncoder.matches(loginDto.getPassword(), validUser.get().getPassword());

		if(passwordStaus == false || validUser==null )
		{
			throw new UserException("invalid password");

		}
		return "login successfully";
	}


	public String onRegister(UserDto userDto) throws UserException, UnsupportedEncodingException
	{

		String emailid = userDto.getEmailid();

		User user=modelMapper.map(userDto, User.class);

		Optional<User> alreadyPresent = userRepository.findByEmailid(user.getEmailid()); 

		if(alreadyPresent.isPresent())
		{
			throw new UserException("already register");
		}
		//encode user password
		String password= passwordEncoder.encode(userDto.getPassword());

		user.setPassword(password);

		userRepository.save(user);
		return "successfully Register";

	}

}


