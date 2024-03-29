package com.esdp.demo_esdp.entity;

import com.esdp.demo_esdp.enums.ProductStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

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
    @Size(min = 4)
    private String name;

    @NotNull
    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;


    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @NotBlank
    private String description;

    @NotNull
    @Min(1)
    private Integer price;


    @Builder.Default
    @NotNull
    @Enumerated(EnumType.STRING)
    private ProductStatus status = ProductStatus.MODERNIZATION;

//    @Past
    @NotNull
    @Column(name = "data_add")
    private LocalDateTime dateAdd;



    @NotNull
    @Column(name = "end_of_payment")
    private LocalDateTime endOfPayment;

    @NotNull
    @Column(name = "up_to_top")
    private LocalDateTime up;

    @NotBlank
    private String localities;
}

