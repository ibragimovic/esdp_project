package com.esdp.demo_esdp.controller;

import com.esdp.demo_esdp.dto.FavoritesDTO;
import com.esdp.demo_esdp.exception.ProductNotFoundException;
import com.esdp.demo_esdp.service.FavoritesService;
import com.esdp.demo_esdp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class FavoritesPageController {
    public final FavoritesService favoritesService;
    public final UserService userService;
    static final String FAVORITES_ID = "_favorites_";

    @GetMapping("/favorites")
    public String favoritesGet(Model model, Principal principal) throws ProductNotFoundException {
        String email;
        if (principal instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) principal;
            email = oAuth2AuthenticationToken.getPrincipal().getAttribute("email");
        } else {
            email = principal.getName();
        }
        var user = userService.getByEmail(email);
        var userFavorites = favoritesService.getFavoritesUser(user.getEmail());
        if (!userFavorites.isEmpty()) {
            model.addAttribute("favoritesList", userFavorites);
        }
        return "favorites";
    }
}
