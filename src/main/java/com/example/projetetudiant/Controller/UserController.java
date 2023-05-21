package com.example.projetetudiant.Controller;


import com.example.projetetudiant.entities.categorie;
import com.example.projetetudiant.security.entities.appUser;
import com.example.projetetudiant.security.repository.appUserRep;
import com.example.projetetudiant.security.services.iservice;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@AllArgsConstructor
public class UserController {
    private iservice serviceImpl;
private appUserRep appUserRep;
    @GetMapping("/SignUp")
    public String signUp(Model model) {
        model.addAttribute("appUser", new appUser());
        model.addAttribute("roles", serviceImpl.getAllRoles());
        return "SignUp";
    }

/*
       @PostMapping("/saveUser")
       public String saveUser(Model model, @Valid appUser appUser, BindingResult bindingResult, @RequestParam("role") String role) {
           if (bindingResult.hasErrors()) {
               return "SignUp";
           }

               serviceImpl.addUser(appUser.getUsername(), appUser.getPassword(), appUser.getPassword());
               serviceImpl.addRoleToUser(appUser.getUsername(), role);
           return "redirect:/login";

       }

 */
@PostMapping("/saveUser")
public String saveUser(Model model, @Valid appUser appUser, BindingResult bindingResult,@RequestParam("role") String role) {
    if (bindingResult.hasErrors()) {
        return "SignUp";
    }

    serviceImpl.addUser(appUser.getUsername(), appUser.getPassword(), appUser.getPassword());
    serviceImpl.addRoleToUser(appUser.getUsername(), role);
    return "redirect:/login";

}

    @GetMapping("/listeUser")
    public String listeUser(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "size", defaultValue = "6") int size,
                                 @RequestParam(name = "key", defaultValue = "") String key
    ) {
        Page<appUser> appUserPage = appUserRep.findAllByUsernameContains(key, PageRequest.of(page, size));
        model.addAttribute("pages", appUserPage.getContent());
        model.addAttribute("nbrPages", new int[appUserPage.getTotalPages()]);
        model.addAttribute("key", key);
        model.addAttribute("pageCurrent", page);
        return "listeUser";
    }
    @GetMapping(value = "/deleteUser")
    public String deleteCategorie(String username, int page, String key) {

        serviceImpl.deleteUserAndRoles(username);
        return "redirect:/listeUser?page=" + page + "&key=" + key;
    }



}

