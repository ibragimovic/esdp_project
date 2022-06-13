package com.esdp.demo_esdp.dto;

import com.esdp.demo_esdp.entity.Localities;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocalitiesDTO {
    public static LocalitiesDTO from(Localities locality){
        return builder()
                .id(locality.getId())
                .name(locality.getName())
                .parent(locality.getParent())
                .build();
    }


    private Long id;
    private String name;
    private Localities parent;
}
