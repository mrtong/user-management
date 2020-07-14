package com.foo.ing.usermanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "addresses")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressId;

    private String street;
    private String city;
    private String state;
    private String postcode;
}
