package com.bridgelabz.fundooNotes.user.connector;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundooNotes.user.dto.LoginDto;
import com.bridgelabz.fundooNotes.user.dto.UserDto;
import com.bridgelabz.fundooNotes.user.exception.UserException;
import com.bridgelabz.fundooNotes.user.model.User;
import com.bridgelabz.fundooNotes.user.services.UserService;
import com.bridgelabz.fundooNotes.user.services.UserServicelogImpl;

@RestController
@RequestMapping("/fundooNotes")
public class UserRestController
{
	@Autowired
	private UserService userservice;


	@Autowired(required=true) 
	UserServicelogImpl userLoginService;


	@GetMapping("/usersget")
	public List<User> findAll() 
	{
		return userservice.findAll();
	}

	// add mapping for GET
	/*
	 * @GetMapping("/users/{employeeId}") public Employee getEmployee(@PathVariable
	 * int employeeId) {
	 * 
	 * Employee theEmployee = UserService.findById(employeeId);
	 * 
	 * if (theEmployee == null) { throw new
	 * RuntimeException("Employee id not found - " + employeeId); }
	 * 
	 * return theEmployee; }
	 */
	// add mapping for POST /employees - add new employee

	// add mapping for PUT /Users - update existing User

	@PutMapping("/usersupdate")
	public UserDto updateUser(@RequestBody UserDto userDTO) 
	{
		userservice.save(userDTO);

		return userDTO;
	}

	// add mapping for DELETE /Users/{UserId} - delete User

	@DeleteMapping("/users/{userid}")
	public String deleteUser(@PathVariable int userid) 
	{
		User tempUser = userservice.findById(userid);

		// throw exception if null

		if (tempUser == null) {
			throw new RuntimeException("User id not found - " + userid);
		}

		userservice.deleteById(userid);

		return "Deleted User id - " + userid;
	}

	@PostMapping("/users")
	public UserDto addUser(@RequestBody UserDto userDTO) 
	{
		// this to save of new item ... instead of update
		System.out.println("User  : "+userDTO);

		userservice.save(userDTO);	

		return userDTO;
	}


	@PostMapping("/login") 
	public String Login( @RequestBody LoginDto loginDto )  throws UserException 
	{ 
		return userLoginService.onLogin(loginDto); 
	}

	@PostMapping("/register") 
	public String registerUser(@RequestBody UserDto userDtO) throws UnsupportedEncodingException, UserException 
	{ 
		return userLoginService.onRegister(userDtO); 
	}

}
