package com.example.springsecurity_exam.entity;

import com.example.springsecurity_exam.entity.enums.PermissionEnum;
import com.example.springsecurity_exam.entity.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private RoleEnum name; //enum

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<PermissionEnum> permissionEnumSet;
}
