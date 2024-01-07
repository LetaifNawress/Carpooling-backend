package com.javatechie.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "userTab")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserApp {
    @Id
    private String id;

    private String name;
    private String email;
    private String password;

    @Lob
    private byte[] profileImage;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
