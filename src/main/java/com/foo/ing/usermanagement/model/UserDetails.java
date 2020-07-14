package com.foo.ing.usermanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "user_details")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetails implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer userId;

    private String title;

    //I do not know why in the requirement it is this.
    @Column(name = "first_name")
    @JsonProperty("firstn")
    private String firstName;

    @Column(name = "last_name")
    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("gender")
    private String gender;

    @OneToOne(cascade = {PERSIST, REMOVE, MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name="address_id")
    private Address address;
}
