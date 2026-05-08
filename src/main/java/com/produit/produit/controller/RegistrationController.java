package com.produit.produit.controller;

import com.produit.produit.entity.User;
import com.produit.produit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult bindingResult,
                               @RequestParam("confirmPassword") String confirmPassword,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        // Vérifier si le nom d'utilisateur existe déjà
        if (userService.usernameExists(user.getUsername())) {
            bindingResult.rejectValue("username", "error.user", "Ce nom d'utilisateur est déjà pris");
        }

        // Vérifier si l'email existe déjà
        if (userService.emailExists(user.getEmail())) {
            bindingResult.rejectValue("email", "error.user", "Cet email est déjà utilisé");
        }

        // Vérifier la correspondance des mots de passe
        if (!user.getPassword().equals(confirmPassword)) {
            bindingResult.rejectValue("password", "error.user", "Les mots de passe ne correspondent pas");
        }

        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            userService.registerNewUser(user);
            redirectAttributes.addFlashAttribute("successMessage", "Inscription réussie ! Vous pouvez maintenant vous connecter.");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Erreur lors de l'inscription : " + e.getMessage());
            return "register";
        }
    }
}