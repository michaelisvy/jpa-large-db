package com.samples;

import lombok.Data;

import javax.persistence.*;

@Data @Entity
// purposely disabled indexes so we could observe behaviour with slow queries
//@Table(indexes = {@Index(name = "last_name_index",  columnList="lastname", unique = true)})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String field1 = "aaaaaaaaaaaa";
    private String field2 = "aaaaaaaaaaaa";
    private String field3 = "aaaaaaaaaaaa";
    private String field4 = "aaaaaaaaaaaa";
    private String field5 = "aaaaaaaaaaaa";
    private String field6 = "aaaaaaaaaaaa";
    private String field7 = "aaaaaaaaaaaa";
    private String field8 = "aaaaaaaaaaaa";
    private String field9 = "aaaaaaaaaaaa";
    private String field10 = "aaaaaaaaaaaa";
    private String field11 = "aaaaaaaaaaaa";

    @Version
    private int version;

    public Customer() {
    }

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
