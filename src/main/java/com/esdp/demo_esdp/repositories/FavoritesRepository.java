package com.esdp.demo_esdp.repositories;

import com.esdp.demo_esdp.entity.Favorites;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoritesRepository extends JpaRepository<Favorites,Long> {
}
