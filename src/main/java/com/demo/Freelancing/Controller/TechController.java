package com.demo.Freelancing.Controller;

 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.Freelancing.Entity.TechExpert;
import com.demo.Freelancing.Repository.TechRepository;
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
	
}
	

