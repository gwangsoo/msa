package com.example.shoppingmall.user.entity;

import com.example.shoppingmall.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity {

    private String username;
    private String email;
    private String password; // This will be managed by Keycloak
    private String firstName;
    private String lastName;

}
