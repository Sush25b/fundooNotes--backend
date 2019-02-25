package com.bridgelabz.fundooNotes.user.services;

import java.util.List;

import com.bridgelabz.fundooNotes.user.dto.UserDto;
import com.bridgelabz.fundooNotes.user.model.User;

public interface UserService 
{
	 List<User> findAll();
	
	 User findById(int theId);
	
	 void save(UserDto userDTO);
	
	 void deleteById(int theId);
}
