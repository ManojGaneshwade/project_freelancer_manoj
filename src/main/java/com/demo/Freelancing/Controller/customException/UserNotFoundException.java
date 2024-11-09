package com.demo.Freelancing.Controller.customException;

public class UserNotFoundException extends RuntimeException{
	
	public UserNotFoundException(String msg)
	{
		super(msg);
	}

}
