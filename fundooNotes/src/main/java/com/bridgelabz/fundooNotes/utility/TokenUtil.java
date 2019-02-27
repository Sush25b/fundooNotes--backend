package com.bridgelabz.fundooNotes.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class TokenUtil 
{
	public static final String TOKEN_SECRET = "s4T2zOIWHMM1sxq";
	
	/**
	   * create token
	 * @param id
	 * @return token
	 */
	public static String createToken(Long id)
	{
			try 
			{
				// to set algorithm
				Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
	
				// payload
				String token = JWT.create().withClaim("userId", id).sign(algorithm);
				return token;
			}
			catch (JWTCreationException exception) 
			{
				exception.printStackTrace();
				// log Token Signing Failed
			}
			return null;
	}
	
	public static void decodeToken(String token)
	{
			try 
			{
			    DecodedJWT jwt = JWT.decode(token);
			    System.out.println("decoded claim is:"+ jwt);
			    
			} 
			catch (JWTDecodeException exception)
			{
			    //Invalid token
			}
	}
	
	
	
}
