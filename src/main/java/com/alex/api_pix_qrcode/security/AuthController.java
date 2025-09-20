package com.alex.api_pix_qrcode.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            @RequestParam(value = "success", required = false) String success,
                            Model model) {
        if (error != null) {
            model.addAttribute("error", "Credenciais inválidas!");
        }
        if (logout != null) {
            model.addAttribute("message", "Logout realizado com sucesso!");
        }
        if (success != null) {
            model.addAttribute("message", "Usuário registrado com sucesso! Faça login.");
        }
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        // Adiciona atributos vazios para evitar erros no Thymeleaf
        model.addAttribute("error", "");
        model.addAttribute("message", "");
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String email,
                               Model model) {

        // Validações
        if (username == null || username.trim().isEmpty()) {
            model.addAttribute("error", "Username é obrigatório!");
            return "register";
        }

        if (password == null || password.length() < 6) {
            model.addAttribute("error", "Senha deve ter pelo menos 6 caracteres!");
            model.addAttribute("username", username);
            model.addAttribute("email", email);
            return "register";
        }

        if (email == null || !email.contains("@")) {
            model.addAttribute("error", "Email inválido!");
            model.addAttribute("username", username);
            return "register";
        }

        // Verifica se usuário já existe
        if (userRepository.existsByUsername(username)) {
            model.addAttribute("error", "Usuário já existe!");
            model.addAttribute("email", email);
            return "register";
        }

        // Verifica se email já existe
        if (userRepository.existsByEmail(email)) {
            model.addAttribute("error", "Email já cadastrado!");
            model.addAttribute("username", username);
            return "register";
        }

        // Cria novo usuário
        User newUser = new User(
                username,
                passwordEncoder.encode(password),
                email,
                Arrays.asList("ROLE_USER") // ✅ Corrigido para ROLE_USER
        );

        userRepository.save(newUser);

        return "redirect:/login?success=true";
    }
}