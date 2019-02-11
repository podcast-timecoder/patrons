package com.podcast.patrons.service;

import com.patreon.PatreonAPI;
import com.patreon.resources.Campaign;
import com.patreon.resources.Pledge;
import com.podcast.patrons.CampaingNotFoundException;
import com.podcast.patrons.model.Patron;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PatronService {

    private static final String AUTOMATION_REMARKS_CAMPAIGN = "automation-remarks.com";
    private final PatreonAPI patreonAPI;

    public List<Patron> getAllPatrons() throws IOException {
        Campaign campaignAutomationRemarks  = getCampaign(patreonAPI);
        List<Pledge> pledgeList = patreonAPI.fetchAllPledges(campaignAutomationRemarks.getId());
        return pledgeList
                .stream()
                .map(this::createPatron)
                .filter(Patron::isActive)
                .collect(Collectors.toList());
    }

    private Campaign getCampaign(PatreonAPI patreonAPI) throws IOException {
        return patreonAPI
                .fetchCampaigns().get()
                .stream()
                .filter(campaign -> campaign.getCreationName().equals(AUTOMATION_REMARKS_CAMPAIGN))
                .findFirst().orElseThrow(CampaingNotFoundException::new);
    }

    private Patron createPatron(Pledge pledge) {
        Patron patron = new Patron();
        patron.setId(Long.parseLong(pledge.getPatron().getId()));
        patron.setFullName(pledge.getPatron().getFullName());
        patron.setAmount(pledge.getAmountCents());
        if (pledge.getDeclinedSince() == null) {
            patron.setActive(true);
        }
        patron.setEmail(pledge.getPatron().getEmail());

        return patron;
    }
}
