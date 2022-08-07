package com.esdp.demo_esdp.controller;

import com.esdp.demo_esdp.dto.FavoritesJson;
import com.esdp.demo_esdp.exception.ProductNotFoundException;
import com.esdp.demo_esdp.service.FavoritesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FavoritesController {
    private final FavoritesService favoritesService;

    @PostMapping("/fav")
    public ResponseEntity<Void> handleFavorites(@RequestBody FavoritesJson fav) throws ProductNotFoundException {
        favoritesService.handleFavorites(fav);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/fav/delete")
    public ResponseEntity<Void> deleteFav(@RequestBody FavoritesJson f) throws ProductNotFoundException {
        favoritesService.removeFromFavorites(f.getUserEmail(),f.getProductId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
