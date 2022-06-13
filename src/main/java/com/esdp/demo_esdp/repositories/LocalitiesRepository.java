package com.esdp.demo_esdp.repositories;

import com.esdp.demo_esdp.entity.Images;
import com.esdp.demo_esdp.entity.Localities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalitiesRepository extends JpaRepository<Localities,Long> {

//    @Query("select l from Localities l where l.parent is not null")
//    List<Localities> findAllChildren();

}
