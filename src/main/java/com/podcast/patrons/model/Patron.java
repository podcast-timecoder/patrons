package com.podcast.patrons.model;

import lombok.Data;

@Data
public class Patron {

    private long id;
    private String fullName;
    private int amount;
    private int totalAmount;
    private boolean isActive;
    private String email;

}
