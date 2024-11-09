package com.demo.Freelancing.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.demo.Freelancing.Controller.customException.ResourceNotFoundException;
import com.demo.Freelancing.Entity.Category;
import com.demo.Freelancing.Entity.Services;
import com.demo.Freelancing.Entity.TechExpert;
import com.demo.Freelancing.Repository.CategoryRepository;
import com.demo.Freelancing.Repository.ServicesRepository;
import com.demo.Freelancing.Repository.TechRepository;

@Service
public class ServicesService {

	@Autowired
	ServicesRepository servicesRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	TechRepository techRepository;
	
	@Autowired
	CategoryService categoryService;

	//create services
	public ResponseEntity<?> createServices(Services services)
	{
		//------
		Category category=null;
		Long categoryId=services.getCategory().getCategoryId();
		Optional<Category> opCategory=categoryRepository.findById(categoryId);
		if(opCategory.isPresent())
		{
			category=opCategory.get();
		}
		else
		{
			throw new ResourceNotFoundException("Category is not present with id "+categoryId);
		}
		
		//------
		TechExpert techExpert=null;
		Long techId=services.getTechExpert().getTechId();
		Optional<TechExpert> opTechExpert=techRepository.findById(techId);
		if(opTechExpert.isPresent())
		{
			techExpert=opTechExpert.get();
		}
		else
		{
			throw new ResourceNotFoundException("TechExpert is not present with id "+techId);
		}
		
		
		services.setCategory(category);
		services.setTechExpert(techExpert);
		
		Services savedService=	servicesRepository.save(services);
		return new ResponseEntity<>(savedService,HttpStatus.CREATED);
	}

	
	//fetch all services
	public ResponseEntity<?> fetchAllServices() {
		List<Services> servicesList=servicesRepository.findAll();
		if(servicesList.isEmpty())
		{
			//return new ResponseEntity<>("no records present. ",HttpStatus.OK);
			throw new ResourceNotFoundException("no records present. ");
		}
		else
		{
			return new ResponseEntity<>(servicesList,HttpStatus.OK);
		}
	}

	//fetch single services
	@Cacheable(value="services",key="#id")
	public ResponseEntity<?> fetchOneService(Long id) 
	{
		Optional<Services> opService=servicesRepository.findById(id);
		if(opService.isPresent())
		{
			Services service=opService.get();
			return new ResponseEntity<>(service,HttpStatus.OK);
		}
		else
		{
			//return new ResponseEntity<>("no record present with id "+id,HttpStatus.NOT_FOUND);
			throw new ResourceNotFoundException("no record present with id "+id);
		}
	}

	//delete service
	@CacheEvict(value="services",key="#id")
	public ResponseEntity<?> deleteService(Long id) 
	{
		Optional<Services> opService=servicesRepository.findById(id);
		if(opService.isPresent())
		{
			servicesRepository.deleteById(id);
			return new ResponseEntity<>("service deleted successfully with id "+id,HttpStatus.OK);
		}
		else
		{
			//return new ResponseEntity<>("no record present with id "+id,HttpStatus.NOT_FOUND);
			throw new ResourceNotFoundException("no record present with id "+id);
		}
	}

	//update service
	@CachePut(value="services",key="#id")
	public ResponseEntity<?> updateService(Services services,Long id) 
	{
		Optional<Services> opService=servicesRepository.findById(id);
		if(opService.isPresent())
		{
			Services existance=opService.get();
			if(services.getServiceName()!=null)
			{
				existance.setServiceName(services.getServiceName());
			}
			if(services.getServiceDesc()!=null)
			{
				existance.setServiceDesc(services.getServiceDesc());
			}
			if(services.getPrice()!=null)
			{
				existance.setPrice(services.getPrice());
			}
			if(services.getCategory()!=null)
			{
				Category excategory=categoryRepository.findById(services.getCategory().getCategoryId()).get();
				existance.setCategory(excategory);
			}
			if(services.getTechExpert()!=null)
			{
				TechExpert extechExpert=techRepository.findById(services.getTechExpert().getTechId()).get();
				existance.setTechExpert(extechExpert);
			}
			Services saved=servicesRepository.save(existance);
			return new ResponseEntity<>(saved,HttpStatus.OK);
		}
		else
		{
			//return new ResponseEntity<>("no record present with id "+id,HttpStatus.NOT_FOUND);
			throw new ResourceNotFoundException("no record present with id "+id);
		}
	}

	
	//------------------------------------SEARCH-------------------------------------------------------//

	
	
	//search services by cat Id  -native query
	public ResponseEntity<?> findbyCategoryId(Long catId)
	{
		Optional<List<Services>> opService=servicesRepository.findbyCategoryId(catId);
		if(opService.isPresent())
		{
			List<Services> serviceList=opService.get();
			if(!serviceList.isEmpty())
				return new ResponseEntity<>(serviceList,HttpStatus.OK);
			else
				//return new ResponseEntity<>("records not present with category "+catId,HttpStatus.NOT_FOUND);
				throw new ResourceNotFoundException("records not present with category "+catId);
		}
		else
		{
			//return new ResponseEntity<>("records not present.",HttpStatus.NOT_FOUND);
			throw new ResourceNotFoundException("records not present.");
		}
	}
	
	//search services by tech Id -native query
	public ResponseEntity<?> findbyTechId(Long techId)
	{
		Optional<List<Services>> opService=servicesRepository.findbyTechId(techId);
		if(opService.isPresent())
		{
			List<Services> serviceList=opService.get();
			if(!serviceList.isEmpty())
				return new ResponseEntity<>(serviceList,HttpStatus.OK);
			else
				//return new ResponseEntity<>("records not present with techId "+techId,HttpStatus.NOT_FOUND);
				throw new ResourceNotFoundException("records not present with techId "+techId);
		}
		else
		{
			//return new ResponseEntity<>("records not present.",HttpStatus.NOT_FOUND);
			throw new ResourceNotFoundException("records not present.");
		}
	}
	

	//search services by cat name
	public ResponseEntity<?> findByCatName(String categoryName)
	{
		Long catId=categoryService.findByCatName(categoryName);
		Optional<List<Services>> opService=servicesRepository.findbyCategoryId(catId);
		if(opService.isPresent())
		{
			List<Services> serviceList=opService.get();
			if(!serviceList.isEmpty())
				return new ResponseEntity<>(serviceList,HttpStatus.OK);
			else
				//return new ResponseEntity<>("records not present with category name= "+categoryName,HttpStatus.NOT_FOUND);
				throw new ResourceNotFoundException("records not present with category name= "+categoryName);
		}
		else
		{
			//return new ResponseEntity<>("records not present.",HttpStatus.NOT_FOUND);
			throw new ResourceNotFoundException("records not present.");
		}
	}
	
}
