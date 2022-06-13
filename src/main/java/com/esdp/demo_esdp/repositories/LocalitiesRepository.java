package com.esdp.demo_esdp.repositories;

import com.esdp.demo_esdp.entity.Localities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalitiesRepository extends JpaRepository<Localities,Long> {
}
