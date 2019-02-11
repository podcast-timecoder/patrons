package com.podcast.patrons;

import java.io.IOException;

public class CampaingNotFoundException extends IOException {

    public CampaingNotFoundException() {
        super("Campaing not found!");
    }

}
