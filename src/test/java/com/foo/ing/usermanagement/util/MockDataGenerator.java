package com.foo.ing.usermanagement.util;

import com.foo.ing.usermanagement.model.Address;
import com.foo.ing.usermanagement.model.UserDetails;

public final class MockDataGenerator {

    private MockDataGenerator() {
    }

    public static UserDetails generateOneUserDetails(){
        return UserDetails.builder()
                .userId(1)
                .firstName("test")
                .lastName("testlast")
                .gender("male")
                .title("mr")
                .address(generateOneAddress())
                .build();
    }

    public static Address generateOneAddress(){
        return Address.builder()
                .addressId(1)
                .city("Sydney")
                .postcode("2000")
                .state("NSW")
                .street("12345 holling rd")
                .build();
    }
}
