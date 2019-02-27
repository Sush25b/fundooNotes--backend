package com.bridgelabz.fundooNotes.user.services;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundooNotes.user.dto.LoginDto;
import com.bridgelabz.fundooNotes.user.dto.UserDto;
import com.bridgelabz.fundooNotes.user.exception.UserException;
import com.bridgelabz.fundooNotes.user.model.User;
import com.bridgelabz.fundooNotes.user.repository.IUserRepository;
import com.bridgelabz.fundooNotes.utility.MailHelper;

@Service
public class UserServicelogImpl 
{
	@Value("${sender.email}")
	String from;
	
	@Value("${sender.password}")
	String password;
	
	String to = null;
	//String receiver= null;
	String msgheader= "Welcome to Bridgeit ";
	String textmsg="Your account has been added successfully";
	
	@Autowired
	IUserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ModelMapper modelMapper;

	public String onLogin(LoginDto loginDto) throws UserException 
	{
			      ////System.out.println(loginDto.getEmailid());
	
			User user = modelMapper.map(loginDto, User.class);
			      //// System.out.println(user);
	
			
			 //get The User (i.e User Object) from Database --->having email passed here
			Optional<User> validUser = userRepository.findByEmailid(user.getEmailid());
	
			
			//match the loginDto password && validUser password 
			boolean passwordStatus = passwordEncoder.matches(loginDto.getPassword(), validUser.get().getPassword());
				//// System.out.println(validUser.get().getPassword()+" "+loginDto.getPassword());

		if (passwordStatus == false || validUser == null) 
		{
			throw new UserException("invalid password");
		} 
		else
		{
			return "login successfully";
		}
	}

	public String onRegister(UserDto userDto) throws UserException, UnsupportedEncodingException
	{

			User user = modelMapper.map(userDto, User.class);
			
			Optional<User> useralreadyPresent = userRepository.findByEmailid(user.getEmailid());
			
			if (useralreadyPresent.isPresent())
			{
				throw new UserException("already register");
			}
			
			// encode user password
			String password = passwordEncoder.encode(userDto.getPassword());
			
			user.setPassword(password);
			
			//the Repository==>will save the Object & also return the Saved Object
			user= userRepository.save(user);
		
			//from,password,to,subject,message  
			MailHelper.send(from,password,"phulsundarsushant26@gmail.com",msgheader,MailHelper.getUrl(user.getId()));  
		
		//return
		return "successfully Register";

	}

}
