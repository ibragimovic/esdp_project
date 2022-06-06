package com.esdp.demo_esdp.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "favorites")
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Favorites {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @NotNull
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
