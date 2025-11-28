package com.agmkhair.ecom.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@Setter
@Getter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String title;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "sub_parent_id")
    private Long subParentId;

    @Column(name = "priority")
    private Long priority;

}
