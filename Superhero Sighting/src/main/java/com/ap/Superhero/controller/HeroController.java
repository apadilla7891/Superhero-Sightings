package com.ap.Superhero.controller;

import com.ap.Superhero.dto.Hero;
import com.ap.Superhero.dto.Location;
import com.ap.Superhero.dto.Organization;
import com.ap.Superhero.dto.Superpower;
import com.ap.Superhero.service.SuperService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Andy Padilla
 */
@Controller
public class HeroController {
    @Autowired
    SuperService service;
    
    Set<ConstraintViolation<Hero>> violations = new HashSet<>();
    
    @GetMapping("hero")
    public String displayHeroes(Model model){
        List<Hero> hero = service.getAllHeroes();
        List<Superpower> superpower = service.getAllPowers();
        model.addAttribute("hero", hero);
        model.addAttribute("superpower", superpower);
        model.addAttribute("errors", violations);
        return "hero";
    }
   
    @PostMapping("addSuperhero")
    public String addHero(Hero hero, HttpServletRequest request){
        String[] powerIds = request.getParameterValues("superpowerId");
        List<Superpower> powers = new ArrayList<>();
        powers = service.setPowerInfo(powerIds, powers);
        hero.setPowers(powers);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(hero);
        if(violations.isEmpty()){
            service.addHero(hero);
        }
        
        return "redirect:/hero";
    }
    
    @GetMapping("deleteHero")
    public String deleteHero(Integer id){
        service.deleteHeroById(id);
        return "redirect:/hero";
    }
    
    @GetMapping("editHero")
    public String editHero(Integer id, Model model){
        Hero hero = service.getHeroById(id);
        List<Superpower> superpower = service.getAllPowers();
        model.addAttribute("hero", hero);
        model.addAttribute("superpower", superpower);
        return "editHero";
    }
    
    @PostMapping("editHero")
    public String performEditHero(@Valid Hero hero,BindingResult result, HttpServletRequest request, Model model){
        String[] powerIds = request.getParameterValues("superpowerId");
        List<Superpower> powers = new ArrayList<>();
        powers = service.setPowerInfo(powerIds, powers);
        hero.setPowers(powers);
        
        if(result.hasErrors()){
            model.addAttribute("superpower", service.getAllPowers());
            model.addAttribute("hero", hero);
            return "editHero";
        }
        
        service.updateHero(hero);
        return "redirect:/hero";
    }
    
    @GetMapping("viewHero")
    public String viewHero(Integer id, Model model){
        Hero hero = service.getHeroById(id);
        List<Organization> organization = service.getOrgForHero(hero);
        List<Location> location = service.getLocationForHero(hero);
        location = service.removeDupeLocal(location);
        List<Superpower> superpower = hero.getPowers();
        model.addAttribute("hero", hero);
        model.addAttribute("superpower", superpower);
        model.addAttribute("organization", organization);
        model.addAttribute("location", location);
        return "viewHero";
    }
}
