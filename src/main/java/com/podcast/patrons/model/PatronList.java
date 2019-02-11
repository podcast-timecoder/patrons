package com.podcast.patrons.model;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PatronList {

    private int totalAmount;
    private List<Patron> patrons;

}
