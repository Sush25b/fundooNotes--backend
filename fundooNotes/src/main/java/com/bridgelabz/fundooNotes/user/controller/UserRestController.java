package com.bridgelabz.fundooNotes.user.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundooNotes.user.dto.LoginDto;
import com.bridgelabz.fundooNotes.user.dto.UserDto;
import com.bridgelabz.fundooNotes.user.exception.UserException;
import com.bridgelabz.fundooNotes.user.exception.UserException2;
import com.bridgelabz.fundooNotes.user.model.User;
import com.bridgelabz.fundooNotes.user.response.UserResp;
import com.bridgelabz.fundooNotes.user.response.UserResponse;
import com.bridgelabz.fundooNotes.user.service.UserService;
import com.bridgelabz.fundooNotes.user.service.UserServicesImpl;
import com.bridgelabz.fundooNotes.utility.TokenUtil;

@RestController
@RequestMapping("/fundooNotes")
@CrossOrigin(origins="http://localhost:4200",allowedHeaders="*",exposedHeaders={"jwtTokens"})
public class UserRestController
{
	@Autowired
	private UserService userservice;

	@Autowired(required=true) 
	UserServicesImpl userServices;

	@GetMapping("/usersget")
	public List<User> findAll() 
	{
		return userservice.findAll();
	}

	@PostMapping("/users")
	public UserDto addUser(@RequestBody UserDto userDTO) 
	{
		// this to save of new item ... instead of update
		System.out.println("User  : "+userDTO);

		userservice.save(userDTO);	

		return userDTO;
	}
	
	//, HttpServletResponse  response
	//successfully
	@PostMapping("/login") 
	public ResponseEntity<UserResp> Login(@RequestBody LoginDto loginDto) // throws UnsupportedEncodingException
	{ 		
		System.out.println("a");
		System.out.println(loginDto);
		return userServices.onLogin(loginDto); 
	}

	//successfully
	@PostMapping("/register") 
	public ResponseEntity<UserResponse> registerUser(@RequestBody UserDto userDtO) // UserException2 
	{ 
		return userServices.onRegister(userDtO); 
	}
	
	  //successfully
	  @PostMapping(value="/forgetpassword") 
	 // public ResponseEntity<UserResponse> forgotPassword(@RequestBody String emailid) //throws UnsupportedEncodingException 
	  public ResponseEntity<UserResponse> forgotPassword(@RequestBody UserDto userDto) //throws UnsupportedEncodingException 
	  {
		  System.out.println(userDto+"@@@@@@@@@@@");
		 return userServices.forgetPassword(userDto);
	  }
	 
	  //successfully
	  @GetMapping(value="resetPassword/user/{token}/valid") 
	  public ResponseEntity<UserResponse> resetPassword(@PathVariable String token,@RequestParam("password") String password) throws UnsupportedEncodingException
	  {
		  	System.out.println("token "+token+" ,  password"+password); 
	  
	  		return userServices.resetPassword(token,password);
	  }
	  
	  //successfully
	  @GetMapping(value="validEmail/user/{token}/validate") 
	  public ResponseEntity<UserResponse> validEmail(@PathVariable String token) //throws UnsupportedEncodingException
	  {
		  System.out.println("token "+token); 
		  
		  	return  userServices.validEmail(token); 
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

		/*
		 * @PutMapping("/usersupdate") public UserDto updateUser(@RequestBody UserDto
		 * userDTO) { userservice.save(userDTO);
		 * 
		 * return userDTO; }
		 * 
		 * // add mapping for DELETE /Users/{UserId} - delete User
		 * 
		 * //successfully
		 * 
		 * @DeleteMapping("/usersdelete/{userid}") public String
		 * deleteUser(@PathVariable Long userid) { User tempUser =
		 * userservice.findById(userid);
		 * 
		 * // throw exception if null if (tempUser == null) //no need of checking it
		 * again { System.out.println("invalid user"); }
		 * 
		 * userservice.deleteById(userid);
		 * 
		 * return "Deleted User id - " + userid; }
		 */
}
