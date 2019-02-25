package com.bridgelabz.fundooNotes.user.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;



import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundooNotes.user.dto.UserDto;
//import com.bridgelabz.fundooNotes.user.dto.UserDto;
import com.bridgelabz.fundooNotes.user.model.User;
import com.bridgelabz.fundooNotes.user.repository.IUserRepository;

//import com.bridgelabz.fundooNotes.service.User;
//import com.bridgelabz.fundooNotes.service.UserRepository;

@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	private IUserRepository userrepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	/*
	 * @Autowired public UserServiceImpl(IUserRepository userrepository) {
	 * userrepository = userrepository; }
	 */
		
	Date date=new Date();
	
	String registerDate,updateDate= date.toString();
	
	@Override
	public List<User> findAll()
	{
		return userrepository.findAll();
	}

	@Override
	public User findById(int id) {
		User user=new User();
		
		Optional<User> result = userrepository.findById(id);
		
		if (result.isPresent())
		{
			user = result.get();
		}
		else {
			//throw new RuntimeException("Did not find User id - " + theId);
		}
	
		return user;
	}

	@Override
	public void save(UserDto userDTO) 
	{
		User user= modelMapper.map(userDTO, User.class);
		
		user.setUpdateDate(updateDate);
		
		user.setRegisterDate(registerDate);
		
		user.setIsVerified("true");
		
		userrepository.save(user);
	}

	@Override
	public void deleteById(int id)
	{
		userrepository.deleteById(id);
	}
	
}










