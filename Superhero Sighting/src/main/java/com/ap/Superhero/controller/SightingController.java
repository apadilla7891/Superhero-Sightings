package com.ap.Superhero.controller;

import com.ap.Superhero.dto.Hero;
import com.ap.Superhero.dto.HeroSighting;
import com.ap.Superhero.dto.Location;
import com.ap.Superhero.service.SuperService;
import java.time.LocalDate;
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
public class SightingController {
    @Autowired
    SuperService service;
    
    Set<ConstraintViolation<HeroSighting>> violations = new HashSet<>();
    
    @GetMapping("index")
    public String displayHomeSightings(Model model){
        
        List<HeroSighting> sighting = service.getLastTen();
        model.addAttribute("sighting", sighting);
        return "index";
    }
    
    @GetMapping("sighting")
    public String displaySightings(Model model){
        
        List<HeroSighting> sighting = service.getAllSightings();
        List<Hero> hero = service.getAllHeroes();
        List<Location> location = service.getAllLocations();
        model.addAttribute("sighting", sighting);
        model.addAttribute("hero",hero);
        model.addAttribute("location",location);
        model.addAttribute("errors", violations);
        return "sighting";
    }
    
    @PostMapping("addSighting")
    public String addSighting(HttpServletRequest request){
        Hero hero = service.getHeroById(Integer.parseInt(request.getParameter("heroId")));
        Location location = service.getLocationById(Integer.parseInt(request.getParameter("locationId")));
        String sDate = request.getParameter("Date");
        boolean dateCorrect= service.checkDate(sDate);
        LocalDate date = service.insertDate(dateCorrect, sDate);
        
        HeroSighting sight = new HeroSighting();
        sight.setDate(date);
        sight.setHero(hero);
        sight.setLocation(location);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sight);
        if(violations.isEmpty()){    
            service.addSighting(sight);
        }
        
        return "redirect:/sighting";
    }
    
    @GetMapping("deleteSighting")
    public String deleteSighting(Integer id){
        service.deleteSightingById(id);
        return "redirect:/sighting";
    }
    
    @GetMapping("editSighting")
    public String editSighting(Integer id, Model model){
        HeroSighting sighting = service.getSightingById(id);
        List<Hero> hero = service.getAllHeroes();
        List<Location> location = service.getAllLocations();
        model.addAttribute("sighting", sighting);
        model.addAttribute("hero",hero);
        model.addAttribute("location",location);
        return "editSighting";
    }
    
    @PostMapping("editSighting")
    public String performEditSighting(HttpServletRequest request, Model model){
        int id = Integer.parseInt(request.getParameter("id"));
        Hero newHero = service.getHeroById(Integer.parseInt(request.getParameter("heroId")));
        Location newLocation = service.getLocationById(Integer.parseInt(request.getParameter("locationId")));
        String sDate = request.getParameter("Date");
        boolean dateCorrect= service.checkDate(sDate);
        LocalDate date = service.insertDate(dateCorrect, sDate);
        String dateError= null;
        
        HeroSighting sighting = new HeroSighting();
        sighting.setId(id);
        sighting.setDate(date);
        sighting.setHero(newHero);
        sighting.setLocation(newLocation);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sighting);
        if(violations.isEmpty()){
            service.updateSighting(sighting);
            return "redirect:/sighting";
        }else{
            dateError = "Please enter a date today or earlier. must be in (YYYY-MM-DD) Format";
            sighting = service.getSightingById(id);
            List<Hero> hero = service.getAllHeroes();
            List<Location> location = service.getAllLocations();
            model.addAttribute("sighting", sighting);
            model.addAttribute("hero",hero);
            model.addAttribute("location",location);
            model.addAttribute("errors", violations);
            model.addAttribute("dateError", dateError);
            return "editSighting";
        }
    }
    
    @GetMapping("viewSighting")
    public String viewSighting(Integer id, Model model){
        HeroSighting sight = service.getSightingById(id);
        LocalDate date = sight.getDate();
        List<HeroSighting> sighting = service.getSightingsForDate(date);
        model.addAttribute("sighting", sighting);
        model.addAttribute("date", date);
        return "viewSighting";
    }
}
