package com.ap.Superhero.controller;

import com.ap.Superhero.dto.Hero;
import com.ap.Superhero.dto.Location;
import com.ap.Superhero.service.SuperService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Andy Padilla
 */
@Controller
public class LocationController {
    
    @Autowired
    SuperService service;
    
    Set<ConstraintViolation<Location>> violations = new HashSet<>();
    
    @GetMapping("location")
    public String displayLocations(Model model){
        List<Location> location = service.getAllLocations();
        model.addAttribute("location", location);
        model.addAttribute("errors", violations);
        return "location";
    }
    
    @PostMapping("addLocation")
    public String addLocation(HttpServletRequest request){
        String name = request.getParameter("Name");
        String description = request.getParameter("Description");
        String address = request.getParameter("Address");
        String longitude = request.getParameter("Longitude");
        String latitude = request.getParameter("Latitude");
        
        Location location = new Location();
        location = service.setLocationInfo(location, name, description, address, longitude, latitude);
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(location);
        if(violations.isEmpty()){
            service.addLocation(location);
        }
            
        return "redirect:/location";
    }
    
    @GetMapping("deleteLocation")
    public String deleteLocation(Integer id){
        service.deleteLocationById(id);
        return "redirect:/location";
    }
    
    @GetMapping("editLocation")
    public String editLocation(Integer id, Model model){
        Location location = service.getLocationById(id);
        model.addAttribute("location", location);
        return "editLocation";
    }
    
    @PostMapping("editLocation")
    public String editLocation(HttpServletRequest request, Model model){
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("Name");
        String description = request.getParameter("Description");
        String address = request.getParameter("Address");
        String longitude = request.getParameter("Longitude");
        String latitude = request.getParameter("Latitude");
        
        Location location = new Location();
        location.setId(id);
        location = service.setLocationInfo(location, name, description, address, longitude, latitude);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(location);
        if(violations.isEmpty()){
            service.updateLocation(location);
            return "redirect:/location";
        }else{
            location = service.getLocationById(id);
            model.addAttribute("location", location);
            model.addAttribute("errors", violations);
            return "editLocation";
        }
    }
    
    @GetMapping("viewLocation")
    public String viewLocation(Integer id, Model model){
        Location location = service.getLocationById(id);
        List<Hero> hero = service.getHeroesByLocation(location);
        hero = service.removeDupeHero(hero);
        model.addAttribute("location", location);
        model.addAttribute("hero", hero);
        return "viewLocation";
    }
}
