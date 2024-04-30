package com.carsharing.app.model;

import com.carsharing.app.enums.UserTypeEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private int age;
    @Column(nullable = false, unique = true)
    @NonNull
    private String email;

    @Column(nullable = false)
    @NonNull
    private String encryptedPassword;

    @Enumerated(EnumType.STRING)
    @NonNull
    private UserTypeEnum type;
}
