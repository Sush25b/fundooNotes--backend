package com.bridgelabz.fundooNotes.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.fundooNotes.user.model.User;

public interface IUserRepository extends JpaRepository<User, Integer>
{
	//creating a new method
	public Optional<User> findByEmailid(String emailid);

}
