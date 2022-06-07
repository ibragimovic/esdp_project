package com.esdp.demo_esdp.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "products")
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;


    @NotNull
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    @NotBlank
    private String description;

    @NotNull
    @Min(1)
    private Integer price;
}
