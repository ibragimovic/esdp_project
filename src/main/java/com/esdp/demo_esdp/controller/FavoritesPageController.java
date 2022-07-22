package com.esdp.demo_esdp.controller;

import com.esdp.demo_esdp.dto.FavoritesDTO;
import com.esdp.demo_esdp.exception.ProductNotFoundException;
import com.esdp.demo_esdp.service.FavoritesService;
import com.esdp.demo_esdp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class FavoritesPageController {
    public final FavoritesService favoritesService;
    public final UserService userService;
    static final String FAVORITES_ID = "_favorites_";

    @GetMapping("/favorites")
    public String favoritesGet(Model model,Authentication authentication) throws ProductNotFoundException {
        var user = userService.getEmailFromAuthentication(authentication);
        var userFavorites = favoritesService.getFavoritesUser(user);
        if (!userFavorites.isEmpty()) {
            model.addAttribute("favoritesList", userFavorites);
        }
        return "favorites";
    }
}
