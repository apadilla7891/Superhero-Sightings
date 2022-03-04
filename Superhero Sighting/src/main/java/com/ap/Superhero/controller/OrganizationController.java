package com.ap.Superhero.controller;

import com.ap.Superhero.dto.Hero;
import com.ap.Superhero.dto.Organization;
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
public class OrganizationController {
    @Autowired
    SuperService service;
    
    Set<ConstraintViolation<Organization>> violations = new HashSet<>();
    
    @GetMapping("organization")
    public String displayOrgs(Model model){
        List<Organization> organization = service.getAllOrgs();
        List<Hero> hero = service.getAllHeroes();
        model.addAttribute("organization", organization);
        model.addAttribute("hero",hero);
        model.addAttribute("errors", violations);
        return "organization";
    }
    
    @PostMapping("addOrganization")
    public String addOrganization(Organization organization, HttpServletRequest request){
        String[] heroIds = request.getParameterValues("heroId");
        List<Hero> heroes = new ArrayList<>();
        heroes = service.setHeroInfo(heroIds, heroes);
        organization.setHeroes(heroes);
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(organization);
        if(violations.isEmpty()){    
            service.addOrg(organization);
        }
        return "redirect:/organization";
    }
    
    @GetMapping("deleteOrganization")
    public String deleteOrganization(Integer id){
        service.deleteOrgById(id);
        return "redirect:/organization";
    }
    
    @GetMapping("editOrganization")
    public String editOrganization(Integer id, Model model){
        Organization organization = service.getOrgById(id);
        List<Hero> hero = service.getAllHeroes();
        model.addAttribute("organization", organization);
        model.addAttribute("hero",hero);
        return "editOrganization";
    }
    
    @PostMapping("editOrganization")
    public String performEditOrganization(@Valid Organization organization, BindingResult result, HttpServletRequest request, Model model){
        String[] heroIds = request.getParameterValues("heroId");
        List<Hero> heroes = new ArrayList<>();
        heroes = service.setHeroInfo(heroIds, heroes);
        organization.setHeroes(heroes);
        
        if(result.hasErrors()){
            model.addAttribute("hero", service.getAllHeroes());
            model.addAttribute("organization", organization);
            return "editOrganization";
        }
        service.updateOrg(organization);
        return "redirect:/organization";
    }
    
    @GetMapping("viewOrganization")
    public String viewOrganization(Integer id, Model model){
        Organization organization = service.getOrgById(id);
        List<Hero> hero = service.getHeroesByOrg(organization);
        model.addAttribute("organization", organization);
        model.addAttribute("hero",hero);
        return "viewOrganization";
    }
}
