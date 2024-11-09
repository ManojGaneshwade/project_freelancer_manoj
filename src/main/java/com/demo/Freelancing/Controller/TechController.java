package com.demo.Freelancing.Controller;

 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.Freelancing.Controller.customException.UserNotFoundException;
import com.demo.Freelancing.Entity.Customer;
import com.demo.Freelancing.Entity.TechExpert;
import com.demo.Freelancing.Service.TechService;

@RestController
public class TechController {
	
	@Autowired
	TechService techService;
	
	//create 
	@PostMapping("/tech/create")
	public ResponseEntity<?> createTech(@RequestBody TechExpert techExpert)
	{
		return techService.createTech(techExpert);
	}
	
	//fetch all
	@GetMapping("/tech/fetch")
	public ResponseEntity<?> getAllTech()
	{
		return techService.getAllTech();
	}
	
	//fetch one record
	@GetMapping("/tech/fetchone")
	public ResponseEntity<?> getSingleTech(@RequestParam("techId") Long id)
	{
		return techService.getSingleTech(id);
	}
	
	//update
	@PatchMapping("/tech/update")
	public ResponseEntity<?> updateTech(@RequestBody TechExpert tech,@RequestParam("techId") Long id)
	{
		return techService.updateTech(tech,id);
	}
	
	//delete
	@DeleteMapping("/tech/delete")
	public ResponseEntity<?> deleteTech(@RequestParam("techId") Long id)
	{
		return techService.deleteTech(id);
	}
	
	
	/////////////////////////////////// Log in  //////////
	//log in
	@PostMapping("/tech/login")
	public ResponseEntity<?> techExpertLogIn(@RequestBody TechExpert techExpert)
	{
		Long mobile=techExpert.getMobile();
		TechExpert existingTechExpert=techService.findByMobile(mobile);

		if(existingTechExpert.getPassword().equals(techExpert.getPassword()))
		{
			return new ResponseEntity<>("Log in Successful",HttpStatus.OK);
		}
		else
		{
			throw new UserNotFoundException("given password is incorrect.");
		}

	}
	
	
	
}
	

