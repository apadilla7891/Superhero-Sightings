package com.ap.Superhero.controller;

import com.ap.Superhero.dto.Superpower;
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
public class SuperpowerController {
    
    @Autowired
    SuperService service;
    
    

    Set<ConstraintViolation<Superpower>> violations = new HashSet<>();


    
    @GetMapping("superpower")
    public String displayPowers(Model model){
        List<Superpower> superpower = service.getAllPowers();
        model.addAttribute("superpower", superpower);
        model.addAttribute("errors", violations);

        return "superpower";
    }
    
    @PostMapping("addSuperpower")
    public String addSuperpower(HttpServletRequest request){
        String name = request.getParameter("PowerName");
        String description = request.getParameter("Description");
        
        Superpower superpower = new Superpower();
        superpower.setName(name);
        superpower.setDescription(description);
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(superpower);
        if(violations.isEmpty()){
            service.addPower(superpower);
        }
        
        return "redirect:/superpower";
    }
    
    @GetMapping("deleteSuperpower")
    public String deleteSuperpower(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        service.deletePowerById(id);
        
        return "redirect:/superpower";
    }

    @GetMapping("editSuperpower")
    public String editSuperpower(Integer id, Model model){
        Superpower superpower = service.getPowerById(id);  
        model.addAttribute("superpower", superpower);
        return "editSuperpower";
    }
    

    @PostMapping("editSuperpower")
    public String performEditSuperpower(HttpServletRequest request, Model model){        
        int id = Integer.parseInt(request.getParameter("id"));
        Superpower superpower = service.getPowerById(id);
        superpower.setName(request.getParameter("PowerName"));
        superpower.setDescription(request.getParameter("Description"));
        
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(superpower);
        if(violations.isEmpty()){
            service.updatePower(superpower);
            return "redirect:/superpower";
        }else{
            superpower = service.getPowerById(superpower.getId());
            model.addAttribute("superpower", superpower);
            model.addAttribute("errors", violations);
            return "editSuperpower";
        }
    }

}
