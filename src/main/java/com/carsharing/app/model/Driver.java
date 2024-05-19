package com.carsharing.app.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Driver {
    @Id

    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private float rating;
    private String phoneNumber;
    private String bio;

// Changed to ManyToOne due to infinite loop in json output of rides on /api/drivers/{id}/rides
//    @OneToOne(fetch = FetchType.LAZY)
//    @JsonManagedReference
//    private Car car;
}
